package cm.filetransfer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cm.filetransfer.UploadThread.OnFileStatusChangedListener;
import cm.filetransfer.constant.ConnectionParams;
import cm.filetransfer.constant.FileStatus;
import cm.filetransfer.constant.UploadResult;
import cm.filetransfer.download.SmartFileDownloader;
import cm.filetransfer.download.SmartFileDownloader.SmartDownloadProgressListener;
import cm.filetransfer.entity.FileEntity;
import cm.filetransfer.util.FileSender.OnProcessChangedListener;
import cm.filetranslate.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RemoteViews.RemoteView;

public class FileTranslateDemoActivity extends Activity implements OnClickListener{
	private static final String TAG = "FileTranslateDemoActivity";
	//���ؽ�����
	private ProgressBar downloadbar;
	private Notification notification;
	private NotificationManager mNM;
	private int notifi_id = 121;
	
	private static final int RESULT_CAPTURE_IMAGE = 1;// �����requestCode
	private static final int REQUEST_CODE_TAKE_VIDEO = 2;// ����������requestCode
	private static final int RESULT_CAPTURE_RECORDER_SOUND = 3;// ¼����requestCode
	private static final int REQUEST_SELECT_FILE = 4;
	
	private static final int MSG_UPLOAD = 1;
	private static final int MSG_DOWN_SUCCESS = 2;
	private static final int MSG_DOWN_FAIL = 3;
	private static final int MSG_STATE = 4;
	
	private TextView info;
	private Button upload, selectFile, uploadPic, uploadVideo, uploadAudio,download;
	private EditText editText;
	String appKey = "test1";
//	private ProgressDialog progressDialog;
//	private static final int PROGRESS_DIALOG = 0;
//	private UploadThread uploadThread = null;
	/*���ļ��Ƿ��ϴ���ɣ�size=0��ʾ���*/
	private int size = 0;
	//����ѡ���ļ�
	private String uploadFilePath = null;
	private StringBuilder sb = new StringBuilder();
	private SmartFileDownloader loader;
	private List<File> uploadFiles = null;
	
	//����
	private int currLen = 0;
	private String name;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        uploadFiles = new ArrayList<File>();
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		info.setText(sb.toString());
	}



	private void initView() {
		// TODO Auto-generated method stub
		info = (TextView) findViewById(R.id.info);
		upload = (Button) findViewById(R.id.upload);
		upload.setOnClickListener(this);
		selectFile = (Button) findViewById(R.id.selectFile);
		selectFile.setOnClickListener(this);
		uploadAudio = (Button) findViewById(R.id.uploadAudio);
		uploadAudio.setOnClickListener(this);
		uploadVideo = (Button) findViewById(R.id.uploadVideo);
		uploadVideo.setOnClickListener(this);
		uploadPic = (Button) findViewById(R.id.uploadPic);
		uploadPic.setOnClickListener(this);
		download = (Button) findViewById(R.id.download);
		download.setOnClickListener(this);
		downloadbar = (ProgressBar) this.findViewById(R.id.downloadbar);
		editText = (EditText) findViewById(R.id.editText);
//		editText.setText("http://file16.top100.cn/201201051509/1501F4078A65955FBF43C88A1C2EB3B7/Special_322611/Love%20the%20Way%20You%20Lie.mp3");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case RESULT_CAPTURE_IMAGE:// ����
				if (resultCode == RESULT_OK) {
					sb.append("capture:"+uploadFilePath+"**");
				}
				break;
			case REQUEST_CODE_TAKE_VIDEO:// ������Ƶ
				if (resultCode == RESULT_OK) {
					Uri uriVideo = data.getData();
					Cursor cursor = this.getContentResolver().query(uriVideo, null,
							null, null, null);
					if (cursor.moveToNext()) {
						/* _data���ļ��ľ���·�� ��_display_name���ļ��� */
						uploadFilePath = cursor.getString(cursor.getColumnIndex("_data"));
						sb.append("video:"+uploadFilePath+"**");
					}
				}
				break;
			case RESULT_CAPTURE_RECORDER_SOUND:// ¼��
				if (resultCode == RESULT_OK) {
					Uri uriRecorder = data.getData();
					Cursor cursor = this.getContentResolver().query(uriRecorder,
							null, null, null, null);
					if (cursor.moveToNext()) {
						/* _data���ļ��ľ���·�� ��_display_name���ļ��� */
						uploadFilePath = cursor.getString(cursor.getColumnIndex("_data"));
						sb.append("audio:"+uploadFilePath+"**");
					}
				}
				break;
			case REQUEST_SELECT_FILE:
				if (resultCode == RESULT_OK) {
					Uri uri = data.getData();    // �����û���ѡ�ļ���·��
					uploadFilePath = uri.getPath();
					sb.append("file:"+uploadFilePath+"**");
//					int last = uploadFilePath.lastIndexOf("/");
//					uploadFilePath = uri.getPath().substring(0, last+1);
//					fileName = uri.getLastPathSegment();
				}
				break;
		}
		//��ӵ��ϴ�
		//ȡ��SDCard��Ŀ¼  
		if(uploadFilePath != null){
		File uploadFile = new File(uploadFilePath);  
		if(uploadFile.exists()){
			uploadFiles.add(uploadFile);	
		}else{
			Toast.makeText(FileTranslateDemoActivity.this, "�ļ�������", 1).show();
		}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		uploadFilePath = null;
		switch(v.getId()){
			case R.id.selectFile:
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))//�ж�SDCard�Ƿ����  
                {
					Intent intent = new Intent();
					//������ʼĿ¼�Ͳ��ҵ�����
					intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");//"*/*"��ʾ�������ͣ�������ʼ�ļ��к��ļ�����
					intent.setClass(FileTranslateDemoActivity.this, FileBrowserActivity.class);
					startActivityForResult(intent, REQUEST_SELECT_FILE);
                }else  
                {  
                    Toast.makeText(FileTranslateDemoActivity.this, "SDCard�����ڣ�", 1).show();  
                }
				break;
			case R.id.uploadAudio:
				soundRecorderMethod();
				break;
			case R.id.uploadPic:
				cameraMethod();
				break;
			case R.id.uploadVideo:
				videoMethod();
				break;
			case R.id.download:
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					Editable url = editText.getText(); 
					if(url.length() == 0){
						Toast.makeText(FileTranslateDemoActivity.this, "��û���������ص�ַ", 1).show();
					}else{
						System.out.println("url:"+url.toString());
						initNotification();
						download(url.toString(), ConnectionParams.FILE_PATH);
					}
				} else {
					Toast.makeText(FileTranslateDemoActivity.this, "û��SDCard", 1).show();
				}
				break;
			case R.id.upload:
				size = uploadFiles.size();
				if(size != 0){
					UploadThread uploadThread = new UploadThread(FileTranslateDemoActivity.this, appKey);
					for(File f:uploadFiles){
						uploadThread.addUploadFile(appKey, f);
					}
					uploadFiles.clear();
					size = uploadThread.getFileList().size();
					uploadThread.setListener(new OnProcessChangedListener() {
						
						@Override
						public void onChangedListener(int position, int length, String fileName) {
							// TODO Auto-generated method stub
							Message msg = new Message();
							msg.what = MSG_UPLOAD;
							msg.getData().putInt("position", position);
							msg.getData().putInt("length", length);
							msg.getData().putString("name", fileName);
							handler.sendMessage(msg);
						}
					});
					uploadThread.setStateListener(new OnFileStatusChangedListener() {
						
						@Override
						public void onStatsChanged(String state, String fileName) {
								// TODO Auto-generated method stub
								Message msg = new Message();
								msg.what = MSG_STATE;
								msg.getData().putString("state", state);
								msg.getData().putString("name", fileName);
								handler.sendMessage(msg);
							}
						});
					uploadThread.start();
					initNotification();
					new Thread(updateNotification).start();
	//				showDialog(PROGRESS_DIALOG);
				}else{
					Toast.makeText(FileTranslateDemoActivity.this, "��û�����ϴ��ļ�", 1).show();
				}
				break;
		}
	}
	/*
	private void dismissDialog(){
		if(progressDialog.isShowing())
			progressDialog.dismiss();
	}
	
	 protected Dialog onCreateDialog(int id) {
	        switch(id) {
	        case PROGRESS_DIALOG:
	            progressDialog = new ProgressDialog(FileTranslateDemoActivity.this);
	            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	            progressDialog.setButton("��ͣ", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						uploadThread.disConnect();
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
	 */
	 /** 
	     * ʹ��Handler�����������̷߳�����Ϣ�� 
	     * �����ڲ��� 
	     */  
	    private Handler handler = new Handler()  
	    {  
	    	@Override
	        public void handleMessage(Message msg)   
	        {  
	    		switch(msg.what){
	    			case MSG_DOWN_FAIL:
	    				Toast.makeText(FileTranslateDemoActivity.this, msg.getData().getString("error"), 1).show();
	    				mNM.cancel(notifi_id);
	    				break;
	    			case MSG_DOWN_SUCCESS:
	    				int s = msg.getData().getInt("size");
	    				downloadbar.setProgress(s);
	    				float result = (float) downloadbar.getProgress() / (float) downloadbar.getMax();
	    				int p = (int) (result * 100);
	    				//����Ҳ�����ϴ�һ��
	    				notification.contentView.setProgressBar(R.id.bar, downloadbar.getMax(), s, false);
	    				notification.contentView.setTextViewText(R.id.title, "��������");
	    				showNotification();
	    				if (downloadbar.getProgress() == downloadbar.getMax()){
	    					Toast.makeText(FileTranslateDemoActivity.this, "���سɹ�", 1).show();
	    					mNM.cancel(notifi_id);
	    				}
	    				break;
	    			case MSG_UPLOAD:
//	    				if(!progressDialog.isShowing()) showDialog(PROGRESS_DIALOG);
	    	            //����ϴ����ȵĽ���  
	    	            int position = msg.getData().getInt("position");  
	    	            int length = msg.getData().getInt("length");
	    	            String fileName = msg.getData().getString("name");
//	    	            progressDialog.setMessage(fileName);
//	    	            progressDialog.setProgress(100*position/length);  
	    	            currLen = 100*position/length;
	    	            name = "�����ϴ�:"+fileName;
//	    	            notification.contentView.setProgressBar(R.id.bar, downloadbar.getMax(), currLen, false);
//	    	            notification.contentView.setTextViewText(R.id.title, name);
//	    				showNotification();
	    				break;
	    			case MSG_STATE:
	    				String state = msg.getData().getString("state");
	    				String file = msg.getData().getString("name");
	    				if(state.equals(UploadResult.ALREADY_UPLOAD)){
	    					Toast.makeText(FileTranslateDemoActivity.this, file+"�Ѿ��ϴ�", 1).show();
	    					size--;
	    				}else if(state.equals(UploadResult.FAILED)){
	    					Toast.makeText(FileTranslateDemoActivity.this, file+"�ϴ�ʧ��", 1).show();
	    					size--;
	    				}else if(state.equals(UploadResult.SUCCESS)){
	    					Toast.makeText(FileTranslateDemoActivity.this, file+"�ϴ��ɹ�", 1).show();
	    					size--;
	    				}else if(state.equals(FileStatus.UPLOAD_FAILED)){
	    					//�����ļ�����ʧ��
	    					Toast.makeText(FileTranslateDemoActivity.this, "������쳣�ϴ�ʧ��", 1).show();
	    					size=0;
	    				}
//	    				if(size == 0) dismissDialog();
	    				if(size == 0) mNM.cancel(notifi_id);
	    				break;
	    		}
	        }  
	    };   
	    
	    
	    /**
		 * ���๦��
		 */
		private void cameraMethod() {
			Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			uploadFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();// �����Ƭ���ļ���
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";// ��Ƭ����
			File out = new File(uploadFilePath);
			if (!out.exists()) {
				out.mkdirs();
			}
			out = new File(uploadFilePath, fileName);
			uploadFilePath = uploadFilePath + File.separatorChar + fileName;// ����Ƭ�ľ���·��
			Uri uri = Uri.fromFile(out);
			imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);
		}

		/**
		 * ������Ƶ
		 */
		private void videoMethod() {
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
		}

		/**
		 * ¼������
		 */
		private void soundRecorderMethod() {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("audio/amr");//intent.setType(ContentType.AUDIO_AMR);
			startActivityForResult(intent, RESULT_CAPTURE_RECORDER_SOUND);
		}
		
		private void download(final String path, final File dir) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						loader = new SmartFileDownloader(FileTranslateDemoActivity.this, path, dir, 3);
						int length = loader.getFileSize();// ��ȡ�ļ��ĳ���
						downloadbar.setMax(length);
						loader.download(new SmartDownloadProgressListener() {
							@Override
							public void onDownloadSize(int size) {// ����ʵʱ�õ��ļ����صĳ���
								Message msg = new Message();
								msg.what = MSG_DOWN_SUCCESS;
								msg.getData().putInt("size", size);
								handler.sendMessage(msg);
							}
						});
					} catch (Exception e) {
						Message msg = new Message();// ��Ϣ��ʾ
						msg.what = MSG_DOWN_FAIL;
						msg.getData().putString("error", "����ʧ��");// ������ش�����ʾ��ʾʧ�ܣ�
						handler.sendMessage(msg);
					}
				}
			}).start();// ��ʼ

		}
		
		/**
	     * Show a notification while this service is running.
	     */
	    private void initNotification() {
	        CharSequence text = "���ڴ���";
	        notification = new Notification(android.R.drawable.stat_sys_download, text,
	        		System.currentTimeMillis());
	        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
	        contentView.setTextViewText(R.id.title, "���ڴ���");
	        contentView.setProgressBar(R.id.bar, 100, 0, false);
	        notification.contentView = contentView;
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	                new Intent(this, FileTranslateDemoActivity.class), 0);
	        notification.contentIntent = contentIntent;
	        // Send the notification.
	        // We use a layout id because it is a unique number.  We use it later to cancel.
	        mNM.notify(notifi_id, notification);
	    }
	    
	    /**
	     * �����Ǹ���notification,���Ǹ��½�����
	     */
	    private void showNotification(){
	    	mNM.notify(notifi_id, notification);
	    }
	    
	    /**
	     * ���ڸ���notification����
	     */
	    Runnable updateNotification = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(size != 0){
					notification.contentView.setProgressBar(R.id.bar, 100, currLen, false);
		            notification.contentView.setTextViewText(R.id.title, name);
					showNotification();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
}