package cm.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cm.commons.util.unicode.UnicodeReader;

public class FileUtil {
	
	/**
	 * 从指定文件夹读取指定文件(只是文件夹中的文件)
	 * @param directory 指定目录
	 * @param fileNames 需要读取目录中文件的名字
	 * @return map(key, value), key=文件名， value=文件内容
	 */
	public static Map<String, String> readFileFromDirectory(String directory, String... fileNames){
		List<File> fileList = readFileFromPath(directory);
		if(fileList == null) return null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			if(fileList != null){
				for(File file: fileList){
					for(String name:fileNames){
						if(file.getName().equals(name)){
							String content = readFromFile(file.getPath());
							if(content != null) data.put(name, content);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 读取path目录下的所有文件(不包括文件夹)
	 * @param directory
	 * @return 如果是目录，则返回目录下的所有文件;否则返回当前文件
	 */
	private static List<File> readFileFromPath(String directory){
		List<File> list = new ArrayList<File>();
		File file = new File(directory);
		if(!file.exists() || !file.canRead()){
			return null;
		}
		if(file.isDirectory()){
			String[] fileList = file.list();
			for(String f:fileList){
				File rf = new File(directory + File.separatorChar + f);
				if(rf.isFile()){
					list.add(rf);
				}
			}
		}else{
			list.add(file);
		}
		return list;
	}
	
	/**
	 * 读取路径下的所有文件夹（不包括文件）
	 * @param 路径
	 * @return 如果路径是目录，返回目录下所有文件夹；否则为空
	 */
	public static List<File> readDirectoryFromPath(String path){
		List<File> list = new ArrayList<File>();
		File file = new File(path);
		if(!file.exists() || !file.canRead()) return null;
		if(file.isDirectory()){
			String[] fileList = file.list();
			for(String f:fileList){
				File rf = new File(path + File.separatorChar + f);
				if(rf.isDirectory()){
					list.add(rf);
				}
			}
		}else{
			list = null;
		}
		return list;
	}

	/**
	 * 读取文件内容
	 * @return
	 * @throws IOException 
	 */
	public static String readFromFile(String path) throws IOException{
		BufferedReader reader = null;
		StringBuffer sb = null;
		FileInputStream in = null;
		try { 
			File file = new File(path);
			if(!file.exists() || !file.canRead()){
				return null;
			}
			if(file.isDirectory()){
				return null;
			}
			in = new FileInputStream(file); 
			reader = new BufferedReader(new UnicodeReader(in, "UTF-8"));
            String line = new String();
            sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {  
                sb.append(line);  
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			reader.close();
			in.close();
		}
		return sb.toString().trim();
	}
}
