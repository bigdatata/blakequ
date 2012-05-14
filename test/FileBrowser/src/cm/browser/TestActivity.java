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
				//设置起始目录和查找的类型
		        intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");//"*/*"表示所有类型，设置起始文件夹和文件类型
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
                           Uri uri = data.getData();    // 接收用户所选文件的路径
                           text.setText("select: " + uri); // 在界面上显示路径
                           path = uri.toString();
                  }
        }
	}
	
	

}
