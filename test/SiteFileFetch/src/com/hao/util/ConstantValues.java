package com.hao.util;

import java.io.File;

import android.os.Environment;

public class ConstantValues {
	//上传
	public static final int PORT = 8787;//上传端口
	public static final String HOST = "192.168.1.55";//上传服务器IP
	//下载
	public static final String DOWNLOAD_URL = "http://192.168.1.55:8080/pic/a.pdf";//下载路径
	public static final File FILE_PATH = Environment.getExternalStorageDirectory();// (下载)文件保存目录在SDCard的根目录
}
