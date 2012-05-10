package com.yarin.android.GameEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
public class GameControl extends View implements Runnable
{
	//��Ϸ״̬
	public int mIGameStatus = -1;
	//�Ƿ����߳�
	public boolean mBLoop = false;
	public GameControl(Context context)
	{
		super(context);
	}
	//��ʼ����Ϸ
	public void initGame()
	{
		mBLoop = true;
		mIGameStatus = GameDefinition.Game_Logo;
		
		Thread t = new Thread(this);
		t.start();
	}
	//������Ϸ����
	protected void onDraw(Canvas canvas)
	{
		switch ( mIGameStatus )
		{
			case GameDefinition.Game_Logo:
				//��ʾlogo
				break;
			case GameDefinition.Game_MainMenu:
				//��ʾ���˵�
				break;
			case GameDefinition.Game_Help:
				//��ʾ����
				break;
			default:
				break;
		}
	}
	//�߳̿���
	public void run()
	{
		while (mBLoop)
		{
			try
			{
				Thread.sleep(500);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			;
			postInvalidate(); // ˢ����Ļ
		}
	}
	/* �������� */
	boolean onKeyDown(int keyCode)
	{
		switch (mIGameStatus)
		{
			case GameDefinition.Game_Logo:
				//����logo״̬�İ����¼�
				break;
			case GameDefinition.Game_MainMenu:
				//�������˵�״̬�İ����¼�
				break;
			case GameDefinition.Game_Help:
				///�������״̬�İ����¼�
				break;
			default:
				break;
		}
		return true;
	}
	/* �������� */
	boolean onKeyUp(int keyCode)
	{
		return true;
	}
	/* �����¼� */
	public boolean onTouchEvent(MotionEvent event) {
		int iAction = event.getAction();
		//���ݻ�õĲ�ͬ���¼����д���
		if ( iAction == MotionEvent.ACTION_CANCEL )
		{
		}
		else if (iAction == MotionEvent.ACTION_DOWN) 
		{
		}
		else if( iAction == MotionEvent.ACTION_MOVE ) 
		{
		}
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (mIGameStatus)
		{
			case GameDefinition.Game_Logo:
				//����logo״̬�Ĵ����¼�
				break;
			case GameDefinition.Game_MainMenu:
				//�������˵�״̬�Ĵ����¼�
				break;
			case GameDefinition.Game_Help:
				///�������״̬�Ĵ����¼�
				break;
			default:
				break;
		}
		return true;
	}
}

