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

	private boolean isChanging = false;// �����������ֹ��ʱ����SeekBar�϶�ʱ���ȳ�ͻ
	OnBeginPlayListener playListener;
	private boolean isBegin = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ----------Media�ؼ�����---------//
		m = new MediaPlayer();

		// ���Ž���֮�󵯳���ʾ
		m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				Toast.makeText(MyMediaPlayerActivity.this, "����", 1000).show();
				m.release();
			}
		});

		// ----------��ʱ����¼���Ž���---------//
		/**
		 * ��Ϊ�ڳ����ʼ����ʱ��Դ�����а�MediaPlayer.create()����������OnClickListener���ˣ�
		 * ���Ե���ִ��m.getVideoHeight()ʧ�ܣ���ʱ��û������ý��Դ��,so ��Ӧ�÷���onClickListener��
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
	 * �����¼�����
	 */
	class ClickEvent implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == btn_start_audio) {
				m.reset();// �ָ���δ��ʼ����״̬
				m = MediaPlayer.create(MyMediaPlayerActivity.this, R.raw.big);// ��ȡ��Ƶ
				skb_audio.setMax(m.getDuration());// ����SeekBar�ĳ���
				try {
					m.prepare(); // ׼��
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.start(); // ����
				playListener.onBeginPlayer(1);
			} else if (v == btn_stop_audio || v == btn_stop_video) {
				m.stop();
			} else if (v == btn_start_video) {
				m.reset();// �ָ���δ��ʼ����״̬
				m = MediaPlayer.create(MyMediaPlayerActivity.this, R.raw.test);// ��ȡ��Ƶ
				skb_video.setMax(m.getDuration());// ����SeekBar�ĳ���
				m.setAudioStreamType(AudioManager.STREAM_MUSIC);
				m.setDisplay(surfaceHolder);// ������Ļ

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
	 * SeekBar���ȸı��¼�
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
			m.seekTo(seekBar.getProgress());//���϶���ʱ������������Ƶ�����϶�������λ��
			isChanging = false;
		}

	}
	/**
	 * ��������seekBar�Ľ������Ľ��ȿ�ʼ
	 * @author Administrator
	 *
	 */
	private interface OnBeginPlayListener{
		void onBeginPlayer(int type);
	}

}