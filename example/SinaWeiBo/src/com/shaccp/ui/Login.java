package com.shaccp.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shaccp.logic.IWeiboActivity;
import com.shaccp.logic.MainService;
import com.shaccp.logic.Task;

public class Login extends Activity implements IWeiboActivity{

	
	EditText txtUser;
	EditText txtPwd;
	Button btnLogin;
	
	public ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		txtUser = (EditText)findViewById(R.id.txtUser);
		txtPwd = (EditText)findViewById(R.id.txtPwd);
		
		btnLogin = (Button)findViewById(R.id.loginButton);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				pd = new ProgressDialog(Login.this);
				pd.setMessage("正在登录...");
				pd.show();
				
				String name = txtUser.getText().toString();
				String pass = txtPwd.getText().toString();
				
				Map map = new HashMap();
				map.put("user", name);
				map.put("pass", pass);
				
				Task task = new Task(Task.TASK_LOGIN,map);
				
				MainService.newTask(task);
				
			}
		});
		
		MainService.allActivity.add(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		Intent it = new Intent("com.shaccp.logic.MainService");
		startService(it);
	}

	@Override
	public void refresh(Object... args) {
		// TODO Auto-generated method stub
		
		pd.dismiss();
		if(args[1]==null){
			Toast.makeText(this, "登录失败", 1000).show();
		}else{
			Toast.makeText(this, "登录成功", 3000).show();
		
			Intent it = new Intent();
			it.setClass(Login.this, Main.class);
			startActivity(it);
			finish();
		}
	}

}
