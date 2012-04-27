/**
 * 
 */
package com.mj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Himi
 * 
 */
public class MyRect {
	// Բ�εĿ����뾶
	float x, y, w, h,angle;

	public MyRect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	// ����X����
	public void setX(float x) {
		this.x = x;
	}

	// ����Y����
	public void setY(float y) {
		this.y = y;
	}
	// ����angle�Ƕ�
	public void setAngle(float angle) {
		this.angle = angle;
	}
	// ���ƾ���
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x+w/2, y+h/2);
		canvas.drawRect(new RectF(x , y  , x + w, y + h), paint);
		canvas.restore();
	}
}
