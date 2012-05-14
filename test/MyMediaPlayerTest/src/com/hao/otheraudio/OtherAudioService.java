package com.hao.otheraudio;


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
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class OtherAudioService extends Service implements OnBufferingUpdateListener,
OnCompletionListener, OnPreparedListener, OnErrorListener{

	private static final String TAG = "AudioService";
	private boolean isPlaying = false, isPause = false, isStop = true;
	private MediaPlayer mediaPlayer = null;
	private String path;
	private NotificationManager mNM;
	private int second = 0;
	IServicePlayer.Stub stub = new IServicePlayer.Stub() {
		
		@Override
		public void stop() throws RemoteException {
			// TODO Auto-generated method stub
			stops();
		}
		
		@Override
		public boolean setLoop(boolean loop) throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void seekTo(int current) throws RemoteException {
			// TODO Auto-generated method stub
			seekToPosition(current);
		}
		
		@Override
		public void play() throws RemoteException {
			// TODO Auto-generated method stub
			plays();
		}
		
		@Override
		public int getSecondPosition() throws RemoteException {
			// TODO Auto-generated method stub
			return second;
		}
		
		@Override
		public int getDuration() throws RemoteException {
			// TODO Auto-generated method stub
			if(mediaPlayer != null){
				return mediaPlayer.getDuration();
			}
			return 0;
		}
		
		@Override
		public int getCurrentPosition() throws RemoteException{
			// TODO Auto-generated method stub
			if(mediaPlayer != null && isPlaying){
				return mediaPlayer.getCurrentPosition();
			}
			return 0;
		}

		@Override
		public boolean isPlaying() throws RemoteException {
			// TODO Auto-generated method stub
			return isPlaying;
		}
	};
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return stub;
	}
	/**
	 * 
	 * @Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MyBinder();
	}
	
	class MyBinder extends Binder{
		public void stopService(){
			OtherAudioService.this.stopSelf();
		}
		
		public OtherAudioService getService(){
			return OtherAudioService.this;
		}
	}
	**/

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		path = intent.getStringExtra("path");
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void seekToPosition(int positon){
		if(mediaPlayer != null && !isStop){
			mediaPlayer.seekTo(positon);
		}
	}
	
	private void setListener(){
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
	}
	
	/**
	 * play or pause audio
	 */
	public void plays(){
		if(mediaPlayer == null){
			Log.e(TAG, "play mediaPlayer is null");
			createMediaPlayer(path);
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
			}else{
				Log.e(TAG, "pause");
				mediaPlayer.pause();
				isPause = true;
				isPlaying = false;
			}
		}
	}
	/**
	 * stop the audio
	 */
	public void stops(){
		if(mediaPlayer != null && !isStop){
			if(isPause || isPlaying){
				Log.e(TAG, "stop");
				isPlaying = false;
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
	
	/**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        CharSequence text = getText(R.string.music_play_now)+path;
        Notification notification = new Notification(R.drawable.stat_notify, text,
                System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AudioActivity.class), 0);
        notification.setLatestEventInfo(this, getText(R.string.music_title),
                       text, contentIntent);
        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.music_title, notification);
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
			Toast.makeText(OtherAudioService.this, "没有找到"+path, 1).show();
			Log.e(TAG, "start io exception");
		}
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		int currentProgress=100*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
		Log.e(TAG, "进度："+currentProgress+"% play"+" "+percent+"% buffer");
		second = percent;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCompletion");
		stops();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPrepared");
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		stops();
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
