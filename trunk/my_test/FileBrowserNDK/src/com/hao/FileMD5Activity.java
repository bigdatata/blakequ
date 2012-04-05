package com.hao;

import java.io.File;

import cm.test.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class FileMD5Activity extends Activity {
	TextView text;
	FileBrowser fb;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fb = new FileBrowser();
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text1);
        if(this.isSDCardAvailable()){
        	text.setText(fb.fileMD5(this.getSDCardPath().getPath()));
        }else{
        	text.setText(getResources().getString(R.string.error));
        }
    }
    
    
    static{
    	System.loadLibrary("file-md5");
    }
    
    /**
     * Gets the Android external storage directory
     */
    public File getSDCardPath(){
    	return Environment.getExternalStorageDirectory();
    }

    /**
     * check the state of sdCard
     * @return if is available return true
     */
    public boolean isSDCardAvailable(){
    	return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}

