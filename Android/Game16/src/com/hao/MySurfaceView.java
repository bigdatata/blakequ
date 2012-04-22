package com.hao;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder sfh;
	private Paint paint;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;
	//������ײ����
	private Rect rect = new Rect(0, 0, 50, 50);
	//����Region��ʵ��,�����ж����Ƿ��ھ��θ�����
	private Region r = new Region(rect);
	//��ʾ�Ƿ�����ײ�ı�ʶλ
	private boolean isInclude;
	
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
		screenW = getWidth();
		screenH = getHeight();
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
			if(r.contains((int)event.getX(), (int)event.getY())){
				isInclude = true;
			}else{
				isInclude = false;
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

	private void draw() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				paint.setStyle(Style.FILL); 	
				paint.setColor(Color.BLUE);
				canvas.drawRect(rect, paint);
				if(isInclude){
					canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher),
							100, 60, paint);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(canvas != null){
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

}
