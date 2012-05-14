package com.shaccp.ui;

import java.util.HashMap;
import java.util.List;

import weibo4andriod.Status;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shaccp.logic.IWeiboActivity;
import com.shaccp.logic.MainService;
import com.shaccp.logic.Task;
import com.shaccp.util.NetUtil;

public class Home extends Activity implements IWeiboActivity {
	public static final int REFRESH_WEIBO = 1;
	public static final int REFRESH_ICON = 2;

	View process;
	ListView lv;
	Button btnNew;
	Button btnRef;
	public ListView allStatus;

	public int nowpage = 1;
	public int pagesize = 5;

	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		allStatus = (ListView) this.findViewById(R.id.freelook_listview);
		lv = (ListView) findViewById(R.id.freelook_listview);
		MainService.allActivity.add(this);
		init();
		allStatus = (ListView) this.findViewById(R.id.freelook_listview);
		registerForContextMenu(allStatus);

		allStatus.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (arg3 == -1) {
					nowpage++;
					HashMap para = new HashMap();
					para.put("nowpage", new Integer(nowpage));
					para.put("pagesize", new Integer(pagesize));
					Task task = new Task(Task.TASK_GET_TIMELINE, para);
					MainService.allTask.add(task);
				}

			}

		});
		process = findViewById(R.id.progress);

		tv = (TextView) findViewById(R.id.textView);
		tv.setText(MainService.nowUser.getScreenName());
		btnNew = (Button) findViewById(R.id.title_bt_left);
		btnRef = (Button) findViewById(R.id.title_bt_right);

		btnNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent it = new Intent(Home.this, NewWeibo.class);

				startActivity(it);

			}
		});

		MainService.allActivity.add(this);

		btnRef.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				process.setVisibility(View.VISIBLE);
				Task t = new Task(Task.TASK_GET_TIMELINE, null);
				MainService.newTask(t);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		// "设置" "账号" "官方" "意见" "关于" "退出"
		menu.add(1, 1, 0, "设置").setIcon(R.id.setting);
		/*
		 * menu.add(1, 2, 1, "账号").setIcon(R.id.main_switchuser); menu.add(1, 3,
		 * 2, "官方").setIcon(R.id.setting); menu.add(2, 4, 3,
		 * "意见").setIcon(R.id.setting); menu.add(2, 5, 4,
		 * "关于").setIcon(R.id.about); menu.add(2, 6, 5,
		 * "退出").setIcon(R.id.setting);
		 */
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;

		case 4:

			break;
		case 5:

			break;
		case 6:

			break;

		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		HashMap para = new HashMap();
		para.put("nowpage", new Integer(nowpage));
		para.put("pagesize", new Integer(pagesize));
		Task task = new Task(Task.TASK_GET_TIMELINE, para);
		MainService.allTask.add(task);

	}

	@Override
	public void refresh(Object... args) {
		// TODO Auto-generated method stub

		switch (((Integer) args[0]).intValue()) {

		case REFRESH_WEIBO:
			if (nowpage == 1) {
				process.setVisibility(View.GONE);
				MyAdapter ad = new MyAdapter(this, (List<Status>) args[1]);
				lv.setAdapter(ad);
				// ad.notifyDataSetChanged();
			} else {
				((MyAdapter) allStatus.getAdapter())
						.addMoreDate((List<Status>) args[1]);

			}

			break;

		case REFRESH_ICON:

			((MyAdapter) allStatus.getAdapter()).notifyDataSetChanged();

			break;
		}

	}

}

class MyAdapter extends BaseAdapter {

	public List<Status> alls;
	public Context conts;

	public MyAdapter(Context con, List<Status> st) {
		conts = con;
		alls = st;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alls.size() + 2;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		// return alls.get(index).getId();

		if (index == 0) {
			return 0;
		} else if (index > 0 && index < this.getCount() - 1) {
			return alls.get(index - 1).getId();
		} else {
			return -1;
		}
	}

	public void addMoreDate(List<Status> moreDate) {
		this.alls.addAll(moreDate);
		this.notifyDataSetChanged();
	}

	// 定义静态类用于获取处理列表中每个条目数据的更新
	private static class ViewHolder {
		ImageView ivItemPortrait;// 头像 有默认值
		TextView tvItemName;// 昵称
		ImageView ivItemV;// 新浪认证 默认gone
		TextView tvItemDate;// 时间
		ImageView ivItemPic;// 时间图片 不用修改
		TextView tvItemContent;// 内容
		ImageView contentPic;// 自己增加的内容图片显示的imgView
		View subLayout;// 回复默认gone
		TextView tvItemSubContent;// 回复内容 subLayout显示才可以显示
		ImageView subContentPic;// 自己增加的主要显示回复内容的图片。subLayout显示才可以显示
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (position == 0) {
			View view = LayoutInflater.from(conts).inflate(
					R.layout.list_moreitems, null);
			TextView tv = (TextView) view.findViewById(R.id.textView);
			tv.setText("刷新");
			return view;
		} else if (position == getCount() - 1) {
			View view = LayoutInflater.from(conts).inflate(
					R.layout.list_moreitems, null);
			TextView tv = (TextView) view.findViewById(R.id.textView);
			tv.setText("更多");
			return view;
		}

		View statusView = null;

		if (statusView != null
				&& (convertView.findViewById(R.id.ivItemPortrait) != null)) {

			statusView = (TextView) convertView;
		} else {
			statusView = LayoutInflater.from(conts).inflate(R.layout.itemview,
					null);
		}

		ViewHolder holder = null;
		holder = new ViewHolder();
		holder.ivItemPortrait = (ImageView) statusView
				.findViewById(R.id.ivItemPortrait);
		holder.tvItemName = (TextView) statusView.findViewById(R.id.tvItemName);
		holder.tvItemContent = (TextView) statusView
				.findViewById(R.id.tvItemContent);
		holder.tvItemDate = (TextView)statusView.findViewById(R.id.tvItemDate);

		Log.d("listview", "listv:" + position);

		// 设定昵称
		holder.tvItemName.setText(alls.get(position - 1).getUser()
				.getScreenName());
		// 设定内容
		holder.tvItemContent.setText(alls.get(position - 1).getText());

		// 获取头像
		if (MainService.alluserIcon.get(alls.get(position - 1).getUser()
				.getId()) != null) {

		}
		holder.ivItemPortrait.setImageDrawable(MainService.alluserIcon
				.get(alls.get(position - 1).getUser().getId()));
	

		
		holder.tvItemDate.setText(NetUtil.getTimeDiff(alls.get(position-1).getCreatedAt()));
		
		return statusView;
	}

}
