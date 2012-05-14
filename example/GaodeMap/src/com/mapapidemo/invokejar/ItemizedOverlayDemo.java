package com.mapapidemo.invokejar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.ItemizedOverlay;
import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapController;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.OverlayItem;
import com.autonavi.mapapi.Projection;
/**
 *ItemizedOverlay实现地图上给定位置用自定义图片标三个点 
 */
public class ItemizedOverlayDemo extends MapActivity {

	MapView mMapView;
	MapController mMapController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemizedoverlay);

		mMapView = (MapView) findViewById(R.id.itemizedoverlayview);
		mMapView.setBuiltInZoomControls(true); //设置启用内置的缩放控件
		mMapController = mMapView.getController();

		Drawable marker = getResources().getDrawable(R.drawable.poi_1);  //得到需要标在地图上的资源
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
				.getIntrinsicHeight());   //为maker定义位置和边界
		
		mMapView.getOverlays().add(new OverItemT(marker, this)); //添加ItemizedOverlay实例到mMapView
	}

}
class OverItemT extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
	private Drawable marker;
	private Context mContext;

	private double mLat1 = 39.9022; // point1纬度
	private double mLon1 = 116.3922; // point1经度

	private double mLat2 = 39.607723;
	private double mLon2 = 116.397741;

	private double mLat3 = 39.917723;
	private double mLon3 = 116.6552;

	public OverItemT(Drawable marker, Context context) {
		super(boundCenterBottom(marker));

		this.marker = marker;
		this.mContext = context;

		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));

		// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
		GeoList.add(new OverlayItem(p1, "P1", "point1"));
		GeoList.add(new OverlayItem(p2, "P2", "point2"));
		GeoList.add(new OverlayItem(p3, "P3", "point3"));		
		populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		// Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换
		Projection projection = mapView.getProjection(); 
		for (int index = size() - 1; index >= 0; index--) { // 遍历GeoList
			OverlayItem overLayItem = getItem(index); // 得到给定索引的item

			String title = overLayItem.getTitle();
			// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
			Point point = projection.toPixels(overLayItem.getPoint(), null); 

			Paint paintCircle = new Paint();
			paintCircle.setColor(Color.RED);
			canvas.drawCircle(point.x, point.y, 5, paintCircle); // 画圆

			Paint paintText = new Paint();
			paintText.setColor(Color.BLACK);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x, point.y - 25, paintText); // 绘制文本

		}

		super.draw(canvas, mapView, shadow);
		//调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
		boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return GeoList.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return GeoList.size();
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(int i) {
		setFocus(GeoList.get(i));
		Toast.makeText(this.mContext, GeoList.get(i).getSnippet(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
}
