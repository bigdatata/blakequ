package com.hao.view;

import com.hao.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationSurfaceView extends SurfaceView implements Callback, Runnable{
	private boolean flag = true;
	private Canvas canvas;
	private SurfaceHolder sh;
	private Paint paint;    
    private Bitmap bmp;    
    private int x = 20;    
    private int move_x = 2;
    private Thread thread;
    public static AnimationSurfaceView view;
	public AnimationSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		view = this;
		sh = getHolder();
		sh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		thread = new Thread(this);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//		this.setFocusable(true);//只有当该View获得焦点时才会调用onKeyDown方法
	}
	
	private void onDraw() {
		// TODO Auto-generated method stub
		canvas = sh.lockCanvas();
		if(canvas!=null){  
            canvas.drawColor(Color.WHITE);  
            canvas.drawText("我是   - Surfaceview", x + move_x, getHeight()*5/6, paint);  
            canvas.drawText("你好nnnnnnnnnnnnnnnnnnn", 0, getHeight()-10, paint);
            sh.unlockCanvasAndPost(canvas);  
        }   
	}
	
	private void logic() {    
        x += move_x;  
        if (x > 200 || x < 80) {  
            move_x = -move_x;  
        }  
    }  

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.v("AnimationSurfaceView", "onKeyDown");
		return super.onKeyDown(keyCode, event);
	}

	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("AnimationSurfaceView", "onTouchEvent");
//		return true;
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("AnimationSurfaceView", "surfaceCreated");
		flag = true;
		thread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			onDraw();
			logic();
			try {
				Thread.sleep(1000);
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
		Log.v("AnimationSurfaceView", "surfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("AnimationSurfaceView", "surfaceDestroyed");
		flag = false;
	}



}
