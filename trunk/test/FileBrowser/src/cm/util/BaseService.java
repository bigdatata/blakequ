package cm.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BaseService extends Service {

	 public static List<Activity> activityList = new LinkedList<Activity>(); 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
