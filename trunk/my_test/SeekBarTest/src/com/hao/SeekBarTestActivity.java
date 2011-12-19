package com.hao;

import java.util.Timer;
import java.util.TimerTask;

import com.hao.ProgressView.OnProgressChanged;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarTestActivity extends Activity {
	SeekBar mySB;
	ProgressButton bp;
	int len = 0;
	boolean flag = true;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        bp = (ProgressButton) findViewById(R.id.pb1);
        bp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("main onck");
				bp.setStateChanged(flag);
				flag = !flag;
			}
		});
        bp.setMax(50, 100);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		}, 0, 2000);
        
        
    }
    
    Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			len+=3;
			bp.setProgress(len);
			bp.setSecondProgress(len+4);
		}
    	
    };
}