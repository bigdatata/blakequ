package com.hao.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

import com.hao.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class FileBrowserActivity extends Activity implements OnClickListener{
	/*列表视图*/
	private ListView listView;
	/*存放文件的列表*/
	private List<File> listFile;
	private ListAdapter adapter;
	/*文件当前目录*/
	private File currentDir = new File("/");
	private TextView titleText;
	private ImageButton titleBack;
	private Uri uri = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_browser);
        uri = getIntent().getData();//接收起始目录
//        type = getIntent().getType();//类型,下面做的就是获取指定类型
        if(uri != null) currentDir = new File(uri.getPath());
        
        titleBack = (ImageButton) findViewById(R.id.title_back);
        titleBack.setOnClickListener(this);
        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText(currentDir.toString());
        listFile = new LinkedList<File>();
        adapter = new ListAdapter();
        listView = (ListView) findViewById(R.id.file_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				File file = (File) parent.getItemAtPosition(position);
				if(file.isDirectory()){
					currentDir = file;
					setData(file);
				}else if(uri != null){
					backResult(file);
				}
			}
		});
        setData(currentDir);
    }

	
	/**
	 * 返回file的路径
	 * @param path
	 */
	private void backResult(File path){
		Intent intent = new Intent();
		Uri startDir = Uri.fromFile(path);
		intent.setDataAndType(startDir,  "vnd.android.cursor.dir/lysesoft.andexplorer.file");
		setResult(RESULT_OK, intent);
		finish();
	}
    
    
    private void setData(File dir){
    	titleText.setText(dir.toString());
    	listFile.clear();
    	listFile.addAll(FileUtil.getFolderFileList(dir));
    	listFile.addAll(FileUtil.getCommonFileList(dir));
    	adapter.notifyDataSetChanged();
    }
    
    private class ListAdapter extends BaseAdapter{
    	private LayoutInflater inflate;
    	public ListAdapter(){
    		inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    	}
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listFile.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listFile.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			Holder holder = null;
			if(view == null){
				view = inflate.inflate(R.layout.list_item, null);
				holder = new Holder();
				holder.img = (ImageView) view.findViewById(R.id.file_image);
				holder.text = (TextView) view.findViewById(R.id.file_name);
				view.setTag(holder);
			}else{
				holder = (Holder) view.getTag();
			}
			holder.img.setImageDrawable(FileUtil.getFileImageBackground(FileBrowserActivity.this, listFile.get(position)));
			holder.text.setText(listFile.get(position).getName());
			return view;
		}
		
		class Holder{
			ImageView img;
			TextView text;
		}
    	
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.title_back:
				currentDir = currentDir.getParentFile();
				if(currentDir == null){
					finish();
				}else{
					setData(currentDir);
				}
				break;
		}
	}
}