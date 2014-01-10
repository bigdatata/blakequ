package cm.exchange.ui.searchgoods;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cm.exchange.R;
import cm.exchange.db.HotWordService;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.ShopAndAttenParser;
import cm.exchange.ui.ShowGoodsListActivity;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.UserTask;

/**
 * 
 * @author qh
 *
 */
public class SearchGoods extends BaseActivity{

	AutoCompleteTextView autoTextView;
	Button searchButton;
	TextView hotWord1, hotWord2, moreWord;
	ListView listView;
	ArrayAdapter<String> autoAdapter;
	//需要从历史记录中读取
	String[] keyWord = null;
	int[] listImage = new int[]{R.drawable.book,R.drawable.phone,R.drawable.computer,R.drawable.house,R.drawable.cloth,
			R.drawable.cosmetics,R.drawable.watch,R.drawable.sports,R.drawable.music,R.drawable.eatting,R.drawable.ticket};
	String[] listStr;
	ShopAndAttenParser parser = null;
	List<Integer> searchResultList;
	HotWordService hotwordDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchgoods);
		listStr = getResources().getStringArray(R.array.goods_assortment);
		hotwordDB = new HotWordService(this);
		hotwordDB.open();
		parser = new ShopAndAttenParser();
		List<String> wordlist = hotwordDB.getAllData();
		if(wordlist != null){
			keyWord = new String[wordlist.size()];
			for(int i=0; i<wordlist.size(); i++){
				keyWord[i] = wordlist.get(i);
			}
		}
		initView();
	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(!hotwordDB.getDatabase().isOpen())
			hotwordDB.open();
	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(hotwordDB.getDatabase().isOpen())
			hotwordDB.close();
	}




	protected void initView() {
		// TODO Auto-generated method stub
		searchButton = (Button) findViewById(R.id.searchgoods_search_button);
		searchButton.setOnClickListener(this);
		hotWord1 = (TextView) findViewById(R.id.searchgoods_hot_word1);
		hotWord1.setFocusable(true);
		hotWord1.setText(keyWord[0]);
		hotWord1.setOnClickListener(this);
		hotWord2 = (TextView) findViewById(R.id.searchgoods_hot_word2);
		hotWord2.setFocusable(true);
		hotWord2.setText(keyWord[1]);
		hotWord2.setOnClickListener(this);
		moreWord = (TextView) findViewById(R.id.searchgoods_word_more);
		moreWord.setFocusable(true);
		moreWord.setText(keyWord[2]);
		moreWord.setOnClickListener(this);
		autoTextView = (AutoCompleteTextView) findViewById(R.id.searchgoods_search_textview);
		autoAdapter = new ArrayAdapter<String>(this,  
                android.R.layout.simple_dropdown_item_1line, keyWord);  
		autoTextView.setWidth(200);  
		autoTextView.setAdapter(autoAdapter); 
		//default=2 设置提醒长度
		autoTextView.setThreshold(1);
		listView = (ListView) findViewById(R.id.searchgoods_listview);
		listView.setAdapter(new SimpleAdapter(getBaseContext(), getAll(), 
				R.layout.searchgoods_listitem, new String[]{"image", "text"}, new int[]{R.id.searchgoods_listview_image, R.id.searchgoods_listview_text}));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String title = listStr[position];
				Intent intent = new Intent(SearchGoods.this, ShowGoodsListActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("catagory", position);
				startActivity(intent);
			}
		});
	}

	private List<Map<String, Object>> getAll() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
		for(int i=0; i<listStr.length; i++){
			Map<String ,Object> map = new HashMap<String, Object>();
			map.put("image", listImage[i]);
			map.put("text", listStr[i]);
			lm.add(map);
		}
		return lm;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
			case R.id.searchgoods_hot_word1:
				new MyTask().execute(keyWord[0]);
				break;
			case R.id.searchgoods_hot_word2:
				new MyTask().execute(keyWord[1]);
				break;
			case R.id.searchgoods_word_more:
				new MyTask().execute(keyWord[2]);
				break;
			case R.id.searchgoods_search_button:
				String currentKey = autoTextView.getText().toString();
				if(currentKey.equals("")){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.searchgoods_input_error), 3000).show();
				}else{
					hotwordDB.update(currentKey);
					new MyTask().execute(currentKey);
				}
				break;
		}
	}
	
	
	private class MyTask extends UserTask<String, Integer, Boolean>{
		private final ProgressDialog dialog = new ProgressDialog(SearchGoods.this);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(getResources().getString(R.string.searchgoods_find_now));
			dialog.show();
		}


		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			searchResultList = getData(params[0]);
			if(searchResultList != null){
				publishProgress(0);
			}else{
				publishProgress(1);
			}
			return true;
		}
		

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if(values[0] == 1){
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.searchgoods_find_error), 3000).show();
				cancel(true);
			}
		}



		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(this.dialog.isShowing())
			{
				this.dialog.dismiss();
			}
			if(searchResultList != null){
				Intent intent = new Intent(getApplicationContext(), ShowGoodsListActivity.class);
				String[] str = new String[searchResultList.size()];
				for(int i=0; i<searchResultList.size(); i++){
					str[i] = searchResultList.get(i).toString();
				}
				intent.putExtra("title", getResources().getString(R.string.search));
				intent.putExtra("catagory", -1);
				intent.putExtra("searchResult", str);
				startActivity(intent);
			}
		}
	}
	
	private List<Integer> getData(String key){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		List<Integer> result = null;
		try {
			key = URLEncoder.encode(key, "UTF-8");
			is = httpClient.doHttpPost2(URLConstant.SEARCH, new BasicNameValuePair("key", key));
//			is = getAssets().open("search.xml");
			result = parser.parserToList(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("parser", "searchgoods parser error");
			e.printStackTrace();
			return null;
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
		int total = Integer.valueOf(parser.getTotalNum());
		if(total == 0)
			return null;
		return result;
	}
	
	
	
	

	

}
