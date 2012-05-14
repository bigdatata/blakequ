package com.shaccp.ui;

import android.app.Activity;
import android.os.Bundle;

import com.shaccp.logic.IWeiboActivity;

public class UserMSG extends Activity implements IWeiboActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usermsg);
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
