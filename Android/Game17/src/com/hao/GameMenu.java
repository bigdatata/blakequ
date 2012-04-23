package com.hao;

import com.hao.base.BaseItemImpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * 游戏菜单开始背景
 * @author quhao
 *
 */
public class GameMenu extends BaseItemImpl{
	
	private Bitmap bmpMenu;								//菜单背景
	private Bitmap bmpBtNormal, bmpBtPressed;			//游戏开始按钮
	private int btX, btY;								//按钮x，y坐标
	private boolean isPressed;							//是否按下
	public GameMenu(Bitmap bmpMenu, Bitmap bmpBtNormal, Bitmap bmpBtPressed) {
		super();
		this.bmpMenu = bmpMenu;
		this.bmpBtNormal = bmpBtNormal;
		this.bmpBtPressed = bmpBtPressed;
		isPressed = false;
		btX = (GameSurfaceView.screenWidth - bmpBtNormal.getWidth())/2;	//按钮位置
		btY = GameSurfaceView.screenHeight - bmpBtNormal.getHeight();
	}
	
	/**
	 * 绘制游戏菜单背景
	 * @param canvas 画布
	 * @param paint  画笔
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.drawBitmap(bmpMenu, 0, 0, paint);		//绘制菜单背景图
		if(isPressed){									//按下时的按钮图
			canvas.drawBitmap(bmpBtPressed, btX, btY, paint);
		}else{											//未按下时的按钮
			canvas.drawBitmap(bmpBtNormal, btX, btY, paint);
		}
	}
	
	/**
	 * 按下菜单事件处理
	 * @param event
	 */
	public void onTouchEvent(MotionEvent event){
		int x = (int) event.getX();							//当前x位置
		int y = (int) event.getY();							//当前y位置
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
			isPressed = isOnbutton(x, y);
		}else if(event.getAction() == MotionEvent.ACTION_UP){//抬起判断是否点击按钮，防止用户移动到别处
			if(isOnbutton(x, y)){
				isPressed = false;							//还原Button状态为未按下状态
				GameSurfaceView.gameState = GameSurfaceView.GAMEING;//改变游戏状态为开始游戏
			}
		}
	}
	
	/**
	 * 判断当前触控位置是否在按钮上
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isOnbutton(int x, int y){
		if(x > btX && x < btX + bmpBtNormal.getWidth()){
			if(y > btY && y < btY + bmpBtNormal.getHeight()){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}
