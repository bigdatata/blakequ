package com.hao.item;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;

public class Player extends BaseItemImpl{
	private int playerHP = 4;						//玩家血量
	private Bitmap bmpHP;							//血量图片
	private Bitmap bmpPlayer;						//玩家图片
	private int speed = 5;							//玩家移动速度
	public int x, y;								//玩家位置
	private boolean isLeft, isRight, isUp, isDown;  //玩家移动方向
	private boolean isCollision;					//是否碰撞
	private int noCollisionCount = 0;				//碰撞后处于无敌状态（即碰撞之后的一段时间是无敌的），这是计时器
	private static int NO_COLLISION_TIME = 30;		//无敌时间
	public Player(Bitmap bmpHP, Bitmap bmpPlayer) {
		super();
		this.bmpHP = bmpHP;
		this.bmpPlayer = bmpPlayer;
		this.x = (GameSurfaceView.screenWidth - bmpPlayer.getWidth())/2;
		this.y = GameSurfaceView.screenHeight - bmpPlayer.getHeight();
		isCollision = false;
	}
	
	/**
	 * 绘制主角
	 */
	public void draw(Canvas canvas, Paint paint){
		if(isCollision){							//当处于无敌时间时，让主角闪烁
			if(noCollisionCount % 2 == 0){			//每2次游戏循环，绘制一次主角
				canvas.drawBitmap(bmpPlayer, x, y, paint);
			}
		}else{
			canvas.drawBitmap(bmpPlayer, x, y, paint);
		}
		for(int i=0; i<playerHP; i++){
			canvas.drawBitmap(bmpHP, i * bmpHP.getWidth(), (GameSurfaceView.screenHeight - bmpHP.getHeight()), paint);
		}
		
	}
	
	/**
	 * 逻辑处理
	 */
	public void logic(){
		if(isLeft){										//判断运动方向
			x -= speed;
		}else if(isRight){
			x += speed;
		}else if(isUp){
			y -= speed;
		}else if(isDown){
			y += speed;
		}
		
		if(x < 0){										//判断x边界
			x = 0;
		}else if(x > GameSurfaceView.screenWidth - bmpPlayer.getWidth()){
			x = GameSurfaceView.screenWidth - bmpPlayer.getWidth();
		}
		
		if(y < 0){										//判断y边界
			y = 0;
		}else if(y > GameSurfaceView.screenHeight - bmpPlayer.getHeight()){
			y = GameSurfaceView.screenHeight - bmpPlayer.getHeight();
		}
		
		if(isCollision){								//如果无敌状态，计时
			noCollisionCount ++;
			if(noCollisionCount >= NO_COLLISION_TIME){	//无敌时间过后，接触无敌状态及初始化计数器
				noCollisionCount = 0;
				isCollision = false;
			}
		}
	}
	
	
	public void onKeyUp(int keyCode, KeyEvent event){
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_UP:
			isUp = false;
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			isDown = false;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			isLeft = false;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			isRight = false;
			break;
	}
	}
	
	public void onKeyDown(int keyCode, KeyEvent event){
		switch(keyCode){
			case KeyEvent.KEYCODE_DPAD_UP:
				isUp = true;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				isDown = true;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				isLeft = true;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				isRight = true;
				break;
		}
	}

	/**
	 * 获取主角血量
	 * @return
	 */
	public int getPlayerHP() {
		return playerHP;
	}

	/**
	 * 设置主角血量
	 * @param bmpHP
	 */
	public void setPlayerHP(int playerHP) {
		this.playerHP = playerHP;
	}

	
	/**
	 * 判断是否和敌机碰撞
	 * @param e
	 * @return
	 */
	public boolean isCollisionWith(Enemy e){
		//非无敌时间才判断碰撞
		if(!isCollision){
			int x2 = e.x;
			int y2 = e.y;
			int w2 = e.frameWidth;
			int h2 = e.frameHeight;
			//将所有的未碰撞状态判断出来
			boolean b = isCollsionWithRect(x, y, bmpPlayer.getHeight(), bmpPlayer.getWidth(), x2, y2, h2, w2);
			if(b){
				isCollision = true;											//设置冲突（无敌状态）
			}
			return b;
		}
		return false;
	}
	
	/**
	 * 判断是否和子弹冲突
	 * @param bullet
	 * @return
	 */
	public boolean isCollisionWith(Bullet bullet){
		if(!isCollision){
			int x2 = bullet.x;
			int y2 = bullet.y;
			int w2 = bullet.bmpBullet.getWidth();
			int h2 = bullet.bmpBullet.getHeight();
			boolean b = isCollsionWithRect(x, y, bmpPlayer.getHeight(), bmpPlayer.getWidth(), x2, y2, h2, w2);
			if(b){
//				playerHP --;											//像这个应该交给逻辑处理，不然很乱
				isCollision = true;
			}
			return b;
		}
		return false;
	}
}
