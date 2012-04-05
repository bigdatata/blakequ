package com.hao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NDKDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        Point a = new Point();
        a.setX(10);
        a.setY(10);
        
        Point b = new Point();
        b.setX(20);
        b.setY(20);
        text.setText(distance(a, b)+"");
        setContentView(text);
    }
    
    public native float distance(Point x, Point y);
    public native String fileMD5(String path);
    
    static{
    	System.loadLibrary("ndk-demo");
    }
}

