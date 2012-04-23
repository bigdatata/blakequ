package com.hao.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

/**
 * �ӵ�
 * @author Administrator
 *
 */
public class Bullet extends BaseItemImpl{
	/****************��ͨ����**********************************************************/
	public Bitmap bmpBullet;								//�ӵ�ͼƬ
	public int x, y;										//�ӵ�����
	public int bulletType;									//�ӵ�����
	private int speed;										//�ӵ��ٶ�
	public static final int BULLET_PLAYER = -1;				//���ǵ�
	public static final int BULLET_DUCK = 1;				//Ѽ�ӵ�
	public static final int BULLET_FLY = 2;					//��Ӭ��
	public static final int BULLET_BOSS = 3;				//Boss��
	private boolean isDead;									//�ӵ��Ƿ����� �Ż�����
	
	/****************Boss���״̬���ӵ���س�Ա����*************************************/
	private int dir;										//��ǰBoss�ӵ�����
	public static final int DIR_UP = -1;					//8������
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
	 * ר���ڴ���Boss���״̬�´������ӵ�
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
	 * ��ͬ���ӵ������ٶȲ�һ
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
			case BULLET_PLAYER:										//���ǵ��ӵ��������˶�
				y -= speed;
				if(y < -50) isDead = true;
				break;
			case BULLET_DUCK:										//���˵��ӵ������˶�
			case BULLET_FLY:
				y += speed;
				if(y > GameSurfaceView.screenHeight) isDead = true;
				break;
			case BULLET_BOSS:										//Boss���״̬�µ�8�����ӵ��߼�
				switch (dir) {
					case DIR_UP:									//�����ϵ��ӵ�
						y -= speed;
						break;
					case DIR_DOWN:									//�����µ��ӵ�
						y += speed;
						break;
					case DIR_LEFT:									//��������ӵ�
						x -= speed;
						break;
					case DIR_RIGHT:									//�����ҵ��ӵ�
						x += speed;
						break;
					case DIR_UP_LEFT:								//�������ϵ��ӵ�
						y -= speed;
						x -= speed;
						break;
					case DIR_UP_RIGHT:								//�������ϵ��ӵ�
						x += speed;
						y -= speed;
						break;
					case DIR_DOWN_LEFT:								//�������µ��ӵ�
						x -= speed;
						y += speed;
						break;
					case DIR_DOWN_RIGHT:							//�������µ��ӵ�
						y += speed;
						x += speed;
						break;
				}
				//�߽紦��
				if (y > GameSurfaceView.screenHeight || y <= -40 || x > GameSurfaceView.screenWidth || x <= -40) {
					isDead = true;
				}
				break;
	}
	}
	
	/**
	 * �л��ӵ��Ƿ�����Ļ
	 * @return
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * ��ȡ�ӵ��ٶ�
	 * @return
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * �����ӵ��ٶ�
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	

}
