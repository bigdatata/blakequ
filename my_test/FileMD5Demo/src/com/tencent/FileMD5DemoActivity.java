package com.tencent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tencent.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.backup.FileBackupHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author quhao
 *
 */
public class FileMD5DemoActivity extends Activity {
	private FileNative fn = null;
	private List<FileInfo> files;
	private ListAdapter adapter;
	private ListView listView;
	private TextView title;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.file_list);
        title = (TextView) findViewById(R.id.title_text);
        fn = new FileNative();
        files = new ArrayList<FileInfo>();
        
        if(this.isSDCardAvailable()){
        	new MyTask().execute();
        	title.setText(getResources().getString(R.string.show)+"--"+files.size()+"个");
        	adapter = new ListAdapter(this, files);
            listView.setAdapter(adapter);
        }else{
        	title.setText(getResources().getString(R.string.error));
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
     * 异步处理任务
     * @author Administrator
     *
     */
    private class MyTask extends AsyncTask<String, Void, Boolean>{
    	private final ProgressDialog dialog = new ProgressDialog(FileMD5DemoActivity.this);
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(this.dialog.isShowing())
			{
				this.dialog.dismiss();
			}
			title.setText(getResources().getString(R.string.show)+"--"+files.size()+"个");
			adapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(getResources().getString(R.string.info));
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			files = toArrayList(fn.fileMD5());
			return true;
		}    	
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