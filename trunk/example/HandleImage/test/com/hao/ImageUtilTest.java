package com.hao;

import android.test.AndroidTestCase;

public class ImageUtilTest extends AndroidTestCase{

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testCreatePngFilePath(){
		System.out.println(ImageUtil.createPngFilePath("1"));
		System.out.println(ImageUtil.createPngFilePath(FileUtil.getSDCardPath().toString(), "1"));
		System.out.println(ImageUtil.createJpgFilePath("1"));
		System.out.println(ImageUtil.createJpgFilePath(FileUtil.getSDCardPath().toString(), "1"));
	}

}
