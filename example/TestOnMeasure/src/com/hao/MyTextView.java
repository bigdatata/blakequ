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
		//ˢ����ͼ����ʾ������
		requestLayout();
		invalidate();
	}


	private void initialize() {
		// TODO Auto-generated method stub
		//������ʾ���ֵĻ���
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
	 * ��ȡ�ӿռ�ĸ߶�,�߶ȵĻ�ȡ��Ҫ���ɸ��ռ���ӿռ��໥��ϵ����
	 * Ҫô���ռ�����ӿռ䣬��֮��Ȼ
	 * @param widthMeasureSpec
	 * @return
	 */
	private int measureWidth(int widthMeasureSpec){
		int result = 0;
		/**
		 * ���mode��Ҫ�ɸ��ռ�(�������������������)
		 * <com.hao.MyTextView  android:layout_width="match_parent" android:layout_height="30dip"/>
		 * �ؼ���width��height�Ĵ�С�������wrap_content���С��text�������
		 * ��������match_parent,fill_parent��EXACTLY�����ɸ��ռ����
		 * ������Զ���߿�Ҳ��EXACTLY
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);//���Ŀռ��С
		/**
		 * EXACTLY��ʾ���ӿռ�ĳߴ��ɸ��ؼ�����
		 * AT_MOST��ʾ���ӿռ��Լ������Լ��ĳߴ�
		 * UNSPECIFIED�����κδ�С
		 */
		if(specMode == MeasureSpec.EXACTLY){
			//we were told how big to be,�����Ǹ��ռ������С���ʶ�ֱ�ӻ�ȡspecSize
			result = specSize;
		}else{
			//measure the text���Լ������ߴ�ʶ���ȡ����Ĵ�С
			result = (int)textPaint.measureText(text)+getPaddingLeft()+getPaddingRight();
			//���Լ������ߴ��ʱ���ܳ������ռ�Ĵ�С
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

		//����text��ǰ���ߵľ��룬����һ������
		ascent = (int) textPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number����)
			//��ʾ����text�붥���͵ײ����ߵľ���+padding
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
