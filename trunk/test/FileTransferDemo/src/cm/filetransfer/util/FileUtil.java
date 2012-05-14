package cm.filetransfer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import cm.filetransfer.constant.FileType;
import cm.filetransfer.entity.FileEntity;
import cm.filetranslate.R;

public class FileUtil {
	/**
	 * 判断文件的类型
	 * @param fileName 文件
	 * @param postfixName 文件后缀集合
	 * @return 如果是postfixName后缀文件，则返回true
	 */
	public static String checkType(File file){
		String fName=file.getName();
		int dotIndex = fName.lastIndexOf(".");//获取后缀名前的分隔符"."在fName中的位置。
		if(dotIndex < 0){
	        return "";
	    }
	    String end = fName.substring(dotIndex+1,fName.length()).toLowerCase();//获取文件的后缀名 
		if(checkPostfixOfFile(end, FileType.VIDEOS)){
			return FileType.VIDEO;
		}else if(checkPostfixOfFile(end, FileType.IMAGES)){
			return FileType.IMAGE;
		}else if(checkPostfixOfFile(end, FileType.AUDIOS)){
			return FileType.AUDIO;
		}else{
			return FileType.OTHER;
		}
	}
	
	/**
	 * 检查后缀名postfix是否在postfixName集合中
	 * @param postfix 后缀名
	 * @param postfixName 后缀名集合
	 * @return 
	 */
	public static boolean checkPostfixOfFile(String postfix, String... postfixName){
		for(String postfixs : postfixName){
			if(postfixs.equals(postfix)) return true;
		}
		return false;
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
}
