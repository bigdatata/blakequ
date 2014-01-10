package cm.exchange.ui.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import cm.exchange.R;
import cm.exchange.entity.Goods;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/*
 * @author chenshaoyang
 */
public class PointOverlay extends ItemizedOverlay<OverlayItem> {

	private Drawable marker, startMarker, endMarker;
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	private Context context;

	public PointOverlay(Drawable marker, Context context){
		super(marker);
		this.marker = marker;
		this.context = context;
		populate();
	}

	//通过传入商品的信息，依次为其 添加OverlayItem对象
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
		return locations.get(i);
	}

	@Override
	public void draw(Canvas arg0, MapView arg1, boolean arg2) {
		super.draw(arg0, arg1, arg2);
		boundCenterBottom(marker);
	}
	

	@Override
	public int size() {
		return locations.size();
	}

}
