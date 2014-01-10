package cm.exchange.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cm.exchange.R;
import cm.exchange.entity.Comment;
import cm.exchange.util.CustomTextViewUtil;

public class LeaveNoteAdapter extends BaseAdapter {

	Context context;
	List<Comment> mData;
	LayoutInflater mInflater;
	public LeaveNoteAdapter(Context context, List<Comment> mData){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.mData = mData;
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
		View row = convertView;
		Holder holder = null;
		if(row == null){
			row = mInflater.inflate(R.layout.messagegoods_listitem, null);
			holder = new Holder(row);
			row.setTag(holder);
		}else{
			holder = (Holder) row.getTag();
		}
		Comment comment= mData.get(position);
		if(comment != null){
			holder.getContent().setText(comment.getContent());
			CustomTextViewUtil.textHighlight(holder.getContent(), "@", ":");
			holder.getTime().setText(comment.getTime());
			holder.getUser().setText(comment.getUsername());
		}
		return row;
	}
	

	class Holder{
		View view;
		TextView user, time, content;
		public Holder(View view){
			this.view = view;
		}
		public TextView getUser() {
			if(user == null){
				user = (TextView)view.findViewById(R.id.messagegoods_listitem_user);
			}
			return user;
		}
		public TextView getTime() {
			if(time == null){
				time = (TextView)view.findViewById(R.id.messagegoods_listitem_time);
			}
			return time;
		}
		public TextView getContent() {
			if(content == null){
				content = (TextView)view.findViewById(R.id.messagegoods_listitem_content);
			}
			return content;
		}
		
	}

}
