package cm.exchange.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cm.exchange.R;
import cm.exchange.entity.Goods;
import cm.exchange.util.DateUtil;

/**
 * 
 * @author qh
 *
 */
public class MainPageAdapter extends BaseAdapter{
	private Context mContext;
	private List<Goods> mData;
	private LayoutInflater mInflater;
	public MainPageAdapter(Context context, List<Goods> mData) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mData = mData;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.mainpage_listview_listitem, null);
			holder = new Holder();
			holder.data = (TextView) convertView.findViewById(R.id.mainpage_listview_time);
			holder.title = (TextView) convertView.findViewById(R.id.mainpage_listview_title);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.data.setText(DateUtil.formatData(mData.get(position).getCreateData()));
		holder.title.setText(mData.get(position).getDescript());
		return convertView;
	}
	
	static class Holder{
		TextView title;
		TextView data;
	}
}
