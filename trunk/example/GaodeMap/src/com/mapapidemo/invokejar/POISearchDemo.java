package com.mapapidemo.invokejar;

import java.io.IOException;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.PoiOverlay;
import com.autonavi.mapapi.PoiPagedResult;
import com.autonavi.mapapi.PoiSearch;
/**
 *搜索与苏州街相关的兴趣点，并将结果集的第一页在地图上用点标记
*/
public class POISearchDemo extends MapActivity {
	private MapView mMapView = null;
	Button POIsearchBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.poisearch);
		mMapView = ((MapView) findViewById(R.id.poisearchview));
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		POIsearchBtn = (Button) this.findViewById(R.id.poisearch);
		OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (POIsearchBtn.equals(v)) {
					try{
						PoiSearch poiSearch = new PoiSearch(POISearchDemo.this,
								new PoiSearch.Query("中关村", "0504","110000")); // 设置搜索字符串
						PoiPagedResult result = poiSearch.searchPOI(); // 调用搜索POI方法
						if(result.getPage(1).size()==0){
							Toast.makeText(
									getApplicationContext(), "没有找到！",
									Toast.LENGTH_SHORT).show();
							
						}else{
							PoiOverlay poiOverlay = new PoiOverlay(null, result
									.getPage(1)); // 将结果的第一页添加到PoiOverlay
							poiOverlay.addToMap(mMapView); // 将poiOverlay标注在地图上	
							 mMapView.invalidate();
						}
					}catch(Exception e){
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "网络连接错误！",
								Toast.LENGTH_SHORT).show();

					}
				}
			}
		};
		POIsearchBtn.setOnClickListener(mOnClickListener);
		
	}
}
