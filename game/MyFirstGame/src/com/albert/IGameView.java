package com.albert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class IGameView extends SurfaceView implements SurfaceHolder.Callback{

	protected SurfaceHolder surfaceHolder;  
    public static int screenW, screenH;
    protected String viewName;
	
	public IGameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screenH = getHeight();
		screenW = getWidth();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
//		this.reCycle();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ��ͼ
	 * @param	Canvas		
	 */
	public abstract void onDraw(Canvas canvas);
	
	/**
	 * ��������
	 *
	 * @param	keyCode	
	 * @return	boolean
	 */
	public abstract boolean onKeyDown(int keyCode);
	
	/**
	 * ��������
	 * @param	keyCode		
	 * @return	boolean
	 */
	public abstract boolean onKeyUp(int keyCode);
	
	/**
	 * ����
	 */
	public abstract boolean onTouchEvent(MotionEvent event);
	/**
	 * ������Դ
	 */
	public abstract void reCycle();	
	
	/**
	 * ˢ��
	 */
	public abstract void refurbish();

	/**
	 * get the name of view
	 * @return
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * set the name of view
	 * @param viewName
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

}
