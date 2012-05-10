package com.hao;

import com.hao.gif.GameView0;
import com.hao.view.GameView2;
import com.hao.view.GameView3;
import com.hao.view.GameView4;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MExamples_05_10Activity extends Activity {
	private GameView4 mGameView = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mGameView = new GameView4(this);
		
//		setContentView(mGameView);
//		setContentView(new GameView2(this));
//		setContentView(new GameView3(this));
//		setContentView(mGameView);
		setContentView(new GameView0(this));
	}
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return mGameView.onKeyUp(keyCode,event);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ( mGameView == null )
		{
			return false;
		}
		if ( keyCode ==  KeyEvent.KEYCODE_BACK)
		{
			this.finish();
			return true;
		}
		return false;
	}
}