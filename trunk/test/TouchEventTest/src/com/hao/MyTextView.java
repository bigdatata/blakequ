package com.hao;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MyTextView extends TextView {
	private final String TAG = "MyTextView"; 
    public MyTextView(Context context, AttributeSet attrs) { 
       super(context, attrs); 
       Log.e(TAG,TAG); 
    } 

    @Override 
    public boolean onTouchEvent(MotionEvent ev) { 
       int action = ev.getAction(); 
       switch(action){ 
       case MotionEvent.ACTION_DOWN: 
           Log.e(TAG,"onTouchEvent action:ACTION_DOWN"); 
           break; 
       case MotionEvent.ACTION_MOVE: 
           Log.e(TAG,"onTouchEvent action:ACTION_MOVE"); 
           break; 
       case MotionEvent.ACTION_UP: 
           Log.e(TAG,"onTouchEvent action:ACTION_UP"); 
           break; 
       case MotionEvent.ACTION_CANCEL: 
           Log.e(TAG,"onTouchEvent action:ACTION_CANCEL"); 
           break; 
       } 
       return false; 
//       return true;
    } 

    public void onClick(View v) { 
       Log.e(TAG, "onClick"); 
    } 

    public boolean onLongClick(View v) { 
       Log.e(TAG, "onLongClick"); 
       return false; 
    } 
}
