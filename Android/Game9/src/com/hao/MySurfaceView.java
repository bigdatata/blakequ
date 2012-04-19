package com.hao;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private Paint paint;
	private Canvas canvas;
	private SurfaceHolder sfh;
	private boolean flag = true;//������һ������ֵ�ĳ�Ա���� ,������Կ������ǵ��߳�������һ�����أ�
	private Bitmap bmp;
	private Thread thread;
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setAntiAlias(true);
		sfh = getHolder();
		sfh.addCallback(this);
		setFocusable(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.himi_dream);
		/**
		 * Ϊʲô���ǵ��Back��ť���쳣�����Home���쳣�ˣ�
		 * ԭ����Ϊ���Back��ť�ٴν�������ʱ���Ƚ������view���캯���
		 * ��ô����˵������new��һ���̳߳�������������
		 * 
		 * ��ô�����ǵ��Homeȴ��һ����,
		 * ��Ϊ���home֮���ٴν�����򲻻���빹�캯��������ֱ�ӽ�����view�����������surfaceCreated��
		 * ����view������������������и������̵߳Ĳ�������ʵ��һ������������̻߳������У�
		 * so~�����һ���쳣�ˣ�˵�߳��Ѿ�������
		 */
//		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.v("MySurfaceView", "surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("MySurfaceView", "surfaceCreated");
		flag = true;//�������߳�֮ǰ�������������ֵΪture�����߳�һֱ����.
		new Thread(this).start();//*****������ͨ��home����ʱ�����������̣߳��ʶ������ڳ��ִ���
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("MySurfaceView", "surfaceDestroyed");
		flag = false;//��view����ʱ�������������ֵΪfalse�����ٵ�ǰ�̣߳�
	}

	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(color.black);
				canvas.drawBitmap(bmp, 0, 0, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("MySurfaceView", "draw is error!");
		}finally{
			if(canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){//��view����ʱ�������������ֵΪfalse�����ٵ�ǰ�̣߳�
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
