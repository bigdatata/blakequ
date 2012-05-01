/**
 * 
 */
package com.bh;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Himi
 *
 */
public class MyRect {
	//矩形图形的位置、宽高与角度
	private float x, y, w, h, angle;
	//矩形图形的初始化
	public MyRect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	//矩形图形绘制函数
	public void drawRect(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x + w / 2, y + h / 2);
		canvas.drawRect(x, y, x + w, y + h, paint);
		canvas.restore();
	}
	//设置矩形图形的X坐标
	public void setX(float x) {
		this.x = x;
	}
	//设置矩形图形的Y坐标
	public void setY(float y) {
		this.y = y;
	}
	//设置矩形图形的角度
	public void setAngle(float angle) {
		this.angle = angle;
	}
	//获取矩形图形的宽
	public float getWidth() {
		return w;
	}
	//获取矩形图形的高
	public float getHeight() {
		return h;
	}
}
