package com.hao;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import android.os.Environment;

/**
 * 
 * @author qh
 *
 */
public class FileUtil {
	
	/**
	 * Gets the Android external storage directory
	 */
	public static File getSDCardPath(){
		return Environment.getExternalStorageDirectory();
	}
	
	/**
	 * create public picture file if not exists
	 * @return if the file exists return false, otherwise, return false
	 */
	public static boolean createExternalStoragePublicPicture(){
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return path.mkdirs();
	}
	
	/**
	 * check the state of sdCard
	 * @return if is available return true
	 */
	public static boolean isSDCardAvailable(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * check the directory is available , if had existed return true, otherwise created 
	 * @param path the path of file
	 * @return
	 */
	public static boolean isDirectoryAvailable(File path){
		if(path.exists() && path.isDirectory())
			return true;
		if(path.mkdirs())
			return true;
		return false;
	}
	
	/**
	 * check the directory is available , if had existed return true, otherwise created 
	 * @param path the path of file
	 * @return
	 */
	public static boolean isDirectoryAvailable(String path){
		File file = new File(path);
		return isDirectoryAvailable(file);
	}
	
	/**
	 * check the file is exists or not
	 * @param dirctory the file directory
	 * @param name the file name
	 * @return if exists return true
	 */
	public static boolean isImageFileExists(File directory, String name){
		File file = new File(directory, name);
		if(file.exists()) 
			return true;
		return false;
	}
	
	/**
	 * check the picture file is exists or not
	 * @param directory
	 * @param name
	 * @param picType the type of picture(only png,jpg)
	 * @return
	 */
	public static boolean isImageFileExists(File directory, String name, String picType){
		if(picType.equals("png")||picType.equals("jpg")){
			File file = new File(directory, name+"."+picType);
			if(file.exists()) 
				return true;
		}
		return false;
	}
	
	/**
	 * create image file
	 * @param directory
	 * @param name
	 * @param picType the type of picture(only png,jpg)
	 * @return
	 */
	public static boolean createImageFileExists(File directory, String name, String picType){
		if(!isImageFileExists(directory, name, picType)){
			File file = new File(directory, name+"."+picType);
			try {
				return file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * create a file 
	 * @param directory the directory of the file exists
	 * @param name the name of file
	 * @return
	 */
	public static boolean createFile(File directory, String name){
		if(!isImageFileExists(directory, name)){
			File file = new File(directory, name);
			try {
				return file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * create a folder 
	 * @param name the name of folder
	 * @return if success return true
	 */
	public static boolean createAPPFolder(String name){
		File folder = new File(buildAppDirectory(name));
		if(!folder.exists()){
			return folder.mkdirs();
		}
		return false;
	}
	
	public static String getAppPicFolder(){
		return buildAppDirectory("pic");
	}
	
	
	/**
	 * generate the path of the application by the dirname
	 * @param DirName the hierarchy of the file directory
	 * @return the full path of the application
	 */
	private static String buildAppDirectory(String... DirName){
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory());
		builder.append(File.separatorChar);
		builder.append("exchange");
		for(String s : DirName){
			builder.append(File.separatorChar);
			builder.append(sanitizeName(s));
		}
		return builder.toString();
	}
	
	/**
     * A set of characters that are prohibited from being in file names.
     */
    private static final Pattern PROHIBITED_CHAR_PATTERN =
        Pattern.compile("[^ A-Za-z0-9_.()]+");
    
    /**
     * The maximum length of a filename, as per the FAT32 specification.
     */
    private static final int MAX_FILENAME_LENGTH = 50;
    
    /**
     * Normalizes the input string and make sure it is a valid fat32 file name.
     *
     * @param name the name to normalize
     * @param overheadSize the number of additional characters that will be added
     *        to the name after sanitization
     * @return the sanitized name
     */
    static String sanitizeName(String name) {
      String cleaned = PROHIBITED_CHAR_PATTERN.matcher(name).replaceAll("");

      return (cleaned.length() > MAX_FILENAME_LENGTH)
          ? cleaned.substring(0, MAX_FILENAME_LENGTH)
          : cleaned.toString();
    }
}
