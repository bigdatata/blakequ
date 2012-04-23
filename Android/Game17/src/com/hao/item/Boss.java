package com.hao.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

public class Boss extends BaseItemImpl {
	
	public int hp = 50;							//Boss的血量
	private Bitmap bmpBoss;						//Boss的图片资源
	public int x, y;							//Boss坐标
	public int frameW, frameH;					//Boss每帧的宽高
	private int frameIndex;						//Boss当前帧下标
	private int speed = 5;						//Boss运动的速度
	//Boss的运动轨迹,一定时间会向着屏幕下方运动，并且发射大范围子弹，（是否狂态）正常状态下 ，子弹垂直朝下运动
	private boolean isCrazy;
	private int crazyTime = 200;				//进入疯狂状态的状态时间间隔
	private int count;							//计数器

	public Boss(Bitmap bmpBoss) {
		this.bmpBoss = bmpBoss;
		frameW = bmpBoss.getWidth() / 10;
		frameH = bmpBoss.getHeight();
		//Boss的X坐标居中
		x = GameSurfaceView.screenWidth / 2 - frameW / 2;
		y = 0;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.draw(canvas, paint);
		canvas.save();
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(bmpBoss, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		super.logic();
		//不断循环播放帧形成动画
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		//没有疯狂的状态
		if (isCrazy == false) {
			x += speed;
			if (x + frameW >= GameSurfaceView.screenWidth) {
				speed = -speed;
			} else if (x <= 0) {
				speed = -speed;
			}
			count++;
			if (count % crazyTime == 0) {
				isCrazy = true;
				speed = 24;
			}
			//疯狂的状态
		} else {
			speed -= 1;
			//当Boss返回时创建大量子弹
			if (speed == 0) {
				//添加8方向子弹
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_LEFT));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_RIGHT));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_LEFT));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_RIGHT));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
				GameSurfaceView.bulletBoss.add(new Bullet(GameSurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
			}
			y += speed;
			if (y <= 0) {
				//恢复正常状态
				isCrazy = false;
				speed = 5;
			}
		}
	}
	
	public boolean isCollisionWith(Bullet bullet){
		int x2 = bullet.x;
		int y2 = bullet.y;
		int h2 = bullet.bmpBullet.getHeight();
		int w2 = bullet.bmpBullet.getWidth();
		return isCollsionWithRect(x, y, bmpBoss.getHeight(), bmpBoss.getWidth(), x2, y2, h2, w2);
	}

	/**
	 * 获取boss血量
	 * @return
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * 设置boss血量
	 * @param hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	
}
