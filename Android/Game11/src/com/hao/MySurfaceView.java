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
	private Canvas canvas;
	private Paint paint;
	private boolean flag = true;
	private int rockerCircleX = 100;							//固定摇杆背景圆形的X坐标
	private int rockerCircleY = 100;							//固定摇杆背景圆形的y坐标
	private int rockerCircleR = 50;								//固定摇杆背景圆形的半径
	private float smallRockerCircleX = 100;						//摇杆的X坐标
	private float smallRockerCircleY = 100;						//摇杆的Y坐标
	private float smallRockerCircleR = 20;						//摇杆的半径
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
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}
	
	/**
	 * 计算两点在屏幕坐标系中的角度（见摇杆计算图示）
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private double getRad(float x1, float y1, float x2, float y2){
		float x = x2 - x1;										//两点间x的距离
		float y = y2 - y1;										//两点间Y的距离
		float len = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); //算出斜边长
		float rad = (float) Math.acos(x/len);					//根据余弦定理求角度的弧度	
		if(y1 > y2) rad = -rad;									//注意：当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
		return rad;
	}
	
	/**
	 * 根据弧度获取摇杆的坐标
	 * @param centerX 摇杆背景x坐标
	 * @param centerY 摇杆背景y坐标
	 * @param R		  摇杆半径R
	 * @param rad	 弧度
	 */
	private void getSmallRockerXY(float centerX, float centerY, float R, double rad){
		smallRockerCircleX = (float) (R*Math.cos(rad) + centerX);
		smallRockerCircleY = (float) (R*Math.sin(rad) + centerY);
	}
	
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				//设置透明度  
	            paint.setColor(0x70000000);
	            canvas.drawCircle(rockerCircleX, rockerCircleY, rockerCircleR, paint);
	            paint.setColor(0x70ff0000);
	            canvas.drawCircle(smallRockerCircleX, smallRockerCircleY, smallRockerCircleR, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
			float len = (float) Math.sqrt(Math.pow(rockerCircleX - (int)event.getX(), 2) + 
						Math.pow(rockerCircleY - (int)event.getY(), 2));	//计算触摸点与摇杆背景中心点距离
			//当触屏区域不在活动范围内
			if(len > rockerCircleR){
				double rad = getRad(rockerCircleX, rockerCircleY, event.getX(), event.getY());//得到摇杆与触屏点所形成的角度
				getSmallRockerXY(rockerCircleX, rockerCircleY, rockerCircleR, rad); 		  //保证内部小圆运动的长度限制
			}else{
				//如果小球中心点小于活动区域则随着用户触屏点移动即可
				smallRockerCircleX = event.getX();
				smallRockerCircleY = event.getY();
			}
			//当释放按键时摇杆要恢复摇杆的位置为初始位置
			if(event.getAction() == MotionEvent.ACTION_UP){
				smallRockerCircleX = 100;
				smallRockerCircleY = 100;
			}
		}
		return true;
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
