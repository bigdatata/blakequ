package constant;

public interface RequestResult {
	/**
	 * 表明需要进行文件上传
	 */
	String START="start";
	
	/**
	 * 该文件已经在之前的传输过程中传输完成
	 */
	String ALREADY_UPLOAD="alreadyUpload";
}
