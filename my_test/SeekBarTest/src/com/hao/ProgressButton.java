package com.hao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgressButton extends View{
	private Bitmap begin , bm_gray, bm_yellow, bm_second, end_gray, end_yellow, line,begin_gray;
	private Bitmap pausePressedImg;
	private Bitmap playPressedImg;
	private int bitmapWidth = 0 , bitmapHeight = 0, btWidth = 0, btHeight = 0;  
	private int progress = 0, secondProgress = 0;
	private double perLen = 0, max = 0, maxSize = 0;
	private OnProgressChanged mOnProgressChanged;
	private boolean isPlaying = false;
	private Paint mTextPaint;
	private String time = "00:00";
	private int color = Color.BLUE;

	public ProgressButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public ProgressButton(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	public ProgressButton(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	private void init(){
		begin = drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_left_yellow));
		begin_gray = drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_left_gray));
		bm_gray =  drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_gray));
		bm_yellow =  drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_yellow));
		bm_second =  drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_second_yellow));
		end_gray =  drawableToBitmap(getResources().getDrawable( R.drawable.rectangle_right_gray));
		end_yellow =  drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_right_yellow));
		line = drawableToBitmap(getResources().getDrawable(R.drawable.rectangle_line));
		pausePressedImg = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button_pressed);
		playPressedImg = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_pressed);
		bitmapHeight = begin.getHeight();
		bitmapWidth = begin.getWidth();
		btWidth = pausePressedImg.getWidth();
		btHeight = pausePressedImg.getHeight();
		mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(14);
        mTextPaint.setColor(color);
        setPadding(3, 3, 3, 3);
	}
	
	public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }	
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		Log.e("*******", "onMeasure");
		setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
		perLen = maxSize/max; 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.e("*******", "onDraw");
		int middle1 = (int) (progress*perLen), middle2 = (int) (secondProgress*perLen) ,end = (int) maxSize-4;
		if(progress == 0 && secondProgress == 0){
			//draw background
			canvas.drawBitmap(begin_gray, new Rect(0,0,bitmapWidth,bitmapHeight), 
									new Rect(0, 0, bitmapWidth, bitmapHeight), null);
			canvas.drawBitmap(bm_gray, new Rect(0,0,end-middle1,bitmapHeight), 
					new Rect(bitmapWidth, 0, end, bitmapHeight), null);
			canvas.drawBitmap(end_gray, new Rect(0,0,4,bitmapHeight), 
					new Rect(end, 0, end+4, bitmapHeight), null);
			//draw button and line
			canvas.drawBitmap(playPressedImg, new Rect(0, 0, btWidth, btHeight), 
					new Rect(0, 0, btWidth, bitmapHeight), null);
			canvas.drawBitmap(line, new Rect(0, 0, 2, bitmapHeight), 
					new Rect(btWidth, 0, btWidth+2, bitmapHeight), null);
			//draw time and line
			if(time.length() == 5){
				canvas.drawBitmap(line, new Rect(0, 0, 2, bitmapHeight), 
						new Rect(end - 50, 0, end-48, bitmapHeight), null);
				canvas.drawText("-"+time, end-45, bitmapHeight/2+5, mTextPaint);
			}else{
				canvas.drawBitmap(line, new Rect(0, 0, 2, bitmapHeight), 
						new Rect(end - 60, 0, end-58, bitmapHeight), null);
				canvas.drawText("-"+time, end-55, bitmapHeight/2+5, mTextPaint);
			}
		}else{
			//begin
			canvas.drawBitmap(begin, new Rect(0,0,bitmapWidth,bitmapHeight), 
						new Rect(0, 0, bitmapWidth, bitmapHeight), null);
			canvas.drawBitmap(bm_yellow, new Rect(0,0,middle1-bitmapWidth,bitmapHeight), 
					    new Rect(bitmapWidth, 0, middle1, bitmapHeight), null);
			//middle
			if(secondProgress != 0 && secondProgress > progress){
				canvas.drawBitmap(bm_second, new Rect(0,0,bitmapWidth,bitmapHeight), 
						new Rect(middle1, 0, middle2, bitmapHeight), null);
				canvas.drawBitmap(bm_gray, new Rect(0,0,bitmapWidth,bitmapHeight), 
						new Rect(middle2, 0, end, bitmapHeight), null);
			}else{
				canvas.drawBitmap(bm_gray, new Rect(0,0,end-middle1,bitmapHeight), 
						new Rect(middle1, 0, end, bitmapHeight), null);
			}
			//end
			canvas.drawBitmap(end_gray, new Rect(0,0,4,bitmapHeight), 
					new Rect(end, 0, end+4, bitmapHeight), null);
			if(middle2 >= end || middle1 >= end){
				canvas.drawBitmap(end_yellow, new Rect(0,0,4,bitmapHeight), 
						new Rect(end, 0, end+4, bitmapHeight), null);
			}
			//draw button
			if(!isPlaying) {
				canvas.drawBitmap(playPressedImg, new Rect(0, 0, btWidth, btHeight), 
						new Rect(0, 0, btWidth, bitmapHeight), null);
			}else{
				canvas.drawBitmap(pausePressedImg, new Rect(0, 0, btWidth, btHeight), 
						new Rect(0, 0, btWidth, bitmapHeight), null);
			}
			//draw line and time
			canvas.drawBitmap(line, new Rect(0, 0, 2, bitmapHeight), 
					new Rect(btWidth, 0, btWidth+2, bitmapHeight), null);
			if(time.length() == 5){
				canvas.drawBitmap(line, new Rect(0, 0, 2, bitmapHeight), 
						new Rect(end - 50, 0, end-48, bitmapHeight), null);
				canvas.drawText("-"+time, end-45, bitmapHeight/2+5, mTextPaint);
			}else{
				canvas.drawBitmap(line, new Rect(0, 0, 2, bitmapHeight), 
						new Rect(end - 60, 0, end-58, bitmapHeight), null);
				canvas.drawText("-"+time, end-55, bitmapHeight/2+5, mTextPaint);
			}
		}
		super.onDraw(canvas);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//在这里因为要换按钮，故而需要更新整个视图
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			onClickListener.onClick(this);
			invalidate();
		}
		return true;
	}
	
	/**
	 * 这个方法必须设置，当播放的时候
	 * @param isPlaying
	 */
	public void setStateChanged(boolean isPlaying){
		this.isPlaying = isPlaying;
	}
	
	public void setTextColor(int color){
		this.color = color;
		invalidate();
	}
	

	/**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
    	int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
        	result = (int) ((int)max*perLen + getPaddingLeft() + getPaddingRight());
        	if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        System.out.println("width:"+result);
        maxSize = result;
        return result;
    }
    
    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int) getPaddingTop() + getPaddingBottom() + bitmapHeight;
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        System.out.println("Height:"+result);
        return result;
    }
    
    /**
	 * set the time
	 * @param currentTime 当前播放时间
	 * @param totalTime 总播放时间
	 */
	public void setTime(int currentTime, int totalTime){
		int time = totalTime - currentTime;
		if(time <= 1000){
			this.time="00:00";
			return;
		}
		time/=1000;
		int minute = time/60;
		int hour = minute/60;
		int second = time%60;
		minute %= 60;
		if(hour == 0){
			this.time = String.format("%02d:%02d", minute,second);
		}else{
			this.time = String.format("%02d:%02d:%02d", hour, minute,second);
		}
	}

	/**
	 * 
	 * @param viewWidth 组件的宽度
	 */
	public void setMax(int max){
		this.max = max;
	}
	
	public int getMax(){
		return (int)max;
	}
	
	/**
	 * 设置第一进度
	 * @param progress
	 */
	public void setProgress(int progress){
		if(progress>max){
			progress = (int) max;
		}
		else if(progress<0){
			progress = 0;
		}
		if(mOnProgressChanged!=null){
			mOnProgressChanged.onProgressUpdated();
		}
		this.progress = progress;
		invalidate();
	}
	
	/**
	 * 设置第二进度
	 * @param secondProgress
	 */
	public void setSecondProgress(int secondProgress){
		if(secondProgress>max){
			secondProgress = (int) max;
		}
		else if(secondProgress<0){
			secondProgress = 0;
		}
		if(mOnProgressChanged!=null){
			mOnProgressChanged.onSecondProgressUpdated();
		}
		this.secondProgress = secondProgress;
		invalidate();
	}
	
	/**
	 * 设置进度监听器
	 * @param mOnProgressChanged
	 */
	public void setmOnProgressChanged(OnProgressChanged mOnProgressChanged) {
		this.mOnProgressChanged = mOnProgressChanged;
	}


	public interface OnProgressChanged{
		void onProgressUpdated();
		void onSecondProgressUpdated();
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		if(l != null) onClickListener = l;
		super.setOnClickListener(l);
	}

	private View.OnClickListener onClickListener;

}
