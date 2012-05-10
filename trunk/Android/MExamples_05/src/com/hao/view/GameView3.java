package com.hao.view;

import com.hao.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * �̳�Viewʵ��˫���壬��Ҫ��ģ��SurfaceView��˫������ƣ�Surface�Ѿ�ʵ�ֵľ���˫����
 * ԭ����һ������������ʾʱ���������ڸı�����ǰ�����û����ʾ�꣬�����ִ����������
 * �Ӷ������Ļ������˸��Ϊ�˱�����˸��ʹ��˫���壬���䴦���ͼƬ���ڴ��д������֮���ڻ��Ƶ�
 * ��Ļ��
 * @author Administrator
 *
 */
public class GameView3 extends View implements Runnable
{
	/* ����Bitmap���� */
	Bitmap	mBitQQ	= null;
	
	Paint   mPaint = null;
	
	/* ����һ��������
	 * ԭ�����Ƚ�ͼ����Ƶ�mSCBitmap�ϣ�����ɺ��ڽ�
	 * mSCBitmap���Ƶ���Ļ������ֱ�ӽ�ͼ����Ƶ���Ļ
	 *  */
	Bitmap	mSCBitmap = null;
	
	/* ����Canvas���� */
	Canvas mCanvas = null;   
	
	public GameView3(Context context)
	{
		super(context);
		
		/* װ����Դ */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
		
		/* ������Ļ��С�Ļ����� */
		mSCBitmap=Bitmap.createBitmap(320, 480, Config.ARGB_8888);  
		
		/* ����Canvas */
		mCanvas = new Canvas();  
		
		/* ���ý����ݻ�����mSCBitmap�� */
		mCanvas.setBitmap(mSCBitmap); 
		
		mPaint = new Paint();
		
		/* ��mBitQQ���Ƶ�mSCBitmap�� */
		mCanvas.drawBitmap(mBitQQ, 0, 0, mPaint);
		
		/* �����߳� */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* ��mSCBitmap��ʾ����Ļ�� */
		canvas.drawBitmap(mSCBitmap, 0, 0, mPaint);
	}
	
	// �����¼�
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}


	// ���������¼�
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}


	// ���������¼�
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return false;
	}


	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
	}
	
	
	/**
	 * �̴߳���
	 */
	public void run()
	{
		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			//ʹ��postInvalidate����ֱ�����߳��и��½���
			postInvalidate();
		}
	}
}

