package com.hao;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
/**
 * 这个位置改变的通知机制是利用回调监听器OnGetLocationListener
 * @author Administrator
 *
 */
public class LocationHelper2 {
	
	OnGetLocationListener getLocationListener;
	boolean gpsEnabled = false;
	boolean networkEnabled = false;
	Context context;
	LocationManager locationManager;
	
	public interface OnGetLocationListener<T>{
		void onGetLocation(T t);
	}
	
	public void setGetLocationListener(OnGetLocationListener getLocationListener) {
		this.getLocationListener = getLocationListener;
	}
	
	public LocationHelper2(Context context){
		this.context = context;
		if (locationManager == null) {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}
	
	public boolean getLocation(){
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
			// 将获取的location放入Listener
			getLocationListener.onGetLocation(location);
			// 如果已经获取了位置取消自身和网络获取的方式
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
			// 将获取的location放入Listener
			getLocationListener.onGetLocation(location);
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
	public void getLastLocation() {
		// 因为通过Listener已经获取了位置故可以移除Listener
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
				getLocationListener.onGetLocation(gpsLocation);
			} else {
				getLocationListener.onGetLocation(networkLocation);
			}
			return;
		}

		if (gpsLocation != null) {
			getLocationListener.onGetLocation(gpsLocation);
			return;
		}

		if (networkLocation != null) {
			getLocationListener.onGetLocation(networkLocation);
			return;
		}
		getLocationListener.onGetLocation(null);
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
}
