package com.hao;

import com.hao.testview.ImageTest;
import com.hao.testview.MySurfaceView;
import com.hao.testview.MySurfaceView2;
import com.hao.testview.SurfaceView2;
import com.hao.testview.TestCanvasStore;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Game1Activity extends Activity {
	
//	private ImageTest imageTest;
	private Button b1, b2;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(new MyView(this));
//        setContentView(new MySurfaceView(this));
//        setContentView(new TestCanvasStore(this));
//        setContentView(new SurfaceView2(this));
//        imageTest = new ImageTest(this);
//        setContentView(imageTest);
        setContentView(R.layout.surface3);
        initView();
    }
    
    private void initView(){
    	b1 = (Button) findViewById(R.id.button1);
    	b2 = (Button) findViewById(R.id.button2);
    	b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MySurfaceView2.text = "Button one";
			}
		});
    	b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MySurfaceView2.text = "Button two";
			}
		});
    }
    
}