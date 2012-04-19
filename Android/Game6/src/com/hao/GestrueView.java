package com.hao;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnTouchListener;

/**
 *  总结：
 *   1.触屏后、一直触屏不动、演变顺序：onDown->onShowPress->onLongPress；
 *   2.触屏后、一直触屏慢移动是onScroll/快移动是onFling 、手指离开屏幕；
 *   注意 ：触屏后、一直触屏移动，如果手指不离开屏幕一直都是onScroll,不管你移动的速度多快,永远不会是onFling！
 * @author Administrator
 *
 */
public class GestrueView extends SurfaceView implements Runnable, Callback, 
		OnGestureListener, OnTouchListener{
	private Thread th = new Thread(this);  
    private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private Bitmap bmp;  
    private GestureDetector gd;  
    private int bmp_x, bmp_y;  
    private boolean isChagePage;  
    private Vector<String> v_str;// 备注1
    private boolean flag = true;
	public GestrueView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		v_str = new Vector<String>();
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.himi_dream);
		gd = new GestureDetector(this);
		this.setOnTouchListener(this);
		gd.setIsLongpressEnabled(true);
		// setLongClickable( true )是必须的，因为 只有这样，  
        // 我们当前的SurfaceView(view)才能够处理不同于触屏形式;  
        // 例如：ACTION_MOVE，或者多个ACTION_DOWN  
		this.setLongClickable(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			this.draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 当系统调用了此方法才创建了view所以在这里才能取到view的宽高！！有些童鞋总是把东西都放在初始化函数里！  
	 * 线程最好放在这里来启动，因为放在初始化里的画，那view还没有呢,到了提交画布unlockCanvasAndPost的时候就异常啦！  
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		bmp_x = (getWidth() - bmp.getWidth())>>2;
		bmp_y = (getHeight()- bmp.getHeight())>>2;
		flag = true;
		th.start();
	}
	
	public void draw() {  
        try {  
            canvas = sfh.lockCanvas();  
            if (canvas != null) {  
                canvas.drawColor(Color.WHITE);// 画布刷屏  
                canvas.drawBitmap(bmp, bmp_x, bmp_y, paint);   
                paint.setTextSize(20);// 设置文字大小   
                paint.setColor(Color.WHITE);  
                //这里画出一个矩形方便童鞋们看到手势操作调用的函数都是哪些  
                canvas.drawRect(50, 30, 175,120, paint);  
                paint.setColor(Color.RED);// 设置文字颜色  
                if (v_str != null) {  
                    for (int i = 0; i < v_str.size(); i++) {  
                        canvas.drawText(v_str.elementAt(i), 50, 50 + i * 30,  
                                paint);  
                    }  
                }  
            }  
        } catch (Exception e) {  
            Log.v("Himi", "draw is Error!");  
        } finally {  
            sfh.unlockCanvasAndPost(canvas);  
        }  
    }  

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

	// --------------以下是使用OnGestureListener手势监听的时候重写的函数---------  
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onDown");
		return false;
	}

	/**  
     * @以下方法中的参数解释：  
     * @e1：第1个是 ACTION_DOWN MotionEvent 按下的动作  
     * @e2：后一个是ACTION_UP MotionEvent 抬起的动作(这里要看下备注5的解释)  
     * @velocityX：X轴上的移动速度，像素/秒  
     * @velocityY：Y轴上的移动速度，像素/秒  
     */
	 // ACTION_DOWN 、快滑动、 ACTION_UP
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
//		System.out.println("-----------length:"+(e1.getX()-e2.getX()));
		v_str.add("onFling");  
        //-------备注5----------  
		/**
		 * 测试结果：第一个是MotionEvent.ACTION_DOWN 第二个是MotionEvent.ACTION_UP!
		 * 这两个动作知道用户到底滑动的距离等等了，其距离e2.getX()-e1.getX()
		 */
         if(e1.getAction()==MotionEvent.ACTION_MOVE){  
        	 v_str.add("onFling move");  
         }else if(e1.getAction()==MotionEvent.ACTION_DOWN){  
        	 v_str.add("onFling down");  
         }else if(e1.getAction()==MotionEvent.ACTION_UP){  
        	 v_str.add("onFling up");  
         }  
         if(e2.getAction()==MotionEvent.ACTION_MOVE){  
        	 v_str.add("onFling move");  
         }else if(e2.getAction()==MotionEvent.ACTION_DOWN){  
        	 v_str.add("onFling down");  
         }else if(e2.getAction()==MotionEvent.ACTION_UP){  
        	 v_str.add("onFling up");  
         }  
        if (isChagePage)  
            bmp = BitmapFactory.decodeResource(getResources(),  
                    R.drawable.himi_dream);  
        else  
            bmp = BitmapFactory.decodeResource(getResources(),  
                    R.drawable.himi_warm);  
        isChagePage = !isChagePage; 
		return false;
	}

	// ACTION_DOWN 、长按不滑动
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onLongPress");
	}

	// ACTION_DOWN 、慢滑动
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
//		System.out.println("-----------length:"+(e1.getX()-e2.getX()));
		v_str.add("onScroll");
		return false;
	}

	// ACTION_DOWN 、短按不移动 
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onShowPress");
	}

	// 短按ACTION_DOWN、ACTION_UP
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onSingleTapUp");
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v_str != null)  
            v_str.removeAllElements();
		return gd.onTouchEvent(event);
	}

}
