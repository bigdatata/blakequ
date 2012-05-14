package com.hao.video.view;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hao.R;
import com.hao.util.FileBrowserActivity;
import com.hao.video.VideoInfo;
import com.hao.video.view.SoundView.OnVolumeChangedListener;
import com.hao.video.view.VideoView.MyOnCreateListener;
import com.hao.video.view.VideoView.MySizeChangeListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class OtherVideoActivity extends Activity implements OnClickListener, OnLongClickListener{
	private static final String TAG = "VideoActivity";
	private static final long DYLAY_DISMISS_TIME = 4000;
	private VideoView videoView;
	private ImageButton btnPause, btnStop, sound, openFile, zoom, btnpre, btnpost;
	private TextView begin_time,end_Time;
	private SeekBar skbProgress;
	private VideoInfo videoInfo;
	private int screenWidth, screenHeight;
	PopupWindow popupWindow = null, soundPopWin = null;
	ViewGroup menuView;
	private Timer mTimer = new Timer();
	private boolean isFulllScreen = false, isShowControll = true, isPaused = true;
	private ProgressDialog dialog;
	private int myProgress = 0;
	//sound control
	private SoundView soundView;
	private AudioManager mAudioManager = null;
	private int currentVolume = 0, tempVolume = 0;
	private boolean isSilent = false, isSoundShow = false;
	private long lastClickTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.video_view);
		
		Display display = getWindowManager().getDefaultDisplay();
        screenHeight = display.getHeight();
        screenWidth = display.getWidth();
        initPopupView();
		//将屏幕设为横向
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在缓冲数据...");
		videoView = (VideoView) findViewById(R.id.other_video);
		videoView.setOnClickListener(this);
		videoView.setOnLongClickListener(this);
		 //当发送错误的时候会自动调用这个listener并显示dialog
	    videoView.setOnErrorListener(new OnErrorListener(){

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				videoView.stopPlayback();
				if(dialog.isShowing()) dialog.dismiss();
				String error;
				if(what == MediaPlayer.MEDIA_ERROR_SERVER_DIED){
					error = "媒体播放流异常,请重新播放";
				}else if(what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK){
					error = "文件已破坏无法播放";
				}else{
					error = "视频格式或网络异常";
				}
				new AlertDialog.Builder(OtherVideoActivity.this)
	            .setTitle("对不起")
	            .setMessage(error)
	            .setPositiveButton("知道了",
	                    new AlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								videoView.stopPlayback();
							}
	       
	                    })
	            .setCancelable(false)
	            .show();
				return false;
			}
	    });
	    videoView.setMySizeChangeLinstener(new MySizeChangeListener() {
			
			@Override
			public void onSizeChanged() {
				// TODO Auto-generated method stub
				setVideoScale();
			}
		});
	    videoView.setmOnBufferingUpdateListener(new OnBufferingUpdateListener() {
			
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				// TODO Auto-generated method stub
				skbProgress.setSecondaryProgress(percent);
			}
		});
	    videoView.setmOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_play_light));
			}
		});
	    videoView.setmMyOnCreateListener(new MyOnCreateListener() {
			@Override
			public void onCreate() {
				// TODO Auto-generated method stub
				if(popupWindow == null){
					openPopupwin();
				}else{
					if(!popupWindow.isShowing()){
						popupWindow.showAtLocation(findViewById(R.id.baselayout), Gravity.BOTTOM, 0, 0);
						popupWindow.update();
					}
				}
				isShowControll = true;
			}
		});
	    
	    videoView.setmOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if(!videoView.isPlaying()) videoView.start();
				setTime(end_Time, mp.getDuration());
				btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_pause_light));
				if(dialog.isShowing()) dialog.dismiss();
				lastClickTime = System.currentTimeMillis();
				videoView.postDelayed(dismissTask, DYLAY_DISMISS_TIME);
			}
		});
		videoInfo = new VideoInfo();
//		videoInfo.setPath("/sdcard/2.3gp");//ff.flv 2.3gp
		videoInfo.setPath("http://daily3gp.com/vids/family_guy_penis_car.3gp");
		videoView.setVideoInfo(videoInfo);
		mTimer.schedule(mTimerTask, 0, 100);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(popupWindow!=null && popupWindow.isShowing()){
			popupWindow.dismiss();
		}
		if(soundPopWin != null && soundPopWin.isShowing()){
			soundPopWin.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == menuView){
			if(isShowControll){
				popupWindow.dismiss();
			}
		}
		//当点击控制按钮，重新计时
		lastClickTime = System.currentTimeMillis();
		videoView.postDelayed(dismissTask, DYLAY_DISMISS_TIME);
		switch(v.getId()){
			case R.id.btpause:
				playing();
				break;
			case R.id.btstop:
				btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_play_light));
				videoView.stopPlayback();
				break;
			case R.id.other_video:
				if(isShowControll){
					popupWindow.dismiss();
				}else{
					if(popupWindow == null){
						openPopupwin();
					}else{
						popupWindow.showAtLocation(findViewById(R.id.baselayout), Gravity.BOTTOM, 0, 0);
						popupWindow.update();
					}
				}
				isShowControll = !isShowControll;
				break;
			case R.id.sound:
				if(isSoundShow){
					soundPopWin.dismiss();
				}else{
					if(soundPopWin == null){
						openSoundPopupwin();
					}else{
						soundPopWin.showAtLocation(findViewById(R.id.baselayout), Gravity.RIGHT|Gravity.TOP, 15, 30);
						soundPopWin.update(15, 30, SoundView.MY_WIDTH, SoundView.MY_HEIGHT);
					}
				}
				isSoundShow = !isSoundShow;
				break;
			case R.id.open_file:
				Intent intent = new Intent();
				//设置起始目录和查找的类型
		        intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");//"*/*"表示所有类型，设置起始文件夹和文件类型
		        intent.setClass(OtherVideoActivity.this, FileBrowserActivity.class);
		        startActivityForResult(intent, 1);
				break;
			case R.id.zoom:
				zoomScreen();
				break;
			case R.id.btpre:
				int duration = videoView.getDuration();
				if(duration != -1){
					myProgress = videoView.getCurrentPosition();
					//进度条的位置
					int perLen = skbProgress.getMax()/15;
					skbProgress.setProgress(skbProgress.getProgress()-perLen);
					//每次视频后退的长度
					int perDur = (duration*perLen)/skbProgress.getMax();
					videoView.seekTo(myProgress-perDur);
				}else{
					Toast.makeText(OtherVideoActivity.this, "视频还没准备好", 3000).show();
				}
				break;
			case R.id.btpost:
				int durations = videoView.getDuration();
				if(durations != -1){
					myProgress = videoView.getCurrentPosition();
					int postLen = skbProgress.getMax()/15;
					skbProgress.setProgress(skbProgress.getProgress()+postLen);
					int postDur = (durations*postLen)/skbProgress.getMax();
					videoView.seekTo(myProgress+postDur);
				}else{
					Toast.makeText(OtherVideoActivity.this, "视频还没准备好", 3000).show();
				}
				break;
		}
	}
	
	private Runnable dismissTask = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(System.currentTimeMillis() - lastClickTime > DYLAY_DISMISS_TIME){
				if(popupWindow != null && popupWindow.isShowing()){
					popupWindow.dismiss();
				}
			}
		}
	};
	
	/**
	 * playing or pause the video
	 */
	public void playing(){
		if(isPaused){
			if(!dialog.isShowing() && videoView.getDuration() == -1){
				dialog.show();
			}
			if(videoView.getDuration() == -1){
				videoView.setVideoInfo(videoInfo);
			}
			videoView.start();
			btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_pause_light));
		}else{
			videoView.pause();
			btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_play_light));
		}
		isPaused = !isPaused;
	}
	
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.other_video:
				zoomScreen();
				break;
			case R.id.sound:
				if(isSilent){
					sound.setImageResource(R.drawable.sound_icon);
					isSilent = !isSilent;
					soundView.setSlient(tempVolume);
				}else{
					sound.setImageResource(R.drawable.volume_button_disable);
					isSilent = !isSilent;
					tempVolume = currentVolume;
					soundView.setSlient(0);
				}
				break;
		}
		return false;
	}
	
	private void initPopupView(){
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.video_controller, null, true);
		menuView.setOnClickListener(this);
		btnPause = (ImageButton) menuView.findViewById(R.id.btpause);
		btnPause.setOnClickListener(this);
		btnStop = (ImageButton) menuView.findViewById(R.id.btstop);
		btnStop.setOnClickListener(this);
		skbProgress = (SeekBar) menuView.findViewById(R.id.skbProgress2);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		sound = (ImageButton) menuView.findViewById(R.id.sound);
		sound.setOnClickListener(this);
		sound.setOnLongClickListener(this);
		openFile = (ImageButton) menuView.findViewById(R.id.open_file);
		openFile.setOnClickListener(this);
		zoom = (ImageButton) menuView.findViewById(R.id.zoom);
		zoom.setOnClickListener(this);
		btnpre = (ImageButton) menuView.findViewById(R.id.btpre);
		btnpre.setOnClickListener(this);
		btnpost = (ImageButton) menuView.findViewById(R.id.btpost);
		btnpost.setOnClickListener(this);
		begin_time = (TextView) menuView.findViewById(R.id.begin_time);
		end_Time = (TextView) menuView.findViewById(R.id.end_time);
		
		//soundView
		//获取音量
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        tempVolume = currentVolume;
		soundView = new SoundView(this);
		soundView.setOnVolumeChangeListener(new OnVolumeChangedListener() {
			
			@Override
			public void setYourVolume(int index) {
				// TODO Auto-generated method stub
				updateVolume(index);
			}
		});
	}
	
	private void openPopupwin(){
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				if(soundPopWin != null && isSoundShow){
					soundPopWin.dismiss();
					isSoundShow = !isSoundShow;
				}
				isShowControll = false;
			}
		});
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.media_animation);
		/**
		 * 注册一个PopupWindow 显示出来按返回键居然不消失，很是郁闷，结果查查资料原来是没有设置background加上一句
		 * PopupWindow.setBackgroundDrawable(new BitmapDrawable());就Ok了。
		 */
		popupWindow.showAtLocation(findViewById(R.id.baselayout), Gravity.BOTTOM, 0, 0);
		popupWindow.update();
	}
	
	
	private void openSoundPopupwin(){
		soundPopWin = new PopupWindow(soundView);
		soundPopWin.setBackgroundDrawable(new BitmapDrawable());
		soundPopWin.showAtLocation(findViewById(R.id.baselayout), Gravity.RIGHT|Gravity.TOP, 15, 30);
		soundPopWin.update(15, 30, SoundView.MY_WIDTH, SoundView.MY_HEIGHT);
	}

	/**
	 * 全屏与否
	 */
	private void zoomScreen(){
		isFulllScreen = !isFulllScreen;
		if (isFulllScreen) {  
            WindowManager.LayoutParams params = getWindow().getAttributes();  
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;  
            getWindow().setAttributes(params);  
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  
        } else {  
            WindowManager.LayoutParams params = getWindow().getAttributes();  
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);  
            getWindow().setAttributes(params);  
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
	}
	
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener{
		int process;
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			this.process = progress * videoView.getDuration() / seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			lastClickTime = System.currentTimeMillis();
			videoView.postDelayed(dismissTask, DYLAY_DISMISS_TIME);
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			videoView.seekTo(process);
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                     Uri uri = data.getData();    // 接收用户所选文件的路径
                     String uploadFilePath = uri.getPath();
                     videoInfo.setPath(uploadFilePath);
                     String fileName = uri.getLastPathSegment();
                     videoInfo.setName(fileName);
                     
                     skbProgress.setSecondaryProgress(0);
                     skbProgress.setProgress(0);
                     isPaused = true;
                     btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_play_light));
                     setTime(begin_time, 0);
                     setTime(end_Time, 0);
                     videoView.setVideoInfo(videoInfo);
                     videoView.start();
            }
		}
	}
	
	/**
     * 设置Video的尺寸
     * @param flag
     */
    private void setVideoScale(){
    	int videoWidth = videoView.getVideoWidth();
    	int videoHeight = videoView.getVideoHeight();
    	int mWidth = screenWidth;
    	int mHeight = screenHeight - 25;
    	if (videoWidth > 0 && videoHeight > 0) {
    	      if ( videoWidth * mHeight  > mWidth * videoHeight ) {
    	    	  //Log.i("@@@", "image too tall, correcting");
    	          mHeight = mWidth * videoHeight / videoWidth;
    	          } else if ( videoWidth * mHeight  < mWidth * videoHeight ) {
    	           //Log.i("@@@", "image too wide, correcting");
    	           mWidth = mHeight * videoWidth / videoHeight;
    	      } 
    	}
    	videoView.setVideoScale(mWidth, mHeight);
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    
    TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (videoView.getDuration() == -1)
				return;
			if (!isPaused && skbProgress.isPressed() == false) {
				handleProgress.sendEmptyMessage(0);
			}
		}
	};
	
	
	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {
			if(videoView.getDuration() != -1){
				int position = videoView.getCurrentPosition();
				int duration = videoView.getDuration();
				if (duration > 0) {
					long pos = skbProgress.getMax() * position / duration;
					skbProgress.setProgress((int) pos);
					setTime(begin_time, position);
				}
			}

		};
	};
	
	/**
	 * 更新当前视频的音量
	 * @param index
	 */
	private void updateVolume(int index){
		if(mAudioManager != null){
			if(isSilent && index == 0){
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
			}else{
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
				sound.setImageResource(R.drawable.sound_icon);
			}
			currentVolume = index;
			if(index != 0) tempVolume = index;
		}
	}
	
	/**
	 * set the time
	 * @param text
	 * @param time
	 */
	private void setTime(TextView text, int time){
		time/=1000;
		int minute = time/60;
		int hour = minute/60;
		int second = time%60;
		minute %= 60;
		text.setText(String.format("%02d:%02d:%02d", hour,minute,second));
	}
}
