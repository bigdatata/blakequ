package cm.browser;

import java.io.File;

import cm.browser.view.FileBrowserActivity;
import cm.util.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends BaseActivity {

	Button button;
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		button = (Button) findViewById(R.id.testButton);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//������ʼĿ¼�Ͳ��ҵ�����
		        intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");//"*/*"��ʾ�������ͣ�������ʼ�ļ��к��ļ�����
		        intent.setClass(TestActivity.this, FileBrowserActivity.class);
		        startActivityForResult(intent, 1);
			}
		});
		text= (TextView) findViewById(R.id.testText);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String path;
        if (resultCode == RESULT_OK) {
                  if (requestCode == 1) {
                           Uri uri = data.getData();    // �����û���ѡ�ļ���·��
                           text.setText("select: " + uri); // �ڽ�������ʾ·��
                           path = uri.toString();
                  }
        }
	}
	
	

}
