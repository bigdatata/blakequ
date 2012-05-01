/**
 * 
 */
package com.hao.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Himi
 * 
 */
public class MyRect {
	// 圆形的宽高与半径
	public float x, y, w, h,angle;
	private Bitmap bmp = null;
	
	/**
	 * 自定义矩形
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public MyRect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * 图片矩形
	 * @param bmp
	 * @param x
	 * @param y
	 */
	public MyRect(Bitmap bmp, float x, float y){
		this.bmp = bmp;
		this.x = x;
		this.y = y;
		this.w = bmp.getWidth();
		this.h = bmp.getHeight();
	}

	// 设置X坐标
	public void setX(float x) {
		this.x = x;
	}

	// 设置Y坐标
	public void setY(float y) {
		this.y = y;
	}
	// 设置angle角度
	public void setAngle(float angle) {
		this.angle = angle;
	}
	// 绘制矩形
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x+w/2, y+h/2);
		if(bmp != null){
			canvas.drawBitmap(bmp, x, y, paint);
		}else{
			canvas.drawRect(new RectF(x , y  , x + w, y + h), paint);
		}
		canvas.restore();
	}
}
