package cm.browser.view.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cm.browser.R;
import cm.constant.MyServiceInfo;
import cm.util.BaseActivity;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BackServiceActivity extends BaseActivity implements OnItemClickListener{
	ListView listView;
	private TextView title;
	private ImageButton back;
	private List<MyServiceInfo> serviceList;
	private MyAdapter adapter;
	private ActivityManager mActivityManager = null;
	private static final String TAG = "BackServiceActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		serviceList = new ArrayList<MyServiceInfo>(); //用来保存所有进程信息
		adapter = new MyAdapter();
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		listView = (ListView) findViewById(R.id.file_list);
		listView.setOnItemClickListener(this);
		title = (TextView) findViewById(R.id.title_text);
		back = (ImageButton) findViewById(R.id.title_back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		getRunningServiceInfo();
		// 对集合排序  
        Collections.sort(serviceList, new comparatorServiceLable());  
        listView.setAdapter(adapter);
        title.setText(getResources().getString(R.string.current_service)+serviceList.size());
	}
	
	/**
	 *  获得系统正在运行的进程信息  
	 */
    private void getRunningServiceInfo() {  
  
        // 设置一个默认Service的数量大小  
        int defaultNum = 20;  
        // 通过调用ActivityManager的getRunningAppServicees()方法获得系统里所有正在运行的进程  
        List<ActivityManager.RunningServiceInfo> runServiceList = mActivityManager.getRunningServices(defaultNum);  
        for (ActivityManager.RunningServiceInfo runServiceInfo : runServiceList) {  
            // 获得Service所在的进程的信息  
            int pid = runServiceInfo.pid; // service所在的进程ID号  
            int uid = runServiceInfo.uid; // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等  
            String processName = runServiceInfo.process; //进程名，默认是包名或者由属性android：process指定    
            long activeSince = runServiceInfo.activeSince; // 该Service启动时的时间值 
            int clientCount = runServiceInfo.clientCount; // 如果该Service是通过Bind方法方式连接，则clientCount代表了service连接客户端的数目
            long lastActivityTime = runServiceInfo.lastActivityTime;
            boolean foreground = runServiceInfo.foreground;
  
            // 获得该Service的组件信息 可能是pkgname/servicename  
            ComponentName serviceCMP = runServiceInfo.service;  
            String serviceName = serviceCMP.getShortClassName(); //service 的类名  
            String pkgName = serviceCMP.getPackageName(); //包名  
  
            //这儿我们通过service的组件信息，利用PackageManager获取该service所在应用程序的包名 ，图标等  
            PackageManager mPackageManager = this.getPackageManager(); // 获取PackagerManager对象;  
            try {
                //获取该pkgName的信息 
                ApplicationInfo appInfo = mPackageManager.getApplicationInfo(pkgName, 0);  
                MyServiceInfo runService = new MyServiceInfo();  
                runService.setAppIcon(appInfo.loadIcon(mPackageManager));  
                runService.setAppLabel(appInfo.loadLabel(mPackageManager) + "");  
                runService.setServiceName(serviceName);  
                runService.setPkgName(pkgName);
                Intent intent = new Intent();  // 设置该service的组件信息 
                intent.setComponent(serviceCMP);  
                runService.setIntent(intent);  
                runService.setPid(pid);  
                runService.setProcessName(processName);  
                runService.setActiveSince(activeSince);
                runService.setClientCount(clientCount);
                runService.setLastActivityTime(lastActivityTime);
                serviceList.add(runService);  
  
            } catch (NameNotFoundException e) {  
                // TODO Auto-generated catch block  
                Log.e(TAG,"---------------NameNotFoundException error -------------");  
                e.printStackTrace();  
            }  
  
        }  
    }  
	
    /**
     * 自定义排序 根据AppLabel排序  
     * @author Administrator
     *
     */
    private class comparatorServiceLable implements Comparator<MyServiceInfo> {  
  
        @Override  
        public int compare(MyServiceInfo object1, MyServiceInfo object2) {  
            // TODO Auto-generated method stub  
            return object1.getAppLabel().compareTo(object2.getAppLabel());  
        }  
  
    }
	
	private class MyAdapter extends BaseAdapter{
		LayoutInflater inflater = null;
		public MyAdapter(){
			inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return serviceList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return serviceList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = null;
			if(convertView == null){
				holder = new Holder();
				convertView = inflater.inflate(R.layout.service_list_item, null);
				holder.icon = (ImageView) convertView.findViewById(R.id.service_icon);
				holder.tv1 = (TextView) convertView.findViewById(R.id.service_label_name);
				holder.tv2 = (TextView) convertView.findViewById(R.id.service_name);
				holder.tv3 = (TextView) convertView.findViewById(R.id.service_pkg_name);
				holder.tv4 = (TextView) convertView.findViewById(R.id.service_process_name);
				holder.tv5 = (TextView) convertView.findViewById(R.id.service_time);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			MyServiceInfo info = serviceList.get(position);
			if(info != null){
				holder.icon.setImageDrawable(info.getAppIcon());
				holder.tv1.setText(info.getAppLabel());
				holder.tv2.setText(getResources().getString(R.string.service_name)+info.getServiceName());
				holder.tv3.setText(getResources().getString(R.string.service_pkg)+info.getPkgName());
				holder.tv4.setText(getResources().getString(R.string.service_pro)+info.getProcessName());
				holder.tv5.setText(getResources().getString(R.string.service_start)+" "+getTime(info.getActiveSince())+getResources().getString(R.string.service_last)+getTime(info.getLastActivityTime()));
			}
			return convertView;
		}
		
		class Holder{
			ImageView icon;
			TextView tv1;
			TextView tv2;
			TextView tv3;
			TextView tv4;
			TextView tv5;
		}
		
	}
	
	private String getTime(long time){
		time/=1000;
		long minute = time/60;
		long hour = minute/60;
		long second = time%60;
		minute %= 60;
		if(hour == 0) return String.format("%02d:%02d",minute,second);
		return String.format("%02d:%02d:%02d", hour,minute,second);
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		final Intent stopserviceIntent = serviceList.get(position).getIntent();
		new AlertDialog.Builder(BackServiceActivity.this).setTitle(getResources().getString(R.string.service_stop))
				.setMessage(getResources().getString(R.string.service_notice))
				.setPositiveButton(getResources().getString(R.string.stop), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 停止该Service
						// 由于权限不够的问题，为了避免应用程序出现异常，捕获该SecurityException ，并弹出对话框
						try {
							stopService(stopserviceIntent);
						} catch (SecurityException sEx) {
							// 发生异常 说明权限不够
							new AlertDialog.Builder(BackServiceActivity.this)
									.setTitle(getResources().getString(R.string.service_not_permission))
									.setMessage(getResources().getString(R.string.service_permission_text))
									.create()
									.show();
						}
						// 刷新界面
						// 获得正在运行的Service信息
						serviceList.clear();
						getRunningServiceInfo();
						// 对集合排序
						Collections.sort(serviceList, new comparatorServiceLable());
						adapter.notifyDataSetChanged();
						title.setText(getResources().getString(R.string.current_service)+serviceList.size());
					}

				}).setNegativeButton(getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).create().show();
	}

}
