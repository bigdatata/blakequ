package com.ExPanel;

import java.util.ArrayList;
import java.util.HashMap;

import com.ExPanel.Panel.PanelClosedEvent;
import com.ExPanel.Panel.PanelOpenedEvent;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class main extends Activity {
	public Panel panel;
	public LinearLayout container;
	public GridView gridview;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setTitle("“可动态布局”的抽屉组件之完整篇-----hellogv");
		gridview = (GridView) findViewById(R.id.gridview);
		container=(LinearLayout)findViewById(R.id.container);
		panel=new Panel(this,gridview,200,LayoutParams.FILL_PARENT);
		container.addView(panel);//加入Panel控件
		
		//新建测试组件
		TextView tvTest=new TextView(this);
		tvTest.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		tvTest.setText("测试组件，红字白底");
		tvTest.setTextColor(Color.RED);
		tvTest.setBackgroundColor(Color.WHITE);
		//加入到Panel里面
		panel.fillPanelContainer(tvTest);
		
		panel.setPanelClosedEvent(panelClosedEvent);
		panel.setPanelOpenedEvent(panelOpenedEvent);
		
		//往GridView填充测试数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.icon);
			map.put("ItemText", "NO." + String.valueOf(i));
			lstImageItem.add(map);
		}

		SimpleAdapter saImageItems = new SimpleAdapter(this, 
				lstImageItem,
				R.layout.item, 
				new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		gridview.setAdapter(saImageItems);
		gridview.setOnItemClickListener(new ItemClickListener());
		
	}

	PanelClosedEvent panelClosedEvent =new PanelClosedEvent(){

		@Override
		public void onPanelClosed(View panel) {
			Log.e("panelClosedEvent","panelClosedEvent");
		}
		
	};
	
	PanelOpenedEvent panelOpenedEvent =new PanelOpenedEvent(){

		@Override
		public void onPanelOpened(View panel) {
			Log.e("panelOpenedEvent","panelOpenedEvent");
		}
		
	};
	
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			setTitle((String) item.get("ItemText"));
		}

	}
}