package com.hao.util;

import java.io.File;

import android.os.Environment;

public class ConstantValues {
	//�ϴ�
	public static final int PORT = 8787;//�ϴ��˿�
	public static final String HOST = "192.168.1.55";//�ϴ�������IP
	//����
	public static final String DOWNLOAD_URL = "http://192.168.1.55:8080/pic/a.pdf";//����·��
	public static final File FILE_PATH = Environment.getExternalStorageDirectory();// (����)�ļ�����Ŀ¼��SDCard�ĸ�Ŀ¼
}
