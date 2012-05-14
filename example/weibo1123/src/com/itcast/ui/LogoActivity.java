package com.itcast.ui;

import com.itcast.util.GPSPoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class LogoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉Activity标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉任务条
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.logo);
        
        //定义splash   动画 
        AlphaAnimation aa=new AlphaAnimation(0.1f,1.0f);
        aa.setDuration(3000);
        this.findViewById(R.id.ImageView01).startAnimation(aa);
        aa.setAnimationListener(new AnimationListener()
        {

			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(LogoActivity.this,LoginActivity.class);
				LogoActivity.this.startActivity(it);
				finish();
			}

			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        }
        );
    }
}