package com.shaccp.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import android.graphics.drawable.BitmapDrawable;

public class NetUtil {

	public static BitmapDrawable getImageFromUrl(URL url) {

		BitmapDrawable icon = null;

		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			icon = new BitmapDrawable(conn.getInputStream());

			conn.disconnect();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return icon;

	}

	public static String getTimeDiff(Date date) {
		Calendar cal = Calendar.getInstance();
		long diff = 0;
		Date dnow = cal.getTime();
		String str = "";
		diff = dnow.getTime() - date.getTime();
		
		System.out.println("diff---->"+date);

		if (diff > 24 * 60 * 60 * 1000) {
			//System.out.println("1天前");
			str="1天前";
		} else if (diff > 5 * 60 * 60 * 1000) {
			//System.out.println("2小时前");
			str="2小时前";
		} else if (diff > 1 * 60 * 60 * 1000) {
			//System.out.println("1小时前");
			str="小时前";
		} else if (diff > 30 * 60 * 1000) {
			//System.out.println("30分钟前");
			str="30分钟前";
		} else if (diff > 15 * 60 * 1000) {
			//System.out.println("15分钟前");
			str="15分钟前";
		} else if (diff > 5 * 60 * 1000) {
			//System.out.println("5分钟前");
			str="5分钟前";
		} else if (diff > 1 * 60 * 1000) {
			//System.out.println("1分钟前");
			str="1分钟前";
		}else{
			str="刚刚";
		}
		

		return str;
	}

}
