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
 * 继承View实现双缓冲，主要是模仿SurfaceView的双缓冲机制，Surface已经实现的就是双缓冲
 * 原理：当一个动画增先显示时，程序又在改变它，前面的又没有显示完，程序又从新请求绘制
 * 从而造成屏幕不断闪烁，为了避免闪烁，使用双缓冲，让其处理的图片在内存中处理完成之后在绘制到
 * 屏幕上
 * @author Administrator
 *
 */
public class GameView3 extends View implements Runnable
{
	/* 声明Bitmap对象 */
	Bitmap	mBitQQ	= null;
	
	Paint   mPaint = null;
	
	/* 创建一个缓冲区
	 * 原理是先将图像绘制到mSCBitmap上，都完成后在将
	 * mSCBitmap绘制到屏幕，不是直接将图像绘制到屏幕
	 *  */
	Bitmap	mSCBitmap = null;
	
	/* 创建Canvas对象 */
	Canvas mCanvas = null;   
	
	public GameView3(Context context)
	{
		super(context);
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
		
		/* 创建屏幕大小的缓冲区 */
		mSCBitmap=Bitmap.createBitmap(320, 480, Config.ARGB_8888);  
		
		/* 创建Canvas */
		mCanvas = new Canvas();  
		
		/* 设置将内容绘制在mSCBitmap上 */
		mCanvas.setBitmap(mSCBitmap); 
		
		mPaint = new Paint();
		
		/* 将mBitQQ绘制到mSCBitmap上 */
		mCanvas.drawBitmap(mBitQQ, 0, 0, mPaint);
		
		/* 开启线程 */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 将mSCBitmap显示到屏幕上 */
		canvas.drawBitmap(mSCBitmap, 0, 0, mPaint);
	}
	
	// 触笔事件
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}


	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}


	// 按键弹起事件
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return false;
	}


	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
	}
	
	
	/**
	 * 线程处理
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
			//使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
}

