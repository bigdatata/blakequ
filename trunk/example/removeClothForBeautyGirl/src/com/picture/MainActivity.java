package com.picture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements ViewFactory {
	private ImageSwitcher is;
	private Gallery gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupViews();
		setupListener();
	}
	
	private void setupViews(){
		is = (ImageSwitcher)findViewById(R.id.image_switcher);
		is.setFactory(this);
		gallery = (Gallery)findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(MainActivity.this));
	}
	private void setupListener(){
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int drawableId = 0;
    			try {
    				drawableId = R.drawable.class.getDeclaredField(
    						"pre" + position).getInt(this);
    			} catch (IllegalArgumentException e) {
    				e.printStackTrace();
    			} catch (SecurityException e) {
    				e.printStackTrace();
    			} catch (IllegalAccessException e) {
    				e.printStackTrace();
    			} catch (NoSuchFieldException e) {
    				e.printStackTrace();
    			}
            	is.setImageResource(drawableId);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}  
        });
		
		is.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				Intent intent = new Intent();
				intent.putExtra("imagePosition", gallery.getSelectedItemPosition());
				intent.setClass(MainActivity.this, RemoveClothActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private class ImageAdapter extends BaseAdapter {
		private Context mContext;
		public ImageAdapter(Context c){
			mContext = c;
		}
		@Override
		public int getCount() {
			return 11;//11’≈Õº∆¨
		}
		@Override
		public Object getItem(int position) {
			return position;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);
			int drawableId = 0;
			try {
				drawableId = R.drawable.class.getDeclaredField(
						"pre" + position).getInt(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			imageView.setLayoutParams(new Gallery.LayoutParams(120,120));
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageResource(drawableId);
			return imageView;
		}
	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	};
}
