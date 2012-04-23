package com.hao.base;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class BaseItemImpl implements BaseItemInterface {

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub

	}

	/**
	 * 矩形碰撞检测
	 * @param x1 矩形1的x坐标
	 * @param y1 矩形1的y坐标
	 * @param h1 矩形1的高
	 * @param w1 矩形1的宽
	 * @param x2 矩形2的x坐标
	 * @param y2 矩形2的y坐标
	 * @param h2 矩形2的高
	 * @param w2 矩形2的宽
	 * @return
	 */
	public boolean isCollsionWithRect(int x1, int y1, int h1, int w1, int x2, int y2, int h2, int w2){
		//将所有的未碰撞状态判断出来
		if(x1 >= x2 && x1 >= x2+w2){ 				//(x1, y1)位于(x2, y2)的右边
			return false;
		}else if(x1 <= x2 && x2 >= x1+w1){			//(x1, y1)位于(x2, y2)的左边
			return false;
		}else if(y2 >= y1 && y2 >= y1+h1){			//(x1, y1)位于(x2, y2)的上边
			return false;
		}else if(y2 <= y1 && y1 >= y2+h2){			//(x1, y1)位于(x2, y2)的下边
			return false;
		}
		return true;
	}
	
	/**
	 * 检测圆形是否碰撞
	 * @param x1 圆形1的x坐标
	 * @param y1 圆形1的y坐标
	 * @param r1 圆形1的半径
	 * @param x2 圆形2的x坐标
	 * @param y2 圆形2的y坐标
	 * @param r2 圆形2的半径
	 * @return
	 */
	public boolean isCollisionWithCir(int x1, int y1, int r1, int x2, int y2, int r2){
		float length = (float) Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		if(length >= r1+r2){
			return false;
		}
		return true;
	}
}
