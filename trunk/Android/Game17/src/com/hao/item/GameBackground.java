package com.hao.item;

import com.hao.GameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ��Ϸ����
 * @author Administrator
 *
 */
public class GameBackground {
	//Ϊ��ѭ�����ţ����ﶨ������λͼ����,ͼƬ��һ��
	private Bitmap bg1;
	private Bitmap bg2;
	private int x1, y1, x2, y2;					//ͼƬ��������
	private int speed = 3;						//ͼƬ�����ٶ�
	public GameBackground(Bitmap background) {
		this.bg1 = background;
		this.bg2 = background;
		//��ͼƬ�ĵײ���������Ļ���룬��ʱ���ͼƬ����Ļ����������y�����������Ļ֮�⣬�ʶ�Ϊ����
		y1 = -Math.abs(background.getHeight() - GameSurfaceView.screenHeight);//ע������ȷ��ͼƬ�߶ȴ�����Ļ***
		//�ڶ��ű���ͼ�����ڵ�һ�ű������Ϸ�
		//+100��ԭ����Ȼ���ű���ͼ�޷�϶���ӵ�����ΪͼƬ��Դͷβ
		//ֱ�����Ӳ���г��Ϊ�����Ӿ�������������ͼ���Ӷ�������λ��
		y2 = y1 - background.getHeight() + 100; //ע�����ĳ�ʼ����Ҳ�����棬�ʶ��Ǹ���
	}
	
	/**
	 * ���Ʊ���
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint){
		canvas.drawBitmap(bg1, x1, y1, paint);	
		canvas.drawBitmap(bg2, x2, y2, paint);
	}
	
	/**
	 * �����߼���Ҫ���ϵ������ƶ�
	 */
	public void logic(){
		y1 += speed;
		y2 += speed;
		//����һ��ͼƬ��Y���곬����Ļ��
		//���������������õ��ڶ���ͼ���Ϸ�
		if(y1 > GameSurfaceView.screenHeight){
			y1 = y2 - bg1.getHeight() + 100;
		}
		//�ڶ���ͬ��
		if(y2 > GameSurfaceView.screenHeight){
			y2 = y1 - bg1.getHeight() + 100;
		}
	}
	
}
