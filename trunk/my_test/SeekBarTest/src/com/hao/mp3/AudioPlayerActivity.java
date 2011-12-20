package com.hao.mp3;

import com.hao.ProgressButton;
import com.hao.R;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public class AudioPlayerActivity extends Activity {
	ProgressButton bp;
	private boolean flag = true;
	private ProcessUpdateReceiver receiver = null;
	private SecondUpdateReceiver secondReceiver = null;
	private int position=0, duration=0;
	private UpdateProcessListener listener;
	private ProcessTask task;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
	
		this.setTitle("在线音乐播放");
		
		bp = (ProgressButton) findViewById(R.id.pb1);
		bp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bp.setStateChanged(flag);
				// TODO Auto-generated method stub
				Intent intent = new Intent(AudioPlayerActivity.this, AudioService.class);
				intent.putExtra(AudioService.PATH, "http://219.138.125.22/myweb/mp3/CMP3/JH19.MP3");
//				intent.putExtra(AudioService.PATH, "/sdcard/download/I cry.mp3");
				intent.putExtra(AudioService.MSG, AudioService.PLAY_MSG);
				startService(intent);
				flag = !flag;
			}
		});
		bp.setMax(100);
		listener = new UpdateProcessListener() {
			
			@Override
			public void OnUpdateListener(int state) {
				// TODO Auto-generated method stub
				System.out.println("state:"+state);
				switch(state){
					case AudioService.CONTINUE_MSG:
						bp.setStateChanged(true);
						task.setFlag(AudioService.CONTINUE_MSG);
						break;
					case AudioService.PAUSE_MSG:
						bp.setStateChanged(false);
						task.setFlag(AudioService.PAUSE_MSG);
						break;
					case AudioService.PLAY_MSG:
						task.setFlag(AudioService.PLAY_MSG);
						break;
					case AudioService.STOP_MSG:
						bp.setStateChanged(false);
						task.setFlag(AudioService.STOP_MSG);
						break;
				}
			}
		};
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(secondReceiver == null){
			secondReceiver = new SecondUpdateReceiver();
		}
		registerReceiver(secondReceiver, new IntentFilter(AudioService.SECOND_MSG));
		if(receiver == null){
			receiver = new ProcessUpdateReceiver();
		}
		registerReceiver(receiver, new IntentFilter(AudioService.PROCESS_MSG));
//		Intent intent = new Intent(AudioPlayerActivity.this, AudioService.class);
//		intent.putExtra("msg", AudioService.CONTINUE_MSG);
//		startService(intent);
		task = new ProcessTask();
		task.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(receiver);
		unregisterReceiver(secondReceiver);
		Intent intent = new Intent(AudioPlayerActivity.this, AudioService.class);
		intent.putExtra("msg", AudioService.STOP_MSG);
		stopService(intent);
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		task.setType(false);
	}


	class ProcessUpdateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bd = intent.getBundleExtra(AudioService.PROCESS);
			position = bd.getInt(AudioService.POSITION);
			duration = bd.getInt(AudioService.DURATION);
			listener.OnUpdateListener(intent.getIntExtra(AudioService.STATE, -1));
		}
	}
	
	class SecondUpdateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int process = intent.getIntExtra(AudioService.PROCESS, 0);
			bp.setSecondProgress(process);
		}
		
	}
	
	private class ProcessTask extends Thread{
		boolean type = true;
		int flag = -1;
		public void setType(boolean type){
			this.type = type;
		}
		public void setFlag(int flag){
			this.flag = flag;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(type){
					if(flag == AudioService.PLAY_MSG  || flag == AudioService.CONTINUE_MSG){
						handleProgress.sendEmptyMessage(0);//这样就会调用handler更新进度
					}
					Thread.sleep(duration/100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {
			if (duration > 0 && position < duration) {
				long pos = bp.getMax() * position / duration;
				position+=duration/100;
				bp.setProgress((int) pos);
				bp.setTime(position, duration);
			}
		};
	};
	

	private interface UpdateProcessListener{
		void OnUpdateListener(int state);
	}
}