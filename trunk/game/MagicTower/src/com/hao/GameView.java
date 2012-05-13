package com.hao;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * View抽象类
 * @author Administrator
 *
 */
public abstract class GameView extends View
{
	private String name;	//当前视图名字，方便调试
	public GameView(Context context)
	{
		super(context);
	}
	/**
	 * 绘图
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	protected abstract void onDraw(Canvas canvas);
	/**
	 * 按键按下
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyDown(int keyCode);
	/**
	 * 按键弹起
	 *
	 * @param		N/A		
	 *
	 * @return		null
	 */
	public abstract boolean onKeyUp(int keyCode);
	/**
	 * 回收资源
	 *
	 */
	protected abstract void reCycle();	
	
	/**
	 * 刷新
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

