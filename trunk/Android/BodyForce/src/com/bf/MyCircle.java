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
	//Բ�εĿ����뾶
	float x, y, r;
	public MyCircle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	//����Բ�ε�X����
	public void setX(float x) {
		this.x = x;
	}
	//���ð뾶��Y����
	public void setY(float y) {
		this.y = y;
	}
	//����Բ��
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawArc(new RectF(x, y, x + 2*r, y + 2*r), 0, 360, true, paint);
	}
}
