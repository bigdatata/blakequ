package com.hao;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder sfh;
	private Paint paint;
	private boolean flag = true;
	private Canvas canvas;
	private int screenW, screenH;
	private int r1 = 20, r2 = 20;							//定义两个圆形的半径与坐标
	private int x1 = 50, y1 = 100, x2 = 150, y2 = 100;
	private boolean isCollision;							//定义一个碰撞标识位
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setAntiAlias(true);
		sfh = getHolder();
		sfh.addCallback(this);
		setFocusableInTouchMode(true);
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
		screenH = getHeight();
		screenW = getWidth();
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
			//让圆形1随着触屏位置移动
			x1 = (int) event.getX();
			y1 = (int) event.getY();
			if(isCollisionWithCir(x1, y1, r1, x2, y2, r2)){
				isCollision = true;
			}else{
				isCollision = false;
			}
		}
		return true;
	}
	
	/**
	 * 检测圆形是否碰撞
	 * @param x1
	 * @param y1
	 * @param r1
	 * @param x2
	 * @param y2
	 * @param r2
	 * @return
	 */
	private boolean isCollisionWithCir(int x1, int y1, int r1, int x2, int y2, int r2){
		float length = (float) Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		if(length >= r1+r2){
			return false;
		}
		return true;
	}

	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				if(isCollision){
					paint.setColor(Color.RED);
					paint.setTextSize(20);
					canvas.drawText("Collision！", 0, 30, paint);
				}else{
					paint.setColor(Color.BLACK);
				}
				canvas.drawCircle(x1, y1, r1, paint);
				canvas.drawCircle(x2, y2, r2, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(canvas != null){
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
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
