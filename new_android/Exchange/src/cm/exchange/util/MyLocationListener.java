package cm.exchange.util;

import java.util.Iterator;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import cm.exchange.R;
import cm.exchange.ui.map.MoveOverlay;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
	private MoveOverlay moveOverlay;
	private Context context;
	private LocationManager manager;
	private static final int MINTIME = 2*60*1000;
	private static final int MINDISTANCE = 10;
	
	public MyLocationListener(Context context, MoveOverlay moveOverlay){
		this.moveOverlay = moveOverlay;
		this.context = context;
		manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		manager.addGpsStatusListener(gpsListener);
	}
	
	public void requestLocationListener(){
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = manager.getBestProvider(criteria, true);
		if(provider != null){
			//注意在模拟器测试的时候，如果provider是NetWork则不会触发listener
			//只有provider是GPS才能触发Listener
			manager.requestLocationUpdates(provider, MINTIME, MINDISTANCE, this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double lat = 0, lng = 0;
		lat = location.getLatitude();
		lng = location.getLongitude();
		//创建一个GeoPoint对象，通过经纬度，指定地图上的一个点
		 GeoPoint point = new GeoPoint((int) (lat*1E6),(int) (lng*1E6));
		//创建一个OverLayItem对象，一个对象代表一个标记
		 OverlayItem overlayItem = new OverlayItem(point, "Haaa", "this is my location");
		//将创建好的OverLayItem对象，添加到firstOverlay对象当中,但应该先清空原来的
		 moveOverlay.addOverlay(overlayItem);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(context, context.getResources().getString(R.string.map_location_not_service), 3000).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * remove the LocationListener when not used
	 * ordinary, you should use in onStop()
	 */
	public void removeUpdateListener(){
		manager.removeUpdates(this);
		manager.removeGpsStatusListener(gpsListener);
	}
	
	private GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
		// GPS状态发生变化时触发
		@Override
		public void onGpsStatusChanged(int event) {
			// 获取当前状态
			GpsStatus gpsstatus = manager.getGpsStatus(null);
			switch (event) {
			// 第一次定位时的事件
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				break;
			// 开始定位的事件
			case GpsStatus.GPS_EVENT_STARTED:
				break;
			// 发送GPS卫星状态事件
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//				Toast.makeText(context, "GPS_EVENT_SATELLITE_STATUS",
//						Toast.LENGTH_SHORT).show();
				Iterable<GpsSatellite> allSatellites = gpsstatus
						.getSatellites();
				Iterator<GpsSatellite> it = allSatellites.iterator();
				int count = 0;
				while (it.hasNext()) {
					count++;
				}
				Toast.makeText(context, "Satellite Count:" + count,
						Toast.LENGTH_SHORT).show();
				if(count == 0){
					Toast.makeText(context, "The Gps Satellite unable",
							Toast.LENGTH_SHORT).show();
				}
				break;
			// 停止定位事件
			case GpsStatus.GPS_EVENT_STOPPED:
				Log.d("Location", "GPS_EVENT_STOPPED");
				break;
			}
		}
	};

}
