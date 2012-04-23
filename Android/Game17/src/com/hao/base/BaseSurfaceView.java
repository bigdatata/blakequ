package com.hao.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * �Զ���SurfaceView���ں��ڻ���������̹߳������
 * @author Administrator
 *
 */
public abstract class BaseSurfaceView extends SurfaceView {
	
	public BaseSurfaceView(Context context){
		super(context);
	}

	public BaseSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * �߼�����
	 */
	public abstract void logic();
	
	/**
	 * ����ͼ��
	 */
	public abstract void draw();
}
