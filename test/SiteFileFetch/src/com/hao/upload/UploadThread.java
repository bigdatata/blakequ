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
	/*需要上传文件的路径*/
	private File uploadFile;
	/*上传文件服务器的IP地址*/
	private String dstName;
	/*上传服务器端口号*/
	private int dstPort;
	/*上传socket链接*/
	private Socket socket;
	/*存储上传的数据库*/
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
	 * 模拟断开连接
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
			// 判断文件是否已有上传记录
			String souceid = logService.getBindId(uploadFile);
			// 构造拼接协议
			String head = "Content-Length=" + uploadFile.length()
					+ ";filename=" + uploadFile.getName() + ";sourceid="
					+ (souceid == null ? "" : souceid) + "%";
			// 通过Socket取得输出流
			socket = new Socket(dstName, dstPort);
			OutputStream outStream = socket.getOutputStream();
			outStream.write(head.getBytes());
			Log.i(TAG, "write to outStream");

			InputStream inStream = socket.getInputStream();
			// 获取到字符流的id与位置
			String response = StreamTool.readLine(inStream);
			Log.i(TAG, "response:" + response);
			String[] items = response.split(";");
			String responseid = items[0].substring(items[0].indexOf("=") + 1);
			String position = items[1].substring(items[1].indexOf("=") + 1);
			// 代表原来没有上传过此文件，往数据库添加一条绑定记录
			if (souceid == null) {
				logService.save(responseid, uploadFile);
			}
			RandomAccessFile fileOutStream = new RandomAccessFile(uploadFile, "r");
			// 查找上次传送的最终位置，并从这开始传送
			fileOutStream.seek(Integer.valueOf(position));
			byte[] buffer = new byte[1024];
			int len = -1;
			// 初始化上传的数据长度
			int length = Integer.valueOf(position);
			while ((len = fileOutStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
				// 设置长传数据长度
				length += len;
				listener.onUploadSize(length);
			}
			fileOutStream.close();
			outStream.close();
			inStream.close();
			socket.close();
			// 判断上传完则删除数据
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
