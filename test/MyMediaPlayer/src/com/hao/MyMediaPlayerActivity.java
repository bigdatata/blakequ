package com.hao;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MyMediaPlayerActivity extends Activity {
	/** Called when the activity is first created. */

	private SeekBar skb_audio = null;
	private Button btn_start_audio = null;
	private Button btn_stop_audio = null;

	private SeekBar skb_video = null;
	private Button btn_start_video = null;
	private Button btn_stop_video = null;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;

	private MediaPlayer m = null;
	private Timer mTimer;
	private TimerTask mTimerTask;

	private boolean isChanging = false;// 互斥变量，防止定时器与SeekBar拖动时进度冲突
	OnBeginPlayListener playListener;
	private boolean isBegin = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ----------Media控件设置---------//
		m = new MediaPlayer();

		// 播放结束之后弹出提示
		m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				Toast.makeText(MyMediaPlayerActivity.this, "结束", 1000).show();
				m.release();
			}
		});

		// ----------定时器记录播放进度---------//
		/**
		 * 因为在程序初始化的时候，源代码中把MediaPlayer.create()方法放在了OnClickListener中了，
		 * 所以导致执行m.getVideoHeight()失败（此时还没有设置媒体源）,so ，应该放在onClickListener中
		 */
		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				if (isChanging == true)
					return;

				if (m.getVideoHeight() == 0)
					skb_audio.setProgress(m.getCurrentPosition());
				else
					skb_video.setProgress(m.getCurrentPosition());
			}
		};
		playListener = new OnBeginPlayListener() {
			@Override
			public void onBeginPlayer(int type) {
				// TODO Auto-generated method stub
				if(type == 1 || type == 2){
					if(isBegin){
						mTimer.schedule(mTimerTask, 0, 10);
					} 
				}
				isBegin = false;
			}
		};
		
		btn_start_audio = (Button) this.findViewById(R.id.Button01);
		btn_stop_audio = (Button) this.findViewById(R.id.Button02);
		btn_start_audio.setOnClickListener(new ClickEvent());
		btn_stop_audio.setOnClickListener(new ClickEvent());
		skb_audio = (SeekBar) this.findViewById(R.id.SeekBar01);
		skb_audio.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		btn_start_video = (Button) this.findViewById(R.id.Button03);
		btn_stop_video = (Button) this.findViewById(R.id.Button04);
		btn_start_video.setOnClickListener(new ClickEvent());
		btn_stop_video.setOnClickListener(new ClickEvent());
		skb_video = (SeekBar) this.findViewById(R.id.SeekBar02);
		skb_video.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		surfaceView = (SurfaceView) findViewById(R.id.SurfaceView01);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setFixedSize(100, 100);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/*
	 * 按键事件处理
	 */
	class ClickEvent implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == btn_start_audio) {
				m.reset();// 恢复到未初始化的状态
				m = MediaPlayer.create(MyMediaPlayerActivity.this, R.raw.big);// 读取音频
				skb_audio.setMax(m.getDuration());// 设置SeekBar的长度
				try {
					m.prepare(); // 准备
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.start(); // 播放
				playListener.onBeginPlayer(1);
			} else if (v == btn_stop_audio || v == btn_stop_video) {
				m.stop();
			} else if (v == btn_start_video) {
				m.reset();// 恢复到未初始化的状态
				m = MediaPlayer.create(MyMediaPlayerActivity.this, R.raw.test);// 读取视频
				skb_video.setMax(m.getDuration());// 设置SeekBar的长度
				m.setAudioStreamType(AudioManager.STREAM_MUSIC);
				m.setDisplay(surfaceHolder);// 设置屏幕

				try {
					m.prepare();

				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.start();
				playListener.onBeginPlayer(2);
			}
		}
	}

	/*
	 * SeekBar进度改变事件
	 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			isChanging = true;
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			m.seekTo(seekBar.getProgress());//当拖动的时候让视屏或音频跳到拖动结束的位置
			isChanging = false;
		}

	}
	/**
	 * 用来控制seekBar的进度条的进度开始
	 * @author Administrator
	 *
	 */
	private interface OnBeginPlayListener{
		void onBeginPlayer(int type);
	}

}