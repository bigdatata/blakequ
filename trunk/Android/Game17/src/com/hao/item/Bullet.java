package com.hao.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

/**
 * 子弹
 * @author Administrator
 *
 */
public class Bullet extends BaseItemImpl{
	/****************普通属性**********************************************************/
	public Bitmap bmpBullet;								//子弹图片
	public int x, y;										//子弹坐标
	public int bulletType;									//子弹类型
	private int speed;										//子弹速度
	public static final int BULLET_PLAYER = -1;				//主角的
	public static final int BULLET_DUCK = 1;				//鸭子的
	public static final int BULLET_FLY = 2;					//苍蝇的
	public static final int BULLET_BOSS = 3;				//Boss的
	private boolean isDead;									//子弹是否超屏， 优化处理
	
	/****************Boss疯狂状态下子弹相关成员变量*************************************/
	private int dir;										//当前Boss子弹方向
	public static final int DIR_UP = -1;					//8方向常量
	public static final int DIR_DOWN = 2;
	public static final int DIR_LEFT = 3;
	public static final int DIR_RIGHT = 4;
	public static final int DIR_UP_LEFT = 5;
	public static final int DIR_UP_RIGHT = 6;
	public static final int DIR_DOWN_LEFT = 7;
	public static final int DIR_DOWN_RIGHT = 8;

	public Bullet(Bitmap bmpBullet, int x, int y, int bulletType) {
		super();
		this.bmpBullet = bmpBullet;
		this.x = x;
		this.y = y;
		this.bulletType = bulletType;
		setSpeedByType();
	}
	
	/**
	 * 专用于处理Boss疯狂状态下创建的子弹
	 * @param bmpBullet
	 * @param bulletX
	 * @param bulletY
	 * @param bulletType
	 * @param Dir
	 */
	public Bullet(Bitmap bmpBullet, int x, int y, int bulletType, int dir) {
		this.bmpBullet = bmpBullet;
		this.x = x;
		this.y = y;
		this.bulletType = bulletType;
		setSpeedByType();
		this.dir = dir;
	}
	
	/**
	 * 不同的子弹类型速度不一
	 */
	private void setSpeedByType(){
		switch (bulletType) {
			case BULLET_PLAYER:
				speed = 4;
				break;
			case BULLET_DUCK:
				speed = 3;
				break;
			case BULLET_FLY:
				speed = 4;
				break;
			case BULLET_BOSS:
				speed = 5;
				break;
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.draw(canvas, paint);
		canvas.drawBitmap(bmpBullet, x, y, paint);
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		super.logic();
		switch (bulletType) {
			case BULLET_PLAYER:										//主角的子弹是向上运动
				y -= speed;
				if(y < -50) isDead = true;
				break;
			case BULLET_DUCK:										//敌人的子弹向下运动
			case BULLET_FLY:
				y += speed;
				if(y > GameSurfaceView.screenHeight) isDead = true;
				break;
			case BULLET_BOSS:										//Boss疯狂状态下的8方向子弹逻辑
				switch (dir) {
					case DIR_UP:									//方向上的子弹
						y -= speed;
						break;
					case DIR_DOWN:									//方向下的子弹
						y += speed;
						break;
					case DIR_LEFT:									//方向左的子弹
						x -= speed;
						break;
					case DIR_RIGHT:									//方向右的子弹
						x += speed;
						break;
					case DIR_UP_LEFT:								//方向左上的子弹
						y -= speed;
						x -= speed;
						break;
					case DIR_UP_RIGHT:								//方向右上的子弹
						x += speed;
						y -= speed;
						break;
					case DIR_DOWN_LEFT:								//方向左下的子弹
						x -= speed;
						y += speed;
						break;
					case DIR_DOWN_RIGHT:							//方向右下的子弹
						y += speed;
						x += speed;
						break;
				}
				//边界处理
				if (y > GameSurfaceView.screenHeight || y <= -40 || x > GameSurfaceView.screenWidth || x <= -40) {
					isDead = true;
				}
				break;
	}
	}
	
	/**
	 * 敌机子弹是否在屏幕
	 * @return
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * 获取子弹速度
	 * @return
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * 设置子弹速度
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	

}
