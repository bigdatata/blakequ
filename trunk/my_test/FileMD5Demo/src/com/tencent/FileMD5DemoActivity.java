package com.tencent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tencent.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

/**
 * 
 * @author quhao
 *
 */
public class FileMD5DemoActivity extends Activity {
	private TextView text;
	private FileNative fn = null;
	private List<FileInfo> files;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView) findViewById(R.id.text);
        fn = new FileNative();
        
        if(this.isSDCardAvailable()){
        	files = toArrayList(fn.fileMD5());
        	
//        	StringBuilder sb = new StringBuilder();
//	        for(String s:str){
//	        	sb.append(s+"\n");
//	        	System.out.println(s+"---");
//	        }
//        	text.setText(sb.toString());
        }else{
        	text.setText(getResources().getString(R.string.error));
        }
    }
    
    /**
     * 将string转换为FileInfo数组
     * @param str 
     * @return
     */
    private List<FileInfo> toArrayList(String[] str){
    	List<FileInfo> files = null;
    	if(str != null && str.length > 0){
    		files = new ArrayList<FileInfo>();
    		for(String s:str){
    			String[] file = s.split(",");
    			if(file != null && file.length > 0){
	    			FileInfo fi = new FileInfo();
	    			fi.setName(file[0]);
	    			fi.setMd5(file[1]);
	    			files.add(fi);
    			}
    		}
    	}
    	return files;
    }
    
    /**
     * Gets the Android external storage directory
     */
    private File getSDCardPath(){
    	return Environment.getExternalStorageDirectory();
    }

    /**
     * check the state of sdCard
     * @return if is available return true
     */
    private boolean isSDCardAvailable(){
    	return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}