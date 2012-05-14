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
	 * ����URL�õ�������
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		url = new URL(urlStr);
		//��URL������
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		//�õ����ӵ�������
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	
	/**
	 * �������ϻ�ȡͼƬ
	 */
	public Bitmap getBitMap(String urlStr)
	{
		Bitmap bitmap = null;
		try {
			InputStream is = getInputStreamFromUrl(urlStr);
			bitmap = BitmapFactory.decodeStream(is);//������������ΪͼƬ
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
	 * ����URL�����ļ���ǰ��������ļ����е��������ı��������ķ���ֵ�����ļ����е�����
	 * 1.����һ��URL����
	 * 2.ͨ��URL���󣬴���һ��HttpURLConnection����
	 * 3.�õ�InputStram,ע���ڴ�����url��ȡ������Ҫ��Manifest�����Ȩ��
	 * 4.��InputStream���ж�ȡ����
	 * @param urlStr
	 * @return
	 */
	public String download(String urlStr) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// ����һ��URL����
			url = new URL(urlStr);
			// ����һ��Http����
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection(); 
			// ʹ��IO����ȡ����,
			//�������InputStream�ֽ�����Ȼ��InputStreamReaderת��Ϊ�ַ�����
			//���������BufferedReader���ַ������һ��һ��(�ȷŵ��˻�����)�Ķ�������
			//ע��buffer��BufferedReader,
			//XML��ȡ�����ĵ����� ,�����������InputStreamReader�����ñ���Ϊgb2312
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream(),"utf-8"));
			//���������һ�о�ѭ��
			while ((line = buffer.readLine()) != null) {
				//��������ӵ�StringBuffer���
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error", "(download)inputStream exception!");
		}
		//��Ҫ�������Ҫ�ر�reader������ͬ�ر����������һ��
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
