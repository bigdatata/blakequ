package cm.exchange.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import cm.exchange.R;
import cm.exchange.adapter.MainGoodsListAdapter;
import cm.exchange.db.GoodsService;
import cm.exchange.entity.Goods;
import cm.exchange.util.BaseActivity;

/**
 * 
 * @author qh
 *
 */
public class ShowGoodsListActivity extends BaseActivity{

	ListView listView;
	View viewTitle;
	ImageButton left, right;
	Spinner sortByPrice, sortByDistance;
	TextView title;
	String titleText;
	GoodsService goodsDB = null;
	List<Goods> goodsList = null;
	MainGoodsListAdapter adapter = null;
	int catagory,distance,sortPrice;
	String[] searchResult;
	@Override
	public void initTitle() {
		// TODO Auto-generated method stub
		super.initTitle();
		left.setImageResource(R.drawable.main_title_back_icon);
		title.setText(titleText);
		right.setImageResource(R.drawable.refresh_icon);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
			case R.id.main_title_left:
				finish();
				break;
			case R.id.main_title_right:
				List<Goods> list = null;
				if(searchResult == null){
					list = goodsDB.getLimitDataByPriceAndDistance(catagory, distance, sortPrice);
				}else{
					list = goodsDB.getLimitDataByPriceAndDistance(distance, sortPrice, searchResult);
				}
				if(list != null){
					goodsList.clear();
					goodsList.addAll(list);
				}else{
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.searchgoods_fresh_error), 3000).show();
				}
				freshListView();
				break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_goods_list);
		titleText = getIntent().getStringExtra("title");
		catagory = getIntent().getIntExtra("catagory", -1);
		searchResult = getIntent().getStringArrayExtra("searchResult");
		initView();
		goodsDB = new GoodsService(this);
		goodsList = new ArrayList<Goods>();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Goods goods = (Goods) parent.getItemAtPosition(position);
				Intent intent = new Intent(ShowGoodsListActivity.this, DetailGoodsActivity.class);
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
			selectCatagory();
		}
	}
	
	private void selectCatagory(){
		List<Goods> list = null;
		if(searchResult == null){
			list = goodsDB.getDataByCatagory(catagory);
		}else{
			list = goodsDB.getDataById(searchResult);
		}
		if(list != null){
			goodsList.addAll(list);
			freshListView();
		}
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		goodsDB.close();
	}


	private void initView() {
		// TODO Auto-generated method stub
		listView = (ListView)findViewById(R.id.main_goods_list_listview);
		viewTitle = (View)findViewById(R.id.main_goods_list_title);
		left = (ImageButton)viewTitle.findViewById(R.id.main_title_left);
		left.setOnClickListener(this);
		right = (ImageButton)viewTitle.findViewById(R.id.main_title_right);
		right.setOnClickListener(this);
		title = (TextView)viewTitle.findViewById(R.id.main_title_text);
		sortByPrice = (Spinner)findViewById(R.id.main_goods_list_spinner_sort_price);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
				R.array.main_goods_list_price, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortByPrice.setAdapter(adapter);
		sortByPrice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				sortPrice = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		sortByDistance = (Spinner) findViewById(R.id.main_goods_list_spinner_sort_distance);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				getBaseContext(), R.array.main_goods_list_distance,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortByDistance.setAdapter(adapter1);
		sortByDistance.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position == 0){
					distance = -1;
				}else{
					String[] str = getResources().getStringArray(R.array.main_goods_list_distance);
					distance = Integer.valueOf(str[position]);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}

	
	private void freshListView(){
		if(adapter == null){
			adapter = new MainGoodsListAdapter(this, goodsList);
			listView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
}
