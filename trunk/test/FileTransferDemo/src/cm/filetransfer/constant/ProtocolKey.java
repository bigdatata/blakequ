package cm.filetransfer.constant;

public interface ProtocolKey {

	/**
	 * the number of upload files
	 */
	String FILE_SIZE = "fileSize";
	/**
	 * the key of application
	 */
	String APPLICATION_KEY = "appKey";
	/**
	 * the key of file, from server
	 */
	String FILE_KEY = "fileKey";
	/**
	 * the type of file
	 */
	String FILE_TYPE = "fileType";
	/**
	 * the name of file
	 */
	String FILE_NAME = "fileName";
	/**
	 * the length of file
	 */
	String FILE_LENGTH = "fileLen";
	/**
	 * the valid of file
	 */
	String FILE_VALI_CODE = "valiCode";
	/**
	 * the last transfer location of file 
	 */
	String SKIP_POSITION = "skipPosition";
	
	String REQUEST_RESULT = "requestResult";
	
	String UPLOAD_RESULT = "uploadResult";
}
