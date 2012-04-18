package com.hao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 关于触屏事件和按键事件在自定义View中的实现
 * @author Administrator
 *
 */
public class TouchView extends View{

	private Paint p;
	private int bmp_y = 0;
	private int bmp_x = 0;
	public TouchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		/**
		 * 在surfaceview中我们的onKeyDown 虽然是重写了view的函数，但是仍然需要在初始化的时候去声明获取焦点，
		 * setFocusable(true); 如果不调用此方法，那么会造成按键无效。原因是因为如果是自己定义一个继承自View的类,
		 * 重新实现onKeyDown方法后,只有当该View获得焦点时才会调用onKeyDown方法,Actvity中的onKeyDown方法是当
		 * 所有控件均没有处理该按键事件时,才会调用.
		 */
		System.out.println("----"+isFocused()+","+isFocusable()+","+isFocusableInTouchMode());
		setFocusable(true);//设置它之后才会调用onFocusChanged
		setFocusableInTouchMode(true);//可接收触摸焦点
		System.out.println("----"+isFocused()+","+isFocusable()+","+isFocusableInTouchMode());
		p = new Paint();
		p.setColor(Color.RED);
		p.setAntiAlias(true);
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawText("你好啊", 0, 3, getWidth()/2, getHeight()/2, p);
	}



	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		Log.v("test", "onFocusChanged");  
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.v("test", "onKeyDown");  
        bmp_x++;
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 触屏响应的函数，onTouchEvent()！ 重写此函数的时候默认最后一句是依照基类的返回方式，
	 * return super.onTouchEvent(event)； 然后我们在其中去判定 MotionEvent.ACTION_MOVE、MotionEvent.ACTION_DOWN、
	 * MotionEvent.ACTION_UP 相对应触屏操作的 拖动、按下、抬起；对此一切都是正确的，
	 * 但是真正的的运行起项目的时候发现 Log.v("Himi", "ACTION_MOVE"); 这里log的"ACTION_MOVE"，永远不会执行！！！
	 * 为此我找到了解决方法，那么先解释下为什么会出现此类情况。解释：onTouchEvent()，预设使用Oeverride这个方法，
	 * 通常情況下去呼叫super.onTouchEvent()并传回布林值。但是这里要注意一点，预设如果去呼叫super.onTouchEvent()
	 * 則很有可能super里面并没做任何事，并且回传false回來，一旦回传false回來，很可能后面的event (例如：Action_Move、Action_Up) 
	 * 都会收不到了，所以为了确保保后面event能順利收到，要注意是否要直接呼super.TouchEvent()。
	 * 
	 * 这个问题也是当时用到此函数的时候发现的，找了很多资料才找到其解释、所以以后使用onTouchEvent（）函数的时候最后的
	 * return super.onTouchEvent(event);一定要改为return true;
	 * 
	 * 最后还要注意一点：在初始化的时候不要忘记setFocusableInTouchMode（true）;触屏模式获取焦点，
	 * 比较类似 setFocusable(true);——setFocusable(true);//此方法是用来响应按键！
	 * 如果是自己定义一个继承自View的类,重新实现onKeyDown方法后,只有当该View获得焦点时才会调用onKeyDown方法,
	 * Actvity中的onKeyDown方法是当所有控件均没有处理该按键事件时,才会调用.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("test", "onTouchEvent");  
        bmp_y++;  
        if (event.getAction() == MotionEvent.ACTION_MOVE) {  
            Log.v("Himi", "ACTION_MOVE");  
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {  
            Log.v("Himi", "ACTION_DOWN");  
        } else if (event.getAction() == MotionEvent.ACTION_UP) {  
            Log.v("Himi", "ACTION_UP");  
        }  
        return true; 
//		return super.onTouchEvent(event);
	}
	
	

}
