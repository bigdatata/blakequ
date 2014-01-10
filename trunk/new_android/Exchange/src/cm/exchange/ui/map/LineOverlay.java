package cm.exchange.ui.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import cm.exchange.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class LineOverlay extends ItemizedOverlay {
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	Context context;
	MapView mapView;
	Drawable startMarker, endMarker;
	
	public LineOverlay(Context context, MapView mapView){
		super(context.getResources().getDrawable(R.drawable.location));
		this.context = context;
		this.mapView = mapView;
		startMarker = context.getResources().getDrawable(R.drawable.icon_nav_start);
		endMarker = context.getResources().getDrawable(R.drawable.icon_nav_end);
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
	protected int getIndexToDraw(int drawingOrder) {
		// TODO Auto-generated method stub
		if(locations.get(drawingOrder).getSnippet().equals("-1")){
			startMarker.setBounds(-10, -startMarker.getIntrinsicHeight(), startMarker.getIntrinsicWidth()-10, 0);
			locations.get(drawingOrder).setMarker(startMarker);
		}
		if(locations.get(drawingOrder).getSnippet().equals("-2")){
			endMarker.setBounds(-10, -endMarker.getIntrinsicHeight(), endMarker.getIntrinsicWidth()-10, 0);
			locations.get(drawingOrder).setMarker(endMarker);
		}
		return super.getIndexToDraw(drawingOrder);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		
        Paint paint = new Paint(); 
        paint.setColor(Color.RED); 
        paint.setDither(true); 
        paint.setStyle(Paint.Style.STROKE); 
        paint.setStrokeJoin(Paint.Join.ROUND); 
        paint.setStrokeCap(Paint.Cap.ROUND); 
        paint.setStrokeWidth(2); 
        Projection projection = mapView.getProjection(); 
        if(locations.size() >= 2){
        	Path path = new Path();
        	GeoPoint gpoint = locations.get(0).getPoint();
        	Point p = new Point(); 
        	projection.toPixels(gpoint, p);
        	path.moveTo(p.x, p.y);
	        for(int i=1; i<locations.size(); i++){
	        	gpoint = locations.get(i).getPoint();
	        	p = new Point();
		        projection.toPixels(gpoint, p); 
		        path.lineTo(p.x, p.y); 
	        }
	        canvas.drawPath(path, paint);
        }
	}
	
	
}
