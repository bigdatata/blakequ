package com.hao.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import android.content.Context;
import android.util.Log;

import com.hao.db.UploadLogService;
import com.hao.util.StreamTool;

public class UploadThread extends Thread {

	private static final String TAG = "UploadThread";
	/*��Ҫ�ϴ��ļ���·��*/
	private File uploadFile;
	/*�ϴ��ļ���������IP��ַ*/
	private String dstName;
	/*�ϴ��������˿ں�*/
	private int dstPort;
	/*�ϴ�socket����*/
	private Socket socket;
	/*�洢�ϴ������ݿ�*/
	private UploadLogService logService; 
	private UploadProgressListener listener;
	public UploadThread(Context context, File uploadFile, final String dstName,final int dstPort){
		this.uploadFile = uploadFile;
		this.dstName = dstName;
		this.dstPort = dstPort;
		logService = new UploadLogService(context);
	}
	
	public void setListener(UploadProgressListener listener) {
		this.listener = listener;
	}

	/**
	 * ģ��Ͽ�����
	 */
	public void closeLink(){
		try{
			if(socket != null) socket.close();
		}catch(IOException e){
			e.printStackTrace();
			Log.e(TAG, "close socket fail");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// �ж��ļ��Ƿ������ϴ���¼
			String souceid = logService.getBindId(uploadFile);
			// ����ƴ��Э��
			String head = "Content-Length=" + uploadFile.length()
					+ ";filename=" + uploadFile.getName() + ";sourceid="
					+ (souceid == null ? "" : souceid) + "%";
			// ͨ��Socketȡ�������
			socket = new Socket(dstName, dstPort);
			OutputStream outStream = socket.getOutputStream();
			outStream.write(head.getBytes());
			Log.i(TAG, "write to outStream");

			InputStream inStream = socket.getInputStream();
			// ��ȡ���ַ�����id��λ��
			String response = StreamTool.readLine(inStream);
			Log.i(TAG, "response:" + response);
			String[] items = response.split(";");
			String responseid = items[0].substring(items[0].indexOf("=") + 1);
			String position = items[1].substring(items[1].indexOf("=") + 1);
			// ����ԭ��û���ϴ������ļ��������ݿ����һ���󶨼�¼
			if (souceid == null) {
				logService.save(responseid, uploadFile);
			}
			RandomAccessFile fileOutStream = new RandomAccessFile(uploadFile, "r");
			// �����ϴδ��͵�����λ�ã������⿪ʼ����
			fileOutStream.seek(Integer.valueOf(position));
			byte[] buffer = new byte[1024];
			int len = -1;
			// ��ʼ���ϴ������ݳ���
			int length = Integer.valueOf(position);
			while ((len = fileOutStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
				// ���ó������ݳ���
				length += len;
				listener.onUploadSize(length);
			}
			fileOutStream.close();
			outStream.close();
			inStream.close();
			socket.close();
			// �ж��ϴ�����ɾ������
			if (length == uploadFile.length())
				logService.delete(uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public interface UploadProgressListener{
		void onUploadSize(int size);
	}
}
