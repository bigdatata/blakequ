package cm.util.app;

import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class SystemInfoUtil {
	/**
	 * 获取系统版本
	 * @return
	 */
	public static String getSystemVersion(){
		String result = null;
		CMDExecute cmdExe = new CMDExecute();
		String[ ] args = {"/system/bin/cat", "/proc/version"};
		try {
			result = cmdExe.run(args, "system/bin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result != null){
			int last = result.indexOf("(");
			result = result.substring(0, last);
		}
		return result;
	}
	
	/**
	 *获取系统属性
	 * @return
	 */
	public static String getSystemProperty() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(initProperty("官网","java.vendor.url"));
//		buffer.append(initProperty("java.class.path","java.class.path"));
//		buffer.append(initProperty("user.home","user.home"));
		buffer.append(initProperty("系统版本","os.version"));
		buffer.append(initProperty("用户目录","user.dir"));
//		buffer.append(initProperty("user.timezone","user.timezone"));
		return buffer.toString();
	}
	
	private static String initProperty(String description,String propertyStr) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(description).append(":");
		buffer.append (System.getProperty(propertyStr)).append("\n");
		return buffer.toString();
	}
	
	/**
	 *  运营商信息
	 * @param cx
	 * @return
	 */
	public static String fetch_tel_status(Context cx) {
		String result = null;
		TelephonyManager tm = (TelephonyManager) cx.getSystemService(Context.TELEPHONY_SERVICE);
		String str = " ";
		str += "DeviceId(IMEI) = " + tm.getDeviceId() + " ";
		str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion()+" ";
		// TODO: Do something ...
		int mcc = cx.getResources().getConfiguration().mcc;
		int mnc = cx.getResources().getConfiguration().mnc;
		str +="IMSI MCC (Mobile Country Code): " +String.valueOf(mcc) + " ";
		str +="IMSI MNC (Mobile Network Code): " +String.valueOf(mnc) + " ";
		result = str;
		return result;
	}
	
	/**
	 * 获取CPU信息
	 * @return
	 */
	public static String fetch_cpu_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[ ] args = {"/system/bin/cat", "/proc/cpuinfo"};
			result = cmdexe.run(args, "/system/bin/");
			Log.i("result", "result=" + result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	*系统内存情况查看
	*/
	public static String getMemoryInfo(Context context) {
		StringBuffer memoryInfo = new StringBuffer();
		final ActivityManager activityManager =(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(outInfo);
		memoryInfo.append(" Total Available Memory :").append(outInfo.availMem >> 10).append("k");
		memoryInfo.append(" Total Available Memory :").append(outInfo.availMem >> 20).append("k");
		memoryInfo.append(" In low memory situation:").append(outInfo.lowMemory);
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[ ] args = {"/system/bin/cat", "/proc/meminfo"};
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info","ex=" + ex.toString());
		}
		return (memoryInfo.toString() + " " + result);
	}
	
	
	/**
	 *  获取磁盘信息
	 * @return
	 */
	public static String fetch_disk_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[ ] args = {"/system/bin/df"};
			result = cmdexe.run(args, "/system/bin/");
			Log.i("result", "result=" + result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取网络信息
	 */
	public static String fetch_netcfg_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[ ] args = {"/system/bin/netcfg"};
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info","ex=" + ex.toString());
		}
		return result;
	}
	
	/**
	 * 获取显示屏信息
	 * @param cx
	 * @return
	 */
	public static String getDisplayMetrics(Context cx) {
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		str += "The absolute width: " + String.valueOf(screenWidth) + "pixels ";
		str += "The absolute heightin: " + String.valueOf(screenHeight) + "pixels ";
		str += "The logical density of the display. : " + String.valueOf(density) + " ";
		str += "X dimension : " + String.valueOf(xdpi) +"pixels per inch ";
		str += "Y dimension : " + String.valueOf(ydpi) +"pixels per inch ";
		return str;
	}

}
