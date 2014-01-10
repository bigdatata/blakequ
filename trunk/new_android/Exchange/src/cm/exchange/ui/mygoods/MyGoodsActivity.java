package cm.exchange.ui.mygoods;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cm.exchange.R;
import cm.exchange.adapter.MainGoodsListAdapter;
import cm.exchange.db.GoodsService;
import cm.exchange.entity.Goods;
import cm.exchange.ui.DetailGoodsActivity;
import cm.exchange.ui.ExchangeApplication;
import cm.exchange.util.BaseActivity;

public class MyGoodsActivity extends BaseActivity {
	ListView listView;
	List<Goods> goodsList = null;
	MainGoodsListAdapter adapter = null;
	GoodsService goodsDB = null;
	ExchangeApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygoods);
		listView = (ListView) findViewById(R.id.mygoods_list_listview);
		goodsList = new LinkedList<Goods>();
		goodsDB = new GoodsService(this);
		app = (ExchangeApplication) getApplicationContext();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Goods goods = (Goods) parent.getItemAtPosition(position);
				Intent intent = new Intent(MyGoodsActivity.this,
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
		// set data to list from database
		if (adapter == null) {
			List<Goods> l = goodsDB.getDataById(new String[]{app.getUid()});
			if(l != null){
				goodsList.addAll(l);
				freshListView();
			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(goodsDB.getDatabase().isOpen())
			goodsDB.close();
	}

	@Override
	public void onRightButtonClicked() {
		// TODO Auto-generated method stub
		super.onRightButtonClicked();
		Toast.makeText(this, getResources().getString(R.string.mygoods_new_data), 3000).show();
	}



	/**
	 * notifyDataSetChanged must use same data source(goodsList)
	 */
	private void freshListView() {
		if (adapter == null) {
			adapter = new MainGoodsListAdapter(this, goodsList);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}
}
