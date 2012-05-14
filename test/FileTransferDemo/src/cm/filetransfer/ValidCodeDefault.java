package cm.filetransfer;

import java.io.File;


/**
 * for use create the valid code of file
 * @author Administrator
 *
 */
public class ValidCodeDefault{

	public static String create(File file) {
		// TODO Auto-generated method stub
		return String.valueOf(file.length());
	}

}
