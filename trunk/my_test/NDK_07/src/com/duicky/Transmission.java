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
     * ����JNI���ɵ�so���ļ�
     */
    static {
        System.loadLibrary(libSoName);
    }
}
