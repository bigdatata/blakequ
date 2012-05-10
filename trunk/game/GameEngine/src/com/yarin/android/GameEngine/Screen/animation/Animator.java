package com.yarin.android.GameEngine.Screen.animation;

import javax.microedition.lcdui.game.Sprite;

import android.graphics.Bitmap;

import com.yarin.android.GameEngine.Util.Calculagraph;

//������ɫ��
public class Animator extends Sprite
{
	private Calculagraph cal=null;
	
	public Animator(Bitmap img,
			int frameWidth,int frameHeight,
			int loopTime){
		super(img,frameWidth,frameHeight);
		cal=new Calculagraph(loopTime);
	}
	
	public Animator(Sprite s,int loopTime){
		super(s);
		cal=new Calculagraph(loopTime);
	}
	
	/**
	 * ���Ŷ���
	 *
	 */
	public void PlayAnimation(){
		if (cal.getLoopTime()>0){
			//�����ʱ�������¼�ʱ��������һFrame
			if (cal.isTimeout()){
				cal.reset();
				this.nextFrame();
			}
			//���������ʱ
			else{
				cal.calculate();
			}
		}
	}
	
	/**
	 * ֹͣ���Ŷ���
	 *
	 */
	public void StopAnimation(){
		cal.reset();
	}
	/**
	 * ˢ�¶�����λ��
	 * @param x ����������ϵ��x��λ��
	 * @param y ����������ϵ��y��λ��
	 */
	public void flushPosition(int x,int y){
		setRefPixelPosition(x,y);
	}
}

