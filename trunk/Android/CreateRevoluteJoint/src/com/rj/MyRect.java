/**
 * 
 */
package com.rj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Himi
 * 
 */
public class MyRect {
	// 圆形的宽高与半径
	float x, y, w, h,angle;

	public MyRect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
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
		canvas.drawRect(new RectF(x , y  , x + w, y + h), paint);
		canvas.restore();
	}
}
