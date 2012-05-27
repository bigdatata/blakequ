package com.albert;

import android.view.KeyEvent;

public class GamePara {

	//audio type
	public static final int MUSIC = 0;
	public static final int SOUND = 1;
	public static final int AUDIO_BOTH = 2; //can play both type audio
	
	//game state
	public static final int	GAME_WELCOME		= 1;
	public static final int	GAME_MENU		= 2;
	public static final int	GAME_ABOUT		= 3;
	public static final int	GAME_HELP		= 4;
	public static final int	GAME_RUN		= 5;
	public static final int	GAME_CONTINUE	= 6;
	public static final int GAME_SETTING 	= 7;
	
	
	/* key code */
	public static final int	KEY_DPAD_UP		= KeyEvent.KEYCODE_DPAD_UP;
	public static final int	KEY_DPAD_DOWN	= KeyEvent.KEYCODE_DPAD_DOWN;
	public static final int	KEY_DPAD_LEFT	= KeyEvent.KEYCODE_DPAD_LEFT;
	public static final int	KEY_DPAD_RIGHT	= KeyEvent.KEYCODE_DPAD_RIGHT;
	public static final int	KEY_DPAD_OK		= KeyEvent.KEYCODE_DPAD_CENTER;	// 23
	public static final int	KEY_SOFT_RIGHT	= KeyEvent.KEYCODE_BACK;
	
	/* the width and height of map */
	
	/* the begin coordinate of map*/
	
	/* the size fo text */
	public static final int	TextSize = 16;
	
	
	/* sleep time of game view refresh */
	public static final int GAME_LOOP = 100;
}
