package com.hao;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
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

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

    private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private boolean flag;  
    public static int screenW, screenH;  
    private Vector<MyArc> vc;						//这里定义装我们自定义圆形的容器  
    private Random ran;								//随机库
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		vc = new Vector<MyArc>();
		ran = new Random();
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = true;
		screenW = getWidth();
		screenH = getHeight();
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag =  false;
	}
	
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.BLACK);
				if(vc != null && vc.size() != 0){
					for(int i=0; i<vc.size(); i++){
						vc.elementAt(i).drawArc(canvas, paint);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("view", "draw error!");
			e.printStackTrace();
		} finally{
			try {
				if(canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {
				// TODO: handle exception
				Log.v("view", "draw unlockCanvasAndPost error!");
			}
		}
		
	}
	
	/**
	 * 当容器不为空，遍历容器中所有圆形逻辑
	 */
	private void logic(){
		if(vc != null){
			for(int i=0 ;i<vc.size(); i++){
				vc.elementAt(i).arcMoveMethod();
			}
		}
	}
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.v("view", "onKeyDown");
		vc.add(new MyArc(ran.nextInt(this.getWidth()), ran.nextInt(this.getHeight()),
				ran.nextInt(50), screenH));
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("view", "onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			logic();
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
