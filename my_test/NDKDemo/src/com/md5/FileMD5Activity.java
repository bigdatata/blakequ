package com.md5;

import com.hao.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FileMD5Activity extends Activity {
	TextView text;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    
//    static{
//    	System.loadLibrary("file-md5");
//    }
}