package cm.util;

import java.io.File;
import java.text.DecimalFormat;

import cm.browser.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.Log;

public class BaseActivity extends Activity {
	/**
	 * 下面是进入进程详细信息
	 */
	private static final String SCHEME = "package";  
	/** 
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本) 
	 */  
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";  
	/** 
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2) 
	 */  
	private static final String APP_PKG_NAME_22 = "pkg";  
	/** 
	 * InstalledAppDetails所在包名 
	 */  
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";  
	/** 
	 * InstalledAppDetails类名 
	 */  
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		BaseService.activityList.add(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseService.activityList.remove(this);
	}
	
	/**
	 * prompt user whether exit the app
	 * Note:using final and subclass will not change
	 */
	public final void promptExitApp() {
		new AlertDialog.Builder(this).setTitle(R.string.app_name).setIcon(
				android.R.drawable.ic_dialog_info).setMessage(
				R.string.promt_exit_app).setPositiveButton(R.string.confirm,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						exitApp(BaseActivity.this);
					}
				}).setNegativeButton(R.string.cancel, null).show();
	}
	
	/**
	 * exit application after finish all activity and other associate resource
	 * @param context
	 */
	private final void exitApp(Context context){
		Log.i("BaseActivity", "exit application");
		for(int i=0; i<BaseService.activityList.size(); i++){
			BaseService.activityList.get(i).finish();
		}
		BaseService.activityList.clear();
		
		ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		activityManager.restartPackage("cm.browser");
		System.exit(0);
	}
	
	/**
	 * check the state of sdcard
	 * @return
	 */
	public boolean checkSDCardState(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/** 
	 * 调用系统InstalledAppDetails界面显示已安装应用程序的详细信息。 对于Android 2.3（Api Level 
	 * 9）以上，使用SDK提供的接口； 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）。 
	 * @param context 
	 * @param packageName 
	 * 应用程序的包名 
	 */ 
	public static void showInstalledAppDetails(Context context, String packageName) {  
	    Intent intent = new Intent();  
	    final int apiLevel = Build.VERSION.SDK_INT;  
	    if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口  
	        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);  
	        Uri uri = Uri.fromParts(SCHEME, packageName, null);  
	        intent.setData(uri);  
	    } else { 
	    	// 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）  
	        // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。  
	        final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22  
	                : APP_PKG_NAME_21);  
	        intent.setAction(Intent.ACTION_VIEW);  
	        intent.setClassName(APP_DETAILS_PACKAGE_NAME,  
	                APP_DETAILS_CLASS_NAME);  
	        intent.putExtra(appPkgName, packageName);  
	    }  
	    context.startActivity(intent);  
	}
	
	/**
     * 将bit转换为KB,MB
     * @param size
     * @return
     */
    public String getDefaultSize(double size){
    	String file_size;
		if(size < 1024){
			file_size = size+"B";
		}else if(size >=1024 && size < 1048576){
			file_size = new DecimalFormat("####.##").format(size/1024)+"KB";
		}else{
			file_size = new DecimalFormat("###,###,###.##").format(size/1048576)+"M";
		}
		return file_size;
    }
    
    /**
     * get the size of sdcard<br>
     * <b>Notice:</b> the base size, in bytes
     * @return i=0, the total size of sdcard
     * i=1, the available size of sdcard
     */
    public double[] getSizeOfSdcard(){
    	double[] size = {0,0};
    	if(checkSDCardState()){
    		//取得sdcard文件路径
    		File path = Environment.getExternalStorageDirectory(); 
    		StatFs statfs = new StatFs(path.getPath());
    		//取block的SIZE
    		long blocSize = statfs.getBlockSize();
    		//获取BLOCK数量
    		long totalBlocks = statfs.getBlockCount();
    		//空闲的Block的数量
    		long availaBlock = statfs.getAvailableBlocks();
    		//计算总空间大小和空闲的空间大小
    		size[0] = totalBlocks * blocSize; 
    		size[1] = availaBlock * blocSize;
    	}
    	return size;
    }
}
