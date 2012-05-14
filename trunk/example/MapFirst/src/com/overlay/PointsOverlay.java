package com.overlay;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class PointsOverlay extends ItemizedOverlay {
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	Context context;
	Drawable marker;
	MapView mapView;
	
	public PointsOverlay(Drawable marker, Context context, MapView mapView) {
		super(marker);
		this.marker = marker;
		this.context = context;
		this.mapView = mapView;
		//һ������overlayItem���ݽ������ڵ�����������֮ǰ�����ȵ���populate()����
		populate();
	}
	
	public PointsOverlay(Drawable defaultMarker) {
		super(defaultMarker);
	}

	// ����һ��OverlayItem���� 
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return locations.get(i);
	}

	// ���ص�ǰ��Overlay������������OverlayItem����
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return locations.size();
	}
	
	public void addOverlay(OverlayItem overlayItem){
		locations.add(overlayItem);
		populate();
	}
	
	public void addOverlayList(List<OverlayItem> locs){
		locations.addAll(locs);
		populate();
	}
	
	public void clear(){
		locations.clear();
	}
	
	public void animateTo(GeoPoint point){
		mapView.getController().animateTo(point);
	}

	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		 // ����ê����ʾ�ڵײ�������  
		// ����ê��������λ�ÿ��Ե��� boundCenter()���� 
        boundCenterBottom(marker);
	}

	//������ڵ�ͼ�ϵĵ���������
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		// TODO Auto-generated method stub
		return super.onTap(p, mapView);
	}
	
	
	// ���û������ǵ�ʱ�������õĺ��� 
	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
