package com.hao.audio;

import java.io.IOException;

import com.hao.R;
import com.hao.TestMp3PlayerActivity;
import com.hao.util.AppValues;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
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

	private static final String TAG = "AudioService";
	private boolean isPlaying = false, isPause = false, isStop = true;
	private AudioInfo audioInfo;
	private MediaPlayer mediaPlayer = null;
	private NotificationManager mNM;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//only play set the audioInfo
		audioInfo = (AudioInfo) intent.getSerializableExtra("audio");
		int msg = intent.getIntExtra("msg", -1);
		switch(msg){
			case AppValues.PLAY_MSG:
				this.play();
				break;
			case AppValues.STOP_MSG:
				this.stop();
				break;
			case AppValues.CONTINUE_MSG:
				sendBroadcast(AppValues.CONTINUE_MSG);
				break;
			case AppValues.SEEK_MSG:
				seekTo(intent.getIntExtra("position", 0));
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
			createMediaPlayer(audioInfo.getPath());
			getAudioName(audioInfo.getPath());
			showNotification();
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
				sendBroadcast(AppValues.CONTINUE_MSG);
			}else{
				Log.e(TAG, "pause");
				mediaPlayer.pause();
				isPause = true;
				isPlaying = false;
				sendBroadcast(AppValues.PAUSE_MSG);
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
				sendBroadcast(AppValues.STOP_MSG);
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer = null;
				isStop = true;
				stopSelf();
				mNM.cancel(R.string.music_title);
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
	
	private String getAudioName(String path){
		int last = path.lastIndexOf("/");
		if(last < 0){
	        return "";
	    }
	    String end = path.substring(last+1,path.length()).toLowerCase();//获取文件的后缀名
	    audioInfo.setName(end);
	    return end;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		int currentProgress=100*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
		Log.e(TAG, "进度："+currentProgress+"% play"+" "+percent+"% buffer");
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
		sendBroadcast(AppValues.PLAY_MSG);//只需要在状态变化的时候将进度等信息传到activity，不用实时传输
	}
	
	private void sendBroadcast(int state){
		if(mediaPlayer != null){
			Bundle bd = new Bundle();
			bd.putInt("position", mediaPlayer.getCurrentPosition());
			bd.putInt("duration", mediaPlayer.getDuration());
			Intent intent = new Intent();
			intent.setAction(AppValues.PROCESS_MSG);
			intent.putExtra("process", bd);
			intent.putExtra("state", state);
			sendBroadcast(intent);
		}
	}
	
	private void sendSecondBroadcase(int process){
		if(mediaPlayer != null){
			Intent intent = new Intent();
			intent.setAction(AppValues.SECOND_MSG);
			intent.putExtra("process", process);
			sendBroadcast(intent);
		}
	}
	
	/**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        CharSequence text = getText(R.string.music_play_now)+audioInfo.getName();
        Notification notification = new Notification(R.drawable.stat_notify, text,
                System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, TestMp3PlayerActivity.class), 0);
        notification.setLatestEventInfo(this, getText(R.string.music_title),
                       text, contentIntent);
        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.music_title, notification);
    }

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		stop();
		String error;
		if(what == MediaPlayer.MEDIA_ERROR_SERVER_DIED){
			error = "媒体播放流异常,请重新播放";
		}else if(what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK){
			error = "文件已破坏无法播放";
		}else{
			error = "视频格式或网络异常";
		}
		Log.e(TAG, "Error "+error);
		Toast.makeText(this, error, 3000).show();
		return false;
	}
}
