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
	private int rockerCircleX = 100;							//�̶�ҡ�˱���Բ�ε�X����
	private int rockerCircleY = 100;							//�̶�ҡ�˱���Բ�ε�y����
	private int rockerCircleR = 50;								//�̶�ҡ�˱���Բ�εİ뾶
	private float smallRockerCircleX = 100;						//ҡ�˵�X����
	private float smallRockerCircleY = 100;						//ҡ�˵�Y����
	private float smallRockerCircleR = 20;						//ҡ�˵İ뾶
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
	 * ������������Ļ����ϵ�еĽǶȣ���ҡ�˼���ͼʾ��
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private double getRad(float x1, float y1, float x2, float y2){
		float x = x2 - x1;										//�����x�ľ���
		float y = y2 - y1;										//�����Y�ľ���
		float len = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); //���б�߳�
		float rad = (float) Math.acos(x/len);					//�������Ҷ�����ǶȵĻ���	
		if(y1 > y2) rad = -rad;									//ע�⣺��������λ��Y����<ҡ�˵�Y��������Ҫȡ��ֵ-0~-180
		return rad;
	}
	
	/**
	 * ���ݻ��Ȼ�ȡҡ�˵�����
	 * @param centerX ҡ�˱���x����
	 * @param centerY ҡ�˱���y����
	 * @param R		  ҡ�˰뾶R
	 * @param rad	 ����
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
				//����͸����  
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
						Math.pow(rockerCircleY - (int)event.getY(), 2));	//���㴥������ҡ�˱������ĵ����
			//�����������ڻ��Χ��
			if(len > rockerCircleR){
				double rad = getRad(rockerCircleX, rockerCircleY, event.getX(), event.getY());//�õ�ҡ���봥�������γɵĽǶ�
				getSmallRockerXY(rockerCircleX, rockerCircleY, rockerCircleR, rad); 		  //��֤�ڲ�СԲ�˶��ĳ�������
			}else{
				//���С�����ĵ�С�ڻ�����������û��������ƶ�����
				smallRockerCircleX = event.getX();
				smallRockerCircleY = event.getY();
			}
			//���ͷŰ���ʱҡ��Ҫ�ָ�ҡ�˵�λ��Ϊ��ʼλ��
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
