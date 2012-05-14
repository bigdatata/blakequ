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
		serviceList = new ArrayList<MyServiceInfo>(); //�����������н�����Ϣ
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
		// �Լ�������  
        Collections.sort(serviceList, new comparatorServiceLable());  
        listView.setAdapter(adapter);
        title.setText(getResources().getString(R.string.current_service)+serviceList.size());
	}
	
	/**
	 *  ���ϵͳ�������еĽ�����Ϣ  
	 */
    private void getRunningServiceInfo() {  
  
        // ����һ��Ĭ��Service��������С  
        int defaultNum = 20;  
        // ͨ������ActivityManager��getRunningAppServicees()�������ϵͳ�������������еĽ���  
        List<ActivityManager.RunningServiceInfo> runServiceList = mActivityManager.getRunningServices(defaultNum);  
        for (ActivityManager.RunningServiceInfo runServiceInfo : runServiceList) {  
            // ���Service���ڵĽ��̵���Ϣ  
            int pid = runServiceInfo.pid; // service���ڵĽ���ID��  
            int uid = runServiceInfo.uid; // �û�ID ������Linux��Ȩ�޲�ͬ��IDҲ�Ͳ�ͬ ���� root��  
            String processName = runServiceInfo.process; //��������Ĭ���ǰ�������������android��processָ��    
            long activeSince = runServiceInfo.activeSince; // ��Service����ʱ��ʱ��ֵ 
            int clientCount = runServiceInfo.clientCount; // �����Service��ͨ��Bind������ʽ���ӣ���clientCount������service���ӿͻ��˵���Ŀ
            long lastActivityTime = runServiceInfo.lastActivityTime;
            boolean foreground = runServiceInfo.foreground;
  
            // ��ø�Service�������Ϣ ������pkgname/servicename  
            ComponentName serviceCMP = runServiceInfo.service;  
            String serviceName = serviceCMP.getShortClassName(); //service ������  
            String pkgName = serviceCMP.getPackageName(); //����  
  
            //�������ͨ��service�������Ϣ������PackageManager��ȡ��service����Ӧ�ó���İ��� ��ͼ���  
            PackageManager mPackageManager = this.getPackageManager(); // ��ȡPackagerManager����;  
            try {
                //��ȡ��pkgName����Ϣ 
                ApplicationInfo appInfo = mPackageManager.getApplicationInfo(pkgName, 0);  
                MyServiceInfo runService = new MyServiceInfo();  
                runService.setAppIcon(appInfo.loadIcon(mPackageManager));  
                runService.setAppLabel(appInfo.loadLabel(mPackageManager) + "");  
                runService.setServiceName(serviceName);  
                runService.setPkgName(pkgName);
                Intent intent = new Intent();  // ���ø�service�������Ϣ 
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
     * �Զ������� ����AppLabel����  
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
						// ֹͣ��Service
						// ����Ȩ�޲��������⣬Ϊ�˱���Ӧ�ó�������쳣�������SecurityException ���������Ի���
						try {
							stopService(stopserviceIntent);
						} catch (SecurityException sEx) {
							// �����쳣 ˵��Ȩ�޲���
							new AlertDialog.Builder(BackServiceActivity.this)
									.setTitle(getResources().getString(R.string.service_not_permission))
									.setMessage(getResources().getString(R.string.service_permission_text))
									.create()
									.show();
						}
						// ˢ�½���
						// ����������е�Service��Ϣ
						serviceList.clear();
						getRunningServiceInfo();
						// �Լ�������
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
