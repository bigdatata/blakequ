package cm.exchange.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cm.exchange.R;
import cm.exchange.adapter.MainPageAdapter;
import cm.exchange.db.GoodsService;
import cm.exchange.entity.Comment;
import cm.exchange.entity.Goods;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.CommentListParser;
import cm.exchange.parser.GoodsListParser;
import cm.exchange.parser.ParserListener;
import cm.exchange.parser.ShopAndAttenParser;
import cm.exchange.ui.map.MapLocationActivity;
import cm.exchange.ui.mygoods.MyGoodsMainActivity;
import cm.exchange.ui.searchgoods.SearchGoods;
import cm.exchange.ui.sellgoods.SellGoods;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.UserTask;

/**
 * 在成功之后需要向服务器请求是否有最新关于用户的回复商品信息，然后一notification方式提醒
 * @author qh
 *
 */
public class MainPageActivity extends BaseActivity {

	GridView gridView;
	ListView listView;
	PopupWindow popupWindow;
	Integer[] gridImages = new Integer[] { R.drawable.main_publish,
			R.drawable.main_search, R.drawable.main_map, R.drawable.main_shopping };
	GoodsService goodsDB = null;
	List<Goods> goodsList = null, addFromDB = null;
	GoodsListParser parser = null;
	MainPageAdapter adapter = null;
	private boolean check = true;
	private int totalNum = 0;
	private static final int LIST_NUM = 7;
	ShopAndAttenParser notifiParser = null;
	private static final int NOTIFI_ID = 1;
	private static final String TASK_GOODS = "1";
	private static final String TASK_NOTIFICATION = "2";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		initView();
		goodsDB = new GoodsService(this);
		goodsDB.open();
		addFromDB = goodsDB.getDataSortByHot(LIST_NUM);
		goodsList = new ArrayList<Goods>(LIST_NUM);
		parser = new GoodsListParser();
		notifiParser = new ShopAndAttenParser();
		new UpdateTask().execute(TASK_NOTIFICATION);
		new UpdateTask().execute(TASK_GOODS);
	}

	protected void initView() {
		gridView = (GridView) findViewById(R.id.mainpage_gridview);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent;
				switch (position) {
				case 0:
					intent = new Intent(getBaseContext(), SellGoods.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(getBaseContext(), SearchGoods.class);
					startActivity(intent);

					break;
				case 2:
					intent = new Intent(getBaseContext(),
							MapLocationActivity.class);
					startActivity(intent);

					break;
				case 3:
					intent = new Intent(getBaseContext(),
							MyGoodsMainActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
		gridView.setAdapter(new SimpleAdapter(this.getBaseContext(), fillMap(),
				R.layout.mainpage_gridview_listitem, new String[] { "image",
						"title" }, new int[] { R.id.mainpage_gridview_image,
						R.id.mainpage_gridview_title }));
		listView = (ListView) findViewById(R.id.mainpage_list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Goods goods = goodsList.get(position);
				Intent intent = new Intent(MainPageActivity.this,
						DetailGoodsActivity.class);
				intent.putExtra("goods", goods);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(goodsDB.getDatabase().isOpen()){
			goodsDB.close();
		}
	}

	/**
	 * fill the image and text to gridview
	 * 
	 * @return
	 */
	private List<Map<String, Object>> fillMap() {
		List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
		String[] titles = getResources().getStringArray(R.array.mainpage_item);
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", gridImages[i]);
			map.put("title", titles[i]);
			lm.add(map);
		}
		return lm;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			promptExitApp();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainpage_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.mainpage_menu_change_user:
			Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
			intent.putExtra("changeUser", 1);
			startActivity(intent);
			break;
		case R.id.mainpage_menu_exit:
			promptExitApp();
			break;
		case R.id.mainpage_menu_help:
			openPopupwin(getResources().getString(
					R.string.mainpage_menu_help_content));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void openPopupwin(String str) {
		LayoutInflater mLayoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.mainpage_popup, null, true);

		TextView text = (TextView) menuView
				.findViewById(R.id.mainpage_popup_text);
		text.setText(str);
		text.setClickable(true);
		text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});

		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.mainpage_layout),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		popupWindow.update();
	}

	private class UpdateTask extends UserTask<String, Object, Boolean> implements
			ParserListener<Goods> {
		List<Integer> notifiMessage;
		int type = 0;
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(params[0].equals(TASK_GOODS)){
				getData(parser, this);
			}else if(params[0].equals(TASK_NOTIFICATION)){
				type = -1;
				notifiMessage = getMessageNum();
				if(notifiMessage != null){
					publishProgress(notifiMessage);
				}
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
//				Toast.makeText(getBaseContext(),
//						getResources().getString(R.string.main_no_new_data),
//						3000).show();
			if(values[0] instanceof Goods){
				if(! (goodsList.size()>=LIST_NUM)){
					goodsList.add((Goods) values[0]);
					freshListView();
				}
			}else{
				showMessage(notifiMessage);
			}
		}
		
		

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(totalNum < 5 && type == 0){
//				List<Goods> list = goodsDB.getDataSortByHot(LIST_NUM - totalNum);
				if(addFromDB != null){
					for(int i=0; addFromDB.get(i) != null && i<LIST_NUM-totalNum; i++){
						goodsList.add(addFromDB.get(i));
					}
					freshListView();
				}
			}
		}

		@Override
		public void onParserOverListener(Goods t) {
			// TODO Auto-generated method stub
			try{
				if (check) {
					totalNum = Integer.valueOf(parser.getTotalNum());
					if (totalNum == 0) {
						cancel(true);
					}
					check = false;
				}
			}catch(NumberFormatException  e){
				e.printStackTrace();
				totalNum = 0;
			}
			if(totalNum != 0){
				publishProgress(t);
				goodsDB.update(t);
			}
		}

	}

	private void getData(GoodsListParser parser, ParserListener<Goods> listener) {
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		try {
			is = httpClient.doHttpGet2(URLConstant.GOODSINFO);
//			is = getAssets().open("goods.xml");
			parser.parse(is, listener);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("adapter", "mainpageActivity open inputstream error");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("adapter", "mainpageActivity parser error");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void freshListView() {
		if (adapter == null) {
			adapter = new MainPageAdapter(this, goodsList);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}
	
	private void showMessage(List<Integer> list){
		CharSequence tickerText = getResources().getString(R.string.main_notifi_new_message);              // ticker-text

		Intent notificationIntent = new Intent(this, LeaveNoteActivity.class);
		notificationIntent.putExtra("goodsID", list.get(0));
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		Notification notification = new Notification(R.drawable.icon, tickerText, System.currentTimeMillis());
		CharSequence contentText = getResources().getString(R.string.main_notifi_num1)+list.size()+getResources().getString(R.string.main_notifi_num2);      // expanded message text
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.mainpage_notification);
		contentView.setImageViewResource(R.id.mainpage_notifi_image, R.drawable.icon);
		contentView.setTextViewText(R.id.mainpage_notifi_text, contentText);
		notification.contentView = contentView;
		notification.contentIntent = contentIntent;
		notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.mac);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;//auto cancel when clicked
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFI_ID, notification);
	}
	
	private List<Integer> getMessageNum(){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		List<Integer> list = null;
		try {
//			is = httpClient.doHttpPost2(URLConstant.MyCOMMENTINFO);
			is = getAssets().open("mycomment.xml");
			list = notifiParser.parserToList(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("notification parser", "MainPageActivity  parser error");
		}finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
