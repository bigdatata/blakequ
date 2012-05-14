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
	*显示地图，启用内置缩放控件，并用MapController控制地图的中心点及Zoom级别
	*/
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewdemo);
		mMapView = (MapView) findViewById(R.id.atmapsView);
		mMapView.setBuiltInZoomControls(true);  //设置启用内置的缩放控件
		mMapController = mMapView.getController();  // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		point = new GeoPoint((int) (39.90923 * 1E6),
				(int) (116.397428 * 1E6));  //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);  //设置地图中心点
		mMapController.setZoom(12);    //设置地图zoom级别
	}

}
