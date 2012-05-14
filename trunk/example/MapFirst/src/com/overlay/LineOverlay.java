package com.overlay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.hao.R;

public class LineOverlay extends ItemizedOverlay {
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	Context context;
	MapView mapView;
	
	public LineOverlay(Context context, MapView mapView){
		super(context.getResources().getDrawable(R.drawable.location));
		this.context = context;
		this.mapView = mapView;
		//һ������overlayItem���ݽ������ڵ�����������֮ǰ�����ȵ���populate()����
		populate();
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

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return locations.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return locations.size();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		//���������ߵĸ��ǲ㣬���Բ���ʾ��ǵĵ�
		
		//��·��
        // ���� 
        Paint paint = new Paint(); 
        paint.setColor(Color.RED); 
        paint.setDither(true); //��������
        paint.setStyle(Paint.Style.STROKE); 
        paint.setStrokeJoin(Paint.Join.ROUND); //���
        paint.setStrokeCap(Paint.Cap.ROUND); 
        paint.setStrokeWidth(2); 
        Projection projection = mapView.getProjection(); //ͶӰ
        if(locations.size() >= 2){
        	Path path = new Path();
        	GeoPoint gpoint = locations.get(0).getPoint();
        	Point p = new Point(); 
        	projection.toPixels(gpoint, p);
        	path.moveTo(p.x, p.y);
	        for(int i=1; i<locations.size(); i++){
	        	gpoint = locations.get(i).getPoint();
	        	p = new Point();
		        projection.toPixels(gpoint, p); //��λ������ת��Ϊ��Ļ����
		        path.lineTo(p.x, p.y); 
	        }
	        canvas.drawPath(path, paint);// ����·�� 
        }
	}
	
	
}
