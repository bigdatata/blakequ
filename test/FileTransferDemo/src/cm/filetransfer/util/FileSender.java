package cm.filetransfer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import cm.filetransfer.entity.FileEntity;
import cm.filetransfer.exception.ConnectionException;
import cm.filetransfer.exception.FileReadException;


public class FileSender {

	private OnProcessChangedListener listener;
	private int bufferSize = 20240;

	public boolean sendFile(FileEntity entity, OutputStream output)
			throws FileNotFoundException, FileReadException, ConnectionException {
		Long fileLen = entity.getFile().length();
		FileInputStream fileIn;

		fileIn = new FileInputStream(entity.getFile());
		if (entity.getSkipPosition() > 0) {
			try {
				fileIn.skip(entity.getSkipPosition());
			} catch (IOException e) {
				throw new FileReadException(e);
			}
		}

		BufferedInputStream bufferedInput = new BufferedInputStream(fileIn);
		BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
		byte[] fileBuffer = new byte[bufferSize];

		while (true) {
			int read = 0;
			if (bufferedInput != null) {
				try {
					read = bufferedInput.read(fileBuffer);
				} catch (IOException e) {
					throw new FileReadException(e);
				}
			}
			try {
				bufferedOutput.write(fileBuffer, 0, read);
			} catch (IOException e) {
				throw new ConnectionException(e);
			}
			entity.setPosition(entity.getPosition() + read);
			if (!afterEverySend(entity)) {
				break;
			}
			if (entity.getPosition().equals(fileLen)) {
				break;
			}
		}
		try {
			fileIn.close();
		} catch (IOException e) {
			throw new FileReadException(e);
		}
		try {
			bufferedOutput.flush();
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
		afterSendFinish(entity);

		return true;
	}

	protected boolean afterEverySend(FileEntity entity) {
		float persent = ((float) entity.getPosition()) / entity.getFileLen();
		int position = entity.getPosition().intValue();
		int length = (int)entity.getFile().length();
		System.out.print("send:position=" + entity.getPosition() + ".fileLen:"
				+ entity.getFile().length());
		System.out.println(".psersent:" + persent);
		listener.onChangedListener(position, length, entity.getFileName());
		return true;
	}

	protected boolean afterSendFinish(FileEntity entity) {
		System.out.println("文件发送成功:" + entity.getFileName());
		return true;
	}
	

	public void setListener(OnProcessChangedListener listener) {
		this.listener = listener;
	}

	public interface OnProcessChangedListener{
		void onChangedListener(int position, int length, String fileName);
	}
}
