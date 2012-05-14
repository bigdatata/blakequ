package com.shaccp.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shaccp.logic.IWeiboActivity;
import com.shaccp.logic.MainService;
import com.shaccp.logic.Task;

public class NewWeibo extends Activity implements IWeiboActivity {

	public static final int NEW_WEIBO =1;
	
	TextView tv;
	TextView tvName;
	Button btnNewBlog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newblog);
		tv = (TextView)findViewById(R.id.etBlog);
		btnNewBlog = (Button)findViewById(R.id.title_bt_right);
		
		tvName = (TextView)findViewById(R.id.textView);
	    tvName.setText(MainService.nowUser.getScreenName());
		
		btnNewBlog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Map map = new HashMap();
				map.put("blog", tv.getText().toString());
				
				Task t = new Task(Task.TASK_NEW_WEIBO,map);
				MainService.newTask(t);
					
			}
		});
		
		MainService.allActivity.add(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... args) {
		// TODO Auto-generated method stub

		Toast.makeText(this, "发表成功", Toast.LENGTH_LONG).show();
	}

}
