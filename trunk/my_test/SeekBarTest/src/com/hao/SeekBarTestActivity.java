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
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarTestActivity extends Activity {
	SeekBar mySB;
	ImageButton bt;
	ProgressButton bp;
	int len = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_progress_bar);
        bp = new ProgressButton(this);
        
        /*bt = (ImageButton) findViewById(R.id.imgbt);
        
        bp.setProgress(23);
        bp.setMax(50, bt.getWidth());
        
        bt.setBackgroundDrawable(bp.getBackground());
        */
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        ll.setPadding(20, 10, 0, 0);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        bp.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        bp.setMax(50, 100);
        ll.addView(bp);
        setContentView(ll);
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