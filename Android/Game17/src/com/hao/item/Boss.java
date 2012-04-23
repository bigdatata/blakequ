package com.hao.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

public class Boss extends BaseItemImpl {
	
	public int hp = 50;							//Boss��Ѫ��
	private Bitmap bmpBoss;						//Boss��ͼƬ��Դ
	public int x, y;							//Boss����
	public int frameW, frameH;					//Bossÿ֡�Ŀ��
	private int frameIndex;						//Boss��ǰ֡�±�
	private int speed = 5;						//Boss�˶����ٶ�
	//Boss���˶��켣,һ��ʱ���������Ļ�·��˶������ҷ����Χ�ӵ������Ƿ��̬������״̬�� ���ӵ���ֱ�����˶�
	private boolean isCrazy;
	private int crazyTime = 200;				//������״̬��״̬ʱ����
	private int count;							//������

	public Boss(Bitmap bmpBoss) {
		this.bmpBoss = bmpBoss;
		frameW = bmpBoss.getWidth() / 10;
		frameH = bmpBoss.getHeight();
		//Boss��X�������
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
		//����ѭ������֡�γɶ���
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		//û�з���״̬
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
			//����״̬
		} else {
			speed -= 1;
			//��Boss����ʱ���������ӵ�
			if (speed == 0) {
				//���8�����ӵ�
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
				//�ָ�����״̬
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
	 * ��ȡbossѪ��
	 * @return
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * ����bossѪ��
	 * @param hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	
}
