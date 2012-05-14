package cm.constant;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo implements Serializable{
	private int id; //应用程序id
	private String appLabel;    //应用程序标签  
    private Drawable appIcon ;  //应用程序图像  
    private Intent intent ;     //启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity  
    private String pkgName ;    //应用程序所对应的包名
    //应用程序的总大小 = cachesize  + codesize  + datasize
    private long cachesize; //占用缓存大小
    private long codesize;  //应用程序大小 
    private long datasize;  //数据大小
    private String processName;
    private String dataDir; //数据目录
    private int targetSdkVersion; //版本
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
