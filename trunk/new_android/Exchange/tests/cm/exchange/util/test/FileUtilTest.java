package cm.exchange.util.test;

import java.io.File;

import cm.exchange.util.FileUtil;
import android.os.Environment;
import android.test.AndroidTestCase;

public class FileUtilTest  extends AndroidTestCase{

	public void testGetSDCardPath(){
		System.out.println(FileUtil.getSDCardPath());
	}
	
	public void testCreateExternalStoragePublicPicture(){
		assertEquals(false, FileUtil.createExternalStoragePublicPicture());
	}
	
	public void testIsSDCardAvailable(){
		assertEquals(true, FileUtil.isSDCardAvailable());
	}
	
	public void testIsDirectoryAvailable(){
		File file = new File(Environment.getExternalStorageDirectory(), "test");
		assertEquals(true, FileUtil.isDirectoryAvailable(file));
	}
	
	public void testIsFileExists(){
		File file = new File(Environment.getExternalStorageDirectory(), "test");
		assertEquals(true, FileUtil.isFileExists(file, "test.txt"));
	}
	
	public void testCreateFile(){
		File file = new File(Environment.getExternalStorageDirectory(), "test");
		assertEquals(true, FileUtil.createFile(file, "test.txt"));
	}
	
	//later
	public void testCreateAPPFolder(){
		assertEquals(true, FileUtil.createAPPFolder("pic"));
	}
	
//	public void testBuildAppDirectory(){
//		System.out.println(FileUtil.buildAppDirectory("pic", "My_ni.jis"));
//	}
}
