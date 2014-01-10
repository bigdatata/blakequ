package cm.exchange.util;

import java.util.List;

import cm.exchange.R;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

public class LocationHelper {
	LocationManager locationManager;
	private LocationResult locationResult;
	boolean gpsEnabled = false;
	boolean networkEnabled = false;
	Context context;
	
	public LocationHelper(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		if (locationManager == null) {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}

	/**
	 * check the net or Gps is available, if not return false, otherwise, get location and return true
	 * @param context
	 * @param result store the location result
	 * @return
	 */
	public boolean getLocation(LocationResult result) {
		locationResult = result;
		// dont start listeners if no provider is enabled
		if (!gpsEnabled && !networkEnabled) {
			return false;
		}

		if (gpsEnabled) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		}
		if (networkEnabled) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,locationListenerNetwork);
		}
		//if location service is available, we begin get last location
		getLastLocation();
		return true;
	}

	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			// ����ȡ��location����LocationResult
			locationResult.gotLocation(location);
			// ����Ѿ���ȡ��λ��ȡ������������ȡ�ķ�ʽ
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerNetwork);

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extra) {
		}
	};

	LocationListener locationListenerNetwork = new LocationListener() {
		public void onLocationChanged(Location location) {
			// ����ȡ��location����LocationResult
			locationResult.gotLocation(location);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerGps);

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extra) {
		}

	};
	
	public void removeListener(){
		locationManager.removeUpdates(locationListenerGps);
		locationManager.removeUpdates(locationListenerNetwork);
	}

	/**
	 * get last location after boot listener, if we get last new location we will get location
	 * otherwise get null
	 */
	private void getLastLocation() {
		// ��Ϊͨ��Listener�Ѿ���ȡ��λ�ùʿ����Ƴ�Listener
		removeListener();
		Location gpsLocation = null;
		Location networkLocation = null;

		if (gpsEnabled) {
			gpsLocation = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		if (networkEnabled) {
			networkLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		// if there are both values use the latest one
		if (gpsLocation != null && networkLocation != null) {
			if (gpsLocation.getTime() > networkLocation.getTime()) {
				locationResult.gotLocation(gpsLocation);
			} else {
				locationResult.gotLocation(networkLocation);
			}
			return;
		}

		if (gpsLocation != null) {
			locationResult.gotLocation(gpsLocation);
			return;
		}

		if (networkLocation != null) {
			locationResult.gotLocation(networkLocation);
			return;
		}
		locationResult.gotLocation(null);
	}
	
	/**
	 * check the location provider is available
	 * @return
	 */
	public boolean enableMyLocation() {
		boolean myLocationEnabled = false;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            myLocationEnabled = true;
            gpsEnabled = true;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            myLocationEnabled = true;
            networkEnabled = true;
        } else {
            myLocationEnabled = false;
        }
        System.out.println("enableMyLocation:"+myLocationEnabled);
        return myLocationEnabled;
    }
	
	/**
	 * if provider is unable, open the setting of location
	 * @param context
	 */
	public void openLocationSetting(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(context.getResources().getString(R.string.location_unable))
		       .setCancelable(false)
		       .setPositiveButton(context.getResources().getString(R.string.location_setting), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
//		        	   		openGPSDirect(context);
		        	   		openGPSSetting();
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
	
	/**
	 * open gps in code
	 * @param context
	 */
	private void openGPSDirect(){
		if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) { 
			Intent gpsIntent = new Intent(); 
			gpsIntent.setClassName("com.android.settings", 
			                "com.android.settings.widget.SettingsAppWidgetProvider"); 
			gpsIntent.addCategory("android.intent.category.ALTERNATIVE"); 
			gpsIntent.setData(Uri.parse("custom:3")); 
			try { 
			        PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send(); 
			} 
			catch (CanceledException e) { 
			        e.printStackTrace(); 
			} 
		}
	}
	
	/**
	 * open GPS
	 * @param context
	 */
	private void openGPSSetting()
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
	 * a abstract method using for get location(should set Location)
	 * @author Administrator
	 *
	 */
	public static abstract class LocationResult {
		public abstract void gotLocation(Location location);
	}
}
