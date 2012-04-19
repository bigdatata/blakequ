package com.hao;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private Paint paint;
	private Canvas canvas;
	private SurfaceHolder sfh;
	private boolean flag = true;//增加了一个布尔值的成员变量 ,这里可以控制我们的线程消亡的一个开关！
	private Bitmap bmp;
	private Thread thread;
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setAntiAlias(true);
		sfh = getHolder();
		sfh.addCallback(this);
		setFocusable(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.himi_dream);
		/**
		 * 为什么我们点击Back按钮不异常、点击Home会异常了！
		 * 原因：因为点击Back按钮再次进入程序的时候先进入的是view构造函数里，
		 * 那么就是说这里又new了一个线程出来，并启动！
		 * 
		 * 那么而我们点击Home却不一样了,
		 * 因为点击home之后再次进入程序不会进入构造函数，而是直接进入了view创建这个函数surfaceCreated，
		 * 而在view创建这个函数中我们有个启动线程的操作，其实第一次启动程序的线程还在运行，
		 * so~这里就一定异常了，说线程已经启动！
		 */
//		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.v("MySurfaceView", "surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("MySurfaceView", "surfaceCreated");
		flag = true;//在启动线程之前，设置这个布尔值为ture，让线程一直运行.
		new Thread(this).start();//*****这样在通过home进入时重新启动的线程，故而不会在出现错误
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("MySurfaceView", "surfaceDestroyed");
		flag = false;//在view销毁时，设置这个布尔值为false，销毁当前线程！
	}

	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(color.black);
				canvas.drawBitmap(bmp, 0, 0, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("MySurfaceView", "draw is error!");
		}finally{
			if(canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){//在view销毁时，设置这个布尔值为false，销毁当前线程！
			draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
