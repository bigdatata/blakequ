package com.starnet.android.pisces.compass;

import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class compass extends Activity{
	private static final String TAG = "Compass";

	private ImageView ImgCompass;
	private TextView OrientText;
//	private SensorManager sm = null;
	private SensorManagerSimulator sm = null;
	private RotateAnimation myAni = null;
	private float DegressQuondam = 0.0f;

	private AlphaAnimation myAnimation_Alpha;
	private ImageView iv;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "Compass: onCreate");
		
		super.onCreate(savedInstanceState);

		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		
		iv = (ImageView) findViewById(R.id.ivStart);
		//设置 
		myAnimation_Alpha=new AlphaAnimation(1.0f, 0.1f);
		myAnimation_Alpha.setDuration(3000);
		myAnimation_Alpha.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				setContentView(R.layout.compass);
				
				OrientText = (TextView) findViewById(R.id.OrientText);
				ImgCompass = (ImageView) findViewById(R.id.ivCompass);
			}
		});

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,  
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	@Override
	public void onStart() {
		Log.e(TAG, "Compass: onStart");
//		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sm = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);

		super.onStart();
		
		iv.startAnimation(myAnimation_Alpha);
	}	

	@Override
	protected void onResume() {
		Log.e(TAG, "Compass: onResume");
		super.onResume();
//		sm.registerListener(listener, SensorManager.SENSOR_ORIENTATION
//		 | SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onStop() {
		Log.e(TAG, "Compass: onStop");
		sm.unregisterListener(listener);		
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "旋转").setIcon(R.drawable.icon);
		menu.add(0, 2, 2, "关于").setIcon(R.drawable.icon);
		menu.add(0, 3, 3, "退出").setIcon(R.drawable.quit);

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			AniRotateImage(DegressQuondam + 90.0f);
			break;
		case 2: {
			Intent theActivity = new Intent(this, about.class);
	        startActivity(theActivity);
			break;
		}
		case 3:
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void AniRotateImage(float fDegress) {
		Log.d(TAG, "Degress: " + DegressQuondam + ", " + fDegress);
		myAni = new RotateAnimation(DegressQuondam, fDegress,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		myAni.setDuration(300);
		myAni.setFillAfter(true);

		ImgCompass.startAnimation(myAni);

		DegressQuondam = fDegress;
	}
	
	/*
	private SensorListener listener = new SensorListener() {
		
		@Override
		public void onSensorChanged(int sensor, float[] values) {
			// TODO Auto-generated method stub
			synchronized (this) {
				if (sensor == SensorManager.SENSOR_ORIENTATION) {
					Log.d(TAG, "onSensorChanged: " + sensor + ", x: " + values[0]
							+ ", y: " + values[1] + ", z: " + values[2]);

					// OrientText.setText("--- NESW ---");
					if (Math.abs(values[0] - DegressQuondam) < 1)
						return;
					
					switch ((int) values[0]) {
					case 0: // North 北
						OrientText.setText("正北");
						break;
					case 90: // East 东
						OrientText.setText("正东");
						break;
					case 180: // South 南
						OrientText.setText("正南");
						break;
					case 270: // West 西
						OrientText.setText("正西");
						break;
					default: {
						int v = (int) values[0];
						if (v > 0 && v < 90) {
							OrientText.setText("北偏东" + v);
						}

						if (v > 90 && v < 180) {
							v = 180 - v;
							OrientText.setText("南偏东" + v);
						}

						if (v > 180 && v < 270) {
							v = v - 180;
							OrientText.setText("南偏西" + v);
						}
						if (v > 270 && v < 360) {
							v = 360 - v;
							OrientText.setText("北偏西" + v);
						}
					}
					}

					((TextView) findViewById(R.id.OrientValue)).setText(String.valueOf(values[0]));

					if (DegressQuondam != -values[0])
						AniRotateImage(-values[0]);
				}

				// if (sensor == SensorManager.SENSOR_ACCELEROMETER) { // //}

			}
		}
		
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
		}
	};
	*/
	
	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(
				org.openintents.sensorsimulator.hardware.Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onAccuracyChanged: " + arg0 + ", accuracy: " + arg1);
		}

		@Override
		public void onSensorChanged(
				org.openintents.sensorsimulator.hardware.SensorEvent arg0) {
			// TODO Auto-generated method stub
			synchronized (this) {
				float values[] = arg0.values;
					Log.d(TAG, "onSensorChanged: x: " + values[0]
							+ ", y: " + values[1] + ", z: " + values[2]);
					// OrientText.setText("--- NESW ---");
					if (Math.abs(values[0] - DegressQuondam) < 1)
						return;
					
					switch ((int) values[0]) {
					case 0: // North 北
						OrientText.setText("正北");
						break;
					case 90: // East 东
						OrientText.setText("正东");
						break;
					case 180: // South 南
						OrientText.setText("正南");
						break;
					case 270: // West 西
						OrientText.setText("正西");
						break;
					default: {
						int v = (int) values[0];
						if (v > 0 && v < 90) {
							OrientText.setText("北偏东" + v);
						}

						if (v > 90 && v < 180) {
							v = 180 - v;
							OrientText.setText("南偏东" + v);
						}

						if (v > 180 && v < 270) {
							v = v - 180;
							OrientText.setText("南偏西" + v);
						}
						if (v > 270 && v < 360) {
							v = 360 - v;
							OrientText.setText("北偏西" + v);
						}
					}
					}

					((TextView) findViewById(R.id.OrientValue)).setText(String.valueOf(values[0]));

					if (DegressQuondam != -values[0])
						AniRotateImage(-values[0]);

			}
		}
		
		
	};
}