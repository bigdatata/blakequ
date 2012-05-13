package com.hao.view;

import com.hao.GameView;
import com.hao.MainGame;
import com.hao.util.CMIDIPlayer;
import com.hao.util.yarin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * �Ƿ�����Ч��ͼ
 * @author Administrator
 *
 */
public class SplashScreen extends GameView
{
	private int			tick		= 0;
	private Paint		paint		= null;
	private MainGame	mMainGame	= null;


	public SplashScreen(Context context, MainGame mainGame)
	{
		super(context);
		paint = new Paint();
		mMainGame = mainGame;
		paint.setTextSize(yarin.TextSize);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		setName("SplashScreen");
	}


	protected void onDraw(Canvas canvas)
	{
		tick++;
		//��䱳��Ϊ��ɫ
		paint.setColor(Color.BLACK);
		yarin.fillRect(canvas, 0, 0, yarin.SCREENW, yarin.SCREENH, paint);

		//���ð�ɫ����
		paint.setColor(Color.WHITE);
		String string = "�Ƿ�����Ч��";
		yarin.drawString(canvas, string, (yarin.SCREENW - paint.measureText(string)) / 2, (yarin.SCREENH - paint.getTextSize()) / 2, paint);
		string = "��";
		yarin.drawString(canvas, string, 5, yarin.SCREENH - paint.getTextSize() - 5, paint);
		string = "��";
		yarin.drawString(canvas, string, yarin.SCREENW - paint.measureText(string) - 5, yarin.SCREENH - paint.getTextSize() - 5, paint);
	}


	public boolean onKeyUp(int keyCode)
	{
		if (keyCode == yarin.KEY_DPAD_OK)
		{
			mMainGame.mbMusic = 1;
		}
		//��ͼ��ת
		mMainGame.controlView(yarin.GAME_MENU);
		//����ǲ������־Ϳ�ʼ����
		if (mMainGame.mbMusic == 1)
		{
			mMainGame.mCMIDIPlayer.PlayMusic(CMIDIPlayer.MP3_MENU);	//����Menuʱ������
		}
		return true;
	}


	public boolean onKeyDown(int keyCode)
	{
		return true;
	}


	public void refurbish()
	{
		// if (tick > 10)
		// {
		// mMainGame.controlView(yarin.GAME_MENU);
		// }
	}


	public void reCycle()
	{
		paint = null;	//����Ϊnull�����ڼ�ʱ����paint
		System.gc();
	}
}
