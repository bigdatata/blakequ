package com.hao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class PlaneGame extends Activity {
	
	public static String TAG = "PlaneGame";
	public static PlaneGame instance;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
		//设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//显示自定义的SurfaceView视图
        setContentView(new GameSurfaceView(this, null));
    }
}