package com.hao;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class TestSurfaceViewActivity extends Activity {
	/** Called when the activity is first created. */
	Button btnSimpleDraw, btnTimerDraw, btn;
	SurfaceView sfv;
	SurfaceHolder sfh;

	private Timer mTimer;
	private MyTimerTask mTimerTask;
	int Y_axis[],// 保存正弦波的Y轴上的点
			centerY,// 中心线
			oldX, oldY,// 上一个XY点
			currentX;// 当前绘制到的X轴上的点

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnSimpleDraw = (Button) this.findViewById(R.id.Button01);
		btnTimerDraw = (Button) this.findViewById(R.id.Button02);
		btnSimpleDraw.setOnClickListener(new ClickEvent());
		btnTimerDraw.setOnClickListener(new ClickEvent());
		btn = (Button) findViewById(R.id.Button03);
		btn.setOnClickListener(new ClickEvent());
		sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
		sfh = sfv.getHolder();

		// 动态绘制正弦波的定时器
		mTimer = new Timer();
		mTimerTask = new MyTimerTask();

		// 初始化y轴数据
		centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv
				.getTop()) / 2;
		Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
		for (int i = 1; i < Y_axis.length; i++) {// 计算正弦波
			Y_axis[i - 1] = centerY
					- (int) (100 * Math.sin(i * 2 * Math.PI / 180));
		}
	}

	class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (v == btnSimpleDraw) {
				SimpleDraw(Y_axis.length - 1);// 直接绘制正弦波

			} else if (v == btnTimerDraw) {
				oldY = centerY;//从y轴中间位置开始
				mTimer.schedule(mTimerTask, 0, 5);// 动态绘制正弦波，每5ms画一次
			} else if (v == btn){
				Intent intent = new Intent(TestSurfaceViewActivity.this, surfaceView2.class);
				startActivity(intent);
			}

		}

	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {

			SimpleDraw(currentX);
			currentX++;// 往前进
			if (currentX == Y_axis.length - 1) {// 如果到了终点，则清屏重来
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}

	}

	/*
	 * 绘制指定区域
	 */
	void SimpleDraw(int length) {
		if (length == 0)
			oldX = 0;
		//在画图之前锁定画布，然后再画完了之后就解锁，提交图像
		// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
		Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
				getWindowManager().getDefaultDisplay().getHeight()));// 关键:获取画布，画布是一个矩形区域，也可为null，不指定区域
		Log.i("Canvas:", String.valueOf(oldX) + ","
				+ String.valueOf(oldX + length));

		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// 画笔为绿色
		mPaint.setStrokeWidth(2);// 设置画笔粗细

		int y;
		for (int i = oldX + 1; i < length; i++) {// 绘画正弦波
			y = Y_axis[i - 1];
			canvas.drawLine(oldX, oldY, i, y, mPaint);//x轴不断向前，y沿着正弦波
			oldX = i;
			oldY = y;
		}
		sfh.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
	}

	void ClearDraw() {
		Canvas canvas = sfh.lockCanvas(null);//设置锁定区域为空
		canvas.drawColor(Color.BLACK);// 清除画布
		sfh.unlockCanvasAndPost(canvas);

	}
}