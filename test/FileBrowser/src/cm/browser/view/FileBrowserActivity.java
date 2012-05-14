package cm.browser.view;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cm.browser.R;
import cm.browser.R.array;
import cm.browser.R.id;
import cm.browser.R.layout;
import cm.browser.R.menu;
import cm.browser.R.string;
import cm.browser.R.style;
import cm.constant.CollectFile;
import cm.constant.MIMEType;
import cm.db.FavourService;
import cm.util.BaseActivity;
import cm.util.FileUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class FileBrowserActivity extends BaseActivity implements OnClickListener{
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
	private String copy, cut, collect;
	private boolean isSuccess = false; 
	public static final int CUT_RESULT_CODE = 2;
	public static final int COPY_RESULT_CODE = 3;
	private static final int DIALOG = 1;
	Timer timer = new Timer();
	private PopupWindow popupWindow;
	private FavourService faService;
	private ProgressDialog pd;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        uri = getIntent().getData();//接收起始目录
        copy = getIntent().getStringExtra("copy");
        cut = getIntent().getStringExtra("cut");
        collect = getIntent().getStringExtra("collect");
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
				File file = (File) parent.getItemAtPosition(position);
				//增加重命名,删除等文件夹的功能
				Bundle b = new Bundle();
				b.putSerializable("file", file);
				showDialog(DIALOG, b);
				return false;
			}
		});
        setData(currentDir);
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				myHandler.sendEmptyMessage(0);
			}
		}, 2000);
    }
    
    Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Resources res = getResources();
			switch(msg.what){
				case 0:
					if(copy != null || cut != null){
						Toast.makeText(FileBrowserActivity.this, getResources().getString(R.string.pressed_menu), 3000).show();
					}
					break;
				case 1://cut
					pd.dismiss();
					boolean ct = msg.getData().getBoolean("type");
					File file = (File) msg.getData().getSerializable("file");
					if(ct){
						isSuccess = true;
						listFile.add(file);
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.copy_success), 3000).show();
					}else{
						isSuccess = false;
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.copy_fail), 3000).show();
					}
					adapter.notifyDataSetChanged();
					break;
				case 2://copy
					pd.dismiss();
					boolean cp = msg.getData().getBoolean("type");
					File f = (File) msg.getData().getSerializable("file");
					if(cp){
						isSuccess = true;
						listFile.add(f);
						boolean bb=f.delete();
						//在之后还需要判断删除原文件是否失败，如果失败就是粘贴失败
						System.out.println("file paste delete "+bb);
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.paste_success), 3000).show();
					}else{
						isSuccess = false;
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.paste_fail), 3000).show();
					}
					adapter.notifyDataSetChanged();
					break;
			}
		}
    	
    };
    
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(faService != null && faService.isOpen()) faService.close();
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		switch (id) {
			case DIALOG:
				buildDialog(this, bundle);
				break;
		}
		return super.onCreateDialog(id);
	}
    
    private Dialog buildDialog(Context context, Bundle bundle) {
		// TODO Auto-generated method stub
    	final File file = (File) bundle.getSerializable("file");
    	CharSequence[] items = getResources().getStringArray(R.array.file_operator);
    	AlertDialog.Builder ab = new AlertDialog.Builder(this);
    	ab.setItems(items, new DialogInterface.OnClickListener() {
    		
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			// TODO Auto-generated method stub
    			final Resources res = getResources();
    			switch(which){
    			case 0:
    				Toast.makeText(FileBrowserActivity.this, res.getString(R.string.not_complete), 3000).show();
    				break;
    			case 1:
    				Toast.makeText(FileBrowserActivity.this, res.getString(R.string.not_complete), 3000).show();
    				break;
    			case 2:
    				Toast.makeText(FileBrowserActivity.this, res.getString(R.string.not_complete), 3000).show();
    				break;
    			case 3:
    				Toast.makeText(FileBrowserActivity.this, res.getString(R.string.not_complete), 3000).show();
    				break;
    			case 4:
    				faService = new FavourService(FileBrowserActivity.this);
					CollectFile cf = new CollectFile();
					cf.setPath(file.getPath());
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
					cf.setDate(date);
					cf.setName(file.getName());
					cf.setType(getResources().getString(R.string.folder));
					if(faService.update(cf)){
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.collect_success), 3000).show();
					}else{
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.collect_fail), 3000).show();
					}
    				break;
    			case 5:
    				openPopupwin(file);
    				break;
    			case 6:
    				dialog.dismiss();
    				break;
    			}
    		}
    	});
    	ab.show();
		return ab.create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.mainpage_menu, menu);
    	if(copy != null || cut != null){
    		return true;
    	}else{
    		return false;
    	}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Resources res = getResources();
		switch (item.getItemId()) {
			case R.id.main_paste:
				if(copy != null){
					File file = new File(copy);
					String path = currentDir.getPath()+File.separatorChar+file.getName();
					File end = new File(path);
					if(end.exists()){
						isSuccess = false;
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.file_exists), 3000).show();
					}else{
						new MyTask(path, file).start();
						pd = new ProgressDialog(this);
						pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						pd.setMessage(getResources().getString(R.string.move_now));
						pd.show();
					}
				}
				if(cut != null){//粘贴需要使用异步线程
					File file = new File(cut);
					String path = currentDir.getPath()+File.separatorChar+file.getName();
					File end = new File(path);
					if(end.exists()){
						isSuccess = false;
						Toast.makeText(FileBrowserActivity.this, res.getString(R.string.file_exists), 3000).show();
					}else{
						new MyTask(path, file).start();
						pd = new ProgressDialog(this);
						pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						pd.setMessage(getResources().getString(R.string.move_now));
						pd.show();
					}
				}
				break;
			case R.id.main_cancel:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class MyTask extends Thread{
		String path;
		File file;
		public MyTask(String path, File file){
			this.path = path;
			this.file = file;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = false;
			Message msg = null;
			if(cut != null){
				b = FileUtil.copyToFile(cut, path);
				msg = myHandler.obtainMessage(1);
			}
			if(copy != null){
				b = FileUtil.copyToFile(copy, path);
				msg = myHandler.obtainMessage(2);
			}
			Bundle bd = new Bundle();
			bd.putBoolean("type", b);
			bd.putSerializable("file", file);
			msg.setData(bd);
			myHandler.sendMessage(msg);
		}
		
	}

	/**
	 * 打开文件
	 * @param file
	 */
	public void openFile(File file){
	    //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
	    	Toast.makeText(FileBrowserActivity.this, getResources().getString(R.string.activity_not_found), 1).show();
	    }
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
				setKeyBack();
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			setKeyBack();
		}
		//注意重写了onKeyDown后必须返回false，否则就会将按钮事件阻塞，不在往下传递，就不能调用Menu事件
		if(copy != null || cut != null){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * 返回上级目录
	 */
	private void setKeyBack(){
		if(isSuccess){
			setFileOperatorBack();
		}else{
			File tempDir = currentDir.getParentFile();
			if(tempDir == null){
				//如果是从收藏跳转来的返回
				if(collect != null){
					finish();
					return;
				}
				//如果是从复制剪贴跳转来就over,否则就是退出
				if(copy != null || cut != null){
					finish();
				}else{
					promptExitApp();
				}
			}else{
				currentDir = tempDir;
				setData(currentDir);
			}
		}
	}
	
	/**
	 * 当剪切复制操作之后返回值
	 */
	private void setFileOperatorBack(){
		Intent intent = null;
		if(cut != null){
			File file = new File(cut);
			String path = currentDir.getPath()+File.separatorChar+file.getName();
			intent = new Intent(FileBrowserActivity.this, DetailFileActivity.class);
			intent.putExtra("success", isSuccess);
			intent.putExtra("cut_source", cut);
			intent.putExtra("cut_goal", path);
			setResult(CUT_RESULT_CODE, intent);
			finish();
		}
		if(copy != null){
			File file = new File(copy);
			String path = currentDir.getPath()+File.separatorChar+file.getName();
			intent = new Intent(FileBrowserActivity.this, DetailFileActivity.class);
			intent.putExtra("success", isSuccess);
			intent.putExtra("copy_goal", path);
			setResult(COPY_RESULT_CODE, intent);
			finish();
		}
	}
	
	private void openPopupwin(File files) {
		LayoutInflater mLayoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.detail_file, null, true);
		menuView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		Resources res = getResources();
		TextView text = (TextView) menuView.findViewById(R.id.pop_title);
		text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
		text.setText(files.getName());
		TextView path = (TextView) menuView.findViewById(R.id.pop_path);
		path.setText(res.getString(R.string.route) + "："+files.getPath());
		TextView date = (TextView) menuView.findViewById(R.id.pop_date);
		String file_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(files.lastModified()));
		date.setText(res.getString(R.string.date) + "："+file_date);
		TextView rw = (TextView) menuView.findViewById(R.id.pop_read_write);
		String tag;
		if(files.canWrite() && files.isHidden()){
			tag = res.getString(R.string.rwh);
		}else{
			tag = res.getString(R.string.rwnh);
		}
		rw.setText(res.getString(R.string.rw) + "："+tag);
		TextView size = (TextView) menuView.findViewById(R.id.pop_size);
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
		size.setText(res.getString(R.string.size) + "："+file_size);

		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.main_layout),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
}