package com.hao;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * һ��SurfaceView�Ķ������
 * @author Administrator
 *
 */
public class MySurfaceView extends SurfaceView  implements Callback, Runnable {

	private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private boolean flag;  
    public static int screenW, screenH;
    private static final String TAG = "MySurfaceView";
	private Bitmap bmpIcon;									//iconλͼ
	private Bitmap bmpClip;									//������ͼ
	private Canvas canvasClip;								//�����Ļ���
	private int x1, y1, x2, y2;								//����ʱ�϶�������ʼ��(x1, y1),������(x2, y2)
	private int x3, y3;										//�ƶ�ʱ������
	private boolean isClipOver = false;						//�жϽ����Ƿ����
    
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
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
		isClipOver = false;
		screenH = getHeight();
		screenW = getWidth();
		bmpIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);	//ʵ��Iconλͼ
		//���Լ�����һ��ͼƬ��Ȼ���Ի���Ϊ����������������ݣ����൱����ͼƬ�ϻ�������
		//���Ǿ�̬�Ľ���
//		bmpClip = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Config.ARGB_8888);		//����һ���뵱ǰ��Ļ��С��ͬ��ͼƬ
//		canvasClip = new Canvas(bmpClip);														//ͨ��������ͼƬ���õ�����ʵ��
//		canvasClip.drawColor(Color.WHITE);														//���ý�������ˢ��
//		canvasClip.drawBitmap(bmpIcon, 50, 50, paint);											//���ý�����������һ��iconͼ
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
		if(event.getAction() == MotionEvent.ACTION_UP){
			isClipOver = true;
			x2 = (int) event.getX();
			y2 = (int) event.getY();
		}else if(event.getAction() == MotionEvent.ACTION_DOWN){
			isClipOver = false;
			x1 = (int) event.getX();
			y1 = (int) event.getY();
			System.out.println("x1:"+x1+" y1:"+y1);
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			x3 = (int) event.getX();
			y3 = (int) event.getY();
		}
		return true;
	}

	/**
	 * ����ͼƬ
	 */
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.BLACK);
				canvas.drawBitmap(bmpIcon, 50, 50, paint);							//����iconλͼ
				
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.RED);
				canvas.drawRect(x1, y1, x3, y3, paint);
				
				if(isClipOver){
//					bmpClip = Bitmap.createBitmap(Math.abs(x2-x1), Math.abs(y2-y1), Config.ARGB_8888);//����һ���뵱ǰ��Ļ��С��ͬ��ͼƬ
//					canvasClip = new Canvas(bmpClip);									//ͨ��������ͼƬ���õ�����ʵ��
//					canvasClip.drawColor(Color.WHITE);									//���ý�������ˢ��
//					canvasClip.drawBitmap(bmpClip, 0, 0, paint);	
					int width = Math.abs(x2-x1);
					int height = Math.abs(y2-y1);
					if(x1 > x2 && y1 > y2){
						bmpClip = ScreenShot.takeScreenShot(Game19Activity.instance, x2, y2, width, height);
					}else{
						bmpClip = ScreenShot.takeScreenShot(Game19Activity.instance, x1, y1, width, height);
					}
					canvas.drawBitmap(bmpClip, screenW - bmpClip.getWidth(), screenH - bmpClip.getHeight(), paint);//���ƽ�����λͼ
				}
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
	
	/**
	 * �����߼�����
	 */
	private void logic(){
		
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
				Log.i(TAG, "thread fail!");
			}
		}
	}
	

}
