package cm.exchange.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cm.exchange.R;
import cm.exchange.entity.Goods;
import cm.exchange.ui.map.MapLocationActivity;

/**
 * this class is a adapter 
 * using for add data to list
 * @author qh
 *
 */
public class MainGoodsListAdapter extends BaseAdapter {

	private Context mContext;
	private List<Goods> mData;
	private LayoutInflater mInflater;
	ListHolder holder;
	public MainGoodsListAdapter(Context context, List<Goods> mData){
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
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		if(row == null){
			row = mInflater.inflate(R.layout.main_goods_listitem, null);
			holder = new ListHolder(row);
			row.setTag(holder);
		}else{
			holder = (ListHolder) row.getTag(); 
		}
		setListContent(position, holder);
		return row;
	}
	
	private void setListContent(int position, final ListHolder holder) {
		// TODO Auto-generated method stub
		final Goods goods = mData.get(position);
		if(goods != null){
			holder.getContent().setText(goods.getDescript());
			holder.getContent().setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					double longitude = Double.valueOf(goods.getLongitude());
					double latitude = Double.valueOf(goods.getLatitude());
					Intent intent = new Intent(mContext, MapLocationActivity.class);
					intent.putExtra("longitudes", new Double[]{longitude});
					intent.putExtra("latitudes", new Double[]{latitude});
					mContext.startActivity(intent);
				}
			});
			holder.getContent().setFocusable(false);
			holder.getName().setText(goods.getName());
			holder.getLength().setText(goods.getDistance()+"m");
			holder.getNum().setText(goods.getCommentNum()+"");
			holder.getPrice().setText(goods.getPrice()+"圆");
		}
	}

	class ListHolder{
		View baseView;
		TextView name, content, price, length, num;
		
		public ListHolder(View baseView){
			this.baseView = baseView;
		}

		public TextView getName() {
			if(name == null){
				name = (TextView)baseView.findViewById(R.id.main_listitem_name);
			}
			return name;
		}

		public TextView getContent() {
			if(content == null){
				content = (TextView)baseView.findViewById(R.id.main_listitem_content);
			}
			return content;
		}

		public TextView getPrice() {
			if(price == null){
				price = (TextView)baseView.findViewById(R.id.main_listitem_price);
			}
			return price;
		}

		public TextView getLength() {
			if(length == null){
				length = (TextView)baseView.findViewById(R.id.main_listitem_length);
			}
			return length;
		}

		public TextView getNum() {
			if(num == null){
				num = (TextView)baseView.findViewById(R.id.main_listitem_comment_num);
			}
			return num;
		}

	}

}
