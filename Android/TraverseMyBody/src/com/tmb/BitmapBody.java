/**
 * 
 */
package com.tmb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Himi
 * 
 */
public class BitmapBody {
	private Bitmap bmp;// 图片
	private float x, y, angle;// 图片的坐标和角度，这个会不断改变

	public BitmapBody(Bitmap bmp, float x, float y) {
		this.bmp = bmp;
		this.x = x;
		this.y = y;
	}
	// 绘制图片
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.rotate(angle, x + bmp.getWidth() / 2, y + bmp.getHeight() / 2);
		canvas.drawBitmap(bmp, x, y, paint);
		canvas.restore();
	}
	// 设置角度
	public void setAngle(float angele) {
		this.angle = angele;
	}
	// 设置X轴坐标
	public void setX(float bodyX) {
		this.x = bodyX;
	}
	// 设置Y轴坐标
	public void setY(float y) {
		this.y = y;
	}
	// 获取图片的宽
	public int getW() {
		return bmp.getWidth();
	}
	// 获取图片的高
	public int getH() {
		return bmp.getHeight();
	}
}
