package cm.browser.view;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import cm.browser.R;
import cm.constant.CollectFile;
import cm.constant.MIMEType;
import cm.db.FavourService;
import cm.util.BaseActivity;
import cm.util.FileUtil;

public class CollectActivity extends BaseActivity {

	private ImageButton back;
	private TextView titleView;
	private ListAdapter adapter;
	private List<CollectFile> collectList = null;
	private ListView listView;
	private FavourService faService;
	private int size = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		adapter = new ListAdapter();
		faService = new FavourService(this);
		collectList = faService.getAllDataByType();
		size = faService.getItemCount();
		String title = getIntent().getStringExtra("title");
		back = (ImageButton) findViewById(R.id.title_back);
		titleView = (TextView) findViewById(R.id.title_text);
		titleView.setText(title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView = (ListView) findViewById(R.id.file_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CollectFile cf = (CollectFile) parent.getItemAtPosition(position);
				File file = new File(cf.getPath());
				if(file.isDirectory()){
					Intent intent = new Intent();
					//设置起始目录和查找的类型
			        intent.setDataAndType(Uri.fromFile(new File(cf.getPath())), "*/*");//"*/*"表示所有类型，设置起始文件夹和文件类型
			        intent.setClass(CollectActivity.this, FileBrowserActivity.class);
			        intent.putExtra("collect", "collect");
			        startActivity(intent);
				}else{
					openFile(file);
				}
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final CollectFile cf = (CollectFile) parent.getItemAtPosition(position);
				AlertDialog.Builder ab = new AlertDialog.Builder(CollectActivity.this);
				ab.setTitle(getResources().getString(R.string.collect_manager))
				.setMessage(getResources().getString(R.string.collect_text))
				.setPositiveButton(getResources().getString(R.string.confirm), new  DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						boolean b = faService.deleteByPath(cf.getPath());
						if(b){
							Toast.makeText(CollectActivity.this, getResources().getString(R.string.remove_success), 3000).show();
							collectList.remove(cf);
							adapter.notifyDataSetChanged();
						}else{
							Toast.makeText(CollectActivity.this, getResources().getString(R.string.remove_fail), 3000).show();
						}
					}
				})
				.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.create()
				.show();
				return false;
			}
		});
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(faService != null && !faService.isOpen()){
			faService.open();
			int i = faService.getItemCount();
			if(i != size){
				collectList.clear();
				collectList = faService.getAllDataByType();
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//排序方式，按收藏时间还是类型
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.collect_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.collect_time:
			collectList.clear();
			collectList = faService.getAllData();
			adapter.notifyDataSetChanged();
			break;
		case R.id.collect_type:
			collectList.clear();
			collectList = faService.getAllDataByType();
			adapter.notifyDataSetChanged();
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(faService.isOpen()) faService.close();
	}
	
	/**
	 * 打开文件
	 * @param file
	 */
	public void openFile(File file){
	    //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    //设置intent的Action属性
	    intent.setAction(Intent.ACTION_VIEW);
	    //获取文件file的MIME类型
	    String type = MIMEType.getMIMEType(file);
	    //设置intent的data和Type属性。
	    intent.setDataAndType(Uri.fromFile(file), type);
	    //跳转
	    try{
	    	startActivity(intent);    
	    }catch(ActivityNotFoundException e){
	    	e.printStackTrace();
	    	Toast.makeText(CollectActivity.this, getResources().getString(R.string.activity_not_found), 1).show();
	    }
	}

	private class ListAdapter extends BaseAdapter{
		LayoutInflater inflater;
		Holder holder = null;
		public ListAdapter(){
			inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}
		class Holder implements Serializable{
			ImageView img;
			TextView name;
			TextView size;
			TextView date;
			TextView path;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return collectList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return collectList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			holder = null;
			if(convertView == null){
				holder = new Holder();
				convertView = inflater.inflate(R.layout.detail_listitem, null);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			CollectFile cf = collectList.get(position);
			File files = new File(cf.getPath());
			if(cf != null){
				holder.img = (ImageView) convertView.findViewById(R.id.detail_image);
				holder.img.setImageDrawable(FileUtil.getFileImageBackground(CollectActivity.this, files));
				holder.path = (TextView) convertView.findViewById(R.id.detail_path);
				holder.path.setText("路径:"+cf.getPath());
				holder.date = (TextView) convertView.findViewById(R.id.detail_date);
				holder.date.setText("收藏日期:"+cf.getDate());
				holder.name = (TextView) convertView.findViewById(R.id.detail_name);
				holder.name.setText(cf.getName());
				holder.size = (TextView) convertView.findViewById(R.id.detail_size);
				double sizes = 0;
				if(files.isDirectory()){
					sizes = FileUtil.getFileSize(files);
				}else{
					sizes = files.length();
				}
				String file_size = getDefaultSize(sizes);
				if(files.isDirectory()){
					file_size += " ("+FileUtil.NUMBER+"个文件)";
				}
				holder.size.setText("大小:"+file_size);
			}
			return convertView;
		}
		
	}

}
