package com.caigang.process.ui.task;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class ServiceActivity extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("ServiceActivity", "����onCreate����");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("ServiceActivity", "����onResume����");
	}

}
