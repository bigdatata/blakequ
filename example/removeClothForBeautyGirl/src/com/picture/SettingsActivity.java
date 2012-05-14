package com.picture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity implements View.OnClickListener{
	private Button playBtn,settingBtn,aboutBtn,exitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		setUpView();
		setUpListener();
	}
	
	private void setUpView() {
		playBtn = (Button)findViewById(R.id.playBtn);
		settingBtn = (Button)findViewById(R.id.settingBtn);
		aboutBtn = (Button)findViewById(R.id.aboutBtn);
		exitBtn = (Button)findViewById(R.id.exitBtn);
	}

	private void setUpListener() {
		playBtn.setOnClickListener(this);
		settingBtn.setOnClickListener(this);
		aboutBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.playBtn: {
			Intent intent = new Intent();
    		intent.setClass(SettingsActivity.this, MainActivity.class);
    		startActivity(intent);
		}
			break;
		case R.id.settingBtn: {
		}
			break;
		case R.id.aboutBtn: {
			AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
			builder.setIcon(R.drawable.head);
			builder.setMessage("作者:helloandroid");
			builder.setPositiveButton("返回",null);
			builder.show();
		}
			break;
		case R.id.exitBtn: {
			AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
			builder.setMessage("你确定退出程序吗?");
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					SettingsActivity.this.finish();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:{
				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
				builder.setMessage("你确定退出程序吗?");
				builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						SettingsActivity.this.finish();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
