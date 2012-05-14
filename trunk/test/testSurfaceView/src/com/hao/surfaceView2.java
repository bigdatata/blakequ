package com.hao;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class surfaceView2 extends Activity {
	Button btnSingleThread, btnDoubleThread;
	SurfaceView sfv;
	SurfaceHolder sfh;
	ArrayList<Integer> imgList = new ArrayList<Integer>();
	int imgWidth, imgHeight;
	Bitmap bitmap;// �����̶߳�ȡ�������̻߳�ͼ

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);

		btnSingleThread = (Button) this.findViewById(R.id.Button01);
		btnDoubleThread = (Button) this.findViewById(R.id.Button02);
		btnSingleThread.setOnClickListener(new ClickEvent());
		btnDoubleThread.setOnClickListener(new ClickEvent());
		sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
		sfh = sfv.getHolder();
		sfh.addCallback(new MyCallBack());// �Զ�����surfaceCreated�Լ�surfaceChanged
	}

	class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (v == btnSingleThread) {
				new Load_DrawImage(0, 0).start();// ��һ���̶߳�ȡ����ͼ
			} else if (v == btnDoubleThread) {
				new LoadImage().start();// ��һ���̶߳�ȡ
				new DrawImage(imgWidth + 10, 0).start();// ��һ���̻߳�ͼ
			}

		}

	}

	class MyCallBack implements SurfaceHolder.Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.i("Surface:", "Change");

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.i("Surface:", "Create");

			// �÷����������ȡ��Դ�е�ͼƬID�ͳߴ�
			Field[] fields = R.drawable.class.getDeclaredFields();
			for (Field field : fields) {
				if (!"icon".equals(field.getName()))// ����icon֮���ͼƬ
				{
					int index = 0;
					try {
						index = field.getInt(R.drawable.class);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// ����ͼƬID
					imgList.add(index);
				}
			}
			if(imgList.size() != 0){
				// ȡ��ͼ���С
				Bitmap bmImg = BitmapFactory.decodeResource(getResources(), imgList
						.get(0));
				imgWidth = bmImg.getWidth();
				imgHeight = bmImg.getHeight();
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.i("Surface:", "Destroy");

		}

	}

	/*
	 * ��ȡ����ʾͼƬ���߳�
	 */
	class Load_DrawImage extends Thread {
		int x, y;
		int imgIndex = 0;

		public Load_DrawImage(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void run() {
			while (true) {
				Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x
						+ imgWidth, this.y + imgHeight));
				Bitmap bmImg = BitmapFactory.decodeResource(getResources(),
						imgList.get(imgIndex));
				c.drawBitmap(bmImg, this.x, this.y, new Paint());
				imgIndex++;
				if (imgIndex == imgList.size())
					imgIndex = 0;

				sfh.unlockCanvasAndPost(c);// ������Ļ��ʾ����
			}
		}
	};

	/*
	 * ֻ�����ͼ���߳�
	 */
	class DrawImage extends Thread {
		int x, y;

		public DrawImage(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void run() {
			while (true) {
				if (bitmap != null) {// ���ͼ����Ч
					Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x
							+ imgWidth, this.y + imgHeight));

					c.drawBitmap(bitmap, this.x, this.y, new Paint());

					sfh.unlockCanvasAndPost(c);// ������Ļ��ʾ����
				}
			}
		}
	};

	/*
	 * ֻ�����ȡͼƬ���߳�
	 */
	class LoadImage extends Thread {
		int imgIndex = 0;

		public void run() {
			while (true) {
				bitmap = BitmapFactory.decodeResource(getResources(), imgList
						.get(imgIndex));
				imgIndex++;
				if (imgIndex == imgList.size())// �������ͷ�����¶�ȡ
					imgIndex = 0;
			}
		}
	};
}
