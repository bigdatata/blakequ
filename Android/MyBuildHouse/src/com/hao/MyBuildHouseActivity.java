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
		// ȥ��Ӧ�ó������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��������
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myView = new MySurfaceView(this);
        setContentView(myView);
    }
    
    /**
	 * �˳�ϵͳ
	 */
	public void exit() {
		System.exit(0);
	}
}