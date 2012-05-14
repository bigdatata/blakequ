package cm.filetransfer.constant;

public interface FileTransferStatus {
	/**
	 * 准备进行文件传输
	 */
	String PREAPRE="prepare";
	
	/**
	 * 已经开始进行文件传输
	 */
	String STARTED="started";
	
	/**
	 * 文件传输中断
	 */
	String BREAKPOINT="breakPoint";
	
	/**
	 * 文件传输成功
	 */
	String SUCCESS="success";
}
