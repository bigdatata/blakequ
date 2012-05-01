/**
 * 
 */
package com.bh;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Himi
 *
 */
public class MyTile {
	//砖块的坐标、宽高与角度
	private float x, y, w, h, angle;
	//砖块的图片资源
	private Bitmap bmp;
	//砖块初始化
	public MyTile(float x, float y, float w, float h, Bitmap bmp) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.bmp = bmp;
	}
	//砖块的绘制
	public void drawMyTile(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x + w / 2, y + h / 2);
		canvas.drawBitmap(bmp, x, y, paint);
		canvas.restore();
	}
	//获取砖块的X坐标
	public float getX() {
		return x;
	}
	//获取砖块的Y坐标
	public float getY() {
		return y;
	}
	//设置砖块的x坐标
	public void setX(float x) {
		this.x = x;
	}
	//设置砖块的Y坐标
	public void setY(float y) {
		this.y = y;
	}
	//获取砖块的宽
	public float getWidth() {
		return w;
	}
	//获取砖块的高
	public float getHeight() {
		return h;
	}
	//设置砖块角度
	public void setAngle(float angle) {
		this.angle = angle;
	}
}
