package com.mapapidemo.invokejar;

import android.os.Bundle;
import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapController;
import com.autonavi.mapapi.MapView;

public class MapViewDemo extends MapActivity {

	MapView mMapView;
	MapController mMapController;
	GeoPoint point;

	@Override
	/**
	*��ʾ��ͼ�������������ſؼ�������MapController���Ƶ�ͼ�����ĵ㼰Zoom����
	*/
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewdemo);
		mMapView = (MapView) findViewById(R.id.atmapsView);
		mMapView.setBuiltInZoomControls(true);  //�����������õ����ſؼ�
		mMapController = mMapView.getController();  // �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		point = new GeoPoint((int) (39.90923 * 1E6),
				(int) (116.397428 * 1E6));  //�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);  //���õ�ͼ���ĵ�
		mMapController.setZoom(12);    //���õ�ͼzoom����
	}

}
