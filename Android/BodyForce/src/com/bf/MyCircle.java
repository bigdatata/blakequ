/**
 * 
 */
package com.bf;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Himi
 *
 */
public class MyCircle {
	//圆形的宽高与半径
	float x, y, r;
	public MyCircle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	//设置圆形的X坐标
	public void setX(float x) {
		this.x = x;
	}
	//设置半径的Y坐标
	public void setY(float y) {
		this.y = y;
	}
	//绘制圆形
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawArc(new RectF(x, y, x + 2*r, y + 2*r), 0, 360, true, paint);
	}
}
