package exception;

public class FileReadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1136645342791276346L;

	public FileReadException() {
		super();
	}

	public FileReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileReadException(String message) {
		super(message);
	}

	public FileReadException(Throwable cause) {
		super(cause);
	}

}
