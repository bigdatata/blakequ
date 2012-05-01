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
	//ש������ꡢ�����Ƕ�
	private float x, y, w, h, angle;
	//ש���ͼƬ��Դ
	private Bitmap bmp;
	//ש���ʼ��
	public MyTile(float x, float y, float w, float h, Bitmap bmp) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.bmp = bmp;
	}
	//ש��Ļ���
	public void drawMyTile(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x + w / 2, y + h / 2);
		canvas.drawBitmap(bmp, x, y, paint);
		canvas.restore();
	}
	//��ȡש���X����
	public float getX() {
		return x;
	}
	//��ȡש���Y����
	public float getY() {
		return y;
	}
	//����ש���x����
	public void setX(float x) {
		this.x = x;
	}
	//����ש���Y����
	public void setY(float y) {
		this.y = y;
	}
	//��ȡש��Ŀ�
	public float getWidth() {
		return w;
	}
	//��ȡש��ĸ�
	public float getHeight() {
		return h;
	}
	//����ש��Ƕ�
	public void setAngle(float angle) {
		this.angle = angle;
	}
}
