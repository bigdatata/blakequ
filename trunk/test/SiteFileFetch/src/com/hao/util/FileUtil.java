package com.hao.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.hao.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class FileUtil {
	/**
	 * Gets the Android external storage directory
	 */
	public static File getSDCardPath(){
		return Environment.getExternalStorageDirectory();
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
	public static boolean isFileExists(File directory, String name){
		File file = new File(directory, name);
		if(file.exists()) 
			return true;
		return false;
	}
	
	
	/**
	 * 返回指定目录的文件
	 * @param directory 当前目录
	 * @return
	 */
	public static File[] getCurrentFileList(File directory){
		return directory.listFiles();
	}
	
	/**
	 * 返回文件夹列表
	 * @return
	 */
	public static List<File> getFolderFileList(File currentDir){
		List<File> folders = new ArrayList<File>();
		for(File file: currentDir.listFiles()){
			if(file.isDirectory() && file.canRead()){
				folders.add(file);
			}
		}
		return folders;
	}
	
	/**
	 * 返回普通文件列表
	 * @return
	 */
	public static List<File> getCommonFileList(File currentDir){
		List<File> commFile = new ArrayList<File>();
		for(File file: currentDir.listFiles()){
			if(!file.isDirectory() && file.canRead()){
				commFile.add(file);
			}
		}
		return commFile;
	}
	
	/**
	 * 判断文件的类型
	 * @param fileName 文件
	 * @param postfixName 文件后缀集合
	 * @return 如果是postfixName后缀文件，则返回true
	 */
	public static boolean checkPostfixOfFile(File file, String... postfixName){
		String fName=file.getName();
		int dotIndex = fName.lastIndexOf(".");//获取后缀名前的分隔符"."在fName中的位置。
		if(dotIndex < 0){
	        return false;
	    }
	    String end = fName.substring(dotIndex+1,fName.length()).toLowerCase();//获取文件的后缀名 
		for(String postfix : postfixName){
			if(end.equals(postfix)) return true;
		}
		return false;
	}
	
	/**
	 * 根据文件后缀名设置相应image。
	 * @param file
	 */
	public static Drawable getFileImageBackground(Context context, File file)
	{
		Resources res = context.getResources();
		Drawable img;
	    if(file.isDirectory()){
	    	img = res.getDrawable(R.drawable.folder);
	    }else{
	    	if(checkPostfixOfFile(file, res.getStringArray(R.array.text))){
	    		img = res.getDrawable(R.drawable.document_icon);
	    	}else if(checkPostfixOfFile(file, res.getStringArray(R.array.image))){
	    		img = res.getDrawable(R.drawable.photo_icon);
	    	}else if(checkPostfixOfFile(file, res.getStringArray(R.array.application))){
	    		img = res.getDrawable(R.drawable.app_icon);
	    	}else if(checkPostfixOfFile(file, res.getStringArray(R.array.rar))){
	    		img = res.getDrawable(R.drawable.rar_icon);
	    	}else if(checkPostfixOfFile(file, res.getStringArray(R.array.audio))){
	    		img = res.getDrawable(R.drawable.music_icon);
	    	}else if(checkPostfixOfFile(file, res.getStringArray(R.array.video))){
	    		img = res.getDrawable(R.drawable.video_icon);
	    	}else{
	    		img = res.getDrawable(R.drawable.icon);
	    	}
	    }
	    return img;
	}
	
	/**
	 * create a file 
	 * @param directory the directory of the file exists
	 * @param fileName the name of file
	 * @return
	 */
	public static boolean createFile(File directory, String fileName){
		if(!isFileExists(directory, fileName)){
			File file = new File(directory, fileName);
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
	public static boolean createFolder(String name){
		File folder = new File(buildDirectory(name));
		if(!folder.exists()){
			return folder.mkdirs();
		}
		return false;
	}
	

	
	
	/**
	 * generate the path of the application by the dirname
	 * @param DirName the hierarchy of the file directory
	 * @return the full path of the application
	 */
	private static String buildDirectory(String... DirName){
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory());
		builder.append(File.separatorChar);
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
