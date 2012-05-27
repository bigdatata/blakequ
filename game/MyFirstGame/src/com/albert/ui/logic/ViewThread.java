package com.albert.ui.logic;

import com.albert.GamePara;
import com.albert.IGameView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;


/**
 * the main view
 * @author AlbertQu
 *
 */
public class ViewThread extends SurfaceView implements Callback,Runnable {

	protected SurfaceHolder sfh;
    public static int screenW, screenH;
    private boolean flag = true;
    private static final String TAG = "ViewThread";
    
    
	public ViewThread(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	
	/**
	 * stop thread that refresh the surface view of game
	 */
	public void stopRefreshView(){
		this.flag = false;
	}
	
	/**
	 * start thread and refresh view
	 */
	public void startRefreshView(){
		this.flag = true;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	private void onDraw(){
		Canvas canvas = null;
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				IGameView gameView = MainGameLogic.getCurrentView();
				if(gameView != null){
					gameView.onDraw(canvas);
				}else{
					Log.i(TAG, "the game view is null");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i(TAG, "can not draw!");
		} finally{
			try {
				if(canvas != null){
					sfh.unlockCanvasAndPost(canvas);
				}
			} catch (Exception e2) {
				// TODO: handle exception
				Log.i(TAG, "unlockCanvasAndPost fail!");
			}
		}
	}
	
	/**
	 * refurbish the view
	 */
	private void refurbish(){
		IGameView gameView = MainGameLogic.getCurrentView();
		if(gameView != null){
			gameView.refurbish();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			Long begin = System.currentTimeMillis();
			this.refurbish();
			this.onDraw();
			Long end = System.currentTimeMillis();
			try {
				if(end - begin < GamePara.GAME_LOOP){
						Thread.sleep(GamePara.GAME_LOOP - (end - begin));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screenH = getHeight();
		screenW = getWidth();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		IGameView gameView = MainGameLogic.getCurrentView();
		if(gameView != null){
			gameView.onKeyDown(keyCode);
		}else{
			Log.i(TAG, "the game view is null");
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		IGameView gameView = MainGameLogic.getCurrentView();
		if(gameView != null){
			gameView.onKeyUp(keyCode);
		}else{
			Log.i(TAG, "the game view is null");
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		IGameView gameView = MainGameLogic.getCurrentView();
		if(gameView != null){
			gameView.onTouchEvent(event);
		}else{
			Log.i(TAG, "the game view is null");
		}
		return true;
	}


}
