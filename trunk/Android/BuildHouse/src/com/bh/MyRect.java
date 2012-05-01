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
	//����ͼ�ε�λ�á������Ƕ�
	private float x, y, w, h, angle;
	//����ͼ�εĳ�ʼ��
	public MyRect(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	//����ͼ�λ��ƺ���
	public void drawRect(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x + w / 2, y + h / 2);
		canvas.drawRect(x, y, x + w, y + h, paint);
		canvas.restore();
	}
	//���þ���ͼ�ε�X����
	public void setX(float x) {
		this.x = x;
	}
	//���þ���ͼ�ε�Y����
	public void setY(float y) {
		this.y = y;
	}
	//���þ���ͼ�εĽǶ�
	public void setAngle(float angle) {
		this.angle = angle;
	}
	//��ȡ����ͼ�εĿ�
	public float getWidth() {
		return w;
	}
	//��ȡ����ͼ�εĸ�
	public float getHeight() {
		return h;
	}
}
