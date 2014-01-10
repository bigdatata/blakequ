package cm.exchange.ui;

import java.util.Calendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
import cm.exchange.R;
import cm.exchange.ui.map.MapLocationActivity;
import cm.exchange.util.BaseActivity;
import cm.exchange.util.LocationHelper;
import cm.exchange.util.UserTask;
import cm.exchange.util.LocationHelper.LocationResult;

public class LogoActivity extends BaseActivity {

	LocationHelper locHelper;
	Location myLocation = null;
	private boolean hasLocation = false,isGetMyLocation = false;
	ExchangeApplication app = null;
	AlphaAnimation aa;
	ImageView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		locHelper = new LocationHelper(this);
		app = (ExchangeApplication) getApplicationContext();
		// 去掉Activity标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉任务条
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_logo);
        view = (ImageView) findViewById(R.id.ImageView01);
        getMyLocation();

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isGetMyLocation){
			locHelper.getLocation(locationResult);
			new GetLocationTask().execute();
		}
	}
	
	private void getMyLocation(){
		if(!locHelper.enableMyLocation()){
			locHelper.openLocationSetting();
			isGetMyLocation = true;
		}else{
			//begin
			locHelper.getLocation(locationResult);
			new GetLocationTask().execute();
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
    
    private class GetLocationTask extends UserTask<String, Boolean, Boolean>{
    	private final ProgressDialog dialog = new ProgressDialog(LogoActivity.this);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			aa=new AlphaAnimation(0.1f,1.0f);
			aa.setDuration(1000);
	        view.startAnimation(aa);
	        dialog.setMessage(getResources().getString(R.string.map_get_location_now));
	        dialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			Long t = Calendar.getInstance().getTimeInMillis();
			while (!hasLocation && Calendar.getInstance().getTimeInMillis() - t < 15000) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(this.dialog.isShowing())
			{
				this.dialog.dismiss();
			}
			if(myLocation != null){
				app.setLocation(myLocation);
			}else{
				Toast.makeText(LogoActivity.this, getResources().getString(R.string.no_location), 3000).show();
			}
			Intent it=new Intent(LogoActivity.this,LoginActivity.class);
			LogoActivity.this.startActivity(it);
			finish();
		}
    }
	
}
