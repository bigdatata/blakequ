package com.hao.item;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 游戏的敌人，分有好几种（可以扩充血量+积分）
 * @author Administrator
 *
 */
public class Enemy extends BaseItemImpl{

	public int type;									//敌人种类
	public static final int TYPE_FLY = 1;				//苍蝇
	public static final int TYPE_DUCKL = 2;				//鸭子(从左往右运动)
	public static final int TYPE_DUCKR = 3;				//鸭子(从右往左运动)
	private Bitmap bmpEnemy;							//敌机图片资源
	public int x, y;									//敌机坐标
	public int frameWidth, frameHeight;				//每帧的长宽
	private int frameIndex;								//帧索引
	private int speed;									//敌机移动速度
	private boolean isDead;								//是否死亡（即移出屏幕）
	
	public Enemy(int type, Bitmap bmpEnemy, int x, int y) {
		this.type = type;
		this.bmpEnemy = bmpEnemy;
		this.x = x;
		this.y = y;
		
		frameWidth = bmpEnemy.getWidth()/10;			//每张图片有10帧
		frameHeight = bmpEnemy.getHeight();
		switch(type){									//针对不同的敌机有不同的移动速度
			case TYPE_FLY:
				speed = 25;								//在运动过程中是减速，故而设大些
				break;
			case TYPE_DUCKL:
			case TYPE_DUCKR:
				speed = 3;								//他们在运动过程中加速，故初始比较小
				break;
		}
	}
	
	/**
	 * 绘制敌机
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.save();									//这个要必须的，就是只绘制指定区域，减少开销
		canvas.clipRect(x, y, x + frameWidth, y+frameHeight);
		canvas.drawBitmap(bmpEnemy, x - frameIndex*frameWidth, y, paint);
		canvas.restore();
	}
	
	/**
	 * 敌机处理逻辑，就是要不断的移动
	 */
	public void logic(){
		frameIndex ++;									//不断的播放帧动画
		if(frameIndex > 10) frameIndex = 0;
		switch(type){									//不同的类型运动不一样
			case TYPE_FLY:
				if(!isDead){							//减速出现，加速返回
					speed -= 1;
					y += speed;
					if(y < -60){
						isDead = true;
					}
				}
				break;
			case TYPE_DUCKL:
				if(!isDead){							//斜右下角运动
					x += speed/2;
					y += speed;
					if(x > GameSurfaceView.screenWidth || y > GameSurfaceView.screenHeight){
						isDead = true;
					}
				}
				break;
			case TYPE_DUCKR:
				if(!isDead){							//斜左下角运动
					x -= speed/2;
					y += speed;
					if(x < -50  || y > GameSurfaceView.screenHeight){
						isDead = true;
					}
				}
				break;
		}
		
	}

	/**
	 * 看敌机是否已经死亡（out of screen）
	 * @return
	 */
	public boolean isDead() {
		return isDead;
	}
	
	/**
	 *  检测是否和子弹碰撞
	 * @param bullet
	 * @return
	 */
	public boolean isCollisionWith(Bullet bullet){
		int x2 = bullet.x;
		int y2 = bullet.y;
		int h2 = bullet.bmpBullet.getHeight();
		int w2 = bullet.bmpBullet.getWidth();
		boolean b = isCollsionWithRect(x, y, bmpEnemy.getHeight(), bmpEnemy.getWidth(), x2, y2, h2, w2);
		if(b){
			isDead = true;
		}
		return b;
	}
	
}
