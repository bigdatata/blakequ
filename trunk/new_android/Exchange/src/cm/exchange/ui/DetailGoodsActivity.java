package cm.exchange.ui;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;
import cm.exchange.R;
import cm.exchange.db.GoodsService;
import cm.exchange.db.UserService;
import cm.exchange.entity.Goods;
import cm.exchange.entity.User;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;
import cm.exchange.parser.UserParser;
import cm.exchange.ui.map.MapLocationActivity;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.FileUtil;
import cm.exchange.util.ImageUtil;
import cm.exchange.util.UserTask;

public class DetailGoodsActivity extends BaseActivity {
	View view;
	ImageButton left, right;
	TextView titleView, content;
	TextView name, price, state, time, distance, locationTextView;
	ImageButton telephone, sms, leaveNote, location;
	TextView tabLeaveNote, tabNotice, tabModify, tabState;
	Goods goods = null;
	ImageView icon, image;
	ExchangeApplication app;
	private static final int STATE_DIALOG = 1;
	private static final String REQUEST_USER = "1";
	private static final String REQUEST_ATTENTION = "2";
	private static final String REQUEST_IMAGE = "3";
	private static final String REQUEST_STATE = "4";
	GoodsService goodsDB = null;
	UserService userDB = null;
	UserParser userParser = null;
	User user;
	PopupWindow popupWindow;
	Bitmap bitmap = null;
	@Override
	public void initTitle() {
		// TODO Auto-generated method stub
		super.initTitle();
		left.setImageResource(R.drawable.main_title_back_icon);
		right.setImageResource(R.drawable.home);
		titleView.setText(goods.getName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailgoods);
		goods = (Goods) getIntent().getSerializableExtra("goods");
		app = (ExchangeApplication) getApplicationContext();
		initView();
		goodsDB = new GoodsService(this);
		userDB = new UserService(this);
		userParser = new UserParser();
		if(goods.isHavePicture()){
			new DetailTask().execute(REQUEST_IMAGE, goods.getPictureAddress());
		}
	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		goodsDB.open();
		userDB.open();
		//the createUser is id of user
		int id = Integer.valueOf(goods.getCreateUser());
		//if the user is admin it must exists in user table
		if(userDB.checkHaveDataById(id)){
			user = userDB.getDataById(id);
			if(user.getLocation() == null){
				locationTextView.append(getResources().getString(R.string.detailgoods_not_location));
			}else{
				locationTextView.append(user.getLocation());
			}
		}else{
			new DetailTask().execute(REQUEST_USER,goods.getCreateUser());
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(user != null){
			userDB.update(user);
		}
		goodsDB.close();
		userDB.close();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		//title
		Spannable sp = null;
		view = findViewById(R.id.detailgoods_title);
		left = (ImageButton) view.findViewById(R.id.main_title_left);
		left.setOnClickListener(this);
		right = (ImageButton) view.findViewById(R.id.main_title_right);
		right.setOnClickListener(this);
		titleView = (TextView) view.findViewById(R.id.main_title_text);
		
		//main content
		name = (TextView) findViewById(R.id.detailgoods_name);
		name.setText(R.string.detailgoods_name);
		name.append(goods.getName());
		sp = (Spannable) name.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		price = (TextView)findViewById(R.id.detailgoods_price);
		price.setText(R.string.detailgoods_price);
		price.append(goods.getPrice()+"");
		sp = (Spannable) price.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		state = (TextView)findViewById(R.id.detailgoods_state);
		state.setText(R.string.detailgoods_state);
		if(goods.getGoodsState() == 1){
			state.append(getResources().getString(R.string.detailgoods_sell_now));
		}else{
			state.append(getResources().getString(R.string.detailgoods_selled));
		}
		sp = (Spannable) state.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		time = (TextView) findViewById(R.id.detailgoods_pub_time);
		time.setText(R.string.detailgoods_pub_time);
		time.append(goods.getSaleData()); //SimpleDateFormat to handle the data
		sp = (Spannable) time.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		distance = (TextView)findViewById(R.id.detailgoods_distance);
		distance.setText(R.string.detailgoods_distance);
		distance.append(String.valueOf(goods.getDistance())+"m");
		sp = (Spannable) distance.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		locationTextView = (TextView) findViewById(R.id.detailgoods_location);
		locationTextView.setText(getResources().getString(R.string.detailgoods_location), BufferType.SPANNABLE);
		sp = (Spannable) locationTextView.getText();
		sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		//imagebutton
		telephone = (ImageButton) findViewById(R.id.detailgoods_button_phone);
		telephone.setOnClickListener(this);
		sms = (ImageButton) findViewById(R.id.detailgoods_button_sms);
		sms.setOnClickListener(this);
		leaveNote = (ImageButton) findViewById(R.id.detailgoods_button_leave_note);
		leaveNote.setOnClickListener(this);
		location = (ImageButton) findViewById(R.id.detailgoods_button_location);
		location.setOnClickListener(this);
		
		//four tab
		tabLeaveNote = (TextView) findViewById(R.id.detailgoods_tab_leave_note);
		tabLeaveNote.setOnClickListener(this);
		//**if user is not creater set gray and was not clicked
		tabModify = (TextView) findViewById(R.id.detailgoods_tab_modify);
		tabState = (TextView) findViewById(R.id.detailgoods_tab_state);
		tabNotice = (TextView) findViewById(R.id.detailgoods_tab_notice);
		tabModify.setOnClickListener(this);
		tabState.setOnClickListener(this);
		tabNotice.setOnClickListener(this);
		
		//content
		content = (TextView) findViewById(R.id.detailgoods_content);
		content.setText(goods.getDescript());
		
		//icon and image
		icon = (ImageView) findViewById(R.id.detailgoods_icon);
		image = (ImageView)findViewById(R.id.detailgoods_image);
		if(!goods.isHavePicture()){
			icon.setImageResource(R.drawable.icon);
		}else{
			image.setVisibility(View.VISIBLE);
			image.setImageBitmap(null);
			//loadImage and store image to SDcard
			image.setOnClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent intent;
		switch(v.getId()){
			case R.id.detailgoods_button_leave_note:
				intent = new Intent(this, LeaveNoteActivity.class);
				intent.putExtra("goodsID", goods.getId());
				startActivity(intent);
				break;
			case R.id.detailgoods_button_location:
				Intent it = new Intent(DetailGoodsActivity.this,MapLocationActivity.class);
				it.putExtra("goods", goods);
				startActivity(it);
				break;
			case R.id.detailgoods_button_phone:
				if(user != null && user.getTelephone()!=null){
					intent = new Intent();
					intent.setAction(Intent.ACTION_DIAL);
					intent.setData(Uri.parse("tel:"+ user.getTelephone()));
					startActivity(intent);
				}else{
					Toast.makeText(this, getResources().getString(R.string.detailgoods_no_phone), 3000).show();
				}
				break;
			case R.id.detailgoods_button_sms:
				if(user != null && user.getTelephone()!=null){
					Uri uri = Uri.parse("smsto:"+user.getTelephone());
					intent = new Intent(Intent.ACTION_SENDTO, uri); 
					intent.putExtra("sms_body", "The SMS text"); 
					startActivity(intent); 
				}else{
					Toast.makeText(this, getResources().getString(R.string.detailgoods_no_phone), 3000).show();
				}
				break;
			case R.id.detailgoods_image:
				//blow up picture
				if(bitmap != null){
					openPopupwin(bitmap);
				}else{
					Toast.makeText(DetailGoodsActivity.this, getResources().getString(R.string.detailgoods_no_get_pic), 3000).show();
				}
				break;
			case R.id.detailgoods_tab_leave_note:
				intent = new Intent(this, LeaveNoteActivity.class);
				intent.putExtra("goodsID", goods.getId());
				startActivity(intent);
				break;
			case R.id.detailgoods_tab_modify:
				if(app.getUid().equals(goods.getCreateUser())){
//					intent = new Intent(this, ModifyActivity.class);
//					startActivity(intent);
				}else{
					Toast.makeText(this, getResources().getString(R.string.detailgoods_not_modify), 3000).show();
				}
				break;
			case R.id.detailgoods_tab_notice:
				new DetailTask().execute(REQUEST_ATTENTION, String.valueOf(goods.getId()));
				Toast.makeText(this, getResources().getString(R.string.detailgoods_notice), 3000).show();
				break;
			case R.id.detailgoods_tab_state:
				if(app.getUid().equals(goods.getCreateUser())){
					showDialog(STATE_DIALOG);
				}else{
					Toast.makeText(this, getResources().getString(R.string.detailgoods_not_modify), 3000).show();
				}
				break;
			case R.id.main_title_left:
				finish();
				break;
			case R.id.main_title_right:
				intent = new Intent(this, MainPageActivity.class);
				startActivity(intent);
				break;
			case R.id.detailgoods_popup_title_bt_left:
				popupWindow.dismiss();
				break;
			case R.id.detailgoods_popup_title_bt_right:
				//store to public picture folder
				FileUtil.createExternalStoragePublicPicture();
				ImageUtil.saveBitmapToSdcard(bitmap, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString(), String.valueOf(goods.getId()));
				Toast.makeText(DetailGoodsActivity.this, getResources().getString(R.string.detailgoods_popup_store_ok), 3000).show();
				break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id){
			case STATE_DIALOG:
				buildDialog(DetailGoodsActivity.this);
				break;
			default:
				break;	
		}
		return super.onCreateDialog(id);
	}
	
	private Dialog buildDialog(final Context context) {
		// TODO Auto-generated method stub
		final CharSequence[] items = this.getResources().getStringArray(R.array.detailgoods_change_state);
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle(getResources().getString(R.string.detailgoods_modify_goods_state));
		ab.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(goods.getGoodsState() == which){
					Toast.makeText(context, getResources().getString(R.string.detailgoods_change_state), 3000).show();
				}else{
					goods.setGoodsState(which);
					state.setText(R.string.detailgoods_state);
					if(which == 1){
						state.append(getResources().getString(R.string.detailgoods_sell_now));
					}else{
						state.append(getResources().getString(R.string.detailgoods_selled));
					}
					Spannable sp = (Spannable) state.getText();
					sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_gray)), 0, 2,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					goodsDB.update(goods);
					new DetailTask().execute(REQUEST_STATE,String.valueOf(goods.getId()),String.valueOf(which));
				}
			}
		});
		ab.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		ab.show();
		return ab.create();
		
	}
	
	private class DetailTask extends UserTask<String, Object, Boolean>{
		int type = 0;
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(params[0].equals(REQUEST_USER)){
				//request user
				user = getUser(params[1]);
				type = -1;
			}else if(params[0].equals(REQUEST_ATTENTION)){
				//request attention
				sendAttention(params[1]);
			}else if(params[0].equals(REQUEST_IMAGE)){
				//request image,if not exists in Sdcard and then download and store to folder
				if((bitmap = getImageFormAppPic(String.valueOf(goods.getId())))!=null){
					publishProgress(bitmap);
				}
				else{
					try {
						bitmap = ImageUtil.getBitmapFromUrl(params[1]);
//						bitmap = ImageUtil.getBitmapFromUrl("http://192.168.1.111:8080/pic/2.jpg");
						publishProgress(bitmap);
						//save to Sdcard
						ImageUtil.saveBitmapToAppPic(bitmap, String.valueOf(goods.getId()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(params[0].equals(REQUEST_STATE)){
				//request the state change
				sendStateChange(params[1], params[2]);
			}
			return true;
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if(values[0] != null){
				Bitmap bitmap = (Bitmap) values[0];
				Bitmap bitmap1 = ImageUtil.zoomBitmap(bitmap, 50, 50);
				icon.setImageBitmap(ImageUtil.toRoundCorner(bitmap1, 5));
				int width = (int) (getWindowManager().getDefaultDisplay().getWidth()*0.8);
				int height = (int) (getWindowManager().getDefaultDisplay().getHeight()*0.6);
				Bitmap bitmap2 = ImageUtil.zoomBitmapByScale(bitmap, width, height);
				image.setImageBitmap(bitmap2);
			}else{
				Toast.makeText(DetailGoodsActivity.this, getResources().getString(R.string.detailgoods_download_fail), 3000).show();
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(type == -1 && user != null){
				if(user.getLocation() == null){
					locationTextView.append(getResources().getString(R.string.detailgoods_not_location));
				}else{
					locationTextView.append(user.getLocation());
				}
			}
		}
		
		
		
	}
	
	private User getUser(String userID){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		User user = null;
			try {
				is = httpClient.doHttpPost2(URLConstant.USER, new BasicNameValuePair("userID", userID));
//				is = getAssets().open("user.xml");
				user = userParser.parser(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("user parser", "DetailGoodsActivity parser error");
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
		return user;
	}
	
	private void sendAttention(String goodsID){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		try {
				is = httpClient.doHttpPost2(URLConstant.ATTENTION, new BasicNameValuePair("goodsID", goodsID));
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
	}
	
	private void sendStateChange(String goodsID, String state){
		HttpClient httpClient = new HttpClient(HttpClient.createHttpClient(), null);
		InputStream is = null;
		try {
				is = httpClient.doHttpPost2(URLConstant.GOODS_STATE_CHANGE, new BasicNameValuePair("goodsID", goodsID),
							new BasicNameValuePair("goodsState", state));
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
	}
	
	private Bitmap getImageFormAppPic(String name){
		Bitmap bitmap = null;
		String path = FileUtil.getAppPicFolder();
		if(FileUtil.isImageFileExists(new File(path), name, "jpg")){
			bitmap = ImageUtil.getBitmapFromAppPic(name);
		}
		return bitmap;
	}
	
	
	private final void openPopupwin(Bitmap bitmap) {
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.detailgoods_show_pic, null, true);
		Button back = (Button) menuView.findViewById(R.id.detailgoods_popup_title_bt_left);
		back.setOnClickListener(this);
		Button store = (Button) menuView.findViewById(R.id.detailgoods_popup_title_bt_right);
		store.setOnClickListener(this);
		ImageView img = (ImageView)menuView.findViewById(R.id.detailgoods_pic);
		int w = (int) (this.getWindowManager().getDefaultDisplay().getWidth()*0.9);
		int h = (int) (this.getWindowManager().getDefaultDisplay().getHeight()*0.8);
		bitmap = ImageUtil.zoomBitmapByScale(bitmap, w, h);
		img.setImageBitmap(bitmap);
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(findViewById(R.id.detailgoods_layout), Gravity.CENTER
				| Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
	
}
