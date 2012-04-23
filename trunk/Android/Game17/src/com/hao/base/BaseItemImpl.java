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
	 * ������ײ���
	 * @param x1 ����1��x����
	 * @param y1 ����1��y����
	 * @param h1 ����1�ĸ�
	 * @param w1 ����1�Ŀ�
	 * @param x2 ����2��x����
	 * @param y2 ����2��y����
	 * @param h2 ����2�ĸ�
	 * @param w2 ����2�Ŀ�
	 * @return
	 */
	public boolean isCollsionWithRect(int x1, int y1, int h1, int w1, int x2, int y2, int h2, int w2){
		//�����е�δ��ײ״̬�жϳ���
		if(x1 >= x2 && x1 >= x2+w2){ 				//(x1, y1)λ��(x2, y2)���ұ�
			return false;
		}else if(x1 <= x2 && x2 >= x1+w1){			//(x1, y1)λ��(x2, y2)�����
			return false;
		}else if(y2 >= y1 && y2 >= y1+h1){			//(x1, y1)λ��(x2, y2)���ϱ�
			return false;
		}else if(y2 <= y1 && y1 >= y2+h2){			//(x1, y1)λ��(x2, y2)���±�
			return false;
		}
		return true;
	}
	
	/**
	 * ���Բ���Ƿ���ײ
	 * @param x1 Բ��1��x����
	 * @param y1 Բ��1��y����
	 * @param r1 Բ��1�İ뾶
	 * @param x2 Բ��2��x����
	 * @param y2 Բ��2��y����
	 * @param r2 Բ��2�İ뾶
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
