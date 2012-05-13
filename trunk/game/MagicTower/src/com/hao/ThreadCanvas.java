package com.hao;

import com.hao.util.yarin;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

/**
 * ��Ϸ��ͼ�����ࣨ������ͼ���ƣ���ͼˢ�£���������
 * ��Ϸ���������̣߳��������н���Ļ���
 * ��Ҫ�Ǵ�MainGame��ȡ��ǰ���棬Ȼ����ƽ���
 * @author Administrator
 *
 */
public class ThreadCanvas extends View implements Runnable
{
	private String	m_Tag	= "ThreadCanvas_Tag";
	//����Ϸ������ʱ�򣬱�������Ϊfalse�Ա�֤�����̵߳Ľ���
	private boolean flag = true;

	public ThreadCanvas(Context context)
	{
		super(context);
		flag = true;
	}


	/**
	 * ����Ϸ������ʱ�򣬽���ˢ����ͼ�߳�
	 * @param flag
	 */
	public void setEndViewFlag(boolean flag) {
		this.flag = flag;
	}


	/**
	 * ��ͼ
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
	 * ��ͼ��ʾ
	 * 
	 */
	public void start()
	{
		flag = true;
		Thread t = new Thread(this);
		t.start();
	}


	// ˢ�½���
	private void refurbish()
	{
		if (MainGame.getMainView() != null)
		{
			MainGame.getMainView().refurbish();
		}
	}


	/**
	 * ��Ϸѭ��
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
			refurbish(); // ������ʾ
			postInvalidate(); // ˢ����Ļ
		}
	}


	// ��������(��������)
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


	// ��������
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
