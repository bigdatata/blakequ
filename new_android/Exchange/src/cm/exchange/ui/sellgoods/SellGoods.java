package cm.exchange.ui.sellgoods;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cm.exchange.R;
import cm.exchange.entity.Goods;
import cm.exchange.entity.User;
import cm.exchange.net.HttpClient;
import cm.exchange.net.URLConstant;

import cm.exchange.ui.ExchangeApplication;
import cm.exchange.ui.LogoActivity;
import cm.exchange.ui.map.MapLocationActivity;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.LocationHelper;
import cm.exchange.util.UserTask;
import cm.exchange.util.LocationHelper.LocationResult;

public class SellGoods extends BaseActivity {

	private Spinner mySpinner_catagory, mySpinner_state;
	private CheckBox checkBox;
	ImageButton left, right, button_getGPS, button_address, button_picture;
	Button sendpicture_local, sendpicture_camera, sendpicture_cancel;//图片上传
	Button contact_sure, contact_cancel;//个人信息
	private TextView titleText, upload_text;
	private EditText sellgoods_name_edit_text, sellgoods_descript_edit_text,
			sellgoods_telephone_edit_text, sellgoods_qq_edit_text,
			sellgoods_location_edit_text, sellgoods_price_edit_text;
	ViewStub sellgoods_stub_view = null;
	PopupWindow popWinPic, popWinInfo;
	ImageView imageView = null;
	List<Integer> numlist = null;
	Goods goods = null;
	User user = null;
	int price, sellgoods_descript, check_box;
	private String telephoneEditable, qqEditable, locationEditable, imageFilePath;
	LocationHelper locHelper;
	Location myLocation = null;
	private boolean hasLocation = false,isGetMyLocation = false;
	ExchangeApplication app = null;
	protected double longitude, latitude;//经纬度，如果没有就是(0,0)
	private final static String SENDGOODS_DETAILS = "1";
	private final static String SENDGOODS_IMAGE = "2";
	private final static String TASK_LOCATION = "location";
	private final static int DIALOG_ONE = 1;
	private final static int DIALOG_TWO = 2;
	private final static int DIALOG_THREE = 3;
	private final static int DIALOG_FOUR=4;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;
	private final static int LAUNCH_GALLERY = 2;
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellgoods);
		app = (ExchangeApplication) getApplicationContext();
		locHelper = new LocationHelper(this);
		initView();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.goods_assortment,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner_catagory.setAdapter(adapter);
		mySpinner_catagory.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView arg0, View arg1,int arg2, long arg3) {
				arg0.setVisibility(View.VISIBLE);
			}
			@SuppressWarnings("unchecked")
			public void onNothingSelected(AdapterView arg0) {
				arg0.setVisibility(View.VISIBLE);
			}
		});
		mySpinner_catagory.setOnTouchListener(new Spinner.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				v.setVisibility(View.VISIBLE);
				return false;
			}
		});
		mySpinner_catagory.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				v.setVisibility(View.VISIBLE);
			}
		});
		adapter = ArrayAdapter.createFromResource(this, R.array.goods_state,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner_state.setAdapter(adapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(sellgoods_stub_view == null){
			sellgoods_stub_view = (ViewStub) findViewById(R.id.sellgoods_stub_view);
			sellgoods_stub_view.inflate();
			View sellgoods_inflate_layout = findViewById(R.id.sellgoods_inflate_layout);
			imageView = (ImageView) sellgoods_inflate_layout.findViewById(R.id.sellgoods_imageview);
			upload_text = (TextView) sellgoods_inflate_layout.findViewById(R.id.sellgoods_local_upload);
		}
		if (resultCode == NONE)
			return;
		if (requestCode == LAUNCH_GALLERY) {
			Uri uploadPath = data.getData();
			if (uploadPath != null) {
				try {
					Cursor cursor = getContentResolver().query(uploadPath,
							null, null, null, null);
					cursor.moveToFirst();
					imageFilePath = cursor.getString(1);
					cursor.close();
					Drawable drawable = Drawable.createFromPath(imageFilePath);
					imageView.setBackgroundDrawable(drawable);
					upload_text.setText(imageFilePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (requestCode == PHOTOHRAPH) {
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/picture", "goods" + System.currentTimeMillis() + ".jpg");
			if (picture != null) {
				imageFilePath = picture.toString();
				upload_text.setText(imageFilePath);
			}
		}
	}


	private void initView() {
		// TODO Auto-generated method stub
		View view = findViewById(R.id.sellgoods_title);
		left = (ImageButton) view.findViewById(R.id.main_title_left);
		left.setOnClickListener(this);
		right = (ImageButton) view.findViewById(R.id.main_title_right);
		right.setOnClickListener(this);
		titleText = (TextView) view.findViewById(R.id.main_title_text);

		sellgoods_name_edit_text = (EditText) findViewById(R.id.sellgoods_name_edit);
		sellgoods_price_edit_text = (EditText) findViewById(R.id.sellgoods_price_edit);
		sellgoods_descript_edit_text = (EditText) findViewById(R.id.sellgoods_descript_edit);

		button_picture = (ImageButton) findViewById(R.id.sellgoods_picture);
		button_picture.setOnClickListener(this);
		button_address = (ImageButton) findViewById(R.id.sellgoods_address);
		button_address.setOnClickListener(this);
		button_getGPS = (ImageButton) findViewById(R.id.sellgoods_getgps);
		button_getGPS.setOnClickListener(this);
		checkBox = (CheckBox) findViewById(R.id.sellgoods_price_checkbox);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					check_box = 1;

				} else {
					check_box = 0;
				}
			}
		});
		mySpinner_catagory = (Spinner) findViewById(R.id.sellgoods_catagory_spinner);
		mySpinner_state = (Spinner) findViewById(R.id.sellgoods_state_spinner);
	}

	@Override
	public void initTitle() {
		// TODO Auto-generated method stub
		titleText.setText(getResources().getString(R.string.mygoods_sell));
		left.setImageResource(R.drawable.main_title_back_icon);
		right.setImageResource(R.drawable.refresh_icon);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
			case R.id.sellgoods_picture:
				initPopWindowPic();
				break;
			case R.id.sellgoods_address:
				initPopWindowInfo();
				break;
			case R.id.sellgoods_sendpicture_local:
				intent = new Intent(Intent.ACTION_PICK, null);
				intent.setType("image/*");
				intent.putExtra("outputFormat", "JPEG");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_UNSPECIFIED);
				startActivityForResult(Intent.createChooser(intent,"Select Picture"), LAUNCH_GALLERY);
				popWinPic.dismiss();
				break;
			case R.id.sellgoods_sendpicture_camera:
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
										+ "/picture", "goods"+ System.currentTimeMillis()+ ".jpg")));
				startActivityForResult(intent, PHOTOHRAPH);
				popWinPic.dismiss();
				break;
			case R.id.sellgoods_sendpicture_cancel:
				popWinPic.dismiss();
				break;
			case R.id.sellgoods_contact_sure:
				if ("".equals(sellgoods_telephone_edit_text.getText().toString())) {
					showDialog(DIALOG_THREE);
					return;
				} else {
					telephoneEditable = sellgoods_telephone_edit_text.getText().toString();
					qqEditable = sellgoods_qq_edit_text.getText().toString();
					locationEditable = sellgoods_location_edit_text.getText().toString();
					popWinInfo.dismiss();
				}
				break;
			case R.id.sellgoods_contact_cancel:
				popWinInfo.dismiss();
				break;
			case R.id.sellgoods_getgps:
				getMyLocation();
				break;
			case R.id.main_title_left:
				finish();
				break;
			case R.id.main_title_right:
				if ("".equals(sellgoods_name_edit_text.getText().toString())
						|| "".equals(sellgoods_price_edit_text.getText().toString())
						|| "".equals(sellgoods_descript_edit_text.getText().toString())
						|| latitude == 0 || longitude == 0
						|| "".equals(mySpinner_state.getSelectedItem().toString())
						|| "".equals(mySpinner_catagory.getSelectedItem().toString())) {
					showDialog(DIALOG_FOUR);
					return;
	
				} else {
					String name = sellgoods_name_edit_text.getText().toString();
					String catagory = mySpinner_catagory.getSelectedItem()
							.toString();
					String state = mySpinner_state.getSelectedItem().toString();
					String price = sellgoods_price_edit_text.getText().toString();
					String isDiscuss = String.valueOf(check_box);
					
					String longitude_str=Double.toString(longitude);
	                String latitude_str=Double.toString(latitude);
					
					String telephone = telephoneEditable.toString();
					String qq = qqEditable.toString();
					String locations = locationEditable.toString();
					String descript = sellgoods_descript_edit_text.getText()
							.toString();
					new GoodsTask().execute(SENDGOODS_DETAILS, name, catagory,
							state, price, isDiscuss, longitude_str, latitude_str,
							telephone, qq, locations, descript);
				}

		}
	}
	
	//显示图片选择
	private void initPopWindowPic() {
		View contentView = LayoutInflater.from(getApplicationContext())
				.inflate(R.layout.sellgoods_picture, null);

		popWinPic = new PopupWindow(
				findViewById(R.id.sellgoods_main), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popWinPic.setContentView(contentView);
		popWinPic.setFocusable(true);
		popWinPic.setOutsideTouchable(false);
		popWinPic.setAnimationStyle(R.style.PopupAnimation);
		popWinPic.setBackgroundDrawable(new BitmapDrawable());
		popWinPic.showAtLocation(findViewById(R.id.sellgoods_main),
				Gravity.CENTER, 0, 0);
		popWinPic.update();
		//本地上传
		sendpicture_local = (Button) contentView
				.findViewById(R.id.sellgoods_sendpicture_local);
		sendpicture_local.setOnClickListener(this);
		//照相机上传
		sendpicture_camera = (Button) contentView
				.findViewById(R.id.sellgoods_sendpicture_camera);
		sendpicture_camera.setOnClickListener(this);
		//上传取消
		sendpicture_cancel = (Button) contentView.findViewById(R.id.sellgoods_sendpicture_cancel);
		sendpicture_cancel.setOnClickListener(this);

	}

	//显示个人信息完善
	private void initPopWindowInfo() {
		View contentView = LayoutInflater.from(getApplicationContext())
				.inflate(R.layout.sellgoods_contact, null);
		contact_sure = (Button) contentView.findViewById(R.id.sellgoods_contact_sure);
		contact_sure.setOnClickListener(this);
		contact_cancel = (Button) contentView.findViewById(R.id.sellgoods_contact_cancel);
		contact_cancel.setOnClickListener(this);
		popWinInfo = new PopupWindow(findViewById(R.id.sellgoods_main), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		sellgoods_telephone_edit_text = (EditText) contentView.findViewById(R.id.sellgoods_contact_phone_edit);
		sellgoods_qq_edit_text = (EditText) contentView.findViewById(R.id.sellgoods_contact_qq_edit);
		sellgoods_location_edit_text = (EditText) contentView.findViewById(R.id.sellgoods_contact_location_edit);
		popWinInfo.setContentView(contentView);
		popWinInfo.setFocusable(true);
		popWinInfo.setOutsideTouchable(false);
		popWinInfo.setAnimationStyle(R.style.PopupAnimation);
		popWinInfo.setBackgroundDrawable(new BitmapDrawable());
		popWinInfo.showAtLocation(findViewById(R.id.sellgoods_main),Gravity.CENTER, 0, 0);
		popWinInfo.update();
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case DIALOG_ONE:
			return buildDialogOne(getResources().getString(
					R.string.sellgoods_local_fail));

		case DIALOG_TWO:
			return buildDialogOne(getResources().getString(
					R.string.sellgoods_local_ok));

		case DIALOG_THREE:
			return buildDialogOne(getResources().getString(
					R.string.sellgoods_telephone_must));
		case DIALOG_FOUR:
			return buildDialogOne(getResources().getString(R.string.sellgoods_all_must));
			
		}
		return null;
	}

	private Dialog buildDialogOne(String s) {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("Message").setMessage(s).setPositiveButton(
				R.string.confirm, new DialogInterface.OnClickListener()

				{

					public void onClick(DialogInterface dialog, int which)

					{
					}

				});
		ab.show();
		return ab.create();
	}

	private class GoodsTask extends UserTask<String, Object, Boolean> {
		private final ProgressDialog dialog = new ProgressDialog(SellGoods.this);
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (params[0].equals(SENDGOODS_DETAILS)) {
				sendGoods_details(params[1], params[2], params[3], params[4],
						params[5], params[6], params[7], params[8], params[9],
						params[10], params[11]);
			}else if(params[0].equals(TASK_LOCATION)){
				Long t = Calendar.getInstance().getTimeInMillis();
				while (!hasLocation && Calendar.getInstance().getTimeInMillis() - t < 15000) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            };
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			if(isGetMyLocation){
				if(myLocation != null){
					app.setLocation(myLocation);
					longitude = myLocation.getLongitude();
					latitude = myLocation.getLatitude();
				}else{
					longitude = latitude = 0;
					Toast.makeText(SellGoods.this, getResources().getString(R.string.no_location), 3000).show();
				}
				isGetMyLocation = false;
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(isGetMyLocation){
				dialog.setMessage(getResources().getString(R.string.map_get_location_now));
				dialog.show();
			}
		}
		
		
	}
	
	private void sendGoods_details(String name, String catagory,
			String state, String price, String isDiscuss, String longitude_str,
			String latitude_str, String telephone, String qq, String location,
			String descript) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient(HttpClient
				.createHttpClient(), null);
		InputStream is = null;

		try {
			name = URLEncoder.encode(name, "UTF-8");
			location = URLEncoder.encode(location, "UTF-8");
			descript = URLEncoder.encode(descript, "UTF-8");
			is = httpClient.doHttpPost2(URLConstant.GOODS_UPLOAD,
					new BasicNameValuePair("name", name),
					new BasicNameValuePair("catagory", catagory),
					new BasicNameValuePair("state", state),
					new BasicNameValuePair("price", price),
					new BasicNameValuePair("isDiscuss", isDiscuss),
					new BasicNameValuePair("longitude", longitude_str),
					new BasicNameValuePair("latitude", latitude_str),
					new BasicNameValuePair("telephone", telephone),
					new BasicNameValuePair("qq", qq),
					new BasicNameValuePair("location", location),
					new BasicNameValuePair("descript", descript));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void getMyLocation(){
		if(!locHelper.enableMyLocation()){
			locHelper.openLocationSetting();
			isGetMyLocation = true;
		}else{
			//begin
			locHelper.getLocation(locationResult);
			new GoodsTask().execute(TASK_LOCATION);
		}
	}
	
	public LocationResult locationResult = new LocationResult()
    {
        @Override
        public void gotLocation(final Location location)
        {
        	if(location != null){
        		myLocation = new Location(location);
        		hasLocation = true;
        	}else{
        		System.out.println("LocationResult() location is null");
        	}
        }
    };

}