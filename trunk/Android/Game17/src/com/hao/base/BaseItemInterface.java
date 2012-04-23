package com.hao.base;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface BaseItemInterface {
	/**
	 * ���ƶ���
	 * @param canvas ����
	 * @param paint  ����
	 */
	void draw(Canvas canvas, Paint paint);
	
	/**
	 * �߼�����
	 */
	void logic();
}
