package cm.exchange.ui.mygoods;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cm.exchange.R;
import cm.exchange.adapter.MainGoodsListAdapter;
import cm.exchange.db.GoodsService;
import cm.exchange.entity.Goods;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.ShopAndAttenParser;
import cm.exchange.ui.DetailGoodsActivity;
import cm.exchange.ui.ExchangeApplication;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.UserTask;

/**
 * 应该增加取消关注
 * @author Administrator
 *
 */
public class MyGoodsAttention extends BaseActivity {

	ListView listView;
	List<Goods> goodsList = null;
	List<Integer> requestList = null;
	MainGoodsListAdapter adapter = null;
	GoodsService goodsDB = null;
	ShopAndAttenParser parser = null;
	ExchangeApplication app = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygoods);
		parser = new ShopAndAttenParser();
		goodsList = new LinkedList<Goods>();
		goodsDB = new GoodsService(this);
		requestList = new LinkedList<Integer>();
		app = (ExchangeApplication) getApplicationContext();

		listView = (ListView) findViewById(R.id.mygoods_list_listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Goods goods = (Goods) parent.getItemAtPosition(position);
				Intent intent = new Intent(MyGoodsAttention.this,
						DetailGoodsActivity.class);
				intent.putExtra("goods", goods);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		goodsDB.open();
		if(adapter == null){
			new MyTask().execute(app.getUid());
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(goodsDB.getDatabase().isOpen())
			goodsDB.close();
	}

	private void freshListView() {
		if (adapter == null) {
			adapter = new MainGoodsListAdapter(this, goodsList);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onRightButtonClicked() {
		// TODO Auto-generated method stub
		super.onRightButtonClicked();
		Toast.makeText(this, getResources().getString(R.string.mygoods_new_data), 3000).show();
	}
	
	private class MyTask extends UserTask<String, Boolean, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			requestList = getData(params[0]);
			if(requestList != null){
				publishProgress(true);
			}else{
				publishProgress(false);
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if(values[0]){
				List<Goods> temp = goodsDB.getDataById(requestList);
				if(temp != null){
					goodsList.addAll(temp);
				}
				freshListView();
			}else{
				Toast.makeText(MyGoodsAttention.this, getResources().getString(R.string.mygoods_no_attention), 3000).show();
			}
		}
		
	}
	
	private List<Integer> getData(String userID){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		List<Integer> list = null;
		try {
			is = httpClient.doHttpPost2(URLConstant.USER_ATTENTION, new BasicNameValuePair("userID",  userID));
			list = parser.parserToList(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		try{
			int total = Integer.valueOf(parser.getTotalNum());
			if(total == 0){
				return null;
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			list = null;
		}
		return list;
	}

}
