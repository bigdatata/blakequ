package com.hao;

import com.layout.TransparentPanel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;

public class MySlidingDrawableActivity extends Activity{

	CheckBox c1,c2,c3;
	int key=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        final TransparentPanel popup = (TransparentPanel) findViewById(R.id.popup_window);
    	popup.setVisibility(View.GONE);
		final Button btn=(Button)findViewById(R.id.show_popup_button);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(key==0){
					key=1;
					popup.setVisibility(View.VISIBLE);
					Animation animation = AnimationUtils.loadAnimation(MySlidingDrawableActivity.this, R.anim.popup_enter);
					popup.setAnimation(animation);
					btn.setAnimation(animation);
				}
				else if(key==1){
					key=0;
					Animation animation = AnimationUtils.loadAnimation(MySlidingDrawableActivity.this, R.anim.popup_exit);
					popup.setAnimation(animation);
					popup.setVisibility(View.GONE);
					btn.setAnimation(animation);
				}
			}
		});
	}
}
