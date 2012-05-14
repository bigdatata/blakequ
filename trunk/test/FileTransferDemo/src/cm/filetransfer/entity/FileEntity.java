package cm.filetransfer.entity;

import java.io.File;

import cm.filetransfer.ValidCodeDefault;
import cm.filetransfer.constant.FileTransferStatus;
import cm.filetransfer.util.FileUtil;

public class FileEntity {
	/**
	 * the upload file
	 */
	private File file;
	/**
	 * the name of file
	 */
	private String fileName;
	/**
	 * the type of file
	 */
	private String fileType;
	/**
	 * the length of file
	 */
	private Long fileLen;
	/**
	 * 文件标识
	 */
	private String fileKey;
	private String valiCode;
	/**
	 * 文件上传的位置
	 */
	private Long position = 0L;
	/**
	 * 断点续传情况下开始的位置
	 */
	private Long skipPosition = 0L;

	/**
	 * 文件上传状态
	 */
	private String uploadStatus;

	/**
	 * 上传类型
	 */
	private String uploadType;

	private FileEntity() {
		super();
	}

	/**
	 * 
	 * @param fileKey the key from server back
	 * @param file the upload file 
	 * @param valiCode the valid code
	 * @return
	 */
	public static FileEntity createFileEntity(String fileKey, File file, String valiCode) {
		FileEntity entity = new FileEntity();
		entity.file = file;
		entity.fileKey=fileKey;
		entity.fileName = file.getName();
		entity.fileLen = file.length();
		entity.fileType = FileUtil.checkType(file);
		entity.valiCode = valiCode;
		entity.uploadStatus=FileTransferStatus.PREAPRE;
		return entity;
	}
	
	/**
	 * 
	 * @param fileKey the key from server back
	 * @param file the upload file 
	 * @return
	 */
	public static FileEntity createFileEntity(String fileKey, File file){
		String valiCode = new ValidCodeDefault().create(file);
		return createFileEntity(fileKey, file, valiCode);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getValiCode() {
		return valiCode;
	}

	public void setValiCode(String valiCode) {
		this.valiCode = valiCode;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public Long getSkipPosition() {
		return skipPosition;
	}

	public void setSkipPosition(Long skipPosition) {
		this.skipPosition = skipPosition;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public Long getFileLen() {
		return fileLen;
	}

	public void setFileLen(Long fileLen) {
		this.fileLen = fileLen;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

}
