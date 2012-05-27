package com.albert.ui;

import java.io.IOException;
import java.util.Currency;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.albert.GamePara;
import com.albert.IGameView;
import com.albert.R;
import com.albert.audio.music.Music;
import com.albert.audio.music.MusicFactory;
import com.albert.audio.music.MusicManager;
import com.albert.audio.sound.Sound;
import com.albert.audio.sound.SoundFactory;
import com.albert.ui.logic.MainGameLogic;
import com.albert.ui.logic.ViewThread;

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
		this.mContext = context;
		this.mainGame = mainGame;
		menu = context.getResources().getStringArray(R.array.game_menu);
		itemNum = menu.length;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(GamePara.TextSize);
		mImgMenuBG = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
		setViewName("WelcomeView");
	}
	
	/**
	 * 下条菜单
	 */
	private void next()
	{
		if ((curItem + 1) < itemNum)
			curItem++;
		else
			curItem = 0;
		if ((curItem == 1) && (isFirstPlay))
		{
			curItem++;
		}
	}

	/**
	 * 上条菜单
	 */
	private void pre()
	{
		if ((curItem - 1) >= 0)
			curItem--;
		else
			curItem = itemNum - 1;
		if ((curItem == 1) && (isFirstPlay))
		{
			curItem--;
		}
	}
	
	private void drawItem(Canvas canvas){
		paint.setColor(Color.BLACK);
		for(int i=0 ;i<itemNum; i++){
			float x = (ViewThread.screenW - paint.measureText(menu[i]))/2;
			float y = 50 + GamePara.TextSize*i + 5;
			canvas.drawText(menu[i], x, y, paint);
		}
		
		paint.setColor(Color.RED);
		float x = ((ViewThread.screenW - paint.measureText(menu[curItem]))/2);
		float y = 50 + GamePara.TextSize*curItem + 5;
		canvas.drawText(menu[curItem], x, y, paint);
	}


	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mImgMenuBG, 0, 0, paint);
		drawItem(canvas);
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_DOWN:
			next();
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			pre();
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			dealItem();
			break;
		case KeyEvent.KEYCODE_BACK:
			mainGame.getGameActivity().exit();
			break;
		}
		return true;
	}

	
	private void dealItem(){
		switch(curItem){
		case 0:
//			mainGame.controlView(GamePara.GAME_RUN);
			if(mainGame.getAudioSetting().isPlayBackgroundAudio()){
				try {
					Music music = MusicFactory.createMusicFromResource(mainGame.musicManager, mContext, R.raw.music);
					music.setLooping(true);
					music.play();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 1:
//			mainGame.controlView(GamePara.GAME_CONTINUE);
			if(!mainGame.getAudioSetting().isPlayActionAudio()){
				mainGame.getAudioSetting().setPlayActionAudio(true);
				Sound sound = SoundFactory.createSoundFromResource(mainGame.soundManager, mContext, R.raw.cheer_win);
				if(sound != null)
					sound.play();
			}
			break;
		case 2:
//			mainGame.controlView(GamePara.GAME_SETTING);
			mainGame.musicManager.releaseAll();
			break;
		case 3:
//			mainGame.controlView(GamePara.GAME_HELP);
			break;
		case 4:
//			mainGame.controlView(GamePara.GAME_ABOUT);
			break;
		case 5:
			mainGame.getGameActivity().exit();
			break;
		}
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
		mImgMenuBG = null;
		System.gc();
	}

	@Override
	public void refurbish() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
		}
		return true;
	}

}
