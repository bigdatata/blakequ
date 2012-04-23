package com.hao.base;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface BaseItemInterface {
	/**
	 * 绘制动画
	 * @param canvas 画布
	 * @param paint  画笔
	 */
	void draw(Canvas canvas, Paint paint);
	
	/**
	 * 逻辑处理
	 */
	void logic();
}
