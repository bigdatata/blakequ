package com.tencent;

import java.io.File;
import com.tencent.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class FileMD5DemoActivity extends Activity {
	TextView text;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text);
        if(this.isSDCardAvailable()){
        	String[] str = fileMD5();
        	System.out.println("-------------------");
        	StringBuilder sb = new StringBuilder();
	        for(String s:str){
	        	sb.append(s+"\n");
	        	System.out.println(s+"---");
	        }
        	text.setText(sb.toString());
        }else{
        	text.setText(getResources().getString(R.string.error));
        }
    }
    
    public native String[] fileMD5();
    
    static{
    	System.loadLibrary("ndk-demo");
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