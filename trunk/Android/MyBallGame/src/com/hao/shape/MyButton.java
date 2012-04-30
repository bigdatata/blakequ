package com.hao.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class MyButton {
	//按钮的位置及高宽
	private float x, y, w, h;
	//按钮图片
	private Bitmap bmpButton;
	public MyButton(float x, float y, Bitmap bmpButton) {
		super();
		this.x = x;
		this.y = y;
		this.bmpButton = bmpButton;
		this.w = bmpButton.getWidth();
		this.h = bmpButton.getHeight();
	}
	
	/**
	 * 绘制按钮
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.drawRect(x, y, x + w, y + h, paint);
	}
	
	/**
	 * 检测按钮是否被按下
	 * @param event
	 * @return
	 */
	public boolean isPreesed(MotionEvent event){
		//根据用户点击的位置判断是否点击屏幕
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(event.getX() > x && event.getX() < x + w){
				if(event.getY() > y && event.getY() < y + h){
					return true;
				}
			}
		}
		return false;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	
	
}
