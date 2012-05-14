package com.hao;

import java.io.File;

import com.hao.util.AppValues;
import com.hao.util.FileBrowserActivity;
import com.hao.video.Player;
import com.hao.video.VideoInfo;
import com.hao.video.Player.OnSizeChangedListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

public class VideoActivity extends Activity implements OnClickListener, Player.OnPlayingStateChanged
	, View.OnLongClickListener{
	private SurfaceView surfaceView;
	private ImageButton btnPause, btnStop, sound, openFile, zoom;
	private TextView begin_time,end_Time;
	private SeekBar skbProgress;
	private Player player;
	private VideoInfo videoInfo;
	private boolean isFulllScreen = false, isShowControll = true;
	PopupWindow popupWindow;
	ViewGroup menuView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video);
		
		initPopupView();
		//将屏幕设为横向
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.setOnClickListener(this);
		surfaceView.setOnLongClickListener(this);
		player = new Player(surfaceView, skbProgress, this);
		player.setOnPlayingStateChanged(this);
		player.setOnSurfaceCreated(new Player.OnSurfaceCreated() {
			
			@Override
			public void onCreatedListener(boolean flag) {
				// TODO Auto-generated method stub
				//它只能在主控件渲染完毕后再使用
				openPopupwin();
			}
		});

		videoInfo = new VideoInfo();
//		videoInfo.setPath("/sdcard/2.3gp");//ff.flv 2.3gp
		videoInfo.setPath("http://daily3gp.com/vids/family_guy_penis_car.3gp");
//		videoInfo.setPath("http://192.168.1.104:8080/fsServer_beta_v1.0/transferssss/video/transferssss.html");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(popupWindow!=null && popupWindow.isShowing()){
			popupWindow.dismiss();
		}
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
		openFile = (ImageButton) menuView.findViewById(R.id.open_file);
		openFile.setOnClickListener(this);
		zoom = (ImageButton) menuView.findViewById(R.id.zoom);
		zoom.setOnClickListener(this);
		begin_time = (TextView) menuView.findViewById(R.id.begin_time);
		end_Time = (TextView) menuView.findViewById(R.id.end_time);
	}

	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.getDuration()
			this.progress = progress * player.getDuration() / seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			player.seekTo(progress);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == menuView){
			if(popupWindow.isShowing()){
				popupWindow.dismiss();
			}
		}
		switch(v.getId()){
			case R.id.btpause:
				player.play(videoInfo);
				break;
			case R.id.btstop:
				player.stop();
				break;
			case R.id.surfaceView1:
				isShowControll = !isShowControll;
				if(isShowControll && popupWindow.isShowing()){
					popupWindow.dismiss();
				}else{
					openPopupwin();
				}
				break;
			case R.id.sound:
				System.out.println("sound");
				break;
			case R.id.open_file:
				Intent intent = new Intent();
				//设置起始目录和查找的类型
		        intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");//"*/*"表示所有类型，设置起始文件夹和文件类型
		        intent.setClass(VideoActivity.this, FileBrowserActivity.class);
		        startActivityForResult(intent, 1);
				break;
			case R.id.zoom:
				zoomScreen();
				break;
		}
	}

	@Override
	public void onStateChangedListener(int state) {
		// TODO Auto-generated method stub
		switch(state){
			case AppValues.STOP_MSG:
				btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_play_light));
				break;
			case AppValues.PAUSE_MSG:
				btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_play_light));
				break;
			case AppValues.PLAY_MSG:
				btnPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_pause_light));
				if(popupWindow.isShowing()){
					popupWindow.dismiss();
				}
				break;
		}
	}
	
	private void openPopupwin(){
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		/**
		 * 注册一个PopupWindow 显示出来按返回键居然不消失，很是郁闷，结果查查资料原来是没有设置background加上一句
		 * PopupWindow.setBackgroundDrawable(new BitmapDrawable());就Ok了。
		 */
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(surfaceView, Gravity.BOTTOM, 0, 0);
		popupWindow.update();
	}

	//当长按时设置全屏幕
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		zoomScreen();
		return false;
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
                     player.play(videoInfo);
            }
		}
	}
}
