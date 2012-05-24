package com.albert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class IGameView extends SurfaceView implements SurfaceHolder.Callback{

	protected SurfaceHolder sfh;  
	protected Canvas canvas;  
	protected Paint paint;  
    public static int screenW, screenH;
    protected String viewName;
	
	public IGameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
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
	protected abstract void onDraw(Canvas canvas);
	
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
	 * ������Դ
	 */
	public abstract void reCycle();	
	
	/**
	 * ˢ��
	 */
	protected abstract void refurbish();

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
