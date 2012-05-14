package com.hao;

import java.util.Iterator;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.overlay.MyCompassOverlay;

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

/**
 * �����������λmylocation����һ��λ�ü������Ľӿڣ�һ��λ�÷����ı�֮�����ͼ��Ҳ�仯
 */
public class MyLocationListener implements LocationListener{
	private MyCompassOverlay myOverlay = null;
	private Context context;
	private LocationManager manager;
	private static final int MINTIME = 2*60*1000;
	private static final int MINDISTANCE = 10;
	
	public MyLocationListener(Context context, MyCompassOverlay myOverlay){
		this.myOverlay = myOverlay;
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
			//ע����ģ�������Ե�ʱ�����provider��NetWork�򲻻ᴥ��listener
			//ֻ��provider��GPS���ܴ���Listener
			manager.requestLocationUpdates(provider, MINTIME, MINDISTANCE, this);
		}
	}

	//λ�÷����仯��ʱ����ö�λ�Լ���ͼ��Ҳ������Ӧ�ı仯
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double lat = 0, lng = 0;
		lat = location.getLatitude();
		lng = location.getLongitude();
		//����һ��GeoPoint����ͨ����γ�ȣ�ָ����ͼ�ϵ�һ����
		 GeoPoint point = new GeoPoint((int) (lat*1E6),(int) (lng*1E6));
		//����һ��OverLayItem����һ���������һ�����
		 OverlayItem overlayItem = new OverlayItem(point, "Haaa", "this is my location");
		//�������õ�OverLayItem������ӵ�firstOverlay������,��Ӧ�������ԭ����
		 myOverlay.addOverlay(overlayItem);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(context, context.getResources().getString(R.string.location_not_service), 3000).show();
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
		// GPS״̬�����仯ʱ����
		@Override
		public void onGpsStatusChanged(int event) {
			// ��ȡ��ǰ״̬
			GpsStatus gpsstatus = manager.getGpsStatus(null);
			switch (event) {
			// ��һ�ζ�λʱ���¼�
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				break;
			// ��ʼ��λ���¼�
			case GpsStatus.GPS_EVENT_STARTED:
				break;
			// ����GPS����״̬�¼�
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
			// ֹͣ��λ�¼�
			case GpsStatus.GPS_EVENT_STOPPED:
				Log.d("Location", "GPS_EVENT_STOPPED");
				break;
			}
		}
	};
	
}
