package cm.constant;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class MyServiceInfo {
	private String appLabel;    //应用程序标签
	private Drawable appIcon ;  //应用程序图像
	private String serviceName  ;  //该Service的类名
	private String pkgName ;    //应用程序所对应的包名
	private Intent intent ;  //该Service组件所对应的Intent
	private int pid ;  //该应用程序所在的进程号
	private String processName ;  // 该应用程序所在的进程名
	
	//一些运行时间参数
	/**
     * The time when the service was first made active, either by someone
     * starting or binding to it.
     */
	private long activeSince; 
	/**
     * Number of clients connected to the service.
     */
	private int clientCount;
    /**
     * The time when there was last activity in the service (either
     * explicit requests to start it or clients binding to it).
     */
	private long lastActivityTime;
    /**
     * Set to true if the service has asked to run as a foreground process.
     */
	private boolean foreground;
	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public long getActiveSince() {
		return activeSince;
	}
	public void setActiveSince(long activeSince) {
		this.activeSince = activeSince;
	}
	public int getClientCount() {
		return clientCount;
	}
	public void setClientCount(int clientCount) {
		this.clientCount = clientCount;
	}
	public long getLastActivityTime() {
		return lastActivityTime;
	}
	public void setLastActivityTime(long lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}
	public boolean isForeground() {
		return foreground;
	}
	public void setForeground(boolean foreground) {
		this.foreground = foreground;
	}
    
    
}
