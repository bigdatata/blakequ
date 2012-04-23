package com.hao.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hao.base.BaseItemImpl;

/**
 * ��ըЧ��
 * @author Administrator
 *
 */
public class Boom extends BaseItemImpl {
	
	private Bitmap bmpBoom;											//��ըͼƬ
	public int frameWidth, frameHeight;								//ÿ֡�ĳ���
	private int frameNum;											//֡����
	private int frameIndex;											//֡����
	public int x, y;												//��ǰ֡����
	private boolean isEnd;											//��ըЧ������
	private int boomType;											//��ըЧ�����ͣ��м���Ч����
	public static final int BOOM_FOR_BULLECT = 1;					//ը����ըЧ��
	public static final int BOOM_FOR_BOSS = 2;						//boss��ըЧ��
	
	
	public Boom(Bitmap bmpBoom, int x, int y, int boomType) {
		super();
		init(boomType);
		this.bmpBoom = bmpBoom;
		this.x = x;
		this.y = y;
		this.boomType = boomType;
		frameWidth = bmpBoom.getWidth()/frameNum;
		frameHeight = bmpBoom.getHeight();
		isEnd = false;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.draw(canvas, paint);
		canvas.save();
		canvas.clipRect(x, y, x + frameWidth, y + frameHeight);
		canvas.drawBitmap(bmpBoom, x - frameIndex*frameWidth, y, paint);
		canvas.restore();
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		super.logic();
		if(frameIndex < frameNum){
			frameIndex ++;
		}else{
			isEnd = true;
		}
	}
	
	
	/**
	 * �жϱ�ըЧ���Ƿ����
	 * @return
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * ���ݱ�ը��������֡����
	 * @param boomType
	 */
	private void init(int boomType){
		switch(boomType){
		case BOOM_FOR_BULLECT:
			frameNum = 7;
			break;
		case BOOM_FOR_BOSS:
			frameNum = 5;
			break;
		}
	}
}
