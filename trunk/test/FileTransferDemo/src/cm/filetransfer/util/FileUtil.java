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
	 * �ж��ļ�������
	 * @param fileName �ļ�
	 * @param postfixName �ļ���׺����
	 * @return �����postfixName��׺�ļ����򷵻�true
	 */
	public static String checkType(File file){
		String fName=file.getName();
		int dotIndex = fName.lastIndexOf(".");//��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		if(dotIndex < 0){
	        return "";
	    }
	    String end = fName.substring(dotIndex+1,fName.length()).toLowerCase();//��ȡ�ļ��ĺ�׺�� 
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
	 * ����׺��postfix�Ƿ���postfixName������
	 * @param postfix ��׺��
	 * @param postfixName ��׺������
	 * @return 
	 */
	public static boolean checkPostfixOfFile(String postfix, String... postfixName){
		for(String postfixs : postfixName){
			if(postfixs.equals(postfix)) return true;
		}
		return false;
	}
	
	/**
	 * �����ļ����б�
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
	 * ������ͨ�ļ��б�
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
	 * �ж��ļ�������
	 * @param fileName �ļ�
	 * @param postfixName �ļ���׺����
	 * @return �����postfixName��׺�ļ����򷵻�true
	 */
	public static boolean checkPostfixOfFile(File file, String... postfixName){
		String fName=file.getName();
		int dotIndex = fName.lastIndexOf(".");//��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		if(dotIndex < 0){
	        return false;
	    }
	    String end = fName.substring(dotIndex+1,fName.length()).toLowerCase();//��ȡ�ļ��ĺ�׺�� 
		for(String postfix : postfixName){
			if(end.equals(postfix)) return true;
		}
		return false;
	}
	
	/**
	 * �����ļ���׺��������Ӧimage��
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
