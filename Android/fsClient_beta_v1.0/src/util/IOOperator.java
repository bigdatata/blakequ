package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOOperator {


	/**
	 * 发送字符消息
	 * 
	 * @param msg
	 * @return
	 */
	public static boolean sendMsg(OutputStream outputStream, String msg) {
		try {
			getDataOut(outputStream).writeUTF(msg);
			getDataOut(outputStream).flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 接收字符信息
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String readMsg(InputStream inputStream) throws IOException {
		String msg = getDataIn(inputStream).readUTF();
		return msg;
	}

	public static DataInputStream getDataIn(InputStream inputStream) {
		return new DataInputStream(inputStream);
	}

	public static DataOutputStream getDataOut(OutputStream outputStream) {
		return new DataOutputStream(outputStream);
	}
}
