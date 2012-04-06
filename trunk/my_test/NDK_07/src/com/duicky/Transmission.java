package com.duicky;

/**
 * 
 * 
 * @author luxiaofeng <454162034@qq.com>
 *
 */
public class Transmission {

	private static final String libSoName = "NDK_07";
	
	public native String transferString(String mStrMSG);
	
	public native Object  transferPerson(Person mPerson);

	
	/**
     * 载入JNI生成的so库文件
     */
    static {
        System.loadLibrary(libSoName);
    }
}
