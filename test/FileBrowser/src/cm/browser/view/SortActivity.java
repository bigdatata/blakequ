package cm.browser.view;

import java.util.ArrayList;
import java.util.List;

import cm.browser.OnFileScanOverListener;
import cm.browser.R;
import cm.browser.ScanFile;
import cm.browser.R.drawable;
import cm.browser.R.id;
import cm.browser.R.layout;
import cm.browser.view.process.ProcessTabActivity;
import cm.constant.CollectFile;
import cm.db.FavourService;
import cm.util.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SortActivity extends BaseActivity implements OnItemClickListener{
	GridView gridView;
	Integer[] gridImages = new Integer[] {R.drawable.video_icon, R.drawable.music_icon, R.drawable.photo_icon
			,R.drawable.app_icon, R.drawable.apk_icon, R.drawable.document_icon, R.drawable.archive_icon,
			R.drawable.favorites_icon, R.drawable.star_icon};
	String[] gridText; 
	Integer[] gridNum = {0,0,0,0,0,0,0,0,0};
	ScanFile scanFile;
	GridAdapter adapter;
	FavourService faService;
	private static final int RESULT_BACK = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sort);
		adapter = new GridAdapter();
		gridText = getResources().getStringArray(R.array.file_sort);
		scanFile = ScanFile.getInstance();
		scanFile.setmOnFileScanOverListener(new OnFileScanOverListener() {
			
			@Override
			public void onScanOverListener() {
				// TODO Auto-generated method stub
				gridNum[0] = scanFile.getVideoFile().size();
				gridNum[1] = scanFile.getAudioFile().size();
				gridNum[2] = scanFile.getImageFile().size();
				gridNum[3] = scanFile.getAppFile().size();
				gridNum[4] = 0;
				gridNum[5] = scanFile.getDocumentFile().size();
				gridNum[6] = scanFile.getRarFile().size();
				myHandler.sendEmptyMessage(0);
			}
		});
		if(checkSDCardState()){
			scanFile.start();
		}
		gridView = (GridView) findViewById(R.id.sort_gridview);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		faService = new FavourService(this);
		gridNum[8] = faService.getItemCount();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(faService!= null && !faService.isOpen()){
			faService.open();
			int num = faService.getItemCount();
			if(num != gridNum[8]){
				gridNum[8] = num;
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(faService != null){
			faService.close();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data!= null && requestCode == RESULT_BACK){
			int type = data.getIntExtra("type", 0);
			int size = data.getIntExtra("size", 0);
			int i = 0;
			switch(type){
				case R.drawable.video_icon:
					i = 0;
					break;
				case R.drawable.music_icon:
					i = 1;
					break;
				case R.drawable.photo_icon:
					i = 2;
					break;
				case R.drawable.app_icon:
					i = 3;
					break;
				case R.drawable.document_icon:
					i = 5;
					break;
				case R.drawable.archive_icon:
					i = 6;
					break;
				case R.drawable.favorites_icon:
					i = 7;
					break;
				case R.drawable.star_icon:
					i = 8;
					break;
			}
			gridNum[i] = size;
			adapter.notifyDataSetChanged();
		}
	}



	Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			adapter.notifyDataSetChanged();
		}
		
	};
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) promptExitApp();
		return super.onKeyDown(keyCode, event);
	}


	private class GridAdapter extends BaseAdapter{
		class Holder{
			ImageView img;
			TextView tv1;
			TextView tv2;
		}
		LayoutInflater inflate;
		public GridAdapter(){
			inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 9;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return gridNum[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = null;
			if(convertView == null){
				convertView = inflate.inflate(R.layout.gridview_item, null);
				holder = new Holder();
				convertView.setTag(holder);
			}else{
				holder = (cm.browser.view.SortActivity.GridAdapter.Holder) convertView.getTag();
			}
			holder.img = (ImageView) convertView.findViewById(R.id.gridView);
			holder.img.setImageResource(gridImages[position]);
			holder.tv1 = (TextView) convertView.findViewById(R.id.grid_title);
			holder.tv1.setText(gridText[position]);
			holder.tv2 = (TextView) convertView.findViewById(R.id.grid_num);
			holder.tv2.setText("("+gridNum[position]+")");
			if(position == 4 || position == 7){
				holder.tv2.setText("");
			}
			return convertView;
		}
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		int num = (Integer) parent.getItemAtPosition(position);
		if(num == 0 && position != 4 && position != 7){
			Toast.makeText(SortActivity.this, getResources().getString(R.string.not_file_this_sort), 3000).show();
			return;
		}
		Intent intent = null;
		switch(position){
			case 0:
				intent = new Intent(SortActivity.this, DetailFileActivity.class);
				intent.putStringArrayListExtra("content", scanFile.getVideoFile());
				intent.putExtra("type", gridImages[0]);
				intent.putExtra("title",gridText[0]);
				break;
			case 1:
				intent = new Intent(SortActivity.this, DetailFileActivity.class);
				intent.putStringArrayListExtra("content", scanFile.getAudioFile());
				intent.putExtra("type", gridImages[1]);
				intent.putExtra("title",gridText[1]);
				break;
			case 2:
				intent = new Intent(SortActivity.this, DetailFileActivity.class);
				intent.putStringArrayListExtra("content", scanFile.getImageFile());
				intent.putExtra("type", gridImages[2]);
				intent.putExtra("title",gridText[2]);
				break;
			case 3:
				intent = new Intent(SortActivity.this, DetailFileActivity.class);
				intent.putStringArrayListExtra("content", scanFile.getAppFile());
				intent.putExtra("type", gridImages[3]);
				intent.putExtra("title",gridText[3]);
				break;
			case 4:
				intent = new Intent(SortActivity.this, ProcessTabActivity.class);
				break;
			case 5:
				intent = new Intent(SortActivity.this, DetailFileActivity.class);
				intent.putStringArrayListExtra("content", scanFile.getDocumentFile());
				intent.putExtra("type", gridImages[5]);
				intent.putExtra("title",gridText[5]);
				break;
			case 6:
				intent = new Intent(SortActivity.this, DetailFileActivity.class);
				intent.putStringArrayListExtra("content", scanFile.getRarFile());
				intent.putExtra("type", gridImages[6]);
				intent.putExtra("title",gridText[6]);
				break;
			case 7:
				intent = new Intent(SortActivity.this, HelpActivity.class);
				break;
			case 8:
				intent = new Intent(SortActivity.this, CollectActivity.class);
				intent.putExtra("title",gridText[8]);
				break;
		}
		startActivityForResult(intent, RESULT_BACK);
	}
}
