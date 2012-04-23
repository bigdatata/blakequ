package com.hao.item;

import com.hao.GameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 游戏背景
 * @author Administrator
 *
 */
public class GameBackground {
	//为了循环播放，这里定义两个位图对象,图片都一样
	private Bitmap bg1;
	private Bitmap bg2;
	private int x1, y1, x2, y2;					//图片背景坐标
	private int speed = 3;						//图片滚动速度
	public GameBackground(Bitmap background) {
		this.bg1 = background;
		this.bg2 = background;
		//让图片的底部正好与屏幕对齐，此时如果图片变屏幕长，则它的y坐标就是在屏幕之外，故而为负。
		y1 = -Math.abs(background.getHeight() - GameSurfaceView.screenHeight);//注：这里确定图片高度大于屏幕***
		//第二张背景图紧接在第一张背景的上方
		//+100的原因：虽然两张背景图无缝隙连接但是因为图片资源头尾
		//直接连接不和谐，为了让视觉看不出是两张图连接而修正的位置
		y2 = y1 - background.getHeight() + 100; //注：它的初始坐标也在外面，故而是负数
	}
	
	/**
	 * 绘制背景
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.drawBitmap(bg1, x1, y1, paint);	
		canvas.drawBitmap(bg2, x2, y2, paint);
	}
	
	/**
	 * 背景逻辑，要不断的向下移动
	 */
	public void logic(){
		y1 += speed;
		y2 += speed;
		//当第一张图片的Y坐标超出屏幕，
		//立即将其坐标设置到第二张图的上方
		if(y1 > GameSurfaceView.screenHeight){
			y1 = y2 - bg1.getHeight() + 100;
		}
		//第二张同理
		if(y2 > GameSurfaceView.screenHeight){
			y2 = y1 - bg1.getHeight() + 100;
		}
	}
	
}
