package cm.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class HttpDownloader {
	private URL url = null;
	private static final String ERROR= "Error";



	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		url = new URL(urlStr);
		//打开URL的链接
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		//得到链接的输入流
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	
	/**
	 * 从网络上获取图片
	 */
	public Bitmap getBitMap(String urlStr)
	{
		Bitmap bitmap = null;
		try {
			InputStream is = getInputStreamFromUrl(urlStr);
			bitmap = BitmapFactory.decodeStream(is);//从输入流解码为图片
			if(bitmap == null)
			{
				Log.e(ERROR, "can not decode bitmap from " + urlStr);
			}
			is.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(ERROR, "URLException(HttpDownloader87)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(ERROR, "IOException(HttpDownloader91)");
		}
		return bitmap;
	}

	/**
	 * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
	 * 1.创建一个URL对象
	 * 2.通过URL对象，创建一个HttpURLConnection对象
	 * 3.得到InputStram,注意在从网络url获取，必须要在Manifest中添加权限
	 * 4.从InputStream当中读取数据
	 * @param urlStr
	 * @return
	 */
	public String download(String urlStr) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection(); 
			// 使用IO流读取数据,
			//最里层是InputStream字节流，然后InputStreamReader转换为字符流，
			//最后在套上BufferedReader将字符流变成一行一行(先放到了缓冲区)的读进来。
			//注意buffer是BufferedReader,
			//XML读取得中文的问题 ,解决方法：在InputStreamReader中设置编码为gb2312
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream(),"utf-8"));
			//如果还有下一行就循环
			while ((line = buffer.readLine()) != null) {
				//将这行添加到StringBuffer最后
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error", "(download)inputStream exception!");
		}
		//不要忘记最后还要关闭reader，就如同关闭输入输出流一样
		finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
		//return null;
	}
}
