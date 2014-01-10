package cm.exchange.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cm.exchange.R;

/**
 * all Activity must extends the class (not include listActivity)
 * @author qh
 *
 */
public abstract class BaseActivity extends Activity implements PrimaryActivity ,OnClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		Log.i(UserTask.TAG, "BaseService.activityList start:"+BaseService.activityList.size());
		BaseService.activityList.add(this);
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initTitle();
	}


	/**
	 * the method when clicked right button of title
	 */
	@Override
	public void onRightButtonClicked() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseService.activityList.remove(this);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * initial the title and will be invoked automatic
	 */
	@Override
	public void initTitle() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void changeTitle(int leftId, int midId, int rightId) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * when the application exit, we should stop all task which is running in the task list
	 */
	@SuppressWarnings("unchecked")
	private void stopTask(){
		Log.i(UserTask.TAG, "stop task");
		if(BaseService.taskList.size() == 0 || BaseService.taskList == null){
			Log.i(UserTask.TAG, "no task running ");
			return;
		}
		for(UserTask userTask : BaseService.taskList){
			if(userTask.getStatus() != AsyncTask.Status.FINISHED){
				userTask.cancel(true);
				Log.i(UserTask.TAG, "cancel");
			}
		}
		BaseService.taskList.clear();
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
		Log.i(UserTask.TAG, "exit application");
		stopTask();
		for(int i=0; i<BaseService.activityList.size(); i++){
			BaseService.activityList.get(i).finish();
		}
		BaseService.activityList.clear();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		activityManager.restartPackage("cm.school");
		System.exit(0);
	}
	
	/**
	 * check the net
	 * @return if the net is available return true, otherwise return false
	 */
	public final boolean checkNetState(){
		boolean connect = false;
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if(info != null && info.isConnected()){
			connect = true;
		}
		return connect;
	}
	
	/**
	 * get the current location of user
	 * 
	 * @param context
	 * @return
	 */
	public static final Location getLocation(final Context context) {
		// open GPS
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			final long maxAgeMilliSeconds = 1000 * 60 * 1; // 1 minute
			final long maxAgeNetworkMilliSeconds = 1000 * 60 * 10; // 10 minutes
			final long now = System.currentTimeMillis();
			Location loc = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (loc == null || loc.getTime() < now - maxAgeMilliSeconds) {
				// We don't have a recent GPS fix, just use cell towers if available
				loc = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (loc == null || loc.getTime() < now - maxAgeNetworkMilliSeconds) {
					// We don't have a recent cell tower location, let the user
					// know:
					Toast.makeText(context, context.getResources().getString(R.string.no_location),
							Toast.LENGTH_LONG).show();
					return null;
				}
			}
			return loc;
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(context.getResources().getString(R.string.location_unable))
			       .setCancelable(false)
			       .setPositiveButton(context.getResources().getString(R.string.location_setting), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                openGPS(context);
			           }
			       })
			       .setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		return null;
	}
	
	/**
	 * open GPS
	 * @param context
	 */
	private static void openGPS(Context context)
	{
		// enter the page of GPS setting
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			// The Android SDK doc says that the location settings activity
			// may not be found. In that case show the general settings.
			// General settings activity
			intent.setAction(Settings.ACTION_SETTINGS);
			try {
				context.startActivity(intent);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * get the distance by the latitude and longitude
	 * @param lat1 latitude of position 1
	 * @param lon1 longitude of positon 1
	 * @param lat2 latitude of position 2
	 * @param lon2 longitude of positon 2
	 * @return
	 */
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {  
	    float[] results=new float[1];  
	    Location.distanceBetween(lat1, lon1, lat2, lon2, results);  
	    return results[0];  
	} 
	
	/**
	 * get address by the latitude and longitude
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return address
	 */
	public static String GetAddr(String longitude, String latitude) {
		String addr = "";
		// 也可以是http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s，不过解析出来的是英文地址
		// 密钥可以随便写一个key=abc
		// output=csv,也可以是xml或json，不过使用csv返回的数据最简洁方便解析
		String url = String.format(
				"http://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s",
				latitude, longitude);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				InputStreamReader insr = new InputStreamReader(httpsConn
						.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(insr);
				String data = null;
				if ((data = br.readLine()) != null) {
					System.out.println(data);
					String[] retList = data.split(",");
					if (retList.length > 2 && ("200".equals(retList[0]))) {
						addr = retList[2];
						addr = addr.replace("\"", "");
					} else {
						addr = "";
					}
				}
				insr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return addr;
	}

	/**
	 * get the latitude and longitude by address 
	 * @param addr
	 */
	public static void getCoordinate(String addr) {
		String addrs = "";
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String output = "csv";
		String key = "abc";
		String url = String.format(
				"http://maps.google.com/maps/geo?q=%s&output=%s&key=%s",
				address, output, key);
		URL myURL = null;
		URLConnection httpsConn = null;
		// 进行转码
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				InputStreamReader insr = new InputStreamReader(httpsConn
						.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(insr);
				String data = null;
				if ((data = br.readLine()) != null) {
					System.out.println(data);
					String[] retList = data.split(",");
					/*
					 * String latitude = retList[2]; String longitude =
					 * retList[3];
					 * 
					 * System.out.println("纬度"+ latitude);
					 * System.out.println("经度"+ longitude);
					 */
					if (retList.length > 2 && ("200".equals(retList[0]))) {
						addrs = retList[2];
						addrs = addr.replace("\"", "");
					} else {
						addrs = "";
					}
				}
				insr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(addrs);
	}
		

}
