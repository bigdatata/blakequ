package cm.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import cm.browser.R;
import cm.browser.R.array;
import cm.browser.R.drawable;
import cm.constant.AudioInfo;
import cm.constant.FileExtension;
import cm.constant.ImageInfo;
import cm.constant.VideoInfo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class FileUtil {
	private static final String TAG = "FileUtil";
	/**
	 * the size of file or directory<p>
	 * <b>Note: </b>before use this you should invoke 
	 * <ul>
	 *   <li> {@link #getFileNumber(File file)} get the number of files in directory</li>
	 *   <li> {@link #getFileSize(File file)} get the number of files in directory</li>
	 * </ul>
	 * if not the size = 0
	 */
	public static double SIZE = 0;
	/**
	 * the number of file or directory<p>
	 * <b>Note: </b>before use this you should invoke 
	 * <ul>
	 *   <li> {@link #getFileNumber(File file)} get the number of files in directory</li>
	 *   <li> {@link #getFileSize(File file)} get the number of files in directory</li>
	 * </ul>
	 * if not the number = 0
	 */
	public static int NUMBER = 0 ;
	
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
	 * get the number of files in directory
	 * @param file
	 * @return if is a file return 1, not exits return 0
	 */
	public static double getFileNumber(File file){
		if(!file.exists()){
			return 0;
		}
		if(file.isFile()){
			return 1;
		}
		SIZE = 0;
		NUMBER = 0;
		getSizeAndNumber(file, true);
		return NUMBER;
	}
	
	/**
	 * return the size of file or directory
	 * @param file
	 * @return if file not exist return 0;
	 */
	public static double getFileSize(File file){
		if(!file.exists()){
			return 0;
		}
		SIZE = 0;
		NUMBER = 0;
		getSizeAndNumber(file, true);
		return SIZE;
	}
	
	/**
	 * get the size and number of directory or file
	 * @param file
	 * @param IsIterative
	 */
	private static void getSizeAndNumber(File file, boolean IsIterative){
		File[] files = file.listFiles();
		if(files != null){
			for(File f:files){
				if (f.isFile()) {// && f.canRead()
					SIZE += f.length();
					NUMBER ++;
					if (!IsIterative)
						break;
				}else{ // 忽略点文件（隐藏文件/文件夹）
					getSizeAndNumber(f, IsIterative);
				}
			}
		}
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
	 * 返回视频文件列表
	 * @param currentDir
	 * @return
	 */
	public static List<VideoInfo> getVideoFileLists(Context context){
		List<VideoInfo> videos = new ArrayList<VideoInfo>();
		VideoInfo video;
		System.out.println("**********************************");
		//下面是设置过滤，只是返回视频文件的列表
		ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{
        		MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED,
        		MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DATE_MODIFIED, MediaStore.Video.Media.DESCRIPTION,
        		MediaStore.Video.Media.MIME_TYPE,MediaStore.Video.Media.DURATION, MediaStore.Video.Media.ALBUM,
        		MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, 
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();
        System.out.println("********filenem:"+fileNum +" columNum:"+cursor.getColumnCount());
        for(int counter = 0; counter < fileNum; counter++){
        	video = new VideoInfo();
        	try{
	            video.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
	            video.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
	            video.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
	            video.setDescription(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DESCRIPTION)));
	            long time = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)));
	            String dateModified = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time*1000));
	            video.setDate_modified(dateModified);
	            long time_create = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)));
	            String dateAdded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time_create*1000));
	            video.setDate_added(dateAdded);
	            video.setMime_type(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE)));
	            video.setDuration(Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))));
	            video.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ALBUM)));
	            video.setDisplay_name(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
        	}catch(Exception e){
        		Log.e(TAG, "error", new Throwable("the error to get information from media"));
        	}
        		System.out.println(video);
	        	videos.add(video);
	            cursor.moveToNext();
        }
        cursor.close();
		return videos;
	}
	
	
	/**
	 * 返回图片文件列表
	 * @param currentDir
	 * @return
	 */
	public static List<ImageInfo> getImageFileList(Context context){
		List<ImageInfo> images = new ArrayList<ImageInfo>();
		ImageInfo image;
		System.out.println("**********************************");
		ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{
        		MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED,
        		MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DATE_MODIFIED, MediaStore.Images.Media.DESCRIPTION,
        		MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, 
                null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();
        System.out.println("********filenem:"+fileNum +" columNum:"+cursor.getColumnCount());
        for(int counter = 0; counter < fileNum; counter++){
        	image = new ImageInfo();
        	try{
	            image.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE)));
	            image.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
	            image.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)));
	            image.setDescription(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION)));
	            long time = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
	            String dateModified = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time*1000));
	            image.setDate_modified(dateModified);
	            long time_create = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)));
	            String dateAdded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time_create*1000));
	            image.setDate_added(dateAdded);
	            image.setMime_type(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)));
	            image.setDisplay_name(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
        	}catch(Exception e){
        		Log.e(TAG, "error", new Throwable("the error to get information from media"));
        	}
        		System.out.println(image);
	        	images.add(image);
	            cursor.moveToNext();
        }
        cursor.close();
		return images;
	}
	
	/**
	 * 返回音频文件列表
	 * @param currentDir
	 * @return
	 */
	public static List<AudioInfo> getAudioFileList(Context context){
		List<AudioInfo> audios = new ArrayList<AudioInfo>();
		AudioInfo audio;
		System.out.println("**********************************");
		ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{
        		MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DATE_ADDED,
        		MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATE_MODIFIED, MediaStore.Audio.Media.YEAR,
        		MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.MIME_TYPE,MediaStore.Audio.Media.DURATION, 
        		MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.DISPLAY_NAME};
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, 
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();
        System.out.println("********filenem:"+fileNum +" columNum:"+cursor.getColumnCount());
        for(int counter = 0; counter < fileNum; counter++){
        	audio = new AudioInfo();
        	try{
	            audio.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
	            audio.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
	            audio.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
	            audio.setYear(Integer.valueOf(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR)));
	            audio.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
	            long time = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)));
	            String dateModified = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time*1000));
	            audio.setDate_modified(dateModified);
	            long time_create = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)));
	            String dateAdded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time_create*1000));
	            audio.setDate_added(dateAdded);
	            audio.setMime_type(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)));
	            audio.setDuration(Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))));
	            audio.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
	            audio.setDisplay_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
        	}catch(Exception e){
        		Log.e(TAG, "error", new Throwable("the error to get information from media"));
        	}
        		System.out.println(audio);
	        	audios.add(audio);
	            cursor.moveToNext();
        }
        cursor.close();
		return audios;
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
	    	}else if(checkPostfixOfFile(file, FileExtension.IMAGE)){
	    		img = res.getDrawable(R.drawable.photo_icon);
	    	}else if(checkPostfixOfFile(file, FileExtension.APP)){
	    		img = res.getDrawable(R.drawable.app_icon);
	    	}else if(checkPostfixOfFile(file, FileExtension.RAR)){
	    		img = res.getDrawable(R.drawable.rar_icon);
	    	}else if(checkPostfixOfFile(file, FileExtension.AUDIO)){
	    		img = res.getDrawable(R.drawable.music_icon);
	    	}else if(checkPostfixOfFile(file, FileExtension.VIDEO)){
	    		img = res.getDrawable(R.drawable.video_icon);
	    	}else if(checkPostfixOfFile(file, "apk")){
	    		img = res.getDrawable(R.drawable.apk);
	    	}else{
	    		img = res.getDrawable(R.drawable.file);
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
	 * 将文件复制到目的文件
	 * @param sourceFile
	 * @param goalFile
	 * @return
	 */
	public static boolean copyToFile(String sourceFile, String goalFile){
		boolean type = true;
		if(sourceFile.equals("") || sourceFile == null ||goalFile.equals("") || goalFile == null){
			Log.e(TAG, "source or direct file is null");
			return false;
		}
		File source = new File(sourceFile);
		if(!source.exists()){
			Log.e(TAG, "source file not exist");
			return false;
		}
		File goal = new File(goalFile);
		System.out.println("end:"+goal.getPath());
		try {
		    if(!goal.createNewFile()) {
		        Log.e(TAG,"File already exists");
		    	return false;
		    } 
		} catch (IOException ex) {
			Log.e(TAG, "create new file IOException");
			ex.printStackTrace();
		}
		try {
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(goal);
			save(fos, read(fis));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "stream IOException");
			e.printStackTrace();
			type = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "stream Exception");
			e.printStackTrace();
			type = false;
		}
		return type;
	}
	
	/*
     * 保存文件
     */
    public static void save(OutputStream outStream, byte[] data) throws Exception {
        // 写入数据
        outStream.write(data);
        outStream.close();
    }

    /*
     * 读取文件
     */
    public static byte[] read(InputStream inStream) throws Exception {
        // 字节流
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        //获取字节数据
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        //得到字节数据
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
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
