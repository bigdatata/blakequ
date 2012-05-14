package cn.anycall.ju;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的类
 * 下面用到了
 * scrollBy(deltaX, 0); 是移动到指定的位置(x,y)
 * scrollTo(deltaX, 0);	是设置移动的位置(x,y)
 * 是完全不同的
 * 如果将scrollTo全部删除，则会滑多远则View就会移动多远
 */
public class ScrollLayout extends ViewGroup {

	private static final String TAG = "ScrollLayout";
	private Scroller mScroller;
	/*
	 * VelocityTracker就是速度跟踪的意思。我们可以获得触摸点的坐标，根据按下的时间可以简单的计算出速度的大小。
		Android直接提供了一种方式来方便我们获得触摸的速度。
		第一步的话，当然是得到该类的一个实例 mVelocityTracker = VelocityTracker.obtain()；
		第二步，需要告诉mVelocityTracker 这个对象你要对那个MotionEvent 进行监控（也就是说得到在那个MotionEvent
		上的速度） 对于MotionEvent 的解释文档是这么说的 {对象，用于报告运动（鼠标，笔，手指，轨迹球）事件。这个类可能持有绝对或相对运动}使用
		mVelocityTracker.addMovement(ev); 方法来制定一个MotionEvent。
	 */
	private VelocityTracker mVelocityTracker;

	private int mCurScreen;
	private int mDefaultScreen = 0;
	
	private OnScreenChangeListener onScreenChangeListener;
	private OnScreenChangeListenerDataLoad onScreenChangeListenerDataLoad;
	private int mTouchState = TOUCH_STATE_REST;//手势状态
	private static final int TOUCH_STATE_REST = 0;//静止，未滑动状态
	private static final int TOUCH_STATE_SCROLLING = 1;//滑动状态

	private static final int SNAP_VELOCITY = 600;

	private int mTouchSlop;
	private float mLastMotionX;
	private float mLastMotionY;

	private int currentScreenIndex = 0;

	public int getCurrentScreenIndex() {
		return currentScreenIndex;
	}

	public void setCurrentScreenIndex(int currentScreenIndex) {
		this.currentScreenIndex = currentScreenIndex;
	}

	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(context);

		mCurScreen = mDefaultScreen;
		//获取一个我们认为的默认滚动像素
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	//指定child view的大小和位置
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childLeft = 0;
		final int childCount = getChildCount();
		System.out.println("childCount=" + childCount);
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				//初始化显示位置是childView的宽和高
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				//初始化，下一个childView的左边显示位置
				childLeft += childWidth;
			}
		}
	}

	//指定孩子空间的大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.e(TAG, "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		//下面的意思就是孩子空间不能指定自己大小(parent设为wrap_content的情况下)
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only can run at EXACTLY mode!");
		}

		// The children are given the same width and height as the scrollLayout
		//viewGroup中的所有view
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			//由于上面已经指定了只能由父空间来决定孩子空间的大小，故而这里可以这么做，由父空间来决定高宽
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		System.out.println("moving to screen " + mCurScreen);
		//将回调onScrollChanged，并刷新view,设置移动滚动的位置
		scrollTo(mCurScreen * width, 0);
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * page.移动到最后的页
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();//也就是屏幕宽度，每次都一样
		//这个getScrollX()是最开始的位置，第0页算起, 这样得到的结果destScreen就是第几屏是整数0开始
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	/*
	 * 移动到指定屏幕
	 */
	public void snapToScreen(int whichScreen) {
		// get the valid layout page，这里保证不会超出显示范围，0<=whichScreen<=getChildCount-1
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		//getScrollX()他的计算方式是从第0页到当前页总得X轴长度
		//判断是不是在当前页,如果不是在移动，是当然就不用了
		if (getScrollX() != (whichScreen * getWidth())) {
			// 要移动的X轴长度 
			final int delta = whichScreen * getWidth() - getScrollX();
			//移动到指定位置
			mScroller.startScroll(getScrollX(), 0, delta, 0,
					Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			onScreenChangeListener.onScreenChange(mCurScreen);
			invalidate(); // Redraw the layout
		}
	}

	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		//设置移动滚动的位置
		scrollTo(whichScreen * getWidth(), 0);
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		//这个方法会及时更新滑动后的values for mScrollX and mScrollY ，但须使用 Scroller object作为滑动控制
		if (mScroller.computeScrollOffset()) {
			System.out.println("computeScroll set3");
			//设置将要移动的位置
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			System.out.println("("+mScroller.getCurrX()+","+mScroller.getCurrY()+")");
			//更新UI线程，更新View(会调用onDraw()方法),如果是在UI线程，直接invalidate();
//			postInvalidate();//the view will be invalidated这个调用scrollTo就会做，故而不需要
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//速度跟踪的意思。我们可以获得触摸点的坐标，根据按下的时间可以简单的计算出速度的大小
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		//将触控的时间添加到里面
		mVelocityTracker.addMovement(event);
		//得到是那种类型的触摸动作UP,DOWN ...
		final int action = event.getAction();
		//得到初始点击的位置
		final float x = event.getX();
		final float y = event.getY();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "onTouchEvent down!");
			//如果滑动的时候动画还没结束，就停止
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			//滑动后的位置
			mLastMotionX = x;
			break;
		//正在移动
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "onTouchEvent move!");
			//得到移动的位置大小
			int deltaX = (int) (mLastMotionX - x);
			mLastMotionX = x;
			//滑动的时候，view也要滑动,注意只是滑动，确定位置还需要snapToScreen
			//这个才是view滑动，就是拖动时候的效果，上面的scrollTo都是设置
			//************如果没有这个是不会滑动的********************
			scrollBy(deltaX, 0);//**************
			System.out.println("("+mScroller.getCurrX()+","+mScroller.getCurrY()+")"+" deltax:"+deltaX);
			break;

		case MotionEvent.ACTION_UP:
			Log.e(TAG, "onTouchEvent up!");

			// if (mTouchState == TOUCH_STATE_SCROLLING) {
			final VelocityTracker velocityTracker = mVelocityTracker;
			//VelocityTracker获得的速度是有正负之分，computerCurrentVelocity（）可以设置单位。
			//1000 表示每秒多少像素（pix/second),1代表每微秒多少像素（pix/millisecond)。
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();
			Log.e(TAG, "velocityX:"+velocityX);
			//如果移动速度达到标准就移动,注意右移速度为负数
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				// Fling enough to move left
				Log.e(TAG, "snap left");
				onScreenChangeListener.onScreenChange(mCurScreen - 1);
				snapToScreen(mCurScreen - 1);
			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {
				// Fling enough to move right
				Log.e(TAG, "snap right");
				onScreenChangeListener.onScreenChange(mCurScreen + 1);
				//只往右移动才加载数据
				onScreenChangeListenerDataLoad.onScreenChange(mCurScreen+1);
				snapToScreen(mCurScreen + 1);
			} else {
				Log.e(TAG, "snap destination");
				snapToDestination();
			}

			if (mVelocityTracker != null) {
				//回收以便重用
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// }
			//状态结束
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return true;
	}

	/**
	 * 由于是View是多层(ViewGroup),故而就有父View子View的事件分发机制。
	 * 用于处理事件并改变事件的传递方向,是否要拦截这个事件ev
	 * 返回 true ，也就是拦截掉了，则交给它的 onTouchEvent 来处理，(也就是说在上层View就拦截了该事件不在分发到下层子View中
	 * 而是由自己的监听onTouchEvent来处理)这个函数监听到事件后就将具体的事件分发到onTouchEvent中的具体事件UP,DOWN中,如果 interceptTouchEvent 
	 * 返回 false ，父没有拦截，而是传递给子 view ，手势会向子控件传递,由子 view 的dispatchTouchEvent 再来开始这个事件的分发。
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//拦截有所的屏幕移动事件
		Log.e(TAG, "onInterceptTouchEvent slop:" + mTouchSlop);

		final int action = ev.getAction();
		//正在移动认为是滑动屏幕,就拦截，不传给子控件
		if ((action == MotionEvent.ACTION_MOVE)&& (mTouchState != TOUCH_STATE_REST)) {
			Log.e(TAG, "onInterceptTouchEvent return true(move)");
			return true;
		}
		
		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
			case MotionEvent.ACTION_MOVE:
				Log.e(TAG, "onInterceptTouchEvent move");
				final int xDiff = (int) Math.abs(mLastMotionX - x);
				//当移动的距离大小大于mTouchSlop才认为是移动
				if (xDiff > mTouchSlop) {
					mTouchState = TOUCH_STATE_SCROLLING;
				}
				break;
			case MotionEvent.ACTION_DOWN:
				Log.e(TAG, "onInterceptTouchEvent down");
				mLastMotionX = x;
				mLastMotionY = y;
				mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
						: TOUCH_STATE_SCROLLING;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				Log.e(TAG, "onInterceptTouchEvent up");
				mTouchState = TOUCH_STATE_REST;
				break;
		}
		Log.e(TAG, "onInterceptTouchEvent return "+(mTouchState != TOUCH_STATE_REST));
		//如果是滑动状态就拦截
		return mTouchState != TOUCH_STATE_REST;
	}

	//分页监听
	public interface OnScreenChangeListener {
		void onScreenChange(int currentIndex);
	}


	public void setOnScreenChangeListener(
			OnScreenChangeListener onScreenChangeListener) {
		this.onScreenChangeListener = onScreenChangeListener;
	}
	
	
	//动态数据监听
	public interface OnScreenChangeListenerDataLoad {
		void onScreenChange(int currentIndex);
	}

	public void setOnScreenChangeListenerDataLoad(OnScreenChangeListenerDataLoad onScreenChangeListenerDataLoad) {
		this.onScreenChangeListenerDataLoad = onScreenChangeListenerDataLoad;
	}

}
