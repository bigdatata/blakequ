package com.pp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 
 * @author Himi
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;
	//������λͼ
	private Bitmap bmpRobot;
	//�����˵ķ�����
	private final int DIR_LEFT = 0;
	private final int DIR_RIGHT = 1;
	//�����˵�ǰ�ķ���(Ĭ�ϳ��ҷ���)
	private int dir = DIR_RIGHT;
	//����֡�±�
	private int currentFrame;
	//�����˵�X,Yλ��
	private int robot_x, robot_y;
	//������������
	private boolean isUp, isDown, isLeft, isRight;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		bmpRobot = BitmapFactory.decodeResource(this.getResources(), R.drawable.robot);
	}

	/**
	 * SurfaceView��ͼ��������Ӧ�˺���
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		//ʵ���߳�
		th = new Thread(this);
		//�����߳�
		th.start();
	}

	/**
	 * 
	 * @param currentFrame ����֡
	 * @param frameW  
	 * 			ÿ֡�ĸ�
	 * @param frameH 
	 * 			ÿ֡�ĸ�
	 * @param canvas  
	 * 			����ʵ��
	 * @param paint	     
	 * 			 ����ʵ��
	 */
	public void drawFrame(int currentFrame, Canvas canvas, Paint paint) {
		int frameW = bmpRobot.getWidth() / 6;
		int frameH = bmpRobot.getHeight() / 2;
		//�õ�λͼ������
		int col = bmpRobot.getWidth() / frameW;
		//�õ���ǰ֡�����λͼ��X����
		int x = currentFrame % col * frameW;
		//�õ���ǰ֡�����λͼ��Y����(ͼƬ�����У����еڶ����ǵ�һ�ж����ļ�������col>6ʱ���ǵڶ���)
		int y = currentFrame / col * frameH;
		canvas.save();
		//����һ������������ÿ֡��ͬ��С�Ŀ�������
		canvas.clipRect(robot_x, robot_y, robot_x + bmpRobot.getWidth() / 6, robot_y + bmpRobot.getHeight() / 2);
		if (dir == DIR_LEFT) {//�����������ƶ�
			//�������, sx,sy�� x y�任(x=-1,y=1��ʾ��x��任,��������)��px��py�ǶԳ�������ĵ�
			canvas.scale(-1, 1, robot_x - x + bmpRobot.getWidth() / 2, robot_y - y + bmpRobot.getHeight() / 2);
		}
		canvas.drawBitmap(bmpRobot, robot_x - x, robot_y - y, paint);
		canvas.restore();
	}

	/**
	 * ��Ϸ��ͼ
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.BLACK);
				drawFrame(currentFrame, canvas, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		Log.e("", "aaaaaaa");
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}

	/**
	 * �����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * �����¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
			dir = DIR_LEFT;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = true;
			dir = DIR_RIGHT;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = false;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * ��Ϸ�߼�
	 */
	private void logic() {
		if (isUp) {
			robot_y -= 5;
		}
		if (isDown) {
			robot_y += 5;
		}
		if (isLeft) {
			robot_x -= 5;
		}
		if (isRight) {
			robot_x += 5;
		}
		currentFrame++;
		if (currentFrame >= 12) {
			currentFrame = 0;
		}
	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/**
	 * SurfaceView��ͼ����ʱ����Ӧ�˺���
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
}
