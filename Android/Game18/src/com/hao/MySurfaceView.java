package com.hao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView  implements Callback, Runnable {

	private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private boolean flag;  
    public static int screenW, screenH;
    private static final String TAG = "MySurfaceView";
    private int x1, y1, x2, y2;					//��������
    private float rate = 1; 					//���ű���
	private float oldRate = 1;					//��¼�ϴεı���
	private float oldLineDistance;				//��¼��һ�δ���ʱ�߶εĳ���
	private boolean isFirst = true;				//�ж��Ƿ�ͷ�ζ�ָ������Ļ
	private Bitmap bmp;
    
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
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
		//�û���ָ̧��Ĭ�ϻ�ԭΪ��һ�δ�����ʶλ�����ұ��汾�ε����ű���
		if(event.getAction() == MotionEvent.ACTION_UP){
			isFirst = true;
			oldRate = rate;
			System.out.println("-------up-----isfirst:"+isFirst);
		}else{
			x1 = (int) event.getX(0);
			y1 = (int) event.getY(0);
			x2 = (int) event.getX(1);
			y2 = (int) event.getY(1);
			if(event.getPointerCount() == 2){
				System.out.println("-------down-----isfirst:"+isFirst);
				if(isFirst){					//�õ���һ�δ���ʱ�߶εĳ���
					oldLineDistance = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
					isFirst = false;
				}else{							//�õ��ǵ�һ�δ���ʱ�߶εĳ���
					float newLineDistance = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
					rate = oldRate * newLineDistance / oldLineDistance;
				}
			}
		}
		return true;
	}

	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				canvas.save();
				canvas.scale(rate, rate, screenW/2, screenH/2);	//���Ż���(��ͼƬ���ĵ�������ţ�XY�����ű�����ͬ)
				canvas.drawBitmap(bmp,(screenW - bmp.getWidth())/2, (screenH - bmp.getHeight())/2, paint);
				canvas.restore();
				//���ڹ۲죬���������������ʱ�γɵ��߶�
				canvas.drawLine(x1, y1, x2, y2, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i(TAG, "can not draw!");
		} finally{
			try {
				if(canvas != null){
					sfh.unlockCanvasAndPost(canvas);
				}
			} catch (Exception e2) {
				// TODO: handle exception
				Log.i(TAG, "unlockCanvasAndPost fail!");
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
				Log.i(TAG, "thread fail!");
			}
		}
	}
	

}
