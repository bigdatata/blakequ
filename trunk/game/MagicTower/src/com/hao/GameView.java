package com.hao;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * View������
 * @author Administrator
 *
 */
public abstract class GameView extends View
{
	private String name;	//��ǰ��ͼ���֣��������
	public GameView(Context context)
	{
		super(context);
	}
	/**
	 * ��ͼ
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	protected abstract void onDraw(Canvas canvas);
	/**
	 * ��������
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyDown(int keyCode);
	/**
	 * ��������
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyUp(int keyCode);
	/**
	 * ������Դ
	 *
	 */
	protected abstract void reCycle();	
	
	/**
	 * ˢ��
	 *
	 */
	protected abstract void refurbish();
	
	/**
	 * get the name of view
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set the name of view
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}

