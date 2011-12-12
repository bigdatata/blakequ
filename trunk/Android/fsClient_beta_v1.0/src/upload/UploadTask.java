package upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import sender.FileSender;
import util.IOOperator;
import util.ValiCodeCreator;
import constant.FileStatus;
import constant.ProtocolKey;
import constant.RequestResult;
import constant.UploadResult;
import entity.FileEntity;
import exception.ConnectionException;
import exception.FileReadException;

public class UploadTask {
	private String appKey;

	private FileSender fileSender=new FileSender();

	private List<FileEntity> fileList = new ArrayList<FileEntity>();

	private int currentFileIndex=-1;

	/**
	 * 上传链接套接字<br>
	 */
	private Socket socket;

	private String serverAddr;

	private int port;

	public UploadTask(String serverAddr, int port, String appKey) {
		this.serverAddr = serverAddr;
		this.port = port;
		this.appKey = appKey;
	}

	/**
	 * 
	 */
	public void connect() throws IOException {
		socket = new Socket(serverAddr, port);
	}

	/**
	 * 添加需要进行上传的文件
	 * 
	 * @param fileType
	 * @param fileKey
	 * @param file
	 * @param breakpoint
	 * @return
	 */
	public boolean addUploadFile(String fileType, String fileKey, File file) {
		if(currentFileIndex!=-1){
			return false;
		}
		String valiCode = ValiCodeCreator.create(file);
		FileEntity entity = FileEntity.createFileEntity(fileType, fileKey,
				file, valiCode);
		return fileList.add(entity);
	}

	/**
	 * 获得第一次的请求信息
	 * 
	 * @param fileEntity
	 * @return
	 */
	public String getFirstRequestMsg(FileEntity fileEntity) {
		JSONObject requestOb = new JSONObject();
		requestOb.put(ProtocolKey.APP_KEY, appKey);
		requestOb.put(ProtocolKey.FILE_SIZE, fileList.size());
		requestOb.put(ProtocolKey.FILE_TYPE, fileEntity.getFileType());
		requestOb.put(ProtocolKey.FILE_KEY, fileEntity.getFileKey());
		requestOb.put(ProtocolKey.FILE_NAME, fileEntity.getFileName());
		requestOb.put(ProtocolKey.FILE_LENGTH, fileEntity.getFileLen());
		requestOb.put(ProtocolKey.FILE_VALI_CODE, fileEntity.getValiCode());
		return requestOb.toString();
	}

	/**
	 * 获取除第一次以外的请求信息
	 * 
	 * @param fileEntity
	 * @return
	 */
	public String getRequestMsg(FileEntity fileEntity) {
		JSONObject requestOb = new JSONObject();
		requestOb.put(ProtocolKey.FILE_TYPE, fileEntity.getFileType());
		requestOb.put(ProtocolKey.FILE_KEY, fileEntity.getFileKey());
		requestOb.put(ProtocolKey.FILE_NAME, fileEntity.getFileName());
		requestOb.put(ProtocolKey.FILE_LENGTH, fileEntity.getFileLen());
		requestOb.put(ProtocolKey.FILE_VALI_CODE, fileEntity.getValiCode());
		return requestOb.toString();
	}

	/**
	 * 请求文件传输
	 * 
	 * @return
	 * @throws ConnectionException
	 */
	public boolean request() throws ConnectionException {
		String request;
		String response;
		if (currentFileIndex == 0) {
			request = getFirstRequestMsg(fileList.get(currentFileIndex));
		} else {
			request = getRequestMsg(fileList.get(currentFileIndex));
		}
		try {
			IOOperator.sendMsg(socket.getOutputStream(), request);
			response = IOOperator.readMsg(socket.getInputStream());
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
		JSONObject responseOb = JSONObject.fromObject(response);
		String requestResult = responseOb.getString(ProtocolKey.REQUEST_RESULT);
		if (requestResult.equals(RequestResult.START)) {
			Long skipPosition = responseOb.getLong(ProtocolKey.SKIP_POSITION);
			fileList.get(currentFileIndex).setSkipPosition(skipPosition);
			fileList.get(currentFileIndex).setPosition(skipPosition);
		} else if (requestResult.equals(RequestResult.ALREADY_UPLOAD)) {
			return false;
		}
		return true;

	}

	/**
	 * 发送文件
	 * @return
	 * @throws ConnectionException
	 */
	private boolean send() throws ConnectionException {

		try {
			fileSender.sendFile(fileList.get(currentFileIndex), socket
					.getOutputStream());
		} catch (FileNotFoundException e) {
			throw new ConnectionException(e);
		} catch (FileReadException e) {
			throw new ConnectionException(e);
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
		return true;
	}

	private boolean finish()throws ConnectionException{
		String result;
		try {
			result=IOOperator.readMsg(socket.getInputStream());
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
		JSONObject resultOb=JSONObject.fromObject(result);
		String uploadResult=resultOb.getString(ProtocolKey.UPLOAD_RESULT);
		if(uploadResult.equals(UploadResult.SUCCESS)){
			fileList.get(currentFileIndex).setFileStatus(UploadResult.SUCCESS);
		}else {
			fileList.get(currentFileIndex).setFileStatus(UploadResult.FAILED);
		}
		return true;
	}
	public boolean upload() {

		for (int i = 0; i < fileList.size(); i++) {
			currentFileIndex = i;
			try {
				if (!request()) {
					continue;
				}
				if(!send()){
					continue;
				}
				finish();
			} catch (ConnectionException e) {
				setUploadFailed(currentFileIndex);
				return false;
			}
		}

		return true;
	}

	/**
	 * 把以index为开始的列表里面的文件状态设成UPLOAD_FAILED
	 * 
	 * @param index
	 */
	private void setUploadFailed(int index) {
		for (int i = index; i < fileList.size(); i++) {
			fileList.get(i).setFileStatus(FileStatus.UPLOAD_FAILED);
		}
	}

	public static void main(String[] args) {
		UploadTask task = new UploadTask("192.168.1.104", 8012, "test");
		try {
			task.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		task
				.addUploadFile(
						"picture",
						"11",
						new File(
								"D:\\TDDOWNLOAD\\[Dymy][Mobile Suit Gundam Unicorn][01][BIG5][720P].rmvb"));
		task.addUploadFile("picture", "12", new File("F:\\music\\美人尖.mp3"));
		task.upload();
	}
}
