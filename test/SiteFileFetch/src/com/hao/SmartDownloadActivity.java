package com.hao;

import java.io.File;

import com.hao.R;
import com.hao.R.id;
import com.hao.R.layout;
import com.hao.download.SmartFileDownloader;
import com.hao.download.SmartFileDownloader.SmartDownloadProgressListener;
import com.hao.util.ConstantValues;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * package com.smart.activoty.download;
 * 
 * @author Administrator
 * 
 */
public class SmartDownloadActivity extends Activity {
	private ProgressBar downloadbar;
	private TextView resultView;
	private String path = ConstantValues.DOWNLOAD_URL;
	SmartFileDownloader loader;
	private Handler handler = new Handler() {
		@Override
		// ��Ϣ
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				downloadbar.setProgress(size);
				float result = (float) downloadbar.getProgress() / (float) downloadbar.getMax();
				int p = (int) (result * 100);
				resultView.setText(p + "%");
				if (downloadbar.getProgress() == downloadbar.getMax())
					Toast.makeText(SmartDownloadActivity.this, "���سɹ�", 1).show();
				break;
			case -1:
				Toast.makeText(SmartDownloadActivity.this, msg.getData().getString("error"), 1).show();
				break;
			}

		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);

		Button button = (Button) this.findViewById(R.id.button);
		Button closeConn = (Button) findViewById(R.id.closeConn);
		closeConn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(loader != null){
					finish();
				}else{
					Toast.makeText(SmartDownloadActivity.this, "��û�п�ʼ���أ�������ͣ", 1).show();
				}
			}
		});
		downloadbar = (ProgressBar) this.findViewById(R.id.downloadbar);
		resultView = (TextView) this.findViewById(R.id.result);
		resultView.setText(path);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					download(path, ConstantValues.FILE_PATH);
				} else {
					Toast.makeText(SmartDownloadActivity.this, "û��SDCard", 1).show();
				}
			}
		});
	}

	// ����UI�ؼ��ĸ���ֻ�������߳�(UI�߳�)��������ڷ�UI�̸߳���UI�ؼ������µĽ�����ᷴӳ����Ļ�ϣ�ĳЩ�ؼ��������
	private void download(final String path, final File dir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					loader = new SmartFileDownloader(SmartDownloadActivity.this, path, dir, 3);
					int length = loader.getFileSize();// ��ȡ�ļ��ĳ���
					downloadbar.setMax(length);
					loader.download(new SmartDownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// ����ʵʱ�õ��ļ����صĳ���
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();// ��Ϣ��ʾ
					msg.what = -1;
					msg.getData().putString("error", "����ʧ��");// ������ش�����ʾ��ʾʧ�ܣ�
					handler.sendMessage(msg);
				}
			}
		}).start();// ��ʼ

	}
}
