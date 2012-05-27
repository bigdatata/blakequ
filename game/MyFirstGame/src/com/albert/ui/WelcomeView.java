package com.albert.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.albert.GamePara;
import com.albert.IGameView;
import com.albert.R;
import com.albert.ui.logic.MainGameLogic;

public class WelcomeView extends IGameView {

	private MainGameLogic mainGame;
	private Context mContext;
	private String[] menu;
	private int itemNum = 0;
	private int	curItem;				//当前菜单选项
	private boolean	isFirstPlay	= false;//是否是第一次玩
	public int	borderX, borderY;
	private Bitmap	mImgMenuBG	= null;	//菜单背景图片
	private Paint paint = null;
	
	public WelcomeView(Context context, MainGameLogic mainGame) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mainGame = mainGame;
		menu = context.getResources().getStringArray(R.array.game_menu);
		itemNum = menu.length;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(GamePara.TextSize);
		mImgMenuBG = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
		setViewName("WelcomeView");
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mImgMenuBG, 0, 0, paint);
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
		paint = null;
		System.gc();
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
