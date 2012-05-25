package com.albert.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.albert.IGameView;
import com.albert.ui.logic.MainGameLogic;

public class WelcomeView extends IGameView {

	private MainGameLogic mainGame;
	private Context mContext;
	
	public WelcomeView(Context context, MainGameLogic mainGame) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mainGame = mainGame;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refurbish() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}


}
