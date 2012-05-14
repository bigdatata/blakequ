package com.hao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;

public class SlidingDrawerTestActivity extends Activity {
    /** Called when the activity is first created. */
	SlidingDrawer slide;
	Button bt, bt1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        slide = (SlidingDrawer) findViewById(R.id.sliding);
        bt = (Button) findViewById(R.id.handle);
        bt1 = (Button) findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SlidingDrawerTestActivity.this, MySlidingDrawableActivity.class);
				startActivity(intent);
			}
		});
        
        slide.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			
			@Override
			public void onDrawerOpened() {
				// TODO Auto-generated method stub
				bt.setText("关闭");
			}
		});
        
        slide.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			
			@Override
			public void onDrawerClosed() {
				// TODO Auto-generated method stub
				bt.setText("打开");
			}
		});
        
        slide.setOnDrawerScrollListener(new OnDrawerScrollListener() {
			
			@Override
			public void onScrollStarted() {
				// TODO Auto-generated method stub
				System.out.println("滑动开始");
			}
			
			@Override
			public void onScrollEnded() {
				// TODO Auto-generated method stub
				System.out.println("滑动结束");
			}
		});
    }
}