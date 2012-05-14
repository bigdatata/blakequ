package com.hao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;

import com.entity.RouteDetail;
import com.entity.SingleStep;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.android.maps.ItemizedOverlay.OnFocusChangeListener;
import com.hao.LocationHelper.LocationResult;
import com.hao.LocationHelper2.OnGetLocationListener;
import com.overlay.LineOverlay;
import com.overlay.MyCompassOverlay;
import com.overlay.PointsOverlay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MapFirstActivity extends MapActivity {
	//location listener
	private LocationControl locationControlTask;
	private boolean hasLocation = false;
//	LocationHelper locHelper;
	LocationHelper2 locHelper;
	Location currentLocation = null;
	
    //map
	MapView map = null;
	MapController mapCon;
	ParserRoute parser = null;
	RouteDetail route = null;
	PopupWindow popupWindow;
	List<Overlay> overlays;
	MyLocationListener listener;
	MyCompassOverlay me = null;
	MyLocationOverlay myLocationOverlay = null;
	GeoPoint geobegin, geoEnd;
	boolean isBeginNavigation = false;//看是否开始导航
	boolean isGetMyLocation = false;//定位我的位置

	final int menuMode = Menu.FIRST;
	final int menuNavigation = Menu.FIRST+1;
	final int menuMyLocation = Menu.FIRST+2;
	final int menuExit = Menu.FIRST+3;
	int chooseItem = 0;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        parser = new ParserRoute();
        map = (MapView) findViewById(R.id.map);
//        locHelper = new LocationHelper(MapFirstActivity.this);
        locHelper = new LocationHelper2(MapFirstActivity.this);
        me = new MyCompassOverlay(this, getResources().getDrawable(R.drawable.location), map);
        myLocationOverlay = new MyLocationOverlay(this, map);
        
        //设置可缩放
        map.setBuiltInZoomControls(true);
        //设置显示模式
        map.setTraffic(false);
        map.setStreetView(false);
        map.setSatellite(false);
        //设置初始显示位置
        geobegin=new GeoPoint((int) (23.055291 * 1E6),  (int) (113.391802 * 1E6));
        geoEnd = new GeoPoint((int)(23.046604 * 1E6), (int)(113.397510 * 1E6));
        mapCon = map.getController();
        mapCon.setCenter(geobegin);
        mapCon.setZoom(16);
        
        overlays = map.getOverlays(); //可以有多层覆盖
    	overlays.add(me);
    	overlays.add(myLocationOverlay);
    	listener = new MyLocationListener(MapFirstActivity.this, me);
    	addMyOverlay(overlays);
    	map.invalidate();
    }
    
    //设置覆盖图层
    private void addMyOverlay(List<Overlay> overlays){
        //从google地图获取路线
    	Drawable marker = this.getResources().getDrawable(R.drawable.flag_icon);
    	marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

    	String result = requestRouteFromGoogle(23.055291,113.391802,23.046604,113.397510,"walking", "cn");
    	if(result == null){
    		Toast.makeText(this, getResources().getString(R.string.route_fail), 3000).show();
    	}
    	else if(result != null && route != null){
    		//添加线覆盖层
	        List<GeoPoint> points = decodePoly(result); 
	        LineOverlay lOverlay = new LineOverlay(this, map);
	        lOverlay.clear();
			lOverlay.addOverlayList(this.geoPintListToOverLayList(points));
			overlays.add(lOverlay); 
			
			//添加点覆盖层
	        List<OverlayItem> listStep = new ArrayList<OverlayItem>();
	        /*
	         //这里我们添加了所有的路段点
	        for(SingleStep steps: route.getSteps()){
	        	Double lat = Double.valueOf(steps.getStart_location_lat());
	        	Double lng = Double.valueOf(steps.getStart_location_lng());
	        	GeoPoint p = new GeoPoint((int) (lat  * 1E6),(int) ( lng * 1E6));
	        	OverlayItem item = new OverlayItem(p, steps.getDistance_text()+" "+steps.getDuration_text(), steps.getHtml_instructions());
	        	listStep.add(item);
	        }
	         //注意，我们应该在最后加一点是终点，因为我获取的时候没有取终点
	        OverlayItem endItem = new OverlayItem(new GeoPoint((int)(23.046604 * 1E6), (int)(113.397510 * 1E6)), route.getDistance_text(), route.getEnd_address());
        	listStep.add(endItem);
	        */
	        //我们现在只添加起点和终点
	        OverlayItem beginItem = new OverlayItem(geobegin, route.getDistance_text(), route.getStart_address());
	        OverlayItem endItem = new OverlayItem(geoEnd, route.getDistance_text(), route.getEnd_address());
        	listStep.add(beginItem);
        	listStep.add(endItem);
	        PointsOverlay pOverLay = new PointsOverlay(marker, this, map);
	        pOverLay.clear();
	        pOverLay.addOverlayList(listStep);
	        overlays.add(pOverLay);
	        pOverLay.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChanged(ItemizedOverlay overlay,
						OverlayItem newFocus) {
					// TODO Auto-generated method stub
					if(newFocus != null){
						GeoPoint point = newFocus.getPoint();//这行用于popView的定位
						Projection projection = map.getProjection(); //投影
						Point p = new Point(); 
			        	projection.toPixels(point, p);
			        	//int xb=p.x-picWidth/2;
			            //int yb=p.y-picHeight;设置相对位置时，先获取浮动框的大小
//			        	int w = popupWindow.getWidth()/2; int h = popupWindow.getHeight()/2;
			        	openPopupwin(newFocus.getTitle(), newFocus.getSnippet(), p.x-60, p.y-30);
					}
				}
			});
			if (points.size() >= 2){
				mapCon.animateTo(points.get(0)); 
			} 
    	}
    }
    

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		me.enableCompass();
		myLocationOverlay.enableCompass();
		if(isBeginNavigation && locHelper.enableMyLocation()){
			listener.requestLocationListener();
		}
		if(isGetMyLocation){
			getMyLocation();
		}
	}
	
	private void getMyLocation(){
//		locHelper.getLocation(locationResult);
		locationControlTask = new LocationControl();
		locationControlTask.execute(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		me.disableCompass();
		myLocationOverlay.disableCompass();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(listener != null){
			listener.removeUpdateListener();
		}
		//stop task
		if(locationControlTask != null && !locationControlTask.isCancelled()){
			locationControlTask.cancel(true);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,menuMode,0,"地图模式");
		menu.add(0, menuNavigation,1 ,"导航");
		menu.add(0,menuMyLocation, 2, "位置");
		menu.add(0, menuExit, 3, "退出");
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case menuExit:
				finish();
				break;
			case menuNavigation:
				if(!locHelper.enableMyLocation()){
					locHelper.openLocationSetting();
					isBeginNavigation = true;
				}else{
					listener.requestLocationListener();
				}
				break;
			case menuMyLocation:
				if(!locHelper.enableMyLocation()){
					locHelper.openLocationSetting();
					isGetMyLocation = true;
				}else{
					//begin
					getMyLocation();
				}
				break;
			case menuMode:
				Builder dia = new AlertDialog.Builder(this);
				dia.setTitle("地图模式设置");
				dia.setSingleChoiceItems(R.array.mode, chooseItem, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						chooseItem=which;
					}
				});
				dia.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch(chooseItem){
						case 0:
							map.setSatellite(true);
							break;
						case 1:
							map.setTraffic(true);
							break;
						case 2:
							map.setStreetView(true);
							break;
						case 3:
							 map.setTraffic(false);
						     map.setStreetView(false);
						     map.setSatellite(false);
						default:
							break;
						}
					}
				});
				dia.setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dia.create();
				dia.show();
				break;
			}
			return super.onMenuItemSelected(featureId, item);
		}
	
	
	/**
	 * 通过解析google map返回的xml，在map中画路线图 
	 * @param origin_lat
	 * @param origin_lng
	 * @param destination_lat
	 * @param destination_lng
	 * @param drive_method the method include driving, walking and bicycling. but bicycling only in American
	 * @param language you request language (en, cn. ..)
	 * @return
	 */
	public String requestRouteFromGoogle(double origin_lat, double origin_lng, double destination_lat, double destination_lng, String drive_method, String language){
		StringBuilder builder = new StringBuilder("http://maps.google.com/maps/api/directions/xml?");
		builder.append("origin="+origin_lat);
		builder.append(","+origin_lng);
		builder.append("&destination="+destination_lat);
		builder.append(","+destination_lng);
		builder.append("&sensor=true&mode="+drive_method);
		builder.append("&language="+language);
		String url = builder.toString();
//		System.out.println(url);
//		String url = "http://maps.google.com/maps/api/directions/xml?origin=23.055291,113.391802&destination=23.046604,113.397510&sensor=false&mode=walking&language=cn"; 
	
		HttpGet get = new HttpGet(url); 
		String strResult = null; 
		try { 
			HttpParams httpParameters = new BasicHttpParams(); 
			HttpConnectionParams.setConnectionTimeout(httpParameters, 3000); 
			HttpClient httpClient = new DefaultHttpClient(httpParameters); 
		
			HttpResponse httpResponse = null; 
			httpResponse = httpClient.execute(get); 
		
			if (httpResponse.getStatusLine().getStatusCode() == 200){ 
				strResult = EntityUtils.toString(httpResponse.getEntity()); 
			} 
		} catch (Exception e) { 
			return null; 
		} 
		
		route = parserRoute(strResult);
		
		if (-1 == strResult.indexOf("<status>OK</status>")){ 
			Toast.makeText(this, "获取导航路线失败!", Toast.LENGTH_SHORT).show(); 
			return null; 
		} 
		int pos = strResult.indexOf("<overview_polyline>"); 
		pos = strResult.indexOf("<points>", pos + 1); 
		int pos2 = strResult.indexOf("</points>", pos); 
		strResult = strResult.substring(pos + 8, pos2); 
		return strResult;
	} 
	
	
	/** 
	* 解析返回xml中overview_polyline的路线编码 
	* 
	* @param encoded 
	* @return 
	*/ 
	private List<GeoPoint> decodePoly(String encoded) { 
		List<GeoPoint> poly = new ArrayList<GeoPoint>(); 
		int index = 0, len = encoded.length(); 
		int lat = 0, lng = 0; 
		while (index < len) { 
			 int b, shift = 0, result = 0; 
			 do { 
				b = encoded.charAt(index++) - 63; 
				result |= (b & 0x1f) << shift; 
				shift += 5; 
			 } while (b >= 0x20); 
			 
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); 
			lat += dlat; 
			shift = 0; 
			result = 0; 
			
			do { 
				b = encoded.charAt(index++) - 63; 
				result |= (b & 0x1f) << shift; 
				shift += 5; 
			} while (b >= 0x20); 
			
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); 
			lng += dlng; 
			GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),(int) (((double) lng / 1E5) * 1E6)); 
			poly.add(p); 
		} 
		return poly; 
	} 
	
	
	private List<OverlayItem> geoPintListToOverLayList(List<GeoPoint> points){
		List<OverlayItem> list = new ArrayList<OverlayItem>();
		for(GeoPoint point:points){
			list.add(new OverlayItem(point, "", ""));
		}
		return list;
	}
	
	public InputStream getInputStreamFromString(String str){  
		return new ByteArrayInputStream(str.getBytes());  
	} 
	
	public String getStringFromInputStream(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * parser the route
	 * @param routeStr
	 * @return
	 */
	private RouteDetail parserRoute(String routeStr){
		InputStream is, iss;
		RouteDetail route = null ;
		try {
//			strResult = getStringFromInputStream(getAssets().open("route.xml"));
			iss = getInputStreamFromString(routeStr);
			List<SingleStep> list = parser.parserStep(iss);
			is = getInputStreamFromString(routeStr);
			route = parser.parserRoute(is);
			if(list != null){
				route.setSteps(list);
				route.setStepNum(list.size());
//				System.out.println(list);
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("IOException");
		}catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			System.out.println("XmlPullParserException");
			e.printStackTrace();
		}
		return route;
	}
	
	private void openPopupwin(String title, String content, int x, int y) {
		LayoutInflater mLayoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.popup, null, true);

		TextView titleView = (TextView) menuView.findViewById(R.id.title);
		titleView.setText(title);
		TextView contentView = (TextView) menuView.findViewById(R.id.content);
		contentView.setText(content);
		ImageButton button = (ImageButton) menuView.findViewById(R.id.direct);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MapFirstActivity.this, "button", 3000).show();
				popupWindow.dismiss();
			}
		});

		popupWindow = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(map, Gravity.NO_GRAVITY, x, y);
		popupWindow.update();
	}

	
	/**
	 * asynctask using for get current location of user
	 * @author Administrator
	 *
	 */
	private class LocationControl extends AsyncTask<Context, Location, Void> implements OnGetLocationListener<Location>
    {
        private final ProgressDialog dialog = new ProgressDialog(MapFirstActivity.this);

        protected void onPreExecute()
        {
        	locHelper.setGetLocationListener(this);
        	locHelper.getLocation();
            this.dialog.setMessage(getResources().getString(R.string.get_location));
            this.dialog.show();
        }

        protected Void doInBackground(Context... params)
        {
            //Wait 10 seconds to see if we can get a location from either network or GPS, otherwise stop
            Long t = Calendar.getInstance().getTimeInMillis();
            while (!hasLocation && Calendar.getInstance().getTimeInMillis() - t < 10000) {
                try {
//                	locHelper.getLastLocation();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            return null;
        }

        protected void onPostExecute(final Void unused)
        {
            if(this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if (currentLocation != null)
            {
            	GeoPoint point = new GeoPoint((int)(currentLocation.getLatitude() * 1E6), (int)(currentLocation.getLongitude() * 1E6));
            	me.addOverlay(new OverlayItem(point, "my position", "this is my position"));
            }
            else
            {
            	//Couldn't find location, do something like show an alert dialog
            	Toast.makeText(MapFirstActivity.this, getResources().getString(R.string.no_location), 3000).show();
            }
        }
        
		@Override
		protected void onProgressUpdate(Location... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			dialog.dismiss();
		}

		@Override
		public void onGetLocation(Location t) {
			// TODO Auto-generated method stub
			if(t != null){
				currentLocation = new Location(t);
        		hasLocation = true;
        		publishProgress(t);
        	}else{
        		System.out.println("LocationResult() location is null");
        	}
		}
    }
	
	
//	public LocationResult locationResult = new LocationResult()
//    {
//        @Override
//        public void gotLocation(final Location location)
//        {
//        	if(location != null){
//        		currentLocation = new Location(location);
//        		hasLocation = true;
//        	}else{
//        		System.out.println("LocationResult() location is null");
//        	}
//        }
//    };
	
}