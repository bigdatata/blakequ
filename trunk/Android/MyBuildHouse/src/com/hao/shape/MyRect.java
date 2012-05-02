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
	// Բ�εĿ����뾶
	public float x, y, w, h,angle;
	private Bitmap bmp = null;
	
	/**
	 * �Զ������
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
	 * ͼƬ����
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
		if(bmp != null){
			canvas.drawBitmap(bmp, x, y, paint);
		}else{
			canvas.drawRect(new RectF(x , y  , x + w, y + h), paint);
		}
		canvas.restore();
	}
}
