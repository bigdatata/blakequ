package cm.filetransfer.constant;

import java.io.File;

import android.os.Environment;

public interface ConnectionParams {
	String IP = "118.122.88.93";
	int PORT = 8012;
	String DOWN_URL = "";
	File FILE_PATH = Environment.getExternalStorageDirectory();// (下载)文件保存目录在SDCard的根目录
}
