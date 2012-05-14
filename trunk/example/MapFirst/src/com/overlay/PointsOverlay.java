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
		//一旦有新overlayItem数据进来，在调用其他方法之前必须先调用populate()方法
		populate();
	}
	
	public PointsOverlay(Drawable defaultMarker) {
		super(defaultMarker);
	}

	// 创建一个OverlayItem索引 
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return locations.get(i);
	}

	// 返回当前的Overlay当中所包含的OverlayItem对象
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
		 // 定义锚点显示在底部的中央  
		// 定义锚点在中心位置可以调用 boundCenter()方法 
        boundCenterBottom(marker);
	}

	//这个是在地图上的点击都会调用
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		// TODO Auto-generated method stub
		return super.onTap(p, mapView);
	}
	
	
	// 当用户点击标记的时候所调用的函数 
	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
