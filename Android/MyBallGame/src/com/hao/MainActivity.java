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
		// 去除应用程序标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myView = new MySurfaceView(this);
		myView.setInitListener(this);
		setContentView(myView);
		dialog = ProgressDialog.show(MainActivity.this, "", 
                "正在加载资源...", true);
	}

	/**
	 * 退出系统
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
