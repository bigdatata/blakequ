package cm.browser.view;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cm.browser.R;
import cm.browser.ScanFile;
import cm.browser.R.array;
import cm.browser.R.drawable;
import cm.browser.R.id;
import cm.browser.R.layout;
import cm.browser.R.string;
import cm.browser.R.style;
import cm.constant.CollectFile;
import cm.constant.MIMEType;
import cm.db.FavourService;
import cm.util.BaseActivity;
import cm.util.ImageUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class DetailFileActivity extends BaseActivity {
	private ImageButton back;
	private TextView titleView;
	private ListView listView;
	private ListAdapter adapter;
	private ArrayList<String> contentlist = null;
	private int type;
	private String title;
	private PopupWindow popupWindow;
	private String file_size, file_date;
	private static final int DIALOG = 1;
	public static final int BACK_MODIFY = 2;
	public static final int CUT = 3;
	public static final int COPY = 4;
	private ScanFile scanFile;
	private boolean isFileChanged = false;
	private FavourService faService;
	/**
	 * 图片太多的话，可能内存泄露，故而改为弱引用，改进
	 */
	private HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>();
	/**
	 * 保证线程只加载一次图片，不重复加载
	 */
	private HashMap<String, Boolean> isStartTask = new HashMap<String, Boolean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		faService = new FavourService(DetailFileActivity.this);
		contentlist = getIntent().getStringArrayListExtra("content");
		type = getIntent().getIntExtra("type", -1);
		title = getIntent().getStringExtra("title");
		back = (ImageButton) findViewById(R.id.title_back);
		titleView = (TextView) findViewById(R.id.title_text);
		titleView.setText(title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backActivity();
			}
		});
		adapter = new ListAdapter();
		listView = (ListView) findViewById(R.id.file_list);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String path = (String) parent.getItemAtPosition(position);
				Bundle b = new Bundle();
				b.putString("path", path);
				showDialog(DIALOG, b);
				return false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				openFile(new File((String) parent.getItemAtPosition(position)));
			}
		});
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(faService != null && faService.isOpen()){
			faService.close();
		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(faService != null && !faService.isOpen()) faService.open();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imageCache.clear();
		isStartTask.clear();
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



	private Dialog buildDialog(final Context context, Bundle bundle) {
		// TODO Auto-generated method stub
		final CharSequence[] items = getResources().getStringArray(R.array.file_operator);
		final String path = bundle.getString("path");
		final File file = new File(path);
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				final Resources res = getResources();
				switch(which){
					case 0:
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setMessage(res.getString(R.string.sure_delete))
						       .setCancelable(false)
						       .setTitle(res.getString(R.string.delete_file))
						       .setPositiveButton(res.getString(R.string.delete), new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                if(file.delete()){
						                	contentlist.remove(path);
						                	checkType(type);
						                	adapter.notifyDataSetChanged();
						                	Toast.makeText(context, res.getString(R.string.delete_success), 3000).show();
						                	isFileChanged = true;
						                }else{
						                	Toast.makeText(context, res.getString(R.string.delete_fail), 3000).show();
						                }
						           }
						       })
						       .setNegativeButton(res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
						builder.show();
						break;
					case 1:
						Intent intent = new Intent(DetailFileActivity.this, FileBrowserActivity.class);
						intent.putExtra("copy", path);
						intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
						startActivityForResult(intent, COPY);
						break;
					case 2:
						Intent intent1 = new Intent(DetailFileActivity.this, FileBrowserActivity.class);
						intent1.putExtra("cut", path);
						intent1.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
						startActivityForResult(intent1, CUT);
						break;
					case 3:
						final EditText edit = new EditText(DetailFileActivity.this);
						edit.setHint(res.getString(R.string.new_name));
						final AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
						builder2.setView(edit)
						       .setCancelable(false)
						       .setTitle(res.getString(R.string.rename))
						       .setPositiveButton(res.getString(R.string.confirm), new DialogInterface.OnClickListener() {
						    	   /**
						    		 * Android中的弹出框在被点击时， 无论点击哪个按钮都会关闭窗口。 
						    	     * 但是有的情况下我们不需要立即关闭窗口。下面的情况是弹出框中要求用户输入文件名，
						    	     * 并在点击确定时检查文件名的合法性， 不合法则提示用户重新输入， 弹出框要保持在界面上
						    	     * 使用反射
						    		 */
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										switch (which) {
											case DialogInterface.BUTTON_POSITIVE:
												String editStr = edit.getText().toString();
									        	 //文件名不能包含特殊字符，否则打不开
												if(!checkFileName(editStr)){
													edit.setText("");
													edit.setHint(getResources().getString(R.string.name_error));
									        		try {
									        			// 不关闭对话框
									        			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
									        			field.setAccessible(true);
									        			field.set(dialog, false);
									        		} catch (Exception e) {
									        			e.printStackTrace();
									        		}
												}else{
													File file = new File(path);
										        	String newPath = path.substring(0, path.lastIndexOf(File.separatorChar)+1);
										        	String name = file.getName();
										        	String last = name.substring(name.indexOf("."), name.length());
										        	newPath = newPath + editStr + last;
										        	file.renameTo(new File(newPath));
										        	contentlist.remove(path);
										        	contentlist.add(newPath);
										        	checkType(type);
										        	adapter.notifyDataSetChanged();
										        	// 关闭对话框
										        	try {
										        		Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
										        		field.setAccessible(true);
										        		field.set(dialog, true);
										        	} catch (Exception e) {
										        		e.printStackTrace();
										        	}
										        	dialog.dismiss();
												}
												break;
											case DialogInterface.BUTTON_NEGATIVE:
												dialog.dismiss();
												break;
											default:
												break;
										}
									}
						       })
						       .setNegativeButton(res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	// 关闭对话框
							        	try {
							        		Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							        		field.setAccessible(true);
							        		field.set(dialog, true);
							        	} catch (Exception e) {
							        		e.printStackTrace();
							        	}
							        	dialog.dismiss();
						           }
						       });
						builder2.show();
						break;
					case 4:
						CollectFile cf = new CollectFile();
						cf.setPath(path);
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
						cf.setDate(date);
						cf.setName(file.getName());
						cf.setType(title);
						if(faService.update(cf)){
							Toast.makeText(DetailFileActivity.this, res.getString(R.string.collect_success), 3000).show();
						}else{
							Toast.makeText(DetailFileActivity.this, res.getString(R.string.collect_fail), 3000).show();
						}
						break;
					case 5:
						openPopupwin(path);
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
	

	/**
	 * 去除特殊字符
	 * @param str
	 * @return
	 */
	public String stringFilter(String str){   
		// 清除掉所有特殊字符  
		//只允许字母和数字"[^a-zA-Z0-9]"
		try{
			String regEx="[`~!@#$%^&*()+=-|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
			Pattern p = Pattern.compile(regEx);     
			Matcher m = p.matcher(str);     
			return m.replaceAll("").trim();     
		}catch(PatternSyntaxException  e){
			e.printStackTrace();
			return "";
		}
	} 
	
	/**
	 * 判断是否含特殊字符
	 * @param name
	 * @return true不含，false含有
	 */
	public boolean checkFileName(String name){
		//其他字符和中文
		if(name.replaceAll("[a-z]*[A-Z]*\\d*_*[\\u4e00-\\u9fa5]*", "").length() == 0){//\\s*表示空白字符 ，[//u4e00-//u9fbb]
			return true;
		}
		return false;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			if(requestCode == CUT){
				boolean type = data.getBooleanExtra("success", false);
				if(type){
					System.out.println("cut delete file");
					String source = data.getStringExtra("cut_source");
					String end = data.getStringExtra("cut_goal");
					File file = new File(source);
					if(file.exists()) file.delete();
					contentlist.remove(source);
					contentlist.add(end);
					checkType(this.type);
					adapter.notifyDataSetChanged();
					//if collect will update
					if(!faService.isOpen()) faService.open();
					boolean b = faService.checkHaveDataByPath(source);
					if(b){
						faService.deleteByPath(source);
						File f = new File(end);
						CollectFile cf = new CollectFile();
						cf.setName(f.getName());
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
						cf.setDate(date);
						cf.setPath(end);
						cf.setType(title);
						faService.insert(cf);
					}
				}
			}
			else if(requestCode == COPY){
				isFileChanged = true;
				boolean type = data.getBooleanExtra("success", false);
				if(type){
					String end = data.getStringExtra("copy_goal");
					contentlist.add(end);
					checkType(this.type);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}
	
	/**
	 * 当文件修改过后设置列表文件
	 * @param type
	 */
	private void checkType(int type){
		scanFile = ScanFile.getInstance();
		switch(type){
			case R.drawable.video_icon:
				scanFile.setVideoFile(contentlist);
				break;
			case R.drawable.music_icon:
				scanFile.setAudioFile(contentlist);
				break;
			case R.drawable.photo_icon:
				scanFile.setImageFile(contentlist);
				break;
			case R.drawable.app_icon:
				scanFile.setAppFile(contentlist);
				break;
			case R.drawable.document_icon:
				scanFile.setDocumentFile(contentlist);
				break;
			case R.drawable.archive_icon:
				scanFile.setRarFile(contentlist);
				break;
			case R.drawable.favorites_icon:
				break;
			case R.drawable.star_icon:
				break;
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
	    	Toast.makeText(DetailFileActivity.this, getResources().getString(R.string.activity_not_found), 1).show();
	    }
	}
	
	private void openPopupwin(String file) {
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
		File files = new File(file);
		TextView text = (TextView) menuView.findViewById(R.id.pop_title);
		text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
		text.setText(files.getName());
		TextView path = (TextView) menuView.findViewById(R.id.pop_path);
		path.setText(res.getString(R.string.route) + "："+file);
		TextView date = (TextView) menuView.findViewById(R.id.pop_date);
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
		size.setText(res.getString(R.string.size) + "："+file_size);

		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.main_layout),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
	
	/**
	 * 返回时候传输数据
	 */
	private void backActivity(){
		if(isFileChanged){
			Intent intent = new Intent(DetailFileActivity.this, SortActivity.class);
			intent.putExtra("size", contentlist.size());
			intent.putExtra("type", type);
			setResult(BACK_MODIFY, intent);
		}
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			backActivity();
		}
		return false;
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
			return contentlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return contentlist.get(position);
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
			String path = contentlist.get(position);
			final File file = new File(path);
			holder.path = (TextView) convertView.findViewById(R.id.detail_path);
			holder.path.setText("路径: "+file.getPath());
			holder.img = (ImageView) convertView.findViewById(R.id.detail_image);
			if(type == R.drawable.video_icon || type == R.drawable.photo_icon){
				
				Bitmap bp = imageCache.get(path);
				if(bp != null){
					holder.img.setImageBitmap(bp);
				}else if(!isStartTask.containsKey(path)){
					new ImageTask(type, path, holder).start();
					/**
					 * 注：在后期，如果是从网络下载，有时候会下载失败，这时候想重新下载，只需要
					 * 判断isStartTask的value，看是否是true，如果不是重新下载即可
					 */
				}else{
					holder.img.setImageResource(type);
				}
			}else{
				holder.img.setImageResource(type);
			}
			holder.date = (TextView) convertView.findViewById(R.id.detail_date);
			file_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));
			holder.date.setText("日期: "+file_date);
			holder.name = (TextView) convertView.findViewById(R.id.detail_name);
			holder.name.setText(file.getName());
			holder.size = (TextView) convertView.findViewById(R.id.detail_size);
			double size = file.length();
			if(size < 1024){
				file_size = size+"B";
			}else if(size >=1024 && size < 1048576){
				file_size = new DecimalFormat("####.##").format(size/1024)+"KB";
			}else{
				file_size = new DecimalFormat("###,###,###.##").format(size/1048576)+"M";
			}
			holder.size.setText("大小: "+file_size);
			return convertView;
		}
		
		Handler myHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Bundle b = msg.getData();
				String path = b.getString("path");
				Holder h = (Holder) b.getSerializable("holder");
				Bitmap bp = imageCache.get(path);
				h.img.setImageBitmap(bp);
			}
			
		};
		
		private class ImageTask extends Thread{
			int id;
			String path;
			Holder holder;
			public ImageTask(int id, String path, Holder holder){
				this.id = id;
				this.path = path;
				this.holder = holder;
			}
			@Override
			public void run() {
				// TODO Auto-generated method stub
				isStartTask.put(path, false);
				if(!imageCache.containsKey(path)){
					Bitmap bp = null;
					if (id == R.drawable.video_icon) {
						bp = ThumbnailUtils.createVideoThumbnail(path,
								MediaStore.Video.Thumbnails.MICRO_KIND);
					} else if (id == R.drawable.photo_icon) {
						Bitmap b = ImageUtil.getBitmapFromSdcard(path);
						if(b != null){
							b = ImageUtil.zoomBitmapByScale(b, 50, 50);
							b = ImageUtil.toRoundCorner(b, 5);
						}
						bp = b;
					}
					if(bp != null){
						try{
							imageCache.put(path, bp);
						}catch(Exception e){
							e.printStackTrace();
						}
						isStartTask.put(path, true);
						Message msg = myHandler.obtainMessage();
						Bundle b = new Bundle();
						b.putString("path", path);
						b.putSerializable("holder", holder);
						msg.setData(b);
						myHandler.sendMessage(msg);
					}
				}else{
					System.out.println("image had exist");
				}
			}
			
		}
	}
}
