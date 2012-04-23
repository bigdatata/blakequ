package com.hao.item;

import com.hao.GameSurfaceView;
import com.hao.base.BaseItemImpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;

public class Player extends BaseItemImpl{
	private int playerHP = 4;						//���Ѫ��
	private Bitmap bmpHP;							//Ѫ��ͼƬ
	private Bitmap bmpPlayer;						//���ͼƬ
	private int speed = 5;							//����ƶ��ٶ�
	public int x, y;								//���λ��
	private boolean isLeft, isRight, isUp, isDown;  //����ƶ�����
	private boolean isCollision;					//�Ƿ���ײ
	private int noCollisionCount = 0;				//��ײ�����޵�״̬������ײ֮���һ��ʱ�����޵еģ������Ǽ�ʱ��
	private static int NO_COLLISION_TIME = 30;		//�޵�ʱ��
	public Player(Bitmap bmpHP, Bitmap bmpPlayer) {
		super();
		this.bmpHP = bmpHP;
		this.bmpPlayer = bmpPlayer;
		this.x = (GameSurfaceView.screenWidth - bmpPlayer.getWidth())/2;
		this.y = GameSurfaceView.screenHeight - bmpPlayer.getHeight();
		isCollision = false;
	}
	
	/**
	 * ��������
	 */
	public void draw(Canvas canvas, Paint paint){
		if(isCollision){							//�������޵�ʱ��ʱ����������˸
			if(noCollisionCount % 2 == 0){			//ÿ2����Ϸѭ��������һ������
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
	 * �߼�����
	 */
	public void logic(){
		if(isLeft){										//�ж��˶�����
			x -= speed;
		}else if(isRight){
			x += speed;
		}else if(isUp){
			y -= speed;
		}else if(isDown){
			y += speed;
		}
		
		if(x < 0){										//�ж�x�߽�
			x = 0;
		}else if(x > GameSurfaceView.screenWidth - bmpPlayer.getWidth()){
			x = GameSurfaceView.screenWidth - bmpPlayer.getWidth();
		}
		
		if(y < 0){										//�ж�y�߽�
			y = 0;
		}else if(y > GameSurfaceView.screenHeight - bmpPlayer.getHeight()){
			y = GameSurfaceView.screenHeight - bmpPlayer.getHeight();
		}
		
		if(isCollision){								//����޵�״̬����ʱ
			noCollisionCount ++;
			if(noCollisionCount >= NO_COLLISION_TIME){	//�޵�ʱ����󣬽Ӵ��޵�״̬����ʼ��������
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
	 * ��ȡ����Ѫ��
	 * @return
	 */
	public int getPlayerHP() {
		return playerHP;
	}

	/**
	 * ��������Ѫ��
	 * @param bmpHP
	 */
	public void setPlayerHP(int playerHP) {
		this.playerHP = playerHP;
	}

	
	/**
	 * �ж��Ƿ�͵л���ײ
	 * @param e
	 * @return
	 */
	public boolean isCollisionWith(Enemy e){
		//���޵�ʱ����ж���ײ
		if(!isCollision){
			int x2 = e.x;
			int y2 = e.y;
			int w2 = e.frameWidth;
			int h2 = e.frameHeight;
			//�����е�δ��ײ״̬�жϳ���
			boolean b = isCollsionWithRect(x, y, bmpPlayer.getHeight(), bmpPlayer.getWidth(), x2, y2, h2, w2);
			if(b){
				isCollision = true;											//���ó�ͻ���޵�״̬��
			}
			return b;
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ���ӵ���ͻ
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
//				playerHP --;											//�����Ӧ�ý����߼�������Ȼ����
				isCollision = true;
			}
			return b;
		}
		return false;
	}
}
