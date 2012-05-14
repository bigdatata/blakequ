package com.hao.video.view;

/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

import java.io.IOException;

import com.hao.video.VideoInfo;
import com.hao.video.Player.OnSurfaceCreated;
 
/**
 * Displays a video file.  The VideoView class
 * can load images from various sources (such as resources or content
 * providers), takes care of computing its measurement from the video so that
 * it can be used in any layout manager, and provides various display options
 * such as scaling and tinting.
 */
public class VideoView extends SurfaceView{
    private String TAG = "VideoView";
    
    private Context mContext;
    private VideoInfo videoInfo;
    private int         mDuration;
    // All the stuff we need for playing and showing a video
    private SurfaceHolder mSurfaceHolder = null;
    private MediaPlayer mMediaPlayer = null;
    private boolean     mIsPrepared;
    private int         mVideoWidth;
    private int         mVideoHeight;
    private OnCompletionListener mOnCompletionListener;
    private OnPreparedListener mOnPreparedListener;
    private MySizeChangeListener mMyChangeLinstener;
    private OnErrorListener mOnErrorListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private MyOnCreateListener mMyOnCreateListener;
    private int         mCurrentBufferPercentage;
    private boolean     mStartWhenPrepared;
    private int         mSeekWhenPrepared;

    
    
    public VideoView(Context context) {
        super(context);
        mContext = context;
        initVideoView();
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
        initVideoView();
    }

    public VideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initVideoView();
    }
    
    public int getVideoWidth(){
    	return mVideoWidth;
    }
    
    public int getVideoHeight(){
    	return mVideoHeight;
    }
    
    /**
     * 设置Video的显示尺寸
     * @param width
     * @param height
     */
    public void setVideoScale(int width , int height){
    	LayoutParams lp = getLayoutParams();
    	lp.height = height;
		lp.width = width;
		setLayoutParams(lp);
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure");
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if ( mVideoWidth * height  > width * mVideoHeight ) {
                height = width * mVideoHeight / mVideoWidth;
            } else if ( mVideoWidth * height  < width * mVideoHeight ) {
                width = height * mVideoWidth / mVideoHeight;
            } 
        }
        setMeasuredDimension(width,height);
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                /* Parent says we can be as big as we want. Just don't be larger
                 * than max size imposed on ourselves.
                 */
                result = desiredSize;
                break;

            case MeasureSpec.AT_MOST:
                /* Parent says we can be as big as we want, up to specSize.
                 * Don't be larger than specSize, and don't be larger than
                 * the max size imposed on ourselves.
                 */
                result = Math.min(desiredSize, specSize);
                break;

            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = specSize;
                break;
        }
        return result;
}

    private void initVideoView() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        getHolder().addCallback(mSHCallback);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    /**
     * 设置并准备播放
     * @param path
     */
    public void setVideoPath(String path) {
    	this.videoInfo = new VideoInfo();
    	videoInfo.setPath(path);
        setVideoInfo(videoInfo);
    }

    /**
     * 设置播放路径并准备播放
     * @param uri
     */
    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
        mStartWhenPrepared = false;
        mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();//刷新视图
        invalidate();//刷新视图
    }
    /**
     * 停止播放视频并释放资源
     */
    public void stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 准备播放视频
     */
    private void openVideo() {
        if (videoInfo == null || mSurfaceHolder == null) {
            // not ready for playback just yet, will try again later
            return;
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mIsPrepared = false;
            mDuration = -1;
            mCurrentBufferPercentage = 0;
            mMediaPlayer.setDataSource(videoInfo.getPath());
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepareAsync();
        } catch (IOException ex) {
            Log.w(TAG, "Unable to open content: " + videoInfo.getPath(), ex);
            return;
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + videoInfo.getPath(), ex);
            return;
        }
    }


    private MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener =
        new MediaPlayer.OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            	Log.e(TAG, "onVideoSizeChanged");
                mVideoWidth = mp.getVideoWidth();
                mVideoHeight = mp.getVideoHeight();
                
                if(mMyChangeLinstener!=null){
                	mMyChangeLinstener.onSizeChanged();
                }
                
                if (mVideoWidth != 0 && mVideoHeight != 0) {
                    getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                }
            }
    };

    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            // briefly show the mediacontroller
        	Log.e(TAG, "onPrepared");
            mIsPrepared = true;
            if (mOnPreparedListener != null) {
                mOnPreparedListener.onPrepared(mMediaPlayer);
            }
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            if (mVideoWidth != 0 && mVideoHeight != 0) {
            	getHolder().setFixedSize(mVideoWidth, mVideoHeight);
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener =
        new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
        	Log.e(TAG, "onCompletion");
        	stopPlayback();
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(mMediaPlayer);
            }
        }
    };

    private MediaPlayer.OnErrorListener mErrorListener =
        new MediaPlayer.OnErrorListener() {
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            Log.d(TAG, "Error: " + framework_err + "," + impl_err);

            /* If an error handler has been supplied, use it and finish. */
            if (mOnErrorListener != null) {
                if (mOnErrorListener.onError(mMediaPlayer, framework_err, impl_err)) {
                    return true;
                }
            }
            return true;
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener =
        new MediaPlayer.OnBufferingUpdateListener() {
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mCurrentBufferPercentage = percent;
            if(mOnBufferingUpdateListener != null){
            	mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };


    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback()
    {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
        {
        	Log.e(TAG, "surfaceChanged");
            if (mMediaPlayer != null && mIsPrepared && mVideoWidth == w && mVideoHeight == h) {
                if (mSeekWhenPrepared != 0) {
                    mMediaPlayer.seekTo(mSeekWhenPrepared);
                    mSeekWhenPrepared = 0;
                }
            }
        }

        public void surfaceCreated(SurfaceHolder holder)
        {
        	Log.e(TAG, "surfaceCreated");
            mSurfaceHolder = holder;
            mMyOnCreateListener.onCreate();
        }

        public void surfaceDestroyed(SurfaceHolder holder)
        {
        	Log.e(TAG, "surfaceDestroyed");
            // after we return from this we can't use the surface any more
            mSurfaceHolder = null;
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
    };

    public void start() {
        if (mMediaPlayer != null && mIsPrepared) {
                mMediaPlayer.start();
                mStartWhenPrepared = false;
        } else {
            mStartWhenPrepared = true;
        }
    }

    public void pause() {
        if (mMediaPlayer != null && mIsPrepared) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }
        mStartWhenPrepared = false;
    }

    /**
     * 视频的长度，如果有问题，返回-1
     */
    public int getDuration() {
        if (mMediaPlayer != null && mIsPrepared) {
            if (mDuration > 0) {
                return mDuration;
            }
            mDuration = mMediaPlayer.getDuration();
            return mDuration;
        }
        mDuration = -1;
        return mDuration;
    }

    /**
     * 当前播放位置
     */
    public int getCurrentPosition() {
        if (mMediaPlayer != null && mIsPrepared) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 跳转到视频的位置
     */
    public void seekTo(int msec) {
        if (mMediaPlayer != null && mIsPrepared) {
            mMediaPlayer.seekTo(msec);
        } else {
            mSeekWhenPrepared = msec;
        }
    }

    /**
     * 是否正在播放
     */
    public boolean isPlaying() {
        if (mMediaPlayer != null && mIsPrepared) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 获取缓冲视频的百分比
     */
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }
    
    public interface MySizeChangeListener{
    	public void onSizeChanged();
    }
    
    public interface MyOnCreateListener{
    	public void onCreate();
    }

	public void setmMyOnCreateListener(MyOnCreateListener mMyOnCreateListener) {
		this.mMyOnCreateListener = mMyOnCreateListener;
	}

	public void setMySizeChangeLinstener(MySizeChangeListener l){
    	mMyChangeLinstener = l;
    }
    
    /**
     * Register a callback to be invoked when an error occurs
     * during playback or setup.  If no listener is specified,
     * or if the listener returned false, VideoView will inform
     * the user of any errors.
     *
     * @param l The callback that will be run
     */
    public void setOnErrorListener(OnErrorListener l)
    {
        mOnErrorListener = l;
    }

	public void setmOnCompletionListener(OnCompletionListener mOnCompletionListener) {
		this.mOnCompletionListener = mOnCompletionListener;
	}

	public void setmOnBufferingUpdateListener(
			OnBufferingUpdateListener mOnBufferingUpdateListener) {
		this.mOnBufferingUpdateListener = mOnBufferingUpdateListener;
	}

	public void setmOnPreparedListener(OnPreparedListener mOnPreparedListener) {
		this.mOnPreparedListener = mOnPreparedListener;
	}
    
}
