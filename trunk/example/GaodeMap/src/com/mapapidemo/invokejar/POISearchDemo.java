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
 *���������ݽ���ص���Ȥ�㣬����������ĵ�һҳ�ڵ�ͼ���õ���
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
		mMapView.setBuiltInZoomControls(true); // �����������õ����ſؼ�
		POIsearchBtn = (Button) this.findViewById(R.id.poisearch);
		OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (POIsearchBtn.equals(v)) {
					try{
						PoiSearch poiSearch = new PoiSearch(POISearchDemo.this,
								new PoiSearch.Query("�йش�", "0504","110000")); // ���������ַ���
						PoiPagedResult result = poiSearch.searchPOI(); // ��������POI����
						if(result.getPage(1).size()==0){
							Toast.makeText(
									getApplicationContext(), "û���ҵ���",
									Toast.LENGTH_SHORT).show();
							
						}else{
							PoiOverlay poiOverlay = new PoiOverlay(null, result
									.getPage(1)); // ������ĵ�һҳ��ӵ�PoiOverlay
							poiOverlay.addToMap(mMapView); // ��poiOverlay��ע�ڵ�ͼ��	
							 mMapView.invalidate();
						}
					}catch(Exception e){
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "�������Ӵ���",
								Toast.LENGTH_SHORT).show();

					}
				}
			}
		};
		POIsearchBtn.setOnClickListener(mOnClickListener);
		
	}
}
