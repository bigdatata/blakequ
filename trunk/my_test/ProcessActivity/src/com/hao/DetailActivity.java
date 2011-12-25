package com.hao;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String procNameString = bundle.getString("procNameString");
		TextView tv = new TextView(DetailActivity.this);
		tv.setText(procNameString);
		setContentView(tv);
	}

}
