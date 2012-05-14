package com.itcast.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.itcast.logic.IWeiboActivity;
import com.itcast.logic.MainService;

public class MoreActivity extends Activity implements IWeiboActivity{
	private ListView listView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moreitems);
		List<Integer> list = new ArrayList<Integer>();
			list.add(R.string.more_about_us);
			list.add(R.string.more_emial);
			list.add(R.string.more_jianyi);
		    list.add(R.string.more_website);
		listView = (ListView)findViewById(R.id.moreItemsListView);
		listView.setAdapter(new LoginListAdapter(this,list));
		listView.setOnItemClickListener(
		 new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==3)
				{
					//官方网站
					Intent it=new Intent(Intent.ACTION_VIEW
							,Uri.parse("http://t.sina.com.cn"));
					startActivity(it);
				}
			}
			 
		 }		
		); 
	
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
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		
	}

}
