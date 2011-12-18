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
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgressButton extends View{
	private Context mContext;
	private Bitmap begin , bm_gray, bm_yellow, bm_second, end_gray, end_yellow;
	private Bitmap pressedImg, defaultImg;
	private int bitmapWidth = 0 , bitmapHeight = 0, btWidth = 0, btHeight = 0;  
	private int max = 0, progress = 0, secondProgress = 0;
	private int perLen = 0;
	private OnProgressChanged mOnProgressChanged;
	private View.OnClickListener onClickListener;

	public ProgressButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
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
		begin = drawableToBitmap(mContext.getResources().getDrawable(R.drawable.rectangle_left_yellow));
		bm_gray =  drawableToBitmap(mContext.getResources().getDrawable(R.drawable.rectangle_gray));
		bm_yellow =  drawableToBitmap(mContext.getResources().getDrawable(R.drawable.rectangle_yellow));
		bm_second =  drawableToBitmap(mContext.getResources().getDrawable(R.drawable.rectangle_second_yellow));
		end_gray =  drawableToBitmap(mContext.getResources().getDrawable( R.drawable.rectangle_right_gray));
		end_yellow =  drawableToBitmap(mContext.getResources().getDrawable(R.drawable.rectangle_right_yellow));
		pressedImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pause_button_default);
		defaultImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pause_button_pressed);
		bitmapHeight = begin.getHeight();
		bitmapWidth = begin.getWidth();
		btWidth = pressedImg.getWidth();
		btHeight = pressedImg.getHeight();
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
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.e("*******", "onDraw");
		
		int middle1 = progress*perLen, middle2 = secondProgress*perLen ,end = max*perLen;
		canvas.drawBitmap(begin, new Rect(0,0,bitmapWidth,bitmapHeight), 
					new Rect(0, 0, bitmapWidth, bitmapHeight), null);
		canvas.drawBitmap(bm_yellow, new Rect(0,0,middle1-bitmapWidth,bitmapHeight), 
				    new Rect(bitmapWidth, 0, middle1, bitmapHeight), null);
		if(secondProgress != 0 && secondProgress > progress){
			canvas.drawBitmap(bm_second, new Rect(0,0,bitmapWidth,bitmapHeight), 
					new Rect(middle1, 0, middle2, bitmapHeight), null);
			canvas.drawBitmap(bm_gray, new Rect(0,0,bitmapWidth,bitmapHeight), 
					new Rect(middle2, 0, end, bitmapHeight), null);
		}else{
			canvas.drawBitmap(bm_gray, new Rect(0,0,end-middle1,bitmapHeight), 
					new Rect(middle1, 0, end, bitmapHeight), null);
		}
		canvas.drawBitmap(end_gray, new Rect(0,0,5,bitmapHeight), 
				new Rect(end, 0, end+5, bitmapHeight), null);
		
		canvas.drawBitmap(pressedImg, new Rect(0, 0, btWidth, btHeight), 
				new Rect((end-btWidth)/2, 0, (end+btWidth)/2, bitmapHeight), null);
	}
	
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	View.OnClickListener mOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(onClickListener != null) onClickListener.onClick(v);
			
		}
	};

	/**
	 * 
	 * @param viewWidth 组件的宽度
	 */
	public void setMax(int max, int viewWidth){
		this.max = max;
		perLen = viewWidth/max;
	}
	
	/**
	 * 设置第一进度
	 * @param progress
	 */
	public void setProgress(int progress){
		if(progress>max){
			progress = max;
		}
		else if(progress<0){
			progress = 0;
		}
		if(mOnProgressChanged!=null){
			mOnProgressChanged.onProgressUpdated();
		}
		this.progress = progress;
		requestLayout();
		invalidate();
	}
	
	/**
	 * 设置第二进度
	 * @param secondProgress
	 */
	public void setSecondProgress(int secondProgress){
		if(secondProgress>max){
			secondProgress = max;
		}
		else if(secondProgress<0){
			secondProgress = 0;
		}
		if(mOnProgressChanged!=null){
			mOnProgressChanged.onSecondProgressUpdated();
		}
		this.secondProgress = secondProgress;
		requestLayout();
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
    

}
