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
 * �ø���������ʵ���������룬�����õ��ĵ�����Toast��ӡ�ڵ�ͼ��
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
		mMapView.setBuiltInZoomControls(true); // �����������õ����ſؼ�
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				double mLat = 39.907723;
				double mLon = 116.397741;
				// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
				GeoPoint geo = new GeoPoint((int) (mLat * 1E6),
						(int) (mLon * 1E6));
				try {
					if (geo.toString() != "") {
						Geocoder mGeocoder01 = new Geocoder(GeocodingDemo.this);
						int x = geo.getLatitudeE6(); // �õ�geoγ�ȣ���λ΢�� (�� * 1E6)
						double x1 = ((double) x) / 1000000;
						int y = geo.getLongitudeE6(); // �õ�geo���ȣ���λ΢�� (�� * 1E6)
						double y1 = ((double) y) / 1000000;
						//�õ�������룬�����ֱ�Ϊ��γ�ȣ����ȣ��������
						List<Address> lstAddress = mGeocoder01
								.getFromRawGpsLocation(x1, y1, 3);
							if (lstAddress.size()!=0) {
							//Toast���geo����õ��ĵ���
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
					Toast.makeText(getApplicationContext(), "���Ӵ���",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
	};
}
