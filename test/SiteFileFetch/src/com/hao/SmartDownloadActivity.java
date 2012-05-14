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
		// 信息
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				downloadbar.setProgress(size);
				float result = (float) downloadbar.getProgress() / (float) downloadbar.getMax();
				int p = (int) (result * 100);
				resultView.setText(p + "%");
				if (downloadbar.getProgress() == downloadbar.getMax())
					Toast.makeText(SmartDownloadActivity.this, "下载成功", 1).show();
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
					Toast.makeText(SmartDownloadActivity.this, "还没有开始下载，不能暂停", 1).show();
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
					Toast.makeText(SmartDownloadActivity.this, "没有SDCard", 1).show();
				}
			}
		});
	}

	// 对于UI控件的更新只能由主线程(UI线程)负责，如果在非UI线程更新UI控件，更新的结果不会反映在屏幕上，某些控件还会出错
	private void download(final String path, final File dir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					loader = new SmartFileDownloader(SmartDownloadActivity.this, path, dir, 3);
					int length = loader.getFileSize();// 获取文件的长度
					downloadbar.setMax(length);
					loader.download(new SmartDownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// 可以实时得到文件下载的长度
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();// 信息提示
					msg.what = -1;
					msg.getData().putString("error", "下载失败");// 如果下载错误，显示提示失败！
					handler.sendMessage(msg);
				}
			}
		}).start();// 开始

	}
}
