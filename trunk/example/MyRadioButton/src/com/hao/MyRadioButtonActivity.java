package com.hao;

import com.hao.layout.MyRadioButton;
import com.hao.layout.MyRadioGroup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyRadioButtonActivity extends Activity {
	MyRadioButton myRadio;
	MyRadioGroup group;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        group = (MyRadioGroup) findViewById(R.id.group);
        int n = group.getChildCount();
        for(int i = 0; i<n; i++){
        	myRadio = (MyRadioButton) group.getChildAt(i);
        	myRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        		
        		@Override
        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        			// TODO Auto-generated method stub
        			myRadio.setText(String.valueOf(isChecked));
        			myRadio.setmValue(isChecked);
        		}
        	});
        }
        
    }
}