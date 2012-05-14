package com.mapapidemo.invokejar;

import java.util.List;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.Geocoder;
import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapView;
/**
 * 用给定的数据实现逆地理编码，并将得到的地名用Toast打印在地图上
*/
public class GeocodingDemo extends MapActivity {

	private String TAG = "HIPPO_GEO_DEBUG";
	private MapView mMapView;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.geocoding);
		btn = (Button) this.findViewById(R.id.geobtn);
		mMapView = ((MapView) findViewById(R.id.geocodingview));
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				double mLat = 39.907723;
				double mLon = 116.397741;
				// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
				GeoPoint geo = new GeoPoint((int) (mLat * 1E6),
						(int) (mLon * 1E6));
				try {
					if (geo.toString() != "") {
						Geocoder mGeocoder01 = new Geocoder(GeocodingDemo.this);
						int x = geo.getLatitudeE6(); // 得到geo纬度，单位微度 (度 * 1E6)
						double x1 = ((double) x) / 1000000;
						int y = geo.getLongitudeE6(); // 得到geo经度，单位微度 (度 * 1E6)
						double y1 = ((double) y) / 1000000;
						//得到逆理编码，参数分别为：纬度，经度，最大结果集
						List<Address> lstAddress = mGeocoder01
								.getFromRawGpsLocation(x1, y1, 3);
							if (lstAddress.size()!=0) {
							//Toast输出geo编码得到的地名
							for (int i = 0; i < lstAddress.size(); ++i) {
								Address adsLocation = lstAddress.get(i);
								Toast.makeText(getApplicationContext(),
									           adsLocation.getFeatureName().toString(),
											   Toast.LENGTH_LONG).show();
								Log.i(TAG, "Address found = "+ adsLocation.toString());
							}
						} else {
							Log.i(TAG, "Address GeoPoint NOT Found.");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "连接错误！",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
	};
}
