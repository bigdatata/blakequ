package com.hao;

import com.hao.MySurfaceView.OnGameInitListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements OnGameInitListener{
	public static MainActivity main;
	private MySurfaceView myView;
	private ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		main = this;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ȥ��Ӧ�ó������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��������
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myView = new MySurfaceView(this);
		myView.setInitListener(this);
		setContentView(myView);
		dialog = ProgressDialog.show(MainActivity.this, "", 
                "���ڼ�����Դ...", true);
	}

	/**
	 * �˳�ϵͳ
	 */
	public void exit() {
		System.exit(0);
	}

	@Override
	public boolean onInitOver() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		return false;
	}
}
