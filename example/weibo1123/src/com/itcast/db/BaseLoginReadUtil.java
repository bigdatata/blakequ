package com.itcast.db;

import weibo4j.http.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Adapter;

public class BaseLoginReadUtil {
//����������֤�û���Access_Token
	public static void saveUser(Context con,String us,String ps)
	{	SharedPreferences sp=
			con.getSharedPreferences("acToken", Context.MODE_PRIVATE);
		 
	     sp.edit().putString("us", us)
		.putString("ps", ps)
		.commit();
	}
//��ȡ�û���֤��Access Token	
	public static Adapter readUser(Context con)
	{Adapter at=null;
	 SharedPreferences sp=
		   con.getSharedPreferences("acToken", Context.MODE_PRIVATE);
	 String key=sp.getString("key", null);
	 String secret=sp.getString("secret", null);
	 if(key!=null)
	 {
		// at=new AccessToken(key,secret);
		 
	 }
	 return at;
	}
}
