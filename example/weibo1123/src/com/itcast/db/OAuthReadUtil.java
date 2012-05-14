package com.itcast.db;

import weibo4j.http.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;

public class OAuthReadUtil {
//����������֤�û���Access_Token
	public static void saveToken(Context con,AccessToken at)
	{	SharedPreferences sp=
			con.getSharedPreferences("acToken", Context.MODE_PRIVATE);
		 sp.edit().putString("key", at.getToken())
		.putString("secret", at.getTokenSecret())
		.commit();
	}
//��ȡ�û���֤��Access Token	
	public static AccessToken readToken(Context con)
	{AccessToken at=null;
	 SharedPreferences sp=
		   con.getSharedPreferences("acToken", Context.MODE_PRIVATE);
	 String key=sp.getString("key", null);
	 String secret=sp.getString("secret", null);
	 if(key!=null)
	 {
		 at=new AccessToken(key,secret);
		 
	 }
	 return at;
	}
}
