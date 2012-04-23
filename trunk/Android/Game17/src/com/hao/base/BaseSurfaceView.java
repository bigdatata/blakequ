package com.hao.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * 自定义SurfaceView，在后期还可以添加线程管理机制
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
	 * 逻辑处理
	 */
	public abstract void logic();
	
	/**
	 * 绘制图形
	 */
	public abstract void draw();
}
