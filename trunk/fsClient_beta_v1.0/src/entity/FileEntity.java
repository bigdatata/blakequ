package entity;

import java.io.File;

public class FileEntity {
	private File file;
	private String fileName;
	private String fileType;
	private Long fileLen;
	/**
	 * 文件标识<br>
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
	private String fileStatus;

	private FileEntity() {
		super();
	}

	public static FileEntity createFileEntity(String fileType, String fileKey,
			File file, String valiCode) {
		FileEntity entity = new FileEntity();
		entity.fileType = fileType;
		entity.file = file;
		entity.fileKey = fileKey;
		entity.fileName = file.getName();
		entity.fileLen = file.length();
		entity.valiCode = valiCode;
		return entity;
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

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Long getFileLen() {
		return fileLen;
	}

	public void setFileLen(Long fileLen) {
		this.fileLen = fileLen;
	}

}
