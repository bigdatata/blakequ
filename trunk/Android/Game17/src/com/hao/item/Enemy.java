package com.hao.item;

import com.hao.GameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ��Ϸ�ĵ��ˣ����кü��֣���������Ѫ��+���֣�
 * @author Administrator
 *
 */
public class Enemy {

	private int type;									//��������
	public static final int TYPE_FLY = 1;				//��Ӭ
	public static final int TYPE_DUCKL = 2;				//Ѽ��(���������˶�)
	public static final int TYPE_DUCKR = 3;				//Ѽ��(���������˶�)
	private Bitmap bmpEnemy;							//�л�ͼƬ��Դ
	private int x, y;									//�л�����
	private int frameWidth, frameHeight;				//ÿ֡�ĳ���
	private int frameIndex;								//֡����
	private int speed;									//�л��ƶ��ٶ�
	private boolean isDead;								//�Ƿ����������Ƴ���Ļ��
	
	public Enemy(int type, Bitmap bmpEnemy, int x, int y) {
		this.type = type;
		this.bmpEnemy = bmpEnemy;
		this.x = x;
		this.y = y;
		
		frameWidth = bmpEnemy.getWidth()/10;			//ÿ��ͼƬ��10֡
		frameHeight = bmpEnemy.getHeight();
		switch(type){									//��Բ�ͬ�ĵл��в�ͬ���ƶ��ٶ�
			case TYPE_FLY:
				speed = 25;								//���˶��������Ǽ��٣��ʶ����Щ
				break;
			case TYPE_DUCKL:
			case TYPE_DUCKR:
				speed = 3;								//�������˶������м��٣��ʳ�ʼ�Ƚ�С
				break;
		}
	}
	
	/**
	 * ���Ƶл�
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.save();									//���Ҫ����ģ�����ֻ����ָ�����򣬼��ٿ���
		canvas.clipRect(x, y, x + frameWidth, y+frameHeight);
		canvas.drawBitmap(bmpEnemy, x - frameIndex*frameWidth, y, paint);
		canvas.restore();
	}
	
	/**
	 * �л������߼�������Ҫ���ϵ��ƶ�
	 */
	public void logic(){
		frameIndex ++;									//���ϵĲ���֡����
		if(frameIndex > 10) frameIndex = 0;
		switch(type){									//��ͬ�������˶���һ��
			case TYPE_FLY:
				if(!isDead){							//���ٳ��֣����ٷ���
					speed -= 1;
					y += speed;
					if(y < -60){
						isDead = true;
					}
				}
				break;
			case TYPE_DUCKL:
				if(!isDead){							//б���½��˶�
					x += speed/2;
					y += speed;
					if(x > GameSurfaceView.screenWidth || y > GameSurfaceView.screenHeight){
						isDead = true;
					}
				}
				break;
			case TYPE_DUCKR:
				if(!isDead){							//б���½��˶�
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
	 * ���л��Ƿ��Ѿ�������out of screen��
	 * @return
	 */
	public boolean isDead() {
		return isDead;
	}
	
}
