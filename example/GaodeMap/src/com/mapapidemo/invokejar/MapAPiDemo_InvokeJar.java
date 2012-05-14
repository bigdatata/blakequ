package com.mapapidemo.invokejar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapAPiDemo_InvokeJar extends Activity {

	boolean bAutonaviFlag = false;
	Button MapViewBtn;
	Button itemizedBtn;
	Button mylocButton;
	Button geoCodingBtn;
	Button PoiSearchBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MapViewBtn = (Button) this.findViewById(R.id.Button01);
		itemizedBtn = (Button) this.findViewById(R.id.Button04);
		mylocButton = (Button) this.findViewById(R.id.Button05);
		geoCodingBtn = (Button)this.findViewById(R.id.Button08);
		PoiSearchBtn = (Button)this.findViewById(R.id.Button09);
		OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (MapViewBtn.equals(v)) {
					startMapViewDemo();
				} else if (itemizedBtn.equals(v)) {
					startItemizedOverlayDemo();
				} else if (mylocButton.equals(v)) {
					startMyLoactionDemo();
				} else if (geoCodingBtn.equals(v)) {
					startGeocodingDemo();
				} else if (PoiSearchBtn.equals(v)) {
					startPOIDemo();
				}
			}

		};
		MapViewBtn.setOnClickListener(mOnClickListener);
		itemizedBtn.setOnClickListener(mOnClickListener);
		mylocButton.setOnClickListener(mOnClickListener);
		geoCodingBtn.setOnClickListener(mOnClickListener);
		PoiSearchBtn.setOnClickListener(mOnClickListener);
		
	}
	private void startGeocodingDemo() {
		Intent intent = null;
		intent = new Intent(MapAPiDemo_InvokeJar.this,
				GeocodingDemo.class);
		this.startActivity(intent);
	}
	private void startMyLoactionDemo() {
		Intent intent = null;
		intent = new Intent(MapAPiDemo_InvokeJar.this, MylocationDemo.class);

		this.startActivity(intent);
	}
	
	private void startPOIDemo() {
		Intent intent = null;
		intent = new Intent(MapAPiDemo_InvokeJar.this, POISearchDemo.class);

		this.startActivity(intent);
	}

	private void startMapViewDemo() {
		Intent intent = null;
		intent = new Intent(MapAPiDemo_InvokeJar.this, MapViewDemo.class);

		this.startActivity(intent);
	}
	private void startItemizedOverlayDemo() {
		Intent intent = null;
		intent = new Intent(MapAPiDemo_InvokeJar.this,
				ItemizedOverlayDemo.class);

		this.startActivity(intent);
	}



	
	
}