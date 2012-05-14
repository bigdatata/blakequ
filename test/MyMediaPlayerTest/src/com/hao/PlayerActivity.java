package com.hao;

import com.hao.otheraudio.AudioActivity;
import com.hao.video.view.OtherVideoActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayerActivity extends Activity implements OnClickListener{
	Button video, audio, otherV,otherA;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		video = (Button) findViewById(R.id.video);
		video.setOnClickListener(this);
		audio = (Button) findViewById(R.id.audio);
		audio.setOnClickListener(this);
		otherV = (Button) findViewById(R.id.othervideo);
		otherV.setOnClickListener(this);
		otherA = (Button) findViewById(R.id.aidlaudio);
		otherA.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId()){
			case R.id.audio:
				intent = new Intent(PlayerActivity.this, TestMp3PlayerActivity.class);
				startActivity(intent);
				break;
			case R.id.video:
				intent = new Intent(PlayerActivity.this, VideoActivity.class);
				startActivity(intent);
				break;
			case R.id.othervideo:
				intent = new Intent(PlayerActivity.this, OtherVideoActivity.class);
				startActivity(intent);
				break;
			case R.id.aidlaudio:
				intent = new Intent(PlayerActivity.this, AudioActivity.class);
				startActivity(intent);
				break;
		}
	}

}
