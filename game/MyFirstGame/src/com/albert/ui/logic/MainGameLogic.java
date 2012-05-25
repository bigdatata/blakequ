package com.albert.ui.logic;


import com.albert.GamePara;
import com.albert.IGameView;
import com.albert.audio.AudioSetting;
import com.albert.audio.BaseAudioEntity;
import com.albert.audio.music.MusicManager;
import com.albert.audio.sound.SoundManager;
import com.albert.ui.WelcomeView;

import android.content.Context;

/**
 * the class using for control the logic of game
 * @author AlbertQu
 *
 */
public class MainGameLogic {

	
	private Context mContext;
	//current state of game, default -1 means not begin
	private int gameStatus = -1;
	private static IGameView currentView = null;
	
	//set the audio
	private AudioSetting audioSetting = null;
	public BaseAudioEntity audio = null;
	public MusicManager musicManager = null;
	public SoundManager soundManager = null;
	
	public MainGameLogic(Context mContext) {
		super();
		audioSetting = new AudioSetting();
		this.mContext = mContext;
		this.initGame();
	}
	
	private void initGame(){
		gameStatus = -1;
		controlView(GamePara.GAME_WELCOME);
		initAudio();
	}
	
	/**
	 * init audio manager
	 */
	public void initAudio(){
		musicManager = new MusicManager();
		soundManager = new SoundManager();
	}
	
	/**
	 * control current view
	 * @param state
	 */
	public void controlView(int status){
		//如果状态改变，则回收上次的GameView游戏对象
		if(gameStatus != status)
		{
			if(currentView != null)
			{
				currentView.reCycle();
				System.gc();	
			}
		}
		freeGameView(currentView);
		switch (status)
		{
		case GamePara.GAME_WELCOME:
			currentView = new WelcomeView(mContext, this);
			break;
		case GamePara.GAME_MENU:
			break;
		case GamePara.GAME_HELP:
			break;
		case GamePara.GAME_ABOUT:
			break;
		case GamePara.GAME_RUN:
			break;
		case GamePara.GAME_CONTINUE:
			break;
		}
		setGameState(status);
	}
	
	/**
	 * 释放界面对象
	 * @param gameView
	 */
	private void freeGameView(IGameView gameView)
	{
		if(gameView != null)
		{
			gameView = null;
			System.gc();
		}
	}

	/**
	 * get current game state
	 * @return
	 */
	public int getGameStatus() {
		return gameStatus;
	}

	/**
	 * get current view
	 * @return
	 */
	public static IGameView getCurrentView() {
		return currentView;
	}

	/**
	 * change the current view by set game state
	 * @param gameState
	 */
	public void setGameState(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	/**
	 * get the type of audio
	 * @return
	 */
	public int getAudioType() {
		return audioSetting.getAudioType();
	}

	/**
	 * set the type of audio({@link GamePara.MUSIC} and {@link GamePara.SOUND})
	 * @param audioType
	 */
	public void setAudioType(int audioType) {
		audioSetting.setAudioType(audioType);
	}

	public AudioSetting getAudioSetting() {
		return audioSetting;
	}
}
