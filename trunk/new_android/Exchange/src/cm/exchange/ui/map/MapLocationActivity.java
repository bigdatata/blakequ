package cm.exchange.ui.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cm.exchange.R;
import cm.exchange.db.GoodsService;
import cm.exchange.entity.Goods;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.ShopAndAttenParser;
import cm.exchange.ui.DetailGoodsActivity;
import cm.exchange.ui.ExchangeApplication;
import cm.exchange.ui.ShowGoodsListActivity;
import cm.exchange.ui.map.MoveOverlay.OnSelectPointListener;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.BaseService;
import cm.exchange.util.UserTask;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import cm.exchange.util.MyLocationListener;

import cm.exchange.util.LocationHelper;

import cm.exchange.util.LocationHelper.LocationResult;

/*
 * 接收状态:
 * 1.接收单个商品位置信息，然后可以显示商品和自己的位置路线
 * 2.自己请求一个商品的集合(可以设置一个范围)(由主页进入) (某范围的商品id从服务器请求，然后查询本地数据库)
 * @author chenshaoyang
 * 新增功能:1.添加用户到商品位置的线路 2.请求用户最新位置 (一开始登录就必须获取用户当前位置) 3.移动导航
 */
public class MapLocationActivity extends MapActivity implements OnClickListener{

	private static final String TAG = "MapLocationActivity";
	private static final String TASK_REQUEST = "requestGoods";
	private static final String TASK_MY_LOCATION = "myLocation";
	private static final String TASK_REQUEST_ROUTE = "route";
	private static final String TASK_SEARCH = "search";
	private static final int SELECT_DISTANCE_DIALOG = 1;
	private static final int SELECT_GET_POINT_DIALOG = 2;
	private static final int SELECT_GET_POINT_DIALOG2 = 3;
	private MapView mapView;
	private Drawable marker;
	private View popView;
	private Button showMyLocation,searchBtn,showRoute;
	private TextView itemTV;
	private EditText searchEt;
	private ImageButton detailBtn;
	
	private LineOverlay lineOverlay;
	private PointOverlay pointOverlay;
	private List<Goods> goodsList;
	private Projection projection;
	private static OverlayItem focus;
	MapController controller;
	//my location
	private boolean hasLocation = false;
	private LocationHelper locHelper;
	private Location myLocation = null;
	private GeoPoint startPoint, endPoint;
	private MyLocationOverlay myLocationOverlay = null;
	private boolean isGetMyLocation = false, isSetPoint = false, isSearchRoute = false;
	private MoveOverlay moveOverlay;
	ViewGroup menuView;
	List<GeoPoint> route;
	ExchangeApplication app;
	
	private GoodsService goodsService;
	ShopAndAttenParser parser = null;
	private boolean locateMyPositionType = false,isBeginNavigation = false, isSearchGoods = false;
	MyLocationListener listener;
	LocationAvailableListener<Location> locListener;
	
	//map top popupwindow
	Button startPointBt, endPointBt, modeWalkBt, modeTransitBt, modeDrivingBt;
	EditText startPointEt, endPointEt;
	ImageButton searchBt;
	PopupWindow popupWindow;
	String travelMode = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		BaseService.activityList.add(this);
		setContentView(R.layout.map_view);
		mapView = (MapView) findViewById(R.id.map_location_view);
		goodsService = new GoodsService(this);
		goodsList = new ArrayList<Goods>();
		locHelper = new LocationHelper(this);
		marker = getResources().getDrawable(R.drawable.detailgoods_button_location);
		pointOverlay = new PointOverlay(marker, this);
		lineOverlay = new LineOverlay(this, mapView);
		moveOverlay = new MoveOverlay(this, getResources().getDrawable(R.drawable.map_my_location), mapView);
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		listener = new MyLocationListener(this, moveOverlay);
		app = (ExchangeApplication) getApplicationContext();
		
		Goods goods = (Goods) getIntent().getSerializableExtra("goods");
		if(goods != null){
			goodsList.add(goods);
			addGoodsLocationToOverlays(goodsList);
		}else{
			//请求周围的商品 ,先要获取我的位置,这可以考虑在登录的时候获取用户的位置
			if(app.getLocation() != null){
				myLocation = app.getLocation();
				new MyTask().execute(TASK_REQUEST, "-1", myLocation.getLatitude()+"", myLocation.getLongitude()+"", "1000");
			}else{
				locateMyPositionType = true;
				getMyLocation(-2);
				setLocListener(new LocationAvailableListener<Location>(){
					@Override
					public void OnAvailableLocation(Location t) {
						// TODO Auto-generated method stub
						System.out.println("********OnAvailableLocation*******");
						new MyTask().execute(TASK_REQUEST, "-1", myLocation.getLatitude()+"", myLocation.getLongitude()+"", "1000");
					}
				});
			}
		}
		parser = new ShopAndAttenParser();
		initMapView();
		initPopView();
		initView();
		initPopupView();
		mapView.getOverlays().add(myLocationOverlay);
		mapView.getOverlays().add(moveOverlay);
		mapView.getOverlays().add(lineOverlay);
	}

	@Override
	protected void onStart() {
		super.onStart();
		goodsService.open();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLocationOverlay.disableCompass();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myLocationOverlay.enableCompass();
		if(isGetMyLocation){
			getMyLocation(-1);
		}
		if(isBeginNavigation && locHelper.enableMyLocation()){
			listener.requestLocationListener();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(goodsService.getDatabase().isOpen()){
			goodsService.close();
		}
		if(listener != null){
			listener.removeUpdateListener();
		}
		isBeginNavigation = false;
	}
	
	/**
	 * 
	 * @param type 
	 * -2:正常获取位置但不在地图显示
	 * -1:正常获取位置并在地图显示
	 * 0：获取起点，不在地图显示
	 * 1：获取终点，不在地图显示
	 * 
	 */
	private void getMyLocation(int type){
		if(!locHelper.enableMyLocation()){
			locHelper.openLocationSetting();
			isGetMyLocation = true;
		}else{
			//begin
			locHelper.getLocation(locationResult);
			new MyTask().execute(TASK_MY_LOCATION, type+"");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case R.id.map_menu_setting:
				//设置商品显示的范围
				showDialog(SELECT_DISTANCE_DIALOG);
				break;
			case R.id.map_menu_route:
				//显示两点的线路,必须选择两个点,默认起点是我的位置，终点是某商品位置，在地图上选择
				openPopupwin();
				break;
			case R.id.map_menu_navigation:
				//对两点 导航
				if(!locHelper.enableMyLocation()){
					locHelper.openLocationSetting();
					isBeginNavigation = true;
				}else{
					listener.requestLocationListener();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 初始化地图
	 */
	public void initMapView(){
		controller = mapView.getController();
		projection = mapView.getProjection();
		mapView.setBuiltInZoomControls(true);
		mapView.setFocusable(true);
		mapView.getController().setZoom(17);
		mapView.setClickable(true);
	}
	
	/**
	 * 弹出框
	 */
	public void initPopView() {
		popView = super.getLayoutInflater().inflate(R.layout.map_pop_view, null);
		mapView.addView(popView, new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		itemTV = (TextView) popView.findViewById(R.id.map_pop_view_text_view);
		detailBtn = (ImageButton) popView.findViewById(R.id.map_pop_view_more_details_button);
		popView.setVisibility(View.GONE);
	}
	

	// 用来初始化views
	public void initView() {
		searchEt = (EditText) findViewById(R.id.map_search_good_edit_text);
		showMyLocation = (Button) findViewById(R.id.map_show_my_location_button);
		searchBtn = (Button) findViewById(R.id.map_search_button);
		showRoute = (Button)findViewById(R.id.map_show_route_button);
		showMyLocation.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		showRoute.setOnClickListener(this);
		pointOverlay.setOnFocusChangeListener(new ItemizedOverlay.OnFocusChangeListener() {
					@Override
					public void onFocusChanged(ItemizedOverlay overlay,
							OverlayItem newFocus) {
						if (newFocus != null) {
							MapView.LayoutParams geoLP = (MapView.LayoutParams) popView
									.getLayoutParams();
							geoLP.point = newFocus.getPoint();
							mapView.updateViewLayout(popView, geoLP);
							itemTV.setText(newFocus.getTitle());
							popView.setVisibility(View.VISIBLE);
							focus = newFocus;
							detailBtn.setOnClickListener(MapLocationActivity.this);

						} else {
							popView.setVisibility(View.INVISIBLE);
						}

					}
				});

		if (goodsList.size() > 0) {
			GeoPoint centerPoint = getGoodsGeoPoints(goodsList.get(0));
//			System.out.println(goodsList.get(0).getLatitude()+" "+goodsList.get(0).getLongitude());
			controller.setCenter(centerPoint);
		}
		mapView.getOverlays().add(pointOverlay);
		mapView.invalidate();
	}
	

	// 通过传入商品的信息，获取商品的地点信息，即获取GeoPoint对象
	public static GeoPoint getGoodsGeoPoints(Goods goods) {
		GeoPoint point = null;
		try{
			point = new GeoPoint((int) (Double.parseDouble(goods.getLatitude()) * 1E6),
				(int) (Double.parseDouble(goods.getLongitude()) * 1E6)  );
		}catch(NumberFormatException e){
			e.printStackTrace();
			Log.e(TAG, "the goods Latitude or Longitude format exception");
		}
		return point;
	}

	// 根据传入的商品信息的在mapView中依次添加小图标
	public void addGoodsLocationToOverlays(List<Goods> goodsList) {
		List<OverlayItem> list = new ArrayList<OverlayItem>();
		for(int i=0; i<goodsList.size(); i++){
			Goods goods = goodsList.get(i);
			GeoPoint point = getGoodsGeoPoints(goods);
			if(point != null){
				list.add(new OverlayItem(point, goods.getName(), i+""));
			}
		}
		pointOverlay.addOverlayList(list);
	}

	// 通过GPS获取当前设备的位置*********************************
	public Location getMyLocaiton() {
		return BaseActivity.getLocation(this);
	}


	// 获取商品的信息
	public void getSurGoodsData(List<Integer> ids) {
		goodsList.clear();
		if(ids != null){
			List<Goods> list = goodsService.getDataById(ids);
			if(list != null){
				goodsList.addAll(list);
				//将商品的点添加到图层
				addGoodsLocationToOverlays(goodsList);
			}
		}
	}

	
	private class MyTask extends UserTask<String, Integer, Boolean>{
		List<Integer> ids = null,searchResultList = null;
		int type = -1;
		private final ProgressDialog dialog = new ProgressDialog(MapLocationActivity.this);
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(this.dialog.isShowing())
			{
				this.dialog.dismiss();
			}
			if(locateMyPositionType){
	            if (myLocation != null)
	            {	
	            	if(type == -2){
	            		locListener.OnAvailableLocation(myLocation);
	            	}else{
	            		GeoPoint point = new GeoPoint((int)(myLocation.getLatitude() * 1E6), (int)(myLocation.getLongitude() * 1E6));
	            		moveOverlay.addOverlay(new OverlayItem(point, getResources().getString(R.string.map_my_point), "-1"));
	            	}
	            }else{
	            	Toast.makeText(MapLocationActivity.this, getResources().getString(R.string.no_location), 3000).show();
	            }
	            locateMyPositionType = false;
			}
			if(isSetPoint){
				String s;
				if(myLocation != null){
					s = getResources().getString(R.string.map_my_point);
					if(type == 0){
						startPointEt.setText(s);
						startPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, s.length(),
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						startPoint = new GeoPoint((int)(myLocation.getLatitude()*1E6), (int)(myLocation.getLongitude()*1E6)); 
					}else if(type == 1){
						endPointEt.setText(s);
						endPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, s.length(),
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						endPoint = new GeoPoint((int)(myLocation.getLatitude()*1E6), (int)(myLocation.getLongitude()*1E6));
					}
				}else{
					s = getResources().getString(R.string.map_my_point_fail);
					Toast.makeText(MapLocationActivity.this, getResources().getString(R.string.no_location), 3000).show();
					if(type == 0){
						startPointEt.setText(s);
						startPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, s.length(),
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}else if(type == 1){
						endPointEt.setText(s);
						endPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, s.length(),
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}
				isSetPoint = false;
			}
			if(type == 2){
				if(route != null){
					lineOverlay.clear();
					lineOverlay.addOverlayList(convertToOverlayItem(route));
					controller.animateTo(startPoint);
				}else{
					Toast.makeText(MapLocationActivity.this, getResources().getString(R.string.map_route_fail), 3000).show();
				}
			}
			if(isSearchGoods && searchResultList != null){
				Intent intent = new Intent(getApplicationContext(), ShowGoodsListActivity.class);
				String[] str = new String[searchResultList.size()];
				for(int i=0; i<searchResultList.size(); i++){
					str[i] = searchResultList.get(i).toString();
				}
				intent.putExtra("title", getResources().getString(R.string.search));
				intent.putExtra("catagory", -1);
				intent.putExtra("searchResult", str);
				startActivity(intent);
				isSearchGoods = false;
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			type = -1;
			if(isSearchRoute){
				dialog.setMessage(getResources().getString(R.string.map_search_route));
				dialog.show();
				isSearchRoute = false;
			}else if(locateMyPositionType || isSetPoint){
				dialog.setMessage(getResources().getString(R.string.map_get_location_now));
				dialog.show();
			}else if(isSearchGoods){
				dialog.setMessage(getResources().getString(R.string.searchgoods_find_now));
				dialog.show();
			}else{
				dialog.setMessage(getResources().getString(R.string.map_get_data_now));
				dialog.show();
			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			type = Integer.valueOf(params[1]);
			Long t = Calendar.getInstance().getTimeInMillis();
			if(params[0].equals(TASK_MY_LOCATION)){
				//Wait 10 seconds to see if we can get a location from either network or GPS, otherwise stop
	            while (!hasLocation && Calendar.getInstance().getTimeInMillis() - t < 15000) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            };
	            return true;
			}else if(params[0].equals(TASK_REQUEST)){
				ids = getData(params[2], params[3], params[4]);
				if(ids != null){
					publishProgress(0);
				}else{
					publishProgress(1);
				}
			}else if(params[0].equals(TASK_REQUEST_ROUTE)){
				//请求路径
				RequestRoute rr = new RequestRoute(MapLocationActivity.this, startPoint.getLatitudeE6()/1E6, startPoint.getLongitudeE6()/1E6, endPoint.getLatitudeE6()/1E6, endPoint.getLongitudeE6()/1E6);
				route = rr.getPointList(travelMode, "cn");
				while (route == null && Calendar.getInstance().getTimeInMillis() - t < 15000) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            };
			}else if(params[0].equals(TASK_SEARCH)){
				searchResultList = getData(params[2]);
				if(searchResultList != null){
					publishProgress(2);
				}else{
					publishProgress(3);
				}
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if(values[0] == 0){
				getSurGoodsData(ids);
			}else if(values[0] == 1){
				dialog.dismiss();
				Toast.makeText(MapLocationActivity.this, getResources().getString(R.string.map_sur_no_goods), 3000).show();
				cancel(true);
			}else if(values[0] == 3){
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.searchgoods_find_error), 3000).show();
				cancel(true);
			}
		}
		
	}
	
	private List<Integer> getData(String latitude, String longitude, String distance){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		List<Integer> list = null;
		try {
			is = httpClient.doHttpPost2(URLConstant.GOODS_DISTANCE, new BasicNameValuePair("longitude",  longitude),
					new BasicNameValuePair("latitude",  latitude),new BasicNameValuePair("distance",  distance));
			list = parser.parserToList(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "parser error");
		}finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		int total = Integer.valueOf(parser.getTotalNum());
		if(total == 0){
			return null;
		}
		return list;
	}
	
	public LocationResult locationResult = new LocationResult()
    {
        @Override
        public void gotLocation(final Location location)
        {
        	if(location != null){
        		myLocation = new Location(location);
        		hasLocation = true;
        	}else{
        		System.out.println("LocationResult() location is null");
        	}
        }
    };

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id){
			case SELECT_DISTANCE_DIALOG:
				buildDialog1();
				break;
			case SELECT_GET_POINT_DIALOG:
				buildDialog2(getResources().getString(R.string.map_set_start_point), 0);
				break;
			case SELECT_GET_POINT_DIALOG2:
				buildDialog2(getResources().getString(R.string.map_set_end_point), 1);
				break;
		}
		return super.onCreateDialog(id);
	}
	
	private Dialog buildDialog1(){
		final CharSequence[] items = this.getResources().getStringArray(R.array.map_distance);
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle(getResources().getString(R.string.map_distance_select));
		ab.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String[] str = getResources().getStringArray(R.array.map_distance);
				int distance = 100;
				if(which < 4){
					distance = Integer.valueOf(str[which]);
				}
				else if(which == 4){
					distance = 1000;//如果是所有的话，默认是1000m
				}
				if(myLocation != null){
					new MyTask().execute(TASK_REQUEST, "-1", myLocation.getLatitude()+"", myLocation.getLongitude()+"", distance+"");
				}else{
					locateMyPositionType = true;
					getMyLocation(-2);
				}
			}
		});
		ab.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		ab.show();
		return ab.create();
	}
	
	public Dialog buildDialog2(String pointType, final int type){
		final CharSequence[] items = this.getResources().getStringArray(R.array.map_set_point_type);
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle(pointType);
		ab.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch(which){
					case 0:
						//设置我的位置
						isSetPoint = true;
						if(myLocation != null){
							String my = getResources().getString(R.string.map_my_point);
							if(type == 0){//起点
								startPointEt.setText(my);
								startPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, my.length(),
										Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
								startPoint = new GeoPoint((int)(myLocation.getLatitude()*1E6), (int)(myLocation.getLongitude()*1E6));
							}else{//终点
								endPointEt.setText(my);
								endPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, my.length(),
										Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
								endPoint = new GeoPoint((int)(myLocation.getLatitude()*1E6), (int)(myLocation.getLongitude()*1E6));
							}
						}else{
							if(type == 0){
								getMyLocation(0);
							}else{
								getMyLocation(1);
							}
						}
						break;
					case 1:
						//从地图选取
						final String s = getResources().getString(R.string.map_set_from_map);
						popupWindow.dismiss();
						moveOverlay.setSelectItem(true);
						if(type == 0){
							moveOverlay.setPointType(getResources().getString(R.string.map_set_start_from_map));
							startPointEt.setText(s);
							startPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, s.length(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}else{
							moveOverlay.setPointType(getResources().getString(R.string.map_set_end_from_map));
							endPointEt.setText(s);
							endPointEt.getText().setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, s.length(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
						moveOverlay.setListener(new OnSelectPointListener<GeoPoint>() {
							
							@Override
							public void onClicked(GeoPoint t) {
								// TODO Auto-generated method stub
								if(type == 0){
									startPoint = t;
								}else{
									endPoint = t;
								}
								openPopupwin();
							}
						});
						break;
				}
			}
		});
		ab.show();
		return ab.create();
	}
    
	private void initPopupView(){
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.map_pop_top, null, true);
		startPointBt = (Button) menuView.findViewById(R.id.map_start_point_bt);
		startPointBt.setOnClickListener(this);
		endPointBt = (Button) menuView.findViewById(R.id.map_end_point_bt);
		endPointBt.setOnClickListener(this);
		modeWalkBt = (Button) menuView.findViewById(R.id.map_mode_walk_bt);
		modeWalkBt.setOnClickListener(this);
		modeTransitBt = (Button) menuView.findViewById(R.id.map_mode_transit_bt);
		modeTransitBt.setOnClickListener(this);
		modeDrivingBt = (Button) menuView.findViewById(R.id.map_mode_driving_bt);
		modeDrivingBt.setOnClickListener(this);
		searchBt = (ImageButton) menuView.findViewById(R.id.map_search_bt);
		searchBt.setOnClickListener(this);
		startPointEt = (EditText) menuView.findViewById(R.id.map_start_point_et);
		endPointEt = (EditText) menuView.findViewById(R.id.map_end_point_et);
	}
	
	private final void openPopupwin() {
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.map_popupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.map_location_view), Gravity.TOP, 0, 0);
		popupWindow.update();
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.map_show_my_location_button:
			//获取我的位置myPoint
			locateMyPositionType = true;
			getMyLocation(-1);
			break;
		case R.id.map_pop_view_more_details_button:
			int n = Integer.parseInt(focus.getSnippet());
			Intent intent = new Intent(MapLocationActivity.this,DetailGoodsActivity.class);
			intent.putExtra("goods", goodsList.get(n));
			startActivity(intent);
			popView.setVisibility(View.INVISIBLE);
			break;
		case R.id.map_search_button:
			isSearchGoods = true;
			String currentKey = searchEt.getText().toString();
			if(currentKey.equals("")){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.searchgoods_input_error), 3000).show();
			}else{
				new MyTask().execute(TASK_SEARCH, "-1", currentKey);
			}
			break;
		case R.id.map_show_route_button:
			//显示路线
			openPopupwin();
			break;
		//下面是显示路线相关按钮
		case R.id.map_start_point_bt:
			showDialog(SELECT_GET_POINT_DIALOG);
			break;
		case R.id.map_end_point_bt:
			showDialog(SELECT_GET_POINT_DIALOG2);
			break;
		case R.id.map_mode_walk_bt:
			modeWalkBt.setBackgroundResource(R.drawable.mode_walk_on);
			modeDrivingBt.setBackgroundResource(R.drawable.mode_driving_icon);
			modeTransitBt.setBackgroundResource(R.drawable.mode_transit_icon);
			travelMode = "walking";
			break;
		case R.id.map_mode_driving_bt:
			modeDrivingBt.setBackgroundResource(R.drawable.mode_driving_on);
			modeWalkBt.setBackgroundResource(R.drawable.mode_walk_icon);
			modeTransitBt.setBackgroundResource(R.drawable.mode_transit_icon);
			travelMode = "driving";
			break;
		case R.id.map_mode_transit_bt:
			modeDrivingBt.setBackgroundResource(R.drawable.mode_driving_icon);
			modeWalkBt.setBackgroundResource(R.drawable.mode_walk_icon);
			modeTransitBt.setBackgroundResource(R.drawable.mode_transit_on);
			travelMode = "transit";
			break;
		case R.id.map_search_bt:
			if(startPoint == null){
				Toast.makeText(this, getResources().getString(R.string.map_info_not_startPoint), 3000).show();
				return;
			}
			if(endPoint == null){
				Toast.makeText(this, getResources().getString(R.string.map_info_not_endPoint), 3000).show();
				return;
			}
			if(travelMode == null){
				Toast.makeText(this, getResources().getString(R.string.map_info_not_mode), 3000).show();
				return;
			}
			if(travelMode.equals("transit")){
				Toast.makeText(this, getResources().getString(R.string.map_transit_not_support), 3000).show();
			}
			popupWindow.dismiss();
			moveOverlay.setSelectItem(false);
			isSearchRoute = true;
			new MyTask().execute(TASK_REQUEST_ROUTE, 2+"");
			break;
		}
	}
	
	private List<OverlayItem> convertToOverlayItem(List<GeoPoint> list){
		List<OverlayItem> lo = new ArrayList<OverlayItem>();
		int num = list.size();
		for(int i=0; i<num; i++){
			if(i == 0){
				lo.add(new OverlayItem(list.get(0), "start", "-1"));
			}
			else if(i == num-1){
				lo.add(new OverlayItem(list.get(num-1), "end", "-2"));
			}else{
				lo.add(new OverlayItem(list.get(i), "point", "0"));
			}
		}
		return lo;
	}
	
	private List<Integer> getData(String key){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		List<Integer> result = null;
		try {
			key = URLEncoder.encode(key, "UTF-8");
			is = httpClient.doHttpPost2(URLConstant.SEARCH, new BasicNameValuePair("key", key));
//			is = getAssets().open("search.xml");
			result = parser.parserToList(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("parser", "searchgoods parser error");
			e.printStackTrace();
			return null;
		}finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		int total = Integer.valueOf(parser.getTotalNum());
		if(total == 0)
			return null;
		return result;
	}
	
	private interface LocationAvailableListener<T>{
		void OnAvailableLocation(T t);
	}

	public void setLocListener(LocationAvailableListener<Location> locListener) {
		this.locListener = locListener;
	}
}
