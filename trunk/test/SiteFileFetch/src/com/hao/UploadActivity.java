package com.hao;

import java.io.File;
import java.util.List;

import com.hao.upload.UploadThread;
import com.hao.upload.UploadThread.UploadProgressListener;
import com.hao.util.ConstantValues;
import com.hao.util.FileBrowserActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * http://blog.csdn.net/kesenhoo/article/details/6543644
 * http://blog.csdn.net/kesenhoo/article/details/6542997
 * @author Administrator
 *
 */
public class UploadActivity extends Activity implements OnClickListener{
	private static final String TAG = "SiteFileFetchActivity";
	private Button download, upload, select_file;
	private TextView info;
	private static final int PROGRESS_DIALOG = 0;
	private ProgressDialog progressDialog;
	private UploadThread uploadThread;
	private String uploadFilePath = null;
	private String fileName;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        initView();
    }
    
	private void initView(){
    	download = (Button) findViewById(R.id.download);
    	download.setOnClickListener(this);
    	upload = (Button) findViewById(R.id.upload);
    	upload.setOnClickListener(this);
    	info = (TextView) findViewById(R.id.info);
    	select_file = (Button) findViewById(R.id.select_file);
    	select_file.setOnClickListener(this);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                  if (requestCode == 1) {
                           Uri uri = data.getData();    // �����û���ѡ�ļ���·��
                           info.setText("select: " + uri); // �ڽ�������ʾ·��
                           uploadFilePath = uri.getPath();
                           int last = uploadFilePath.lastIndexOf("/");
                           uploadFilePath = uri.getPath().substring(0, last+1);
                           fileName = uri.getLastPathSegment();
                  }
        }
	}
    
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(UploadActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setButton("��ͣ", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					uploadThread.closeLink();
					dialog.dismiss();
				}
			});
            progressDialog.setMessage("�����ϴ�...");
            progressDialog.setMax(100);
            return progressDialog;
        default:
            return null;
        }
    }
    
    /** 
     * ʹ��Handler�����������̷߳�����Ϣ�� 
     * �����ڲ��� 
     */  
    private Handler handler = new Handler()  
    {  
    	@Override
        public void handleMessage(Message msg)   
        {  
            //����ϴ����ȵĽ���  
            int length = msg.getData().getInt("size");  
            progressDialog.setProgress(length);  
            if(progressDialog.getProgress()==progressDialog.getMax())//�ϴ��ɹ�  
            {  
            	progressDialog.dismiss();
                Toast.makeText(UploadActivity.this, getResources().getString(R.string.upload_over), 1).show();  
            }  
        }  
    };   

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Resources r = getResources();
		switch(v.getId()){
			case R.id.select_file:
				Intent intent = new Intent();
				//������ʼĿ¼�Ͳ��ҵ�����
		        intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");//"*/*"��ʾ�������ͣ�������ʼ�ļ��к��ļ�����
		        intent.setClass(UploadActivity.this, FileBrowserActivity.class);
		        startActivityForResult(intent, 1);
				break;
			case R.id.download:
				startActivity(new Intent(UploadActivity.this, SmartDownloadActivity.class));
				break;
			case R.id.upload:
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))//�ж�SDCard�Ƿ����  
                {  
                	if(uploadFilePath == null){
                		Toast.makeText(UploadActivity.this, "��û�����ϴ��ļ�", 1).show();
                	}
                	System.out.println("uploadFilePath:"+uploadFilePath+" "+fileName);
                    //ȡ��SDCard��Ŀ¼  
                    File uploadFile = new File(new File(uploadFilePath), fileName);  
                    Log.i(TAG, "filePath:"+uploadFile.toString());
                    if(uploadFile.exists())  
                    {  
                        showDialog(PROGRESS_DIALOG);
                        info.setText(uploadFile+" "+ConstantValues.HOST+":"+ConstantValues.PORT);
            			progressDialog.setMax((int) uploadFile.length());//���ó����ļ������̶�
                    	uploadThread = new UploadThread(UploadActivity.this, uploadFile, ConstantValues.HOST, ConstantValues.PORT);
                    	uploadThread.setListener(new UploadProgressListener() {
							
							@Override
							public void onUploadSize(int size) {
								// TODO Auto-generated method stub
								Message msg = new Message();
								msg.getData().putInt("size", size);
								handler.sendMessage(msg);
							}
						});
                    	uploadThread.start();
                    }  
                    else  
                    {  
                        Toast.makeText(UploadActivity.this, "�ļ�������", 1).show();  
                    }  
                }  
                else  
                {  
                    Toast.makeText(UploadActivity.this, "SDCard�����ڣ�", 1).show();  
                }  
				break;
		}
			
	}
	
	
}