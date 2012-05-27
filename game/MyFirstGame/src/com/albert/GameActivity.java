package com.albert;

import com.albert.ui.logic.MainGameLogic;
import com.albert.ui.logic.ViewThread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameActivity extends BaseActivity {
	ViewThread viewThread = null;
	MainGameLogic mainGame = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainGame = new MainGameLogic(this);
        viewThread = new ViewThread(this);
        setContentView(viewThread);
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		viewThread.onKeyDown(keyCode, event);
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		viewThread.onKeyUp(keyCode, event);
		return false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		viewThread.requestFocus();
		viewThread.startRefreshView();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		viewThread.onTouchEvent(event);
		return false;
	}

	@Override
	public void onDestroyResources() {
		// TODO Auto-generated method stub
		Log.i("GameActivity", "onDestroyResources");
		viewThread.stopRefreshView();
		mainGame.free();
		System.gc();
	}
    
}