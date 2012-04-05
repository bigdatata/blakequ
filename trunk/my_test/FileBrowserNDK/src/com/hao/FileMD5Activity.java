package com.hao;

import cm.test.R;
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
        text = (TextView) findViewById(R.id.text1);
        text.setText("");
    }
    
    
    static{
    	System.loadLibrary("file-md5");
    }
}