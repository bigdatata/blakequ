package com.hao;

import com.hao.base.BaseItemImpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * ��Ϸ�˵���ʼ����
 * @author quhao
 *
 */
public class GameMenu extends BaseItemImpl{
	
	private Bitmap bmpMenu;								//�˵�����
	private Bitmap bmpBtNormal, bmpBtPressed;			//��Ϸ��ʼ��ť
	private int btX, btY;								//��ťx��y����
	private boolean isPressed;							//�Ƿ���
	public GameMenu(Bitmap bmpMenu, Bitmap bmpBtNormal, Bitmap bmpBtPressed) {
		super();
		this.bmpMenu = bmpMenu;
		this.bmpBtNormal = bmpBtNormal;
		this.bmpBtPressed = bmpBtPressed;
		isPressed = false;
		btX = (GameSurfaceView.screenWidth - bmpBtNormal.getWidth())/2;	//��ťλ��
		btY = GameSurfaceView.screenHeight - bmpBtNormal.getHeight();
	}
	
	/**
	 * ������Ϸ�˵�����
	 * @param canvas ����
	 * @param paint  ����
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.drawBitmap(bmpMenu, 0, 0, paint);		//���Ʋ˵�����ͼ
		if(isPressed){									//����ʱ�İ�ťͼ
			canvas.drawBitmap(bmpBtPressed, btX, btY, paint);
		}else{											//δ����ʱ�İ�ť
			canvas.drawBitmap(bmpBtNormal, btX, btY, paint);
		}
	}
	
	/**
	 * ���²˵��¼�����
	 * @param event
	 */
	public void onTouchEvent(MotionEvent event){
		int x = (int) event.getX();							//��ǰxλ��
		int y = (int) event.getY();							//��ǰyλ��
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
			isPressed = isOnbutton(x, y);
		}else if(event.getAction() == MotionEvent.ACTION_UP){//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			if(isOnbutton(x, y)){
				isPressed = false;							//��ԭButton״̬Ϊδ����״̬
				GameSurfaceView.gameState = GameSurfaceView.GAMEING;//�ı���Ϸ״̬Ϊ��ʼ��Ϸ
			}
		}
	}
	
	/**
	 * �жϵ�ǰ����λ���Ƿ��ڰ�ť��
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
