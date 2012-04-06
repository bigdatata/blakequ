package com.tencent;

/**
 * 
 * @author quhao
 * 调用本地方法获取Sdcard的文件MD5值
 */
public class FileNative {
	
	private static final String libSoName = "ndk-demo";
	/**
	 * 
	 * @return Sdcard文件信息
	 */
	public native String[] fileMD5();
	
	/**
     * 载入JNI生成的so库文件
     */
    static {
        System.loadLibrary(libSoName);
    }
}
