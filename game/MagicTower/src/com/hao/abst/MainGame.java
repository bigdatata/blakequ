package com.hao.abst;

import com.hao.MagicTower;
import com.hao.util.CMIDIPlayer;
import com.hao.util.yarin;
import com.hao.view.AboutScreen;
import com.hao.view.GameScreen;
import com.hao.view.HelpScreen;
import com.hao.view.MainMenu;
import com.hao.view.SplashScreen;

import android.app.Activity;
import android.content.Context;

/**
 * 游戏逻辑控制（视图切换，垃圾回收，音乐播放）
 * 这个是游戏主要的控制类
 * 通过多态控制游戏界面的切换，回收等工作
 * @author Administrator
 *
 */
public class MainGame
{
	//在这里使用的抽象类，就是为了实现多态(所有游戏View都是继承自GameView)
	private static GameView	m_GameView		= null;		// 当前需要显示的对象
	private Context				m_Context		= null;
	private MagicTower			m_MagicTower	= null;
	private int 				m_status		= -1;		//游戏状态
	public  CMIDIPlayer mCMIDIPlayer;
	public byte mbMusic = 0;							//控制是否播放音乐，默认0不播放
	public MainGame(Context context)
	{	
		m_Context = context;
		m_MagicTower = (MagicTower)context;
		m_status = -1;
		
		initGame();
	}
	
	//初始化游戏
	public void initGame()
	{
		controlView(yarin.GAME_SPLASH);
		mCMIDIPlayer = new CMIDIPlayer(m_MagicTower);
	}
	//得到游戏状态
	public int getStatus()
	{
		return m_status;
	}
	//设置游戏状态
	public void setStatus(int status)
	{
		m_status = status;
	}
	//得到主类对象
	public Activity getMagicTower()
	{
		return m_MagicTower;
	}
	
	//得到当前需要显示的对象
	public static GameView getMainView() 
	{
	    return m_GameView;
	}
	
	/**
	 * 控制显示什么界面
	 * @param status
	 */
	public void controlView(int status)
	{
		//如果状态改变，则回收上次的GameView游戏对象
		if(m_status != status)
		{
			if(m_GameView != null)
			{
				m_GameView.reCycle();
				System.gc();			//主动调用垃圾回收器	
			}
		}
		freeGameView(m_GameView);
		switch (status)
		{
		case yarin.GAME_SPLASH:
			m_GameView = new SplashScreen(m_Context,this);
			break;
		case yarin.GAME_MENU:
			m_GameView = new MainMenu(m_Context,this);
			break;
		case yarin.GAME_HELP:
			m_GameView = new HelpScreen(m_Context,this);
			break;
		case yarin.GAME_ABOUT:
			m_GameView = new AboutScreen(m_Context,this);
			break;
		case yarin.GAME_RUN:
			m_GameView = new GameScreen(m_Context,m_MagicTower,this,true);
			break;
		case yarin.GAME_CONTINUE:
			m_GameView = new GameScreen(m_Context,m_MagicTower,this,false);
			break;
		}
		setStatus(status);
	}
	
	/**
	 * 释放界面对象
	 * @param gameView
	 */
	private void freeGameView(GameView gameView)
	{
		if(gameView != null)
		{
			gameView = null;
			System.gc();
		}
	}
}

