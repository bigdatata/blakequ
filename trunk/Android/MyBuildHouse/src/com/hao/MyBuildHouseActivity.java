package com.hao;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MyBuildHouseActivity extends Activity {
	
	public static MyBuildHouseActivity main;
	private MySurfaceView myView;
    /** Called when the activity is first created. */
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
        setContentView(myView);
    }
    
    /**
	 * 退出系统
	 */
	public void exit() {
		System.exit(0);
	}
}