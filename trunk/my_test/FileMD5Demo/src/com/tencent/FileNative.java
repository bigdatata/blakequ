package com.tencent;

/**
 * 
 * @author quhao
 * ���ñ��ط�����ȡSdcard���ļ�MD5ֵ
 */
public class FileNative {
	
	private static final String libSoName = "ndk-demo";
	/**
	 * 
	 * @return Sdcard�ļ���Ϣ
	 */
	public native String[] fileMD5();
	
	/**
     * ����JNI���ɵ�so���ļ�
     */
    static {
        System.loadLibrary(libSoName);
    }
}
