package com.hao.layout;

import com.hao.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class MyRadioButton extends RadioButton {

	private boolean mValue;
	public MyRadioButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
		mValue = array.getBoolean(R.styleable.MyRadioButton_values, false);
		array.recycle();
	}

	public boolean ismValue() {
		return mValue;
	}

	public void setmValue(boolean mValue) {
		this.mValue = mValue;
	}

}
