package cm.browser.view.process;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cm.browser.R;
import cm.constant.AppInfo;
import cm.util.BaseActivity;
import cm.util.app.ProcessMemoryUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * http://blog.csdn.net/qinjuning/article/list/1
 * @author Administrator
 *
 */
public class SortProcessActivity extends BaseActivity implements View.OnClickListener{

	private ListView listView;
	private PackageManager pm;
	private List<AppInfo> myAppList;
	private Button left, middle, right;
	private View titleView;
	private MyAdapter adapter;
    private List<AppInfo> all_app = null;
    private List<AppInfo> system_app = null;
    private List<AppInfo> third_app = null;
    private ProgressDialog pd;
    private static final int DIALOG = 1;
    private PopupWindow popupWindow;
    private ProcessMemoryUtil processMemoryUtil;
    private TextView pop_title,processName,cpuMem,total_size,cachesize,datasize,codesize,other,datadir,version;
    public static int ALLPROCESS = 0, SYSTEMPROCESS = 0, USERPROCESS = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process_list);
		myAppList = new ArrayList<AppInfo>();
		all_app = new ArrayList<AppInfo>();
		system_app = new ArrayList<AppInfo>();
		third_app = new ArrayList<AppInfo>();
		processMemoryUtil = new ProcessMemoryUtil();
		initView();
		adapter = new MyAdapter();
		listView = (ListView) findViewById(R.id.process_list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bd = new Bundle();
				bd.putSerializable("value", (AppInfo)parent.getItemAtPosition(position));
				showDialog(DIALOG, bd);
			}
		});
		listView.setAdapter(adapter);
		new Thread(refreshRun).start();
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(getResources().getString(R.string.progress_tips_content));
		pd.show();
		initPop();
	}
	
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			myAppList = all_app;
			ALLPROCESS = all_app.size();
			SYSTEMPROCESS = system_app.size();
			USERPROCESS = third_app.size();
			adapter.notifyDataSetChanged();
			pd.dismiss();
		}
	};
	
	Runnable refreshRun = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			queryFilterAppInfo();
			myHandler.sendEmptyMessage(0);
		}
	};
	
	private void initPop() {
		// TODO Auto-generated method stub
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.process_pop, null, true);
		menuView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		Resources res = getResources();
		pop_title = (TextView) menuView.findViewById(R.id.process_pop_title);
		processName = (TextView) menuView.findViewById(R.id.process_pop_processName);
		cpuMem = (TextView) menuView.findViewById(R.id.process_pop_cpuMem);
		total_size = (TextView) menuView.findViewById(R.id.process_pop_total_size);
		cachesize = (TextView) menuView.findViewById(R.id.process_pop_cachesize);
		datasize = (TextView) menuView.findViewById(R.id.process_pop_datasize);
		codesize = (TextView) menuView.findViewById(R.id.process_pop_codesize);
		other = (TextView) menuView.findViewById(R.id.process_pop_other);
		datadir = (TextView) menuView.findViewById(R.id.process_pop_datadir);
		version = (TextView) menuView.findViewById(R.id.process_pop_version);
		
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
	}
	
	
	private void openPopupwin(AppInfo pro) {
    	Resources res = getResources();
    	pop_title.setText(pro.getAppLabel());
    	processName.setText(res.getString(R.string.processname)+":"+pro.getProcessName());
    	cpuMem.setText("CPU:"+pro.getCup()+" "+getResources().getString(R.string.memory)+":"+getDefaultSize(pro.getMemSize()));
    	total_size.setText("-----"+res.getString(R.string.total_size)+":"+getDefaultSize(pro.getTotalSize())+"-----");
    	cachesize.setText(res.getString(R.string.cachesize)+":"+getDefaultSize(pro.getCachesize()));
    	datasize.setText(res.getString(R.string.datasize)+":"+getDefaultSize(pro.getDatasize()));
    	codesize.setText(res.getString(R.string.codesize)+":"+getDefaultSize(pro.getCodesize()));
    	other.setText("--------其他属性--------");
    	datadir.setText(res.getString(R.string.datadir)+":"+pro.getDataDir());
    	version.setText(res.getString(R.string.version)+":"+pro.getTargetSdkVersion());
		popupWindow.showAtLocation(findViewById(R.id.process_list_layout),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		switch(id){
			case DIALOG:
				buildDialog(args);
				break;
		}
		return super.onCreateDialog(id, args);
	}
	
	private Dialog buildDialog(Bundle bundle){
		final CharSequence[] items = getResources().getStringArray(R.array.default_process);
		final AppInfo info = (AppInfo) bundle.getSerializable("value");
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setItems(items, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch(which){
					case 0:
						try{
						//通过程序的报名创建URI 
						 Uri packageURI = Uri.parse("package:"+info.getPkgName()); 
						 //创建Intent意图 
						 Intent intent = new Intent(Intent.ACTION_DELETE, packageURI); 
						 //执行卸载程序 
						 startActivity(intent);
						}catch(ActivityNotFoundException e){
							Toast.makeText(SortProcessActivity.this, getResources().getString(R.string.unstail_fail), 3000).show();
							e.printStackTrace();
						}
						break;
					case 1:
						showInstalledAppDetails(SortProcessActivity.this, info.getPkgName());
						break;
					case 2:
						openPopupwin(info);
						break;
					case 3:
						dialog.dismiss();
						break;
				}
			}
		});
		ab.show();
		return ab.create();
	}

	private void initView() {
		// TODO Auto-generated method stub
		titleView = findViewById(R.id.title_button);
		left = (Button) titleView.findViewById(R.id.button_left);
		left.setOnClickListener(this);
		left.setBackgroundResource(R.drawable.title_button_group_left_selected);
		middle = (Button) titleView.findViewById(R.id.button_middle);
		middle.setOnClickListener(this);
		right = (Button) titleView.findViewById(R.id.button_right);
		right.setOnClickListener(this);
	}
	
	/**
	 *  根据查询条件，查询特定的ApplicationInfo  
	 * @param filter
	 * @return
	 */
    private void queryFilterAppInfo() {  
        pm = this.getPackageManager();  
        // 查询所有已经安装的应用程序  
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);  
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序  
//        List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo  
        for(ApplicationInfo app : listAppcations){
        	//所有应用程序
        	all_app.add(getAppInfo(app));
            //系统程序  
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {  
                system_app.add(getAppInfo(app)); 
            }
            
            //非系统程序  
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  
                third_app.add(getAppInfo(app));  
            }   
            //本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了  
            else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){  
            	third_app.add(getAppInfo(app));  
            }
        }
    }  
    
    /**
     *  构造一个AppInfo对象 ，并赋值  
     * @param app
     * @return
     */
    private AppInfo getAppInfo(ApplicationInfo app) {  
        AppInfo appInfo = new AppInfo();  
        appInfo.setAppLabel((String) app.loadLabel(pm));  
        appInfo.setAppIcon(app.loadIcon(pm));  
        appInfo.setPkgName(app.packageName);  
        appInfo.setDataDir(app.dataDir);
        appInfo.setTargetSdkVersion(app.targetSdkVersion);
        appInfo.setProcessName(app.processName);
        appInfo.setCup("0%");
        appInfo.setMemSize(0);
        queryPacakgeSize(app.packageName, appInfo);
        return appInfo;  
    }  


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button_left:
				if(all_app != null){
					myAppList = all_app;
					adapter.notifyDataSetChanged();
				}
				left.setBackgroundResource(R.drawable.title_button_group_left_selected);
				middle.setBackgroundResource(R.drawable.title_button_group_middle_normal);
				right.setBackgroundResource(R.drawable.title_button_group_right_normal);
				break;
			case R.id.button_middle:
				if(system_app != null){
					myAppList = third_app;
					adapter.notifyDataSetChanged();
				}
				left.setBackgroundResource(R.drawable.title_button_group_left_normal);
				middle.setBackgroundResource(R.drawable.title_button_group_middle_selected);
				right.setBackgroundResource(R.drawable.title_button_group_right_normal);
				break;
			case R.id.button_right:
				if(third_app != null){
					myAppList = system_app;
					adapter.notifyDataSetChanged();
				}
				left.setBackgroundResource(R.drawable.title_button_group_left_normal);
				middle.setBackgroundResource(R.drawable.title_button_group_middle_normal);
				right.setBackgroundResource(R.drawable.title_button_group_right_selected);
				break;
		}
	}
	
	public void  queryPacakgeSize(String pkgName, AppInfo appInfo){  
        if ( pkgName != null){  
            //使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo  
            PackageManager pm = getPackageManager();  //得到pm对象  
            try {  
                //通过反射机制获得该隐藏函数  
                Method getPackageSizeInfo = pm.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);  
                //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数  
                getPackageSizeInfo.invoke(pm, pkgName,new PkgSizeObserver(appInfo));  
            }   
            catch(Exception ex){  
                Log.e("RunProcessActivity", "NoSuchMethodException") ;  
                ex.printStackTrace() ;  
            }   
        }  
    }  
     
    //aidl文件形成的Bindler机制服务类  
    public class PkgSizeObserver extends IPackageStatsObserver.Stub{ 
    	AppInfo appInfo;
    	public PkgSizeObserver(AppInfo appInfo){
    		this.appInfo = appInfo;
    	}

    	/*** 回调函数， 
         * @param pStatus ,返回数据封装在PackageStats对象中 
         * @param succeeded  代表回调成功 
         */ 
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			appInfo.setCachesize(pStats.cacheSize);
			appInfo.setDatasize(pStats.dataSize);
			appInfo.setCodesize(pStats.codeSize);
		}  
    }
	
	private class MyAdapter extends BaseAdapter{
		LayoutInflater inflater;
		public MyAdapter(){
			inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myAppList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return myAppList.get(position);
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
				convertView = inflater.inflate(R.layout.process_list_item, null);
				holder.icon = (ImageView) convertView.findViewById(R.id.process_icon);
				holder.name = (TextView) convertView.findViewById(R.id.process_name);
				holder.pkgName = (TextView) convertView.findViewById(R.id.process_pkg_name);
				holder.pkgSize = (TextView) convertView.findViewById(R.id.process_total_size);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			AppInfo info = myAppList.get(position);
			if(info != null){
				holder.icon.setImageDrawable(info.getAppIcon());
				holder.name.setText(info.getAppLabel());
				holder.pkgName.setText(info.getPkgName());
				holder.pkgSize.setText(getDefaultSize(info.getTotalSize()));
			}
			return convertView;
		}
		
		class Holder{
			ImageView icon;
			TextView pkgName;
			TextView pkgSize;
			TextView name;
		}
		
	}

}
