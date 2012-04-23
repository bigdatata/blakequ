package com.hao.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hao.base.BaseItemImpl;

/**
 * 爆炸效果
 * @author Administrator
 *
 */
public class Boom extends BaseItemImpl {
	
	private Bitmap bmpBoom;											//爆炸图片
	public int frameWidth, frameHeight;								//每帧的长宽
	private int frameNum;											//帧数量
	private int frameIndex;											//帧索引
	public int x, y;												//当前帧坐标
	private boolean isEnd;											//爆炸效果结束
	private int boomType;											//爆炸效果类型（有几种效果）
	public static final int BOOM_FOR_BULLECT = 1;					//炸弹爆炸效果
	public static final int BOOM_FOR_BOSS = 2;						//boss爆炸效果
	
	
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
	 * 判断爆炸效果是否结束
	 * @return
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * 根据爆炸类型设置帧数量
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
