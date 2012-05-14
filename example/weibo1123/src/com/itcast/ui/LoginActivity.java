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
		//�������
		if(NetUtil.checkNet(this))
		{//��������
			if(!MainService.isrun)
			{  MainService.isrun=true;
				Intent it=new Intent(this,MainService.class);
			this.startService(it);}
		 //�ж��û��Ƿ��¼��	
			AccessToken at=OAuthReadUtil.readToken(this);
			if(at!=null)//����û��Ѿ���¼����ȡ��¼�û���֤����
			{ //�趨WEibo�������֤������Կ 
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	        	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
			    //MainService.mainService.weibo.setToken(at.getToken(), at.getTokenSecret());	
			  //��ת���û���ҳ
			   Intent it=new Intent(this,MainActivity.class);
			   this.startActivity(it);
			   finish();
			}else
			{
				
			}
		  	
			Log.d("netcheck", ".............ok");
		}else
		{ //��ʾ�û������쳣 
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
	    //�趨TextView�ı����ı� 
	    TextView tvTitle=(TextView)this.findViewById(R.id.textView);
	    tvTitle.setText(this.getResources().getString(R.string.app_name));
	    Button btLogin=(Button)this.findViewById(R.id.loginButton);
	    btLogin.setOnClickListener(new OnClickListener()
	    {   
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    	AutoCompleteTextView acUser=(AutoCompleteTextView)findViewById(R.id.user);
	    	EditText etPass=(EditText)findViewById(R.id.password);
	        //�����¼��֤���� 
	    	HashMap param=new HashMap();
	    	param.put("user", acUser.getText().toString());
	    	param.put("pass", etPass.getText().toString());
	        Task loginTask=new Task(Task.TASK_USER_LOGIN,param);
	        MainService.addNewTask(loginTask);
	    	//��ʾ������
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
		pd.dismiss();//��������ʧ
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
		if(keyCode==4)//����û������˷��ؼ�
		{
			MainService.promptExitApp(this);
		}
		return true;
	}
   
	
}
