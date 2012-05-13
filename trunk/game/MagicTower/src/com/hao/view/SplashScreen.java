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
 * 是否开启音效视图
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
		//填充背景为黑色
		paint.setColor(Color.BLACK);
		yarin.fillRect(canvas, 0, 0, yarin.SCREENW, yarin.SCREENH, paint);

		//设置白色字体
		paint.setColor(Color.WHITE);
		String string = "是否开启音效？";
		yarin.drawString(canvas, string, (yarin.SCREENW - paint.measureText(string)) / 2, (yarin.SCREENH - paint.getTextSize()) / 2, paint);
		string = "是";
		yarin.drawString(canvas, string, 5, yarin.SCREENH - paint.getTextSize() - 5, paint);
		string = "否";
		yarin.drawString(canvas, string, yarin.SCREENW - paint.measureText(string) - 5, yarin.SCREENH - paint.getTextSize() - 5, paint);
	}


	public boolean onKeyUp(int keyCode)
	{
		if (keyCode == yarin.KEY_DPAD_OK)
		{
			mMainGame.mbMusic = 1;
		}
		//视图跳转
		mMainGame.controlView(yarin.GAME_MENU);
		//如果是播放音乐就开始播放
		if (mMainGame.mbMusic == 1)
		{
			mMainGame.mCMIDIPlayer.PlayMusic(CMIDIPlayer.MP3_MENU);	//播放Menu时的音乐
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
		paint = null;	//设置为null，便于及时回收paint
		System.gc();
	}
}
