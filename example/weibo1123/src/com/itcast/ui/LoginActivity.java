package com.itcast.ui;

import java.util.HashMap;

import weibo4j.Weibo;
import weibo4j.http.AccessToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itcast.db.OAuthReadUtil;
import com.itcast.logic.IWeiboActivity;
import com.itcast.logic.MainService;
import com.itcast.logic.Task;
import com.itcast.util.NetUtil;

public class LoginActivity extends Activity implements IWeiboActivity{
    public ProgressDialog pd;
    public static final int REF_LOGIN_RESULT=1;
	public void init() {
		// TODO Auto-generated method stub
		//检查网络
		if(NetUtil.checkNet(this))
		{//联网正常
			if(!MainService.isrun)
			{  MainService.isrun=true;
				Intent it=new Intent(this,MainService.class);
			this.startService(it);}
		 //判断用户是否登录过	
			AccessToken at=OAuthReadUtil.readToken(this);
			if(at!=null)//如果用户已经登录，读取登录用户验证资料
			{ //设定WEibo对象的验证访问密钥 
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	        	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
			    //MainService.mainService.weibo.setToken(at.getToken(), at.getTokenSecret());	
			  //跳转到用户首页
			   Intent it=new Intent(this,MainActivity.class);
			   this.startActivity(it);
			   finish();
			}else
			{
				
			}
		  	
			Log.d("netcheck", ".............ok");
		}else
		{ //提示用户网络异常 
			Log.d("netcheck", ".............error");
			MainService.alertNetError(this);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 init();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this);
		this.setContentView(R.layout.login);
		AutoCompleteTextView acUser=(AutoCompleteTextView)findViewById(R.id.user);
    	EditText etPass=(EditText)findViewById(R.id.password);
        acUser.setText("quhao3100590@163.com");
        etPass.setText("72781213100590");
		//acUser.setAdapter();
		this.findViewById(R.id.title_bt_left).setVisibility(View.GONE);
		this.findViewById(R.id.title_bt_right).setVisibility(View.GONE);
	    //设定TextView的标题文本 
	    TextView tvTitle=(TextView)this.findViewById(R.id.textView);
	    tvTitle.setText(this.getResources().getString(R.string.app_name));
	    Button btLogin=(Button)this.findViewById(R.id.loginButton);
	    btLogin.setOnClickListener(new OnClickListener()
	    {   
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    	AutoCompleteTextView acUser=(AutoCompleteTextView)findViewById(R.id.user);
	    	EditText etPass=(EditText)findViewById(R.id.password);
	        //定义登录验证任务 
	    	HashMap param=new HashMap();
	    	param.put("user", acUser.getText().toString());
	    	param.put("pass", etPass.getText().toString());
	        Task loginTask=new Task(Task.TASK_USER_LOGIN,param);
	        MainService.addNewTask(loginTask);
	    	//显示进度条
	        pd=new ProgressDialog(LoginActivity.this);
	        pd.setMessage(LoginActivity.this.getResources().getString(R.string.app_name));
	        pd.setTitle(LoginActivity.this.getResources().getString(R.string.app_name));
			pd.show();
	      }
	    }
	    );
	}

	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		pd.dismiss();//进度条消失
//		if(((String)param[1]))
		
		Toast.makeText(this, (String)param[1], 1000).show();
	   if(((String)param[1]).indexOf("ok")>=0)
	   { 
		   Intent it=new Intent(this,MainActivity.class);
		   this.startActivity(it);
		   finish();
	   }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.d("keydown", ".................."+keyCode);
		if(keyCode==4)//如果用户按下了返回键
		{
			MainService.promptExitApp(this);
		}
		return true;
	}
   
	
}
