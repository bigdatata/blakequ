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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import cm.exchange.R;
import cm.exchange.db.UserService;
import cm.exchange.entity.User;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.LoginParser;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.UserTask;

public class LoginActivity extends BaseActivity
{
	private EditText loginEditText;
	private EditText passEditText;
	private Button loginButton;
	private Button passButton;
	private CheckBox rememberPwd;
	private CheckBox autoLogin;
	ProgressDialog progressDialog;
	private static final String LOGIN_INFO="UserInfo";
	private static final String ACCOUNT="account";
	private static final String PASSWORD="password";
	private static final String AUTOLOGIN = "autoLogin";
	private static final String REMEMBERPWD = "rememberPwd";
	private static final int DIALOG = 0;
	private SharedPreferences sp;
	private ExchangeApplication userApp;
	LoginParser loginParser = null;
	String account,pwd;
	UserService userDB = null;
	User user = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initView();
		int changeUser = getIntent().getIntExtra("changeUser", -1);
		userDB = new UserService(this);
		loginParser = new LoginParser();
		userApp = (ExchangeApplication) getApplicationContext();
		initConfig();
		rememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				sp = getSharedPreferences(LOGIN_INFO, 0);
				sp.edit().putBoolean(REMEMBERPWD, isChecked).commit();
			}
		});
		autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				sp = getSharedPreferences(LOGIN_INFO, 0);
				sp.edit().putBoolean(AUTOLOGIN, isChecked).commit();
			}
		});
		if(autoLogin.isChecked()&& changeUser == -1){
			beginLogin();
		}
		
	}
	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		userDB.open();
	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(user != null){
			userDB.update(user);
		}
		userDB.close();
	}




	private void initView(){
		loginEditText=(EditText)findViewById(R.id.login_edit_account);
		passEditText=(EditText)findViewById(R.id.login_edit_passwd);
		loginButton=(Button)findViewById(R.id.login_button);
		loginButton.setOnClickListener(this);
		passButton=(Button)findViewById(R.id.login_register_button);
		passButton.setOnClickListener(this);
		rememberPwd=(CheckBox)findViewById(R.id.login_remember_pwd_checkbox);
		autoLogin = (CheckBox) findViewById(R.id.login_autologin_checkbox);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
			case R.id.login_button:
				beginLogin();
				break;
			case R.id.login_register_button:
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				break;
		}
	}
	
	public void beginLogin(){
		account = loginEditText.getText().toString().trim();
		pwd = passEditText.getText().toString().trim();
		if(account.equals("") || pwd.equals("")){
			Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_error), 3000).show();
		}else{
			showDialog(DIALOG);
			new LoginTask().execute();
//			Intent intent=new Intent(LoginActivity.this,MainPageActivity.class);
//			startActivity(intent);
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id) {
        case DIALOG:
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getResources().getString(R.string.login_now));
            return progressDialog;
        default:
            return null;
        }
	}

	
	private class LoginTask extends UserTask<String, Integer, Boolean>{
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
			flag = login(values[0]);
			if(flag){
				Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
				startActivity(intent);
			}else{
				cancel(true);
			}
			dismissDialog(DIALOG);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
	
	/**
	 * @param account
	 * @param pwd
	 * @return
	 */
	private int postRegisterDate(String account, String pwd){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		int result = -10;
		//check the state of net, if not set -3
		if(!checkNetState()) result = -3;
		else{
			try {
				is = httpClient.doHttpPost2(URLConstant.LOGIN, new BasicNameValuePair("account", account),
							new BasicNameValuePair("password", pwd));
//				is = getAssets().open("login_success.xml");
				result = loginParser.parser(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("login parser", "loginActivity parser error");
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
	
	private boolean login(int state){
		//这里检查用户名或密码是否正确1.大于0，成功；-1.没有当前用户，即用户名错误；-2.有当前用户，但密码错误
		boolean loginInfo = false;
		loginInfo = (state>0) ? true:false;
		
		if(!loginInfo){
			if(state == -1){
				Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_fail_no_user), 3000).show();
				loginEditText.setText("");
			}
			else if(state == -2){
				Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_fail_pwd), 3000).show();
				passEditText.setText("");
			}else if(state == -3){
				Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_fail_net_error), 3000).show();
			}else if(state == -10){
				Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_fail_out_of_time), 3000).show();
			}
			return false;
		}else{
			userApp.setName(account);
			userApp.setUid(String.valueOf(state));
			user = new User();
			user.setId(state);
			user.setUsername(account);
			if (rememberPwd.isChecked()) {
				sp = getSharedPreferences(LOGIN_INFO,
						Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
				sp.edit().putString(ACCOUNT,account).commit();
				sp.edit().putString(PASSWORD,pwd).commit();
			}
			return true;
		}
	}
	
	
	private void initConfig() {
		sp = getSharedPreferences(LOGIN_INFO, 0);
		loginEditText.setText(sp.getString(ACCOUNT, null));
		passEditText.setText(sp.getString(PASSWORD, null));
		autoLogin.setChecked(sp.getBoolean(AUTOLOGIN, false));
		rememberPwd.setChecked(sp.getBoolean(REMEMBERPWD, false));
	}
}
