package cm.constant;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo implements Serializable{
	private int id; //Ӧ�ó���id
	private String appLabel;    //Ӧ�ó����ǩ  
    private Drawable appIcon ;  //Ӧ�ó���ͼ��  
    private Intent intent ;     //����Ӧ�ó����Intent ��һ����ActionΪMain��CategoryΪLancher��Activity  
    private String pkgName ;    //Ӧ�ó�������Ӧ�İ���
    //Ӧ�ó�����ܴ�С = cachesize  + codesize  + datasize
    private long cachesize; //ռ�û����С
    private long codesize;  //Ӧ�ó����С 
    private long datasize;  //���ݴ�С
    private String processName;
    private String dataDir; //����Ŀ¼
    private int targetSdkVersion; //�汾
    private double memSize;
    private String cup;
    
    
    public String getProcessName() {
		return processName;
	}
	public String getDataDir() {
		return dataDir;
	}
	public int getTargetSdkVersion() {
		return targetSdkVersion;
	}
	public double getMemSize() {
		return memSize;
	}
	public String getCup() {
		return cup;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}
	public void setTargetSdkVersion(int targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}
	public void setMemSize(double memSize) {
		this.memSize = memSize;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public long getTotalSize(){
    	return cachesize + codesize + datasize;
    }
	public int getId() {
		return id;
	}
	public String getAppLabel() {
		return appLabel;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public Intent getIntent() {
		return intent;
	}
	public String getPkgName() {
		return pkgName;
	}
	public long getCachesize() {
		return cachesize;
	}
	public long getCodesize() {
		return codesize;
	}
	public long getDatasize() {
		return datasize;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public void setCachesize(long cachesize) {
		this.cachesize = cachesize;
	}
	public void setCodesize(long codesize) {
		this.codesize = codesize;
	}
	public void setDatasize(long datasize) {
		this.datasize = datasize;
	}
    
    
}
