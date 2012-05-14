package cm.browser;

import java.io.File;
import java.util.ArrayList;

import cm.constant.FileExtension;
import cm.util.FileUtil;

public class ScanFile{
	private OnFileScanOverListener mOnFileScanOverListener;
	//用于存分类文件
	private ArrayList<String> documentFile = new ArrayList<String>();
	private ArrayList<String> imageFile = new ArrayList<String>();
	private ArrayList<String> rarFile = new ArrayList<String>();
	private ArrayList<String> videoFile = new ArrayList<String>();
	private ArrayList<String> audioFile = new ArrayList<String>();
	private ArrayList<String> otherFile = new ArrayList<String>();
	private ArrayList<String> appFile = new ArrayList<String>();
	private static ScanFile instance = null;
    
	private ScanFile(){
	}
	
	public synchronized static ScanFile getInstance(){
		if(instance == null){
			instance = new ScanFile();
		}
		return instance;
	}
	
	/**
	 * start scan the file System
	 */
	public void start(){
		documentFile.clear();
		imageFile.clear();
		rarFile.clear();
		videoFile.clear();
		audioFile.clear();
		otherFile.clear();
		appFile.clear();
		if(instance != null){
			new Thread(task).start();
		}
	}
	
	Runnable task = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			getFiles("/sdcard/", true);
			mOnFileScanOverListener.onScanOverListener();
		}
	};
	
	
	public void setmOnFileScanOverListener(
			OnFileScanOverListener mOnFileScanOverListener) {
		this.mOnFileScanOverListener = mOnFileScanOverListener;
	}

	public double getTotalSize(ArrayList<String> files){
		File file;
		double size = 0;
		for(String str:files){
			file = new File(str);
			size += file.length();
		}
		return size;
	}

	/**
	 * 用于文件遍历
	 * @param Path 起始路径
	 * @param Extension 扩展名
	 * @param IsIterative 是否进入子文件夹
	 */
	private void getFiles(String Path, boolean IsIterative)
	{
		File[] files = new File(Path).listFiles();
		if(files != null){
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile() && file.canRead()) {
					if(FileUtil.checkPostfixOfFile(file, FileExtension.IMAGE)){
			    		imageFile.add(file.toString());
			    	}else if(FileUtil.checkPostfixOfFile(file, FileExtension.AUDIO)){
			    		audioFile.add(file.toString());
			    	}else if(FileUtil.checkPostfixOfFile(file, FileExtension.VIDEO)){
			    		videoFile.add(file.toString());
			    	}else if(FileUtil.checkPostfixOfFile(file, FileExtension.TEXT)){
						documentFile.add(file.toString());
			    	}else if(FileUtil.checkPostfixOfFile(file, FileExtension.APP)){
			    		appFile.add(file.toString());
			    	}else if(FileUtil.checkPostfixOfFile(file, FileExtension.RAR)){
			    		rarFile.add(file.toString());
			    	}else{
			    		otherFile.add(file.toString());
			    	}
					if (!IsIterative)
						break;
				} else if (file.isDirectory() && file.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
					getFiles(file.getPath(), IsIterative);
			}
		}
	}
	/**
	 * 返回文档文件
	 * @return
	 */
	public ArrayList<String> getDocumentFile() {
		return documentFile;
	}
	/**
	 * 返回图片文件
	 * @return
	 */
	public ArrayList<String> getImageFile() {
		return imageFile;
	}
	/**
	 * 返回压缩文件
	 * @return
	 */
	public ArrayList<String> getRarFile() {
		return rarFile;
	}
	/**
	 * 返回视频文件
	 * @return
	 */
	public ArrayList<String> getVideoFile() {
		return videoFile;
	}
	/**
	 * 返回音频文件
	 * @return
	 */
	public ArrayList<String> getAudioFile() {
		return audioFile;
	}
	/**
	 * 返回其他文件
	 * @return
	 */
	public ArrayList<String> getOtherFile() {
		return otherFile;
	}
	/**
	 * 返回应用文件
	 * @return
	 */
	public ArrayList<String> getAppFile() {
		return appFile;
	}
	

	public void setDocumentFile(ArrayList<String> documentFile) {
		this.documentFile = documentFile;
	}


	public void setImageFile(ArrayList<String> imageFile) {
		this.imageFile = imageFile;
	}


	public void setRarFile(ArrayList<String> rarFile) {
		this.rarFile = rarFile;
	}


	public void setVideoFile(ArrayList<String> videoFile) {
		this.videoFile = videoFile;
	}


	public void setAudioFile(ArrayList<String> audioFile) {
		this.audioFile = audioFile;
	}


	public void setOtherFile(ArrayList<String> otherFile) {
		this.otherFile = otherFile;
	}


	public void setAppFile(ArrayList<String> appFile) {
		this.appFile = appFile;
	}
	
}
