package com.yarin.android.GameEngine;
import com.hao.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
public class GameActivity extends Activity 
{
	public static Context mContext = null;
    /* ���� */
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.main);
    }
    /* �����˵� */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}
	/* ���� */
	protected void onDestroy()
	{
		super.onDestroy();
	}
	/* �������� */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode, event);
	}
	/* �����ظ����� */
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}
	/* �����ͷ� */
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return super.onKeyUp(keyCode, event);
	}
	/* �˵��¼� */
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		return super.onMenuItemSelected(featureId, item);
	}
	/* ��ͣ */
	protected void onPause()
	{
		super.onPause();
	}
	/* ���¿�ʼ */
	protected void onRestart()
	{
		super.onRestart();
	}
	/* ���¼��� */
	protected void onResume()
	{
		super.onResume();
	}
	/* ��ʼ */
	protected void onStart()
	{
		super.onStart();
	}
	/* ֹͣ */
	protected void onStop()
	{
		super.onStop();
	}
	/* �����¼� */
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}
	/* ���ñ��� */
	//ʹ��MVC�������Դ�ļ�ͨ��R.java�ļ���ȡ
	public void setTitle(int titleId)
	{
		super.setTitle(titleId);
	}
}