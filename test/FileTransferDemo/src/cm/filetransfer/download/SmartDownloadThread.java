package cm.filetransfer.download;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * 线程下载
 * @author Administrator
 * 
 */
public class SmartDownloadThread extends Thread {
	private static final String TAG = "SmartDownloadThread";
	private File saveFile;
	private URL downUrl;
	private int block;
	/*  *下载开始位置 */
	private int threadId = -1;
	private int downLength;
	private boolean finish = false;
	private SmartFileDownloader downloader;

	public SmartDownloadThread(SmartFileDownloader downloader, URL downUrl,
			File saveFile, int block, int downLength, int threadId) {
		this.downUrl = downUrl;
		this.saveFile = saveFile;
		this.block = block;
		this.downloader = downloader;
		this.threadId = threadId;
		this.downLength = downLength;
	}

	@Override
	public void run() {
		if (downLength < block) {// 未下载完成
			try {
				HttpURLConnection http = (HttpURLConnection) downUrl
						.openConnection();
				http.setConnectTimeout(5 * 1000);
				http.setRequestMethod("GET");
				http.setRequestProperty("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				http.setRequestProperty("Accept-Language", "zh-CN");
				http.setRequestProperty("Referer", downUrl.toString());
				http.setRequestProperty("Charset", "UTF-8");
				int startPos = block * (threadId - 1) + downLength;// 开始位置
				int endPos = block * threadId - 1;// 结束位置
				http.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);// 设置获取实体数据的范围
				http.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				http.setRequestProperty("Connection", "Keep-Alive");

				InputStream inStream = http.getInputStream();
				byte[] buffer = new byte[1024];
				int offset = 0;
				print("Thread " + this.threadId + " start download from position " + startPos);
				RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");
				threadfile.seek(startPos);
				while ((offset = inStream.read(buffer, 0, 1024)) != -1) {
					threadfile.write(buffer, 0, offset);
					downLength += offset;
					downloader.update(this.threadId, downLength);
					downloader.saveLogFile();
					downloader.append(offset);
				}
				threadfile.close();
				inStream.close();
				print("Thread " + this.threadId + " download finish");
				this.finish = true;
			} catch (Exception e) {
				this.downLength = -1;
				print("Thread " + this.threadId + ":" + e);
			}
		}
	}

	private static void print(String msg) {
		Log.i(TAG, msg);
	}

	/**
	 * 下载是否完成
	 * @return
	 */
	public boolean isFinish() {
		return finish;
	}

	/**
	 * 已经下载的内容大小
	 * @return 如果返回值为-1,代表下载失败
	 */
	public long getDownLength() {
		return downLength;
	}
}
