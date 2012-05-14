package com.itcast.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itcast.logic.MainService;

public class SearchActivity extends Activity
{
	private ListView listView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		List<Integer> list = new ArrayList<Integer>();
			list.add(R.string.search_freelook);
			list.add(R.string.search_tj);
			list.add(R.string.search_hot);
		listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(new LoginListAdapter(this,list));
//		listView.setOnItemClickListener(); 
	
	}
	public void success() {

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.d("keydown", ".................."+keyCode);
		if(keyCode==4)//如果用户按下了返回键
		{
			MainService.promptExitApp(this);
		}
		return true;
	}
}

class LoginListAdapter extends BaseAdapter{
	private Context con;
	private List<Integer> list;
	private LayoutInflater inflater;
	public LoginListAdapter(Context con,List<Integer> list){
		this.con = con;
		this.list = list;
		this.inflater = LayoutInflater.from(con);
	}
    public View getView(int position, View convertView, ViewGroup parent) { 
    	
    	if(convertView==null)
    		convertView = inflater.inflate(R.layout.login_list_item, null); 
    	TextView text = (TextView) convertView.findViewById(R.id.textView); 
    	if(position==0){
    		convertView.setBackgroundResource(R.drawable.circle_list_top);
    	}else if(position==(list.size()-1)){
    		convertView.setBackgroundResource(R.drawable.circle_list_bottom);
    	}else{
    		convertView.setBackgroundResource(R.drawable.circle_list_middle);
    	}
    	text.setText(list.get(position));
        return convertView; 
    }
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}   

}
