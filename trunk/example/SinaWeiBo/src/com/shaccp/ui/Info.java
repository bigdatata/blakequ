package com.shaccp.ui;

import com.shaccp.logic.IWeiboActivity;

import android.app.Activity;
import android.os.Bundle;

public class Info extends Activity implements IWeiboActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
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
