package com.hao.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 自定义surfaceView可以在xml中配置
 * 但是必须要有构造函数2************
 * @author Administrator
 *
 */
public class MySurfaceView2 extends SurfaceView implements Callback, Runnable {


	public static String text;
	private Paint paint,backPaint;
	private SurfaceHolder holder;
	private Thread thread;
	private int loc = 0;
	private int mWidth, mHeight;
	private boolean flag = true;
	public MySurfaceView2(Context context){
		super(context);
	}
	/**
	 * 构造函数2
	 * 如果是new出来的此类实例肯定是没有问题，但是我们为了能在显示SurfaceView同时显示别的组件，所以把自定义的SurfaceView也当作组件注册在了main——xml中，
	 * 所以这里需要注意，当在xml中注册的就必须加上这种初始化方法， 初始化的时候会调用这个方法
	 * 当时这个问题困扰了一天的研究时间，最后在一个群友的帮助下才发现是这里出了问题
	 * 那么第二个参数指的自定义的组件的一些属性,就像长宽一样，你可以给组件属性,就是通过这个来传递的
	 * @param context
	 * @param attrs
	 */
	public MySurfaceView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		backPaint = new Paint();
		backPaint.setAntiAlias(true);
		backPaint.setColor(Color.GRAY);
		holder = getHolder();
		holder.addCallback(this);
		text = "this is my first surfaceview from xml!";
		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mWidth = getWidth();
		mHeight = getHeight();
		flag = true;
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}
	
	private void draw(){
		Canvas canvas = holder.lockCanvas();
		canvas.drawRect(0, 0, mWidth, mHeight, backPaint);
		canvas.drawText(text, mWidth/4, loc, paint);
		holder.unlockCanvasAndPost(canvas);
	}
	
	/**
	 * 逻辑处理函数
	 */
	private void logic(){
		if(loc >= mHeight){
			loc = 0;
		}else{
			loc += 20;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			draw();
			logic();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
