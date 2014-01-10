package cm.exchange.ui.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import cm.exchange.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MoveOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	Drawable balloon;
	MapView map;
	Context context;
	//下面三个都是为了从地图选取点
	boolean selectItem = false;
	String pointType;
	PopupWindow popupWindow;
	OnSelectPointListener<GeoPoint> listener;
	
	public MoveOverlay(Context context, Drawable balloon, MapView map) {
		super(balloon);
		// TODO Auto-generated constructor stub
		this.balloon = balloon;
		this.context = context;
		this.map = map;
		populate();
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
	public void draw(Canvas arg0, MapView arg1, boolean arg2) {
		super.draw(arg0, arg1, arg2);
		balloon.setBounds(-10, -balloon.getIntrinsicHeight(), balloon.getIntrinsicWidth()-10, 0);
		boundCenterBottom(balloon);
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
	public boolean onTap(GeoPoint p, MapView mapView) {
		// TODO Auto-generated method stub
		if(isSelectItem()){
			Projection projection = map.getProjection(); //投影
			Point pp = new Point(); 
        	projection.toPixels(p, pp);
        	openPopopWin(pp.x, pp.y, getPointType(), p);
		}
		//显示标记点
		return super.onTap(p, mapView);
	}

	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isSelectItem() {
		return selectItem;
	}

	public void setSelectItem(boolean selectItem) {
		this.selectItem = selectItem;
	}

	
	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	
	public void setListener(OnSelectPointListener<GeoPoint> listener) {
		this.listener = listener;
	}

	private void openPopopWin(int x, int y, String s, final GeoPoint p){
		TextView text = new TextView(context);
		text.setText(s);
		text.setTextColor(Color.BLACK);
		text.setBackgroundResource(R.drawable.map_select_item_icon);
		text.setClickable(true);
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.onClicked(p);
				popupWindow.dismiss();
			}
		});
		text.setBackgroundResource(R.drawable.map_pop_view_bg);
		popupWindow = new PopupWindow(text, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(map, Gravity.NO_GRAVITY, x-43, y+29);
		popupWindow.update();
	}
	
	
	public interface OnSelectPointListener<T>{
		void onClicked(T t);
	}
}
