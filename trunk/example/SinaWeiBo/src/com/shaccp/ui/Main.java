package com.shaccp.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


import com.shaccp.logic.IWeiboActivity;

public class Main extends TabActivity implements IWeiboActivity {
	
	public TabHost mth;
	public static final String TAB_HOME="TabHome";
	public static final String TAB_MSG="TabMSG";
	public RadioGroup mainbtGroup;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		
		mth = getTabHost();
		TabSpec ts1= mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
		ts1.setContent(new Intent(Main.this,Home.class));
		mth.addTab(ts1);
		TabSpec ts2= mth.newTabSpec(TAB_MSG).setIndicator(TAB_MSG);
		ts2.setContent(new Intent(Main.this,UserMSG.class));
		mth.addTab(ts2);
		
		mainbtGroup = (RadioGroup)this.findViewById(R.id.main_radio);
		
		mainbtGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				Log.d("select ID", "---------------"+checkedId);
				switch(checkedId)
				{
				case R.id.radio_button0://首页
					 mth.setCurrentTabByTag(TAB_HOME);
					 break;
				case R.id.radio_button1://信息
					 mth.setCurrentTabByTag(TAB_MSG);
					 break;
				case R.id.radio_button2://个人资料
				case R.id.radio_button3://搜索
				case R.id.radio_button4://更多	
				
				}
			}
		});
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... args) {
		// TODO Auto-generated method stub
		
	}

}
