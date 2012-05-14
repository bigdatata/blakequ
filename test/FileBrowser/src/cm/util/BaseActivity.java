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
	 * �����ǽ��������ϸ��Ϣ
	 */
	private static final String SCHEME = "package";  
	/** 
	 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.1��֮ǰ�汾) 
	 */  
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";  
	/** 
	 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.2) 
	 */  
	private static final String APP_PKG_NAME_22 = "pkg";  
	/** 
	 * InstalledAppDetails���ڰ��� 
	 */  
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";  
	/** 
	 * InstalledAppDetails���� 
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
	 * ����ϵͳInstalledAppDetails������ʾ�Ѱ�װӦ�ó������ϸ��Ϣ�� ����Android 2.3��Api Level 
	 * 9�����ϣ�ʹ��SDK�ṩ�Ľӿڣ� 2.3���£�ʹ�÷ǹ����Ľӿڣ��鿴InstalledAppDetailsԴ�룩�� 
	 * @param context 
	 * @param packageName 
	 * Ӧ�ó���İ��� 
	 */ 
	public static void showInstalledAppDetails(Context context, String packageName) {  
	    Intent intent = new Intent();  
	    final int apiLevel = Build.VERSION.SDK_INT;  
	    if (apiLevel >= 9) { // 2.3��ApiLevel 9�����ϣ�ʹ��SDK�ṩ�Ľӿ�  
	        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);  
	        Uri uri = Uri.fromParts(SCHEME, packageName, null);  
	        intent.setData(uri);  
	    } else { 
	    	// 2.3���£�ʹ�÷ǹ����Ľӿڣ��鿴InstalledAppDetailsԴ�룩  
	        // 2.2��2.1�У�InstalledAppDetailsʹ�õ�APP_PKG_NAME��ͬ��  
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
     * ��bitת��ΪKB,MB
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
    		//ȡ��sdcard�ļ�·��
    		File path = Environment.getExternalStorageDirectory(); 
    		StatFs statfs = new StatFs(path.getPath());
    		//ȡblock��SIZE
    		long blocSize = statfs.getBlockSize();
    		//��ȡBLOCK����
    		long totalBlocks = statfs.getBlockCount();
    		//���е�Block������
    		long availaBlock = statfs.getAvailableBlocks();
    		//�����ܿռ��С�Ϳ��еĿռ��С
    		size[0] = totalBlocks * blocSize; 
    		size[1] = availaBlock * blocSize;
    	}
    	return size;
    }
}
