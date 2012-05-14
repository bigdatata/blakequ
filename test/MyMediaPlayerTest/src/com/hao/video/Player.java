package com.hao.video;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.hao.R;
import com.hao.audio.AudioInfo;
import com.hao.audio.AudioService;
import com.hao.util.AppValues;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.MediaController.MediaPlayerControl;

public class Player implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback, MediaPlayer.OnVideoSizeChangedListener{
	private static final String TAG = "Player";
	private int videoWidth;
	private int videoHeight;
	private MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	private SeekBar skbProgress;
	private Timer mTimer = new Timer();
	private Context context;
	private boolean isPlaying = false, isPause = false, isStop = true;
	private OnPlayingStateChanged onPlayingStateChanged;
	private OnSurfaceCreated onSurfaceCreated;
	private OnSizeChangedListener onSizeChanged;
	
	public Player(SurfaceView surfaceView, SeekBar skbProgress, Context context) {
		this.skbProgress = skbProgress;
		this.context = context;
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	/*******************************************************
	 * 通过定时器和Handler来更新进度条
	 ******************************************************/
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			if (isPlaying && skbProgress.isPressed() == false) {
				handleProgress.sendEmptyMessage(0);
			}
		}
	};

	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {
			if(mediaPlayer != null){
				int position = mediaPlayer.getCurrentPosition();
				int duration = mediaPlayer.getDuration();
				if (duration > 0) {
					long pos = skbProgress.getMax() * position / duration;
					skbProgress.setProgress((int) pos);
				}
			}

		};
	};

	public void play(VideoInfo videoInfo) {
		if(mediaPlayer == null){
			Log.e(TAG, "play mediaPlayer is null");
			createMediaPlayer(videoInfo.getPath());
			if(!isPlaying(mediaPlayer)){
				Toast.makeText(context, context.getResources().getString(R.string.format_not_surrport), 1).show();
				return;
			}
			getVideoName(videoInfo.getPath());
			isStop = false;
			isPlaying = false;
			isPause = true;
		}
		if(mediaPlayer != null && !isStop){
			if(isPause){
				Log.e(TAG, "start");
				mediaPlayer.start();
				isPause = false;
				isPlaying = true;
				onPlayingStateChanged.onStateChangedListener(AppValues.PLAY_MSG);
			}else{
				Log.e(TAG, "pause");
				mediaPlayer.pause();
				isPause = true;
				isPlaying = false;
				onPlayingStateChanged.onStateChangedListener(AppValues.PAUSE_MSG);
			}
		}
	}

	public void stop() {
		if(mediaPlayer != null && !isStop){
			if(isPause || isPlaying){
				Log.e(TAG, "stop");
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer = null;
				isStop = true;
				onPlayingStateChanged.onStateChangedListener(AppValues.STOP_MSG);
			}
		}
		isPlaying = false;
	}
	
	/**
	 * Gets the duration of the file
	 * @return the duration in milliseconds
	 */
	public int getDuration(){
		if(mediaPlayer != null){
			return mediaPlayer.getDuration();
		}
		return 0;
	}
	
	/**
	 * Seeks to specified time position.
	 * @param progress
	 */
	public void seekTo(int progress){
		if(mediaPlayer != null){
			mediaPlayer.seekTo(progress);
		}
	}
	
	private String getVideoName(String path){
		int last = path.lastIndexOf("/");
		if(last < 0){
	        return "";
	    }
	    String end = path.substring(last+1,path.length()).toLowerCase();//获取文件的后缀名
	    return end;
	}
	
	private void createMediaPlayer(String path){
            try {
            	mediaPlayer = new MediaPlayer();  
            	mediaPlayer.setDisplay(surfaceHolder);  
            	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
				mediaPlayer.setDataSource(path);
				mediaPlayer.prepare();//没有调用它会出现error(-38, 0)错误
				mediaPlayer.setLooping(false);
				mediaPlayer.setOnBufferingUpdateListener(this);  
				mediaPlayer.setOnPreparedListener(this);  
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "create IllegalArgumentException");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "create IllegalStateException");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "create IOException");
			}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.e("mediaPlayer", "surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.e(TAG, "surfaceCreated");
		onSurfaceCreated.onCreatedListener(true);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.e(TAG, "surfaceDestroyed");
		if(isPause || isPlaying){
			stop();
		}
	}

	/**
	 * 有些视频是android播放器不能播放的，不能播放时videoHeight=0，videoWidth=0，以此来判断是否播放视频。
	 */
	public boolean isPlaying(MediaPlayer mediaPlayer){
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {
			return true;
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onCompletion");
		stop();
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
		skbProgress.setSecondaryProgress(bufferingProgress);
		Log.e(TAG, "onBufferingUpdate");
		int currentProgress = skbProgress.getMax()
				* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		Log.e(currentProgress + "% play", bufferingProgress + "% buffer");

	}

	/**
	 * 有些视频是android播放器不能播放的，不能播放时videoHeight=0，videoWidth=0，以此来判断是否播放视频。
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onPrepared");
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight == 0 || videoWidth == 0) {
			throw new IllegalArgumentException("the video format not support");
		}
	}
	
	
	public void setOnPlayingStateChanged(OnPlayingStateChanged onPlayingStateChanged) {
		this.onPlayingStateChanged = onPlayingStateChanged;
	}
	
	public void setOnSurfaceCreated(OnSurfaceCreated onSurfaceCreated) {
		this.onSurfaceCreated = onSurfaceCreated;
	}
	
	public void setOnSizeChanged(OnSizeChangedListener onSizeChanged) {
		this.onSizeChanged = onSizeChanged;
	}

	/**
	 * listener the state of playing
	 */
	public interface OnPlayingStateChanged{
		void onStateChangedListener(int state);
	}
	
	/**
	 * listener the state when the surface created
	 * @author Administrator
	 *
	 */
	public interface OnSurfaceCreated{
		void onCreatedListener(boolean flag);
	}
	
	/**
	 * changed the size of screen according to the media
	 * @author Administrator
	 *
	 */
	public interface OnSizeChangedListener{
		void onSizeChanged(int width, int height);
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if(onSizeChanged != null) onSizeChanged.onSizeChanged(videoWidth, videoHeight);
		if(videoWidth !=0 && videoHeight != 0){
			surfaceHolder.setFixedSize(videoWidth, videoHeight);
		}
	}

}
