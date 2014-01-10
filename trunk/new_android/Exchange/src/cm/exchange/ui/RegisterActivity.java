package cm.exchange.ui;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cm.exchange.R;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.LoginParser;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.UserTask;

public class RegisterActivity extends BaseActivity
{
	private Button register;
	private Button cancel;
	EditText accountEdit;
	EditText pwdEdit;
	EditText pwdReEdit;
	private static final String LOGIN_INFO="UserInfo";
	private static final String ACCOUNT="account";
	private static final String PASSWORD="password";
	private static final int DIALOG = 0;
	ProgressDialog progressDialog;
	private SharedPreferences sp;
	private ExchangeApplication user;
	LoginParser loginParser = null;
	String account,pwd,pwdRe;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initView();
		loginParser = new LoginParser();
		user = (ExchangeApplication) getApplicationContext();
	}
	
	private void initView(){
		accountEdit = (EditText) findViewById(R.id.register_edit_account);
		pwdEdit = (EditText) findViewById(R.id.register_edit_passwd);
		pwdReEdit = (EditText) findViewById(R.id.register_edit_passwd_ensure);
		register=(Button)findViewById(R.id.register_ok_button);
		cancel=(Button)findViewById(R.id.register_cancle_button);
		register.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id) {
        case DIALOG:
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getResources().getString(R.string.register_now));
            return progressDialog;
        default:
            return null;
        }
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId())
		{
		case R.id.register_ok_button:
			beginRegister();
			break;
		case R.id.register_cancle_button:
			finish();
			break;
		}
	}
	
	/**
	 * 当注册成功后跳转到主页面,在检查用户名注册时，看是否已用户名重复
	 */
	private void beginRegister(){
		account = accountEdit.getText().toString().trim();
		pwd = pwdEdit.getText().toString().trim();
		pwdRe = pwdReEdit.getText().toString().trim();
		if(!checkNetState()){
			Toast.makeText(RegisterActivity.this, getResources().getString(R.string.login_fail_net_error), 3000).show();
			return;
		}
		if(account.equals("")){
			Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_null_user), 3000).show();
			return;
		}
		if(pwd.equals("")){
			Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_null_pwd), 3000).show();
			return;
		}
		if(!pwd.equals(pwdRe)){
			Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_pwd_not_same), 3000).show();
			pwdEdit.setText("");
			pwdReEdit.setText("");
			return;
		}
		//begin register to server
		showDialog(DIALOG);
		new RegisterTask().execute();
	}
	
	private class RegisterTask extends UserTask<String, Integer, Boolean>{
		boolean flag = false;
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			publishProgress(postRegisterDate(account, pwd));
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			dismissDialog(DIALOG);
			flag = register(values[0]);
			if(flag){
				Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
				startActivity(intent);
			}else{
				cancel(true);
			}
		}
		
	}
	
	private boolean register(int result){
		boolean info = false;
		info = (result>0) ? true:false;
		if(!info){
			if(result == -1){
				Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_exists_user), 3000).show();
			}else if(result == -2){
				Toast.makeText(RegisterActivity.this, getResources().getString(R.string.login_fail_net_error), 3000).show();
			}
			accountEdit.setText("");
			return false;
		}else{
			user.setName(account);
			user.setUid(String.valueOf(result));
			saveToSP(account, pwd);
			return true;
		}
	}
	
	private int postRegisterDate(String account, String pwd){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		int result = 0;
		//check the state of net, if not set -2
		if(!checkNetState()) result = -2;
		else{
			try {
				is = httpClient.doHttpPost2(URLConstant.REGISTER, new BasicNameValuePair("name", account),
							new BasicNameValuePair("pwd", pwd));
//				is = getAssets().open("register.xml");
				result = loginParser.parser(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("register parser", "registerActivity parser error");
			}finally
			{
				if(is != null)
				{
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return result;
		
	}
	
	
	private void saveToSP(String account, String pwd){
		sp = getSharedPreferences(LOGIN_INFO,
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		sp.edit().putString(ACCOUNT,account).commit();
		sp.edit().putString(PASSWORD,pwd).commit();
	}
	
  
}
