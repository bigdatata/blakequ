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
		sfh.addCallback(this);//���൱��ע��ص��������Զ�����surfaceChanged����������
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
		//ֻ����surface create��ʱ����ܻ�ȡ�߿�,�ڹ��캯���п϶���0,view��û��ʼ��
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
	 * unlockCanvas() �� lockCanvas()��Surface�������ǲ�����ģ�
	 * ������Ҫ��ȫ�ػ�Surface�����ݣ�Ϊ�����Ч��ֻ�ػ�仯�Ĳ�������Ե���lockCanvas(Rect rect)
	 * ������ָ��һ��rect��������������������ݻỺ���������ڵ���lockCanvas������ȡCanvas��
	 * SurfaceView���ȡSurface��һ��ͬ����ֱ������unlockCanvasAndPost(Canvas canvas)�������ͷŸ�����
	 * �����ͬ�����Ʊ�֤��Surface���ƹ����в��ᱻ�ı䣨���ݻ١��޸ģ���
	 * 
	 * *******************
	 * �����Ұ�draw�Ĵ��붼try��������Ҫ��Ϊ�˵�����������һ���׳��쳣�ˣ�
	 * ��ô����Ҳ�� ��finally��ִ�иò����������������׳��쳣��ʱ�򲻻ᵼ��Surface��ȥ��һ�µ�״̬��
	 */
	public void draw() {
		// TODO Auto-generated method stub
		try {
			System.out.println("-- paint ----");
			canvas = sfh.lockCanvas();// �õ�һ��canvasʵ��
			canvas.drawColor(Color.WHITE);// ˢ��    
			canvas.drawText("Himi", 100, 100, paint);// �������ı�    
			canvas.drawText("����Ǽ򵥵�һ����Ϸ���", 100, 130, paint); 
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(canvas != null)
				sfh.unlockCanvasAndPost(canvas);// �����õĻ����ύ
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
