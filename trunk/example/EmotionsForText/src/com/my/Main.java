package com.my;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView tvShow = (TextView) findViewById(R.id.tvShow);
		TextView tvSource = (TextView) findViewById(R.id.tvSource);
		
		SmileyParser parser = new SmileyParser(this);
		String text = "[����][����][���][����][������]";
		tvSource.setText("ԭ�ģ�"+text);
		tvShow.setText(parser.replace("SmileyParser�ദ���:"+text));
	}
	

}