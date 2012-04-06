package com.tencent;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{
	private LayoutInflater inflate;
	private Context context;
	private List<FileInfo> listFile;
	public ListAdapter(Context context, List<FileInfo> listFile){
		this.listFile = listFile;
		this.context = context;
		inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFile.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listFile.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		Holder holder = null;
		if(view == null){
			view = inflate.inflate(R.layout.list_item, null);
			holder = new Holder();
			holder.name = (TextView) view.findViewById(R.id.file_name);
			holder.md5 = (TextView) view.findViewById(R.id.file_md5);
			view.setTag(holder);
		}else{
			holder = (Holder) view.getTag();
		}
		holder.name.setText(listFile.get(position).getName());
		holder.md5.setText("MD5:"+listFile.get(position).getMd5());
		return view;
	}
	
	class Holder{
		TextView name;
		TextView md5;
	}
	
}
