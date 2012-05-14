package com.overlay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.hao.R;

public class MyCompassOverlay extends ItemizedOverlay<OverlayItem> implements SensorListener{
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	Bitmap compass;
	Drawable balloon;
	Paint paint = new Paint();
	MapView map;
	Context context;
	private boolean compassEnabled;
	private SensorManager sensorManager;
    private float[] compassValues = {0.0f};
    
	public MyCompassOverlay(Context context, Drawable balloon, MapView map) {
		super(balloon);
		sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		this.balloon = balloon;
		compass = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.compass)).getBitmap();
		this.context = context;
		this.map = map;
		populate();
	}

	protected void drawCompass(Canvas canvas, float bearing) {
		// TODO Auto-generated method stub
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		int offset = Math.max(h, w)/10;
		//将指南针放在屏幕中央
        Rect r = new Rect(0, 0, 2*offset, 2*offset);
//		Rect r = new Rect(0, 0, w, h);
        canvas.drawBitmap(compass, null, r, paint);
        canvas.rotate(-bearing, offset, offset);
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
	
	public void addOverlay(OverlayItem overlayItem){
		locations.clear();
		locations.add(overlayItem);
		map.getController().animateTo(overlayItem.getPoint());
		populate();
	}
	
	public void clear(){
		locations.clear();
	}
	

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		if (isCompassEnabled() && compassValues != null) {
            drawCompass(canvas, compassValues[0]);
        }
		boundCenterBottom(balloon);
	}


	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		// TODO Auto-generated method stub
		compassValues = values;
	}
	
	public float getOrientation() {
        return compassValues[0];
    }
	
	public synchronized boolean enableCompass() {
        return compassEnabled = sensorManager.registerListener(this, 
                SensorManager.SENSOR_ORIENTATION,
                SensorManager.SENSOR_DELAY_GAME);
    }
    public synchronized void disableCompass() {
        if (compassEnabled)
            sensorManager.unregisterListener(this);
        compassEnabled = false;
    }

	public boolean isCompassEnabled() {
		return compassEnabled;
	}
	
}
