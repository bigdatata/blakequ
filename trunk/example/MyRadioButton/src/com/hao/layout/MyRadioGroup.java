package com.hao.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class MyRadioGroup extends RadioGroup {
	private boolean mValue;
	public MyRadioGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean ismValue() {
		return mValue;
	}

	public void setmValue(boolean mValue) {
		this.mValue = mValue;
	}
	
	public void getChildValue(){
		int n = getChildCount();
		for(int i=0; i<n; i++){
			MyRadioButton radio = (MyRadioButton) getChildAt(i);
			if(radio.isChecked()){
				this.mValue = radio.ismValue();
			}
		}
	}
	
	public void setChildValue(){
		int n = getChildCount();
		for(int i=0; i<n; i++){
			MyRadioButton radio = (MyRadioButton) getChildAt(i);
			if(radio.ismValue() == mValue){
				radio.setChecked(true);
			}else{
				radio.setChecked(false);
			}
		}
	}
	

}
