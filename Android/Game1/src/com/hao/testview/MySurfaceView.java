package com.hao.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback ,Runnable{

	private SurfaceHolder sfh;
	private Paint paint;
	private Thread thread;
	private Canvas canvas;
	private int mWidth, mHight;
	private Boolean flag = true;
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		sfh = this.getHolder();
		sfh.addCallback(this);//就相当于注册回调函数，自动调用surfaceChanged等三个方法
		thread = new Thread(this);
		this.setKeepScreenOn(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//只有在surface create的时候才能获取高宽,在构造函数中肯定是0,view还没初始化
		mWidth = this.getWidth();
		mHight = this.getHeight();
		System.out.println("width:"+mWidth+" ,height:"+mHight);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

	/**
	 * unlockCanvas() 和 lockCanvas()中Surface的内容是不缓存的，
	 * 所以需要完全重绘Surface的内容，为了提高效率只重绘变化的部分则可以调用lockCanvas(Rect rect)
	 * 函数来指定一个rect区域，这样该区域外的内容会缓存起来。在调用lockCanvas函数获取Canvas后，
	 * SurfaceView会获取Surface的一个同步锁直到调用unlockCanvasAndPost(Canvas canvas)函数才释放该锁，
	 * 这里的同步机制保证在Surface绘制过程中不会被改变（被摧毁、修改）。
	 * 
	 * *******************
	 * 这里我把draw的代码都try起来，主要是为了当画的内容中一旦抛出异常了，
	 * 那么我们也能 在finally中执行该操作。这样当代码抛出异常的时候不会导致Surface出去不一致的状态。
	 */
	public void draw() {
		// TODO Auto-generated method stub
		try {
			System.out.println("-- paint ----");
			canvas = sfh.lockCanvas();// 得到一个canvas实例
			canvas.drawColor(Color.WHITE);// 刷屏    
			canvas.drawText("Himi", 100, 100, paint);// 画文字文本    
			canvas.drawText("这就是简单的一个游戏框架", 100, 130, paint); 
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(canvas != null)
				sfh.unlockCanvasAndPost(canvas);// 将画好的画布提交
		}
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

}
