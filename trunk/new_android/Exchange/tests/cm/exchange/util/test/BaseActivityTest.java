package cm.exchange.util.test;

import cm.exchange.util.BaseActivity;
import android.location.Location;
import android.test.AndroidTestCase;

public class BaseActivityTest extends AndroidTestCase{

	public void testGetLocation(){
		Location loc = BaseActivity.getLocation(getContext());
		System.out.println(loc.getLongitude()+" "+loc.getLatitude());
	}
}
