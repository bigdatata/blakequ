package com.hao;

import com.hao.util.yarin;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

/**
 * 游戏视图控制类（控制视图绘制，视图刷新，按键处理）
 * 游戏的主绘制线程，负责所有界面的绘制
 * 主要是从MainGame获取当前界面，然后绘制界面
 * @author Administrator
 *
 */
public class ThreadCanvas extends View implements Runnable
{
	private String	m_Tag	= "ThreadCanvas_Tag";
	//当游戏结束的时候，必须设置为false以保证绘制线程的结束
	private boolean flag = true;

	public ThreadCanvas(Context context)
	{
		super(context);
		flag = true;
	}


	/**
	 * 当游戏结束的时候，结束刷新视图线程
	 * @param flag
	 */
	public void setEndViewFlag(boolean flag) {
		this.flag = flag;
	}


	/**
	 * 绘图
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	protected void onDraw(Canvas canvas)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onDraw(canvas);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
	}


	/**
	 * 绘图显示
	 * 
	 */
	public void start()
	{
		flag = true;
		Thread t = new Thread(this);
		t.start();
	}


	// 刷新界面
	private void refurbish()
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().refurbish();
		}
	}


	/**
	 * 游戏循环
	 * 
	 * @param N
	 *            /A
	 * 
	 * @return null
	 */
	public void run()
	{
		while (flag)
		{
			try
			{
				Thread.sleep(yarin.GAME_LOOP);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
//			System.out.println("-------run view:"+MainGame.getMainView().getName());
			refurbish(); // 更新显示
			postInvalidate(); // 刷新屏幕
		}
	}


	// 按键处理(按键按下)
	boolean onKeyDown(int keyCode)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onKeyDown(keyCode);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
		return true;
	}


	// 按键弹起
	boolean onKeyUp(int keyCode)
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().onKeyUp(keyCode);
		}
		else
		{
			Log.i(m_Tag, "null");
		}
		return true;
	}
}
