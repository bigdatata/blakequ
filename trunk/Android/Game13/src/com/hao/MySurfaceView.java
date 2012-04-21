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
	//定义两个矩形的宽高坐标
	private int x1 = 10, y1 = 110, w1 = 40, h1 = 40;
	private int x2 = 100, y2 = 110, w2 = 40, h2 = 40;
	//便于观察是否发生了碰撞设置一个标识位
	private boolean isCollsion = false;
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
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
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
			//让矩形1随着触屏位置移动
			x1 = (int) (event.getX() - w1/2);
			y1 = (int) (event.getY() - h1/2);
			if(isCollsionWithRect(x1, y1, h1, w1, x2, y2, h2, w2)){
				isCollsion = true;
			}else{
				isCollsion = false;
			}
		}
		return true;
	}
	
	/**
	 * 检测两个矩形的碰撞
	 * @param x1 矩形1的x坐标
	 * @param y1 矩形1的y坐标
	 * @param h1 矩形1的高
	 * @param w1 矩形1的宽
	 * @param x2 矩形2的x坐标
	 * @param y2 矩形2的y坐标
	 * @param h2 矩形2的高
	 * @param w2 矩形2的宽
	 * @return
	 */
	private boolean isCollsionWithRect(int x1, int y1, int h1, int w1, int x2, int y2, int h2, int w2){
		//将所有的未碰撞状态判断出来
		if(x1 >= x2 && x1 >= x2+w2){ 				//(x1, y1)位于(x2, y2)的右边
			return false;
		}else if(x1 <= x2 && x2 >= x1+w1){			//(x1, y1)位于(x2, y2)的左边
			return false;
		}else if(y2 >= y1 && y2 >= y1+h1){			//(x1, y1)位于(x2, y2)的上边
			return false;
		}else if(y2 <= y1 && y1 >= y2+h2){			//(x1, y1)位于(x2, y2)的下边
			return false;
		}
		return true;
	}

	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				if(isCollsion){
					paint.setColor(Color.RED);
					paint.setTextSize(20);
					canvas.drawText("Collision！", 0, 30, paint);
				}else{
					paint.setColor(Color.BLACK);
				}
				//绘制矩形1
				canvas.drawRect(x1, y1, x1+w1, y1+h1, paint);
				//绘制矩形2
				canvas.drawRect(x2, y2, x2+w2, y2+h2, paint);
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
