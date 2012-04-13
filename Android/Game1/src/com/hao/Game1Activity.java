package com.hao;

import com.hao.testview.ImageTest;
import com.hao.testview.MySurfaceView;
import com.hao.testview.SurfaceView2;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Game1Activity extends Activity {
	
//	private ImageTest imageTest;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(new MyView(this));
//        setContentView(new MySurfaceView(this));
        setContentView(new SurfaceView2(this));
//        imageTest = new ImageTest(this);
//        setContentView(imageTest);
    }
    
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
//        }
//        return imageTest.onKeyDown(keyCode, event);
//    }
}