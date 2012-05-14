package com.hao.otheraudio;

import com.hao.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

public class AudioActivity extends Activity implements OnClickListener{

	private Button btnPause, btnStop;
	private SeekBar skbProgress;
	private IServicePlayer iPlayer = null;
	private MyThread task;
	private int duration = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);
		this.setTitle("在线音乐播放");
		
		btnPause = (Button) this.findViewById(R.id.btnPause);
		btnPause.setOnClickListener(this);

		btnStop = (Button) this.findViewById(R.id.btnStop);
		btnStop.setOnClickListener(this);

		skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		
		Intent intent = new Intent(AudioActivity.this, OtherAudioService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
//		intent.putExtra("path", "/sdcard/download/I cry.mp3");
		intent.putExtra("path", "http://219.138.125.22/myweb/mp3/CMP3/JH19.MP3");
		startService(intent);
		task = new MyThread();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(conn);
		task.setType(false);
	}

	Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int position = msg.arg1;
			int secondPosition = msg.arg2;
			if (duration > 0 && position < duration && position > 0) {
				long pos = skbProgress.getMax() * position / duration;
				skbProgress.setProgress((int) pos);
			}
			if(secondPosition != 0){
				skbProgress.setSecondaryProgress(secondPosition);
			}
		}
		
	};
	
	private class MyThread extends Thread{

		boolean type = true;
		public void setType(boolean type){
			this.type = type;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(type){
				try {
					if(iPlayer.isPlaying()){
						Message msg = myHandler.obtainMessage();
						msg.arg1 = iPlayer.getCurrentPosition();
						msg.arg2 = iPlayer.getSecondPosition();
						duration = iPlayer.getDuration();
						myHandler.sendMessage(msg);
					}
					Thread.sleep(duration/100);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.btnPause:
			try {
				iPlayer.play();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case R.id.btnStop:
			try {
				iPlayer.stop();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		}
	}

	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			iPlayer = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.i("onServiceConnected", "ServiceConnection -> onServiceConnected");
			iPlayer = IServicePlayer.Stub.asInterface(service);
			task.start();
		}
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
			try {
				iPlayer.seekTo(progress);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
