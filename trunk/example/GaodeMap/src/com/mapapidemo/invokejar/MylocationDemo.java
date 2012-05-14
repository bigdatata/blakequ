package com.mapapidemo.invokejar;

import android.os.Bundle;

import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapController;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.MyLocationOverlay;

/**
 * MyLocationOverlay�õ���ǰλ�ã���MyLocationOverlayĬ�ϵ�ͼ���ڵ�ͼ�ϱ�ǣ� ���ڵ�ͼ�ϴ����γ�����꣨���ȣ�γ�ȣ�
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
		mylocTest.enableCompass(); // ��ָ����
		map.getOverlays().add(mylocTest);
	}
}
