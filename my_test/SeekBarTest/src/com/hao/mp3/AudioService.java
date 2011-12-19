package com.hao.mp3;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * audio service
 * @author Administrator
 *
 */
public class AudioService extends Service implements OnBufferingUpdateListener,
OnCompletionListener, OnPreparedListener, OnErrorListener{
	/*播放的三个状态*/
	public static final int PLAY_MSG = 1;
	public static final int PAUSE_MSG = 2;
	public static final int STOP_MSG = 3;
	/*两个控制*/
	public static final int CONTINUE_MSG = 4;
	public static final int SEEK_MSG = 5;
	/*协议关键字*/
	public static final String PATH = "path";
	public static final String MSG = "msg";
	public static final String POSITION = "position";
	public static final String DURATION = "duration";
	public static final String PROCESS = "process";
	public static final String STATE = "state";
	/*广播播放进度*/
	public static final String PROCESS_MSG = "com.hao.audio.AudioService";
	/*网络播放时缓冲区*/
	public static final String SECOND_MSG = "com.hao.audio.SecondProcess";
	private static final String TAG = "AudioService";
	private boolean isPlaying = false, isPause = false, isStop = true;
	private MediaPlayer mediaPlayer = null;
	private String path;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//only play set the audioInfo
//		audioInfo = (AudioInfo) intent.getSerializableExtra("audio");
		path = intent.getStringExtra(PATH);
		int msg = intent.getIntExtra(MSG, -1);
		switch(msg){
			case PLAY_MSG:
				this.play();
				break;
			case STOP_MSG:
				this.stop();
				break;
			case CONTINUE_MSG:
				sendBroadcast(CONTINUE_MSG);
				break;
			case SEEK_MSG:
				seekTo(intent.getIntExtra(POSITION, 0));
				break;
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void setListener(){
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
	}
	
	/**
	 * play or pause audio
	 */
	public void play(){
		if(mediaPlayer == null){
			Log.e(TAG, "play mediaPlayer is null");
			createMediaPlayer(path);
			isStop = false;
			isPlaying = false;
			isPause = true;
		}
		if(mediaPlayer != null && !isStop){
			if(isPause){
				Log.e(TAG, "continue");
				mediaPlayer.start();
				isPause = false;
				isPlaying = true;
				sendBroadcast(CONTINUE_MSG);
			}else{
				Log.e(TAG, "pause");
				mediaPlayer.pause();
				isPause = true;
				isPlaying = false;
				sendBroadcast(PAUSE_MSG);
			}
		}
	}
	/**
	 * stop the audio
	 */
	public void stop(){
		if(mediaPlayer != null && !isStop){
			if(isPause || isPlaying){
				Log.e(TAG, "stop");
				sendBroadcast(STOP_MSG);
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer = null;
				isStop = true;
				stopSelf();
			}
		}
		isPlaying = false;
	}
	
	public void seekTo(int positon){
		if(mediaPlayer != null && !isStop){
			mediaPlayer.seekTo(positon);
		}
	}
	
	private void createMediaPlayer(String path){
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(path);
			mediaPlayer.setLooping(false);
			mediaPlayer.prepare();//没有调用它会出现error(-38, 0)错误
			setListener();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "start from url illegal argument exception");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "start state exception");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "start io exception");
		}
	}


	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
//		int currentProgress=100*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
//		Log.e(TAG, "进度："+currentProgress+"% play"+" "+percent+"% buffer");
		sendSecondBroadcase(percent);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCompletion");
		stop();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPrepared");
		sendBroadcast(PLAY_MSG);
	}
	
	private void sendBroadcast(int state){
		if(mediaPlayer != null){
			Bundle bd = new Bundle();
			bd.putInt(POSITION, mediaPlayer.getCurrentPosition());
			bd.putInt(DURATION, mediaPlayer.getDuration());
			Intent intent = new Intent();
			intent.setAction(PROCESS_MSG);
			intent.putExtra(PROCESS, bd);
			intent.putExtra(STATE, state);
			sendBroadcast(intent);
		}
	}
	
	private void sendSecondBroadcase(int process){
		if(mediaPlayer != null){
			Intent intent = new Intent();
			intent.setAction(SECOND_MSG);
			intent.putExtra(PROCESS, process);
			sendBroadcast(intent);
		}
	}


	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		stop();
		String error;
		if(what == MediaPlayer.MEDIA_ERROR_SERVER_DIED){
			error = "播放错误1";
		}else if(what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK){
			error = "播放错误2";
		}else{
			error = "播放错误3";
		}
		Log.e(TAG, error);
		Toast.makeText(this, error, 3000).show();
		return false;
	}

}
