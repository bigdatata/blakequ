package com.hao;

import java.util.Timer;
import java.util.TimerTask;

import com.hao.audio.AudioInfo;
import com.hao.audio.AudioService;
import com.hao.util.AppValues;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class TestMp3PlayerActivity extends Activity {
	private Button btnPause, btnStop;
	private SeekBar skbProgress;
	private ProcessUpdateReceiver receiver = null;
	private SecondUpdateReceiver secondReceiver = null;
	private int position=0, duration=0;
	private UpdateProcessListener listener;
	private ProcessTask task;
	private AudioInfo audio;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);
	
		this.setTitle("在线音乐播放");
		
		btnPause = (Button) this.findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new ClickEvent());

		btnStop = (Button) this.findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new ClickEvent());

		skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent()); 
		
		audio = new AudioInfo();
		audio.setPath("/sdcard/download/I cry.mp3");
//		audio.setPath("http://219.138.125.22/myweb/mp3/CMP3/JH19.MP3");
//		audio.setPath("http://192.168.1.104:8080/fsServer_beta_v1.0/file/test/sound/20111211/1323579950588.mp3");
//		audio.setPath("http://118.122.88.93:8080/fsServer_beta_v1.0/test/sound/1.html");
		
		listener = new UpdateProcessListener() {
			
			@Override
			public void OnUpdateListener(int state) {
				// TODO Auto-generated method stub
				/**不能暂停进度条
				Timer mTimer = new Timer();
				mTimer.schedule(new TimerTask() {//这个任务执行是根据duration，更新歌曲进度
					@Override
					public void run() {
						System.out.println("main");
						handleProgress.sendEmptyMessage(0);//这样就会调用handler更新进度
						
					}
				}, 0, duration/100);
				*/
				System.out.println("state:"+state);
				switch(state){
					case AppValues.CONTINUE_MSG:
						btnPause.setText(getResources().getString(R.string.pause));
						task.setFlag(4);
						break;
					case AppValues.PAUSE_MSG:
						btnPause.setText(getResources().getString(R.string.playing));
						task.setFlag(2);
						break;
					case AppValues.PLAY_MSG:
						task.setFlag(1);
						break;
					case AppValues.STOP_MSG:
						btnPause.setText(getResources().getString(R.string.playing));
						task.setFlag(3);
						break;
				}
			}
		};
	}
	

	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btnPause) {
				Intent intent = new Intent(TestMp3PlayerActivity.this, AudioService.class);
				intent.putExtra("audio", audio);
				intent.putExtra("msg", AppValues.PLAY_MSG);
				startService(intent);
			} else if (v == btnStop) {
				Intent intent = new Intent(TestMp3PlayerActivity.this, AudioService.class);
				intent.putExtra("msg", AppValues.STOP_MSG);
				startService(intent);
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(secondReceiver == null){
			secondReceiver = new SecondUpdateReceiver();
		}
		registerReceiver(secondReceiver, new IntentFilter(AppValues.SECOND_MSG));
		if(receiver == null){
			receiver = new ProcessUpdateReceiver();
		}
		registerReceiver(receiver, new IntentFilter(AppValues.PROCESS_MSG));
		Intent intent = new Intent(TestMp3PlayerActivity.this, AudioService.class);
		intent.putExtra("msg", AppValues.CONTINUE_MSG);
		startService(intent);
		task = new ProcessTask();
		task.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(receiver);
		unregisterReceiver(secondReceiver);
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
			Bundle bd = intent.getBundleExtra("process");
			position = bd.getInt("position");
			duration = bd.getInt("duration");
			listener.OnUpdateListener(intent.getIntExtra("state", -1));
		}
	}
	
	class SecondUpdateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int process = intent.getIntExtra("process", 0);
			skbProgress.setSecondaryProgress(process);
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
					if(flag == 1  || flag == 4){
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
				long pos = skbProgress.getMax() * position / duration;
				position+=duration/100;
				skbProgress.setProgress((int) pos);
			}
		};
	};
	
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
			this.progress = progress * duration / seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			Intent intent = new Intent(TestMp3PlayerActivity.this, AudioService.class);
			intent.putExtra("msg", AppValues.SEEK_MSG);
			intent.putExtra("position", progress);
			startService(intent);
			position = progress;
		}
	}

	private interface UpdateProcessListener{
		void OnUpdateListener(int state);
	}
}