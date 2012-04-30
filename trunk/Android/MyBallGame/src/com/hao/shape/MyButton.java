package com.hao.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class MyButton {
	//��ť��λ�ü��߿�
	private float x, y, w, h;
	//��ťͼƬ
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
	 * ���ư�ť
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.drawRect(x, y, x + w, y + h, paint);
	}
	
	/**
	 * ��ⰴť�Ƿ񱻰���
	 * @param event
	 * @return
	 */
	public boolean isPreesed(MotionEvent event){
		//�����û������λ���ж��Ƿ�����Ļ
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
