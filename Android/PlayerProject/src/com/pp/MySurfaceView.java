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
	//机器人位图
	private Bitmap bmpRobot;
	//机器人的方向常量
	private final int DIR_LEFT = 0;
	private final int DIR_RIGHT = 1;
	//机器人当前的方向(默认朝右方向)
	private int dir = DIR_RIGHT;
	//动作帧下标
	private int currentFrame;
	//机器人的X,Y位置
	private int robot_x, robot_y;
	//处理按键卡现象
	private boolean isUp, isDown, isLeft, isRight;

	/**
	 * SurfaceView初始化函数
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
	 * SurfaceView视图创建，响应此函数
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		th.start();
	}

	/**
	 * 
	 * @param currentFrame 绘制帧
	 * @param frameW  
	 * 			每帧的高
	 * @param frameH 
	 * 			每帧的高
	 * @param canvas  
	 * 			画布实例
	 * @param paint	     
	 * 			 画笔实例
	 */
	public void drawFrame(int currentFrame, Canvas canvas, Paint paint) {
		int frameW = bmpRobot.getWidth() / 6;
		int frameH = bmpRobot.getHeight() / 2;
		//得到位图的列数
		int col = bmpRobot.getWidth() / frameW;
		//得到当前帧相对于位图的X坐标
		int x = currentFrame % col * frameW;
		//得到当前帧相对于位图的Y坐标(图片有两列，其中第二列是第一列动作的继续，当col>6时就是第二列)
		int y = currentFrame / col * frameH;
		canvas.save();
		//设置一个宽高与机器人每帧相同大小的可视区域
		canvas.clipRect(robot_x, robot_y, robot_x + bmpRobot.getWidth() / 6, robot_y + bmpRobot.getHeight() / 2);
		if (dir == DIR_LEFT) {//如果是向左侧移动
			//镜像操作, sx,sy是 x y变换(x=-1,y=1表示在x轴变换,其他类似)；px和py是对称轴的中心点
			canvas.scale(-1, 1, robot_x - x + bmpRobot.getWidth() / 2, robot_y - y + bmpRobot.getHeight() / 2);
		}
		canvas.drawBitmap(bmpRobot, robot_x - x, robot_y - y, paint);
		canvas.restore();
	}

	/**
	 * 游戏绘图
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
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * 按键事件监听
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
	 * 游戏逻辑
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
	 * SurfaceView视图状态发生改变，响应此函数
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/**
	 * SurfaceView视图消亡时，响应此函数
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
}
