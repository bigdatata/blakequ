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
 * �Զ���surfaceView������xml������
 * ���Ǳ���Ҫ�й��캯��2************
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
	 * ���캯��2
	 * �����new�����Ĵ���ʵ���϶���û�����⣬��������Ϊ��������ʾSurfaceViewͬʱ��ʾ�����������԰��Զ����SurfaceViewҲ�������ע������main����xml�У�
	 * ����������Ҫע�⣬����xml��ע��ľͱ���������ֳ�ʼ�������� ��ʼ����ʱ�������������
	 * ��ʱ�������������һ����о�ʱ�䣬�����һ��Ⱥ�ѵİ����²ŷ����������������
	 * ��ô�ڶ�������ָ���Զ���������һЩ����,���񳤿�һ��������Ը��������,����ͨ����������ݵ�
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
	 * �߼�������
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
