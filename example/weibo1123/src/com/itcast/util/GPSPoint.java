package com.itcast.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class GPSPoint {

	public static double[] getGPSPoint(Context context)
	{
		//得到GPS设备的访问
		LocationManager locationManager = 
			(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
////
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
////
		String provider = locationManager.getBestProvider(criteria, true);
////
		Location location = locationManager.getLastKnownLocation(provider);
//		获取GPS传感器的坐标信息
		Double latitude = location.getLatitude() * 1E6;
		Double longitude = location.getLongitude() * 1E6;
		double point[]={latitude,
        		longitude};
        return point;
	}
}
