package com.mapapidemo.invokejar;

import android.os.Bundle;

import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapController;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.MyLocationOverlay;

/**
 * MyLocationOverlay得到当前位置，用MyLocationOverlay默认的图标在地图上标记， 并在地图上打出经纬度坐标（经度，纬度）
 */
public class MylocationDemo extends MapActivity {
	MapView map = null;
	MapController ctrlMap = null;
	MyLocationOverlay mylocTest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylocationdemo);
		map = (MapView) findViewById(R.id.mylocationview);
		map.setBuiltInZoomControls(true); //
		ctrlMap = map.getController();
		mylocTest = new MyLocationOverlay(MylocationDemo.this, map);
		mylocTest.enableMyLocation();
		mylocTest.enableCompass(); // 打开指南针
		map.getOverlays().add(mylocTest);
	}
}
