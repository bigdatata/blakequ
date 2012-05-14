package cm.filetransfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cm.filetransfer.constant.ConnectionParams;
import cm.filetransfer.constant.FileStatus;
import cm.filetransfer.constant.ProtocolKey;
import cm.filetransfer.constant.RequestResult;
import cm.filetransfer.constant.UploadResult;
import cm.filetransfer.entity.FileEntity;
import cm.filetransfer.exception.ConnectionException;
import cm.filetransfer.exception.FileReadException;
import cm.filetransfer.util.FileSender;
import cm.filetransfer.util.IOOperator;
import cm.filetransfer.util.FileSender.OnProcessChangedListener;

import org.json.JSONException;
import org.json.JSONObject;





public class UploadThread extends Thread{
	private static final String TAG = "UploadThread";
	/*上传socket链接*/
	private Socket socket;
	/*存储上传的数据库*/
	private boolean isFirst=true;
	/*上传文件的数量*/
	private int fileNum = 0;
	private FileSender fileSender=new FileSender();
	private List<FileEntity> fileList = new ArrayList<FileEntity>();
	private int currentFileIndex=-1;
	private String appKey;
	private OnFileStatusChangedListener stateListener;
	
	public UploadThread(Context context, String appKey){
		this.appKey = appKey;
	}
	
	public void connect() throws IOException {
		socket = new Socket(ConnectionParams.IP, ConnectionParams.PORT);
	}
	
	public void disConnect(){
		try {
			if(socket != null) socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public boolean addUploadFile(String fileKey, File file) {
		if(currentFileIndex!=-1){
			return false;
		}
		String valiCode = ValidCodeDefault.create(file);
		FileEntity entity = FileEntity.createFileEntity(fileKey,
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
		try {
			requestOb.put(ProtocolKey.APPLICATION_KEY, appKey);
			requestOb.put(ProtocolKey.FILE_SIZE, fileList.size());
			requestOb.put(ProtocolKey.FILE_TYPE, fileEntity.getFileType());
			requestOb.put(ProtocolKey.FILE_KEY, fileEntity.getFileKey());
			requestOb.put(ProtocolKey.FILE_NAME, fileEntity.getFileName());
			requestOb.put(ProtocolKey.FILE_LENGTH, fileEntity.getFileLen());
			requestOb.put(ProtocolKey.FILE_VALI_CODE, fileEntity.getValiCode());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			requestOb.put(ProtocolKey.FILE_TYPE, fileEntity.getFileType());
			requestOb.put(ProtocolKey.FILE_KEY, fileEntity.getFileKey());
			requestOb.put(ProtocolKey.FILE_NAME, fileEntity.getFileName());
			requestOb.put(ProtocolKey.FILE_LENGTH, fileEntity.getFileLen());
			requestOb.put(ProtocolKey.FILE_VALI_CODE, fileEntity.getValiCode());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestOb.toString();
	}
	
	/**
	 * 请求文件传输
	 * 
	 * @return
	 * @throws ConnectionException
	 * @throws JSONException 
	 */
	public boolean request() throws ConnectionException, JSONException {
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
		JSONObject responseOb = new JSONObject(response);
		String requestResult = responseOb.getString(ProtocolKey.REQUEST_RESULT);
		if (requestResult.equals(RequestResult.START)) {
			Long skipPosition = responseOb.getLong(ProtocolKey.SKIP_POSITION);
			fileList.get(currentFileIndex).setSkipPosition(skipPosition);
			fileList.get(currentFileIndex).setPosition(skipPosition);
		} else if (requestResult.equals(RequestResult.ALREADY_UPLOAD)) {
			fileList.get(currentFileIndex).setUploadStatus(UploadResult.ALREADY_UPLOAD);
			stateListener.onStatsChanged(UploadResult.ALREADY_UPLOAD, fileList.get(currentFileIndex).getFileName());
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
	
	private boolean finish()throws ConnectionException, JSONException{
		String result;
		try {
			result=IOOperator.readMsg(socket.getInputStream());
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
		JSONObject resultOb=new JSONObject(result);
		String uploadResult=resultOb.getString(ProtocolKey.UPLOAD_RESULT);
		if(uploadResult.equals(UploadResult.SUCCESS)){
			fileList.get(currentFileIndex).setUploadStatus(UploadResult.SUCCESS);
			stateListener.onStatsChanged(UploadResult.SUCCESS, fileList.get(currentFileIndex).getFileName());
		}else {
			fileList.get(currentFileIndex).setUploadStatus(UploadResult.FAILED);
			stateListener.onStatsChanged(UploadResult.FAILED, fileList.get(currentFileIndex).getFileName());
		}
		return true;
	}
	
	public boolean upload() throws JSONException {

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
			fileList.get(i).setUploadStatus(FileStatus.UPLOAD_FAILED);
			stateListener.onStatsChanged(FileStatus.UPLOAD_FAILED, "all");
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			connect();
			boolean b = upload();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * get the head of socket protocol
	 * @param appKey
	 * @param fileNum
	 * @return
	 */
	private String getHeadMsg(FileEntity fileEntity){
		JSONObject head = new JSONObject();
		try {
			if(isFirst){
				head.put(ProtocolKey.APPLICATION_KEY, appKey);
				head.put(ProtocolKey.FILE_SIZE, fileNum);
				isFirst=false;
			}
			head.put(ProtocolKey.FILE_KEY, fileEntity.getFileKey());
			head.put(ProtocolKey.FILE_TYPE, fileEntity.getFileType());
			head.put(ProtocolKey.FILE_NAME, fileEntity.getFileName());
			head.put(ProtocolKey.FILE_LENGTH, fileEntity.getFileLen());
			head.put(ProtocolKey.FILE_VALI_CODE, fileEntity.getValiCode());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return head.toString();
	}

	
	/**
	 * listening the change of upload file 
	 */
	public void setListener(OnProcessChangedListener listener) {
		fileSender.setListener(listener);
	}
	
	public interface OnFileStatusChangedListener{
		void onStatsChanged(String state, String fileName);
	}
	
	/**
	 * the listener to listening the state of file upload
	 * @param stateListener
	 */
	public void setStateListener(OnFileStatusChangedListener stateListener) {
		this.stateListener = stateListener;
	}

	/**
	 * this method you can get the file upload state, name ,length and so on.
	 * @return
	 */
	public List<FileEntity> getFileList() {
		return fileList;
	}
}
