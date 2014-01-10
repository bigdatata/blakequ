package cm.exchange.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * running background using for keep watch on the user task and activity
 * @author qh
 *
 */
public class BaseService extends Service {

	public static boolean isrun = false;
//	public static BaseService mainService;
	@SuppressWarnings("unchecked")
	public static List<UserTask> taskList = new LinkedList<UserTask>();
    public static List<Activity> activityList = new LinkedList<Activity>(); 
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	

}
