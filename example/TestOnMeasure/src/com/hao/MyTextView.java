package com.hao;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyTextView extends View {
	private static final String TAG = "MyTextView";
	Paint textPaint;
	String text;
	int mColor, mSize;
	int ascent;
	
	public MyTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize();
	}


	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
		int n = ta.getIndexCount();
		for(int i=0; i<n; i++){
			int attr = ta.getIndex(i);
			switch(attr){
				case R.styleable.MyTextView_text:
					updateText(ta.getString(R.styleable.MyTextView_text));
					break;
				case R.styleable.MyTextView_textColor:
					mColor = ta.getColor(attr, Color.WHITE);
					break;
				case R.styleable.MyTextView_textSize:
					mSize = ta.getDimensionPixelSize(attr, 16);
					break;
				default:
					break;
			}
		}
		initialize();
		ta.recycle();
	}
	
	private void updateText(String s) {
		// TODO Auto-generated method stub
		text = s;
		//刷新视图，显示新内容
		requestLayout();
		invalidate();
	}


	private void initialize() {
		// TODO Auto-generated method stub
		//设置显示文字的画笔
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(mSize);
		textPaint.setColor(mColor);
		setPadding(3, 3, 3, 3);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	/**
	 * 获取子空间的高度,高度的获取主要是由父空间和子空间相互关系决定
	 * 要么父空间决定子空间，反之亦然
	 * @param widthMeasureSpec
	 * @return
	 */
	private int measureWidth(int widthMeasureSpec){
		int result = 0;
		/**
		 * 这个mode主要由父空间(在这里就是下面语句决定)
		 * <com.hao.MyTextView  android:layout_width="match_parent" android:layout_height="30dip"/>
		 * 关键是width和height的大小，如果是wrap_content则大小由text自身决定
		 * 其余两个match_parent,fill_parent是EXACTLY，即由父空间决定
		 * 如果是自定义高宽，也是EXACTLY
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);//父的空间大小
		/**
		 * EXACTLY表示孩子空间的尺寸由父控件决定
		 * AT_MOST表示孩子空间自己决定自己的尺寸
		 * UNSPECIFIED孩子任何大小
		 */
		if(specMode == MeasureSpec.EXACTLY){
			//we were told how big to be,由于是父空间决定大小，故而直接获取specSize
			result = specSize;
		}else{
			//measure the text，自己决定尺寸故而获取自身的大小
			result = (int)textPaint.measureText(text)+getPaddingLeft()+getPaddingRight();
			//当自己决定尺寸的时候不能超过父空间的大小
			if(specMode == MeasureSpec.AT_MOST){
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	
	private int measureHeight(int heightMeasureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(heightMeasureSpec);
		int specSize = MeasureSpec.getSize(heightMeasureSpec);

		//距离text当前底线的距离，它是一个负数
		ascent = (int) textPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number负数)
			//表示加上text离顶部和底部基线的距离+padding
			result = (int) (-ascent + textPaint.descent()) + getPaddingTop()+ getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawText(text, getPaddingLeft(), getPaddingTop()-ascent, textPaint);
	}

}
