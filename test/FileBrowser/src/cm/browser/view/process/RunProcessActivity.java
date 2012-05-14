package cm.browser.view.process;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cm.browser.R;
import cm.constant.AppInfo;
import cm.util.BaseActivity;
import cm.util.app.PackageUtil;
import cm.util.app.ProcessMemoryUtil;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RunProcessActivity extends BaseActivity {
	
	private ListView listView;
	private TextView title;
	private ImageButton back;
	private PackageManager packageManager;
	private ProgressDialog pd;
	private Handler handler;
	private List<AppInfo> list = null;
	private PackageUtil packageUtil;
	private ProcessMemoryUtil processMemoryUtil;
	private ActivityManager mActivityManager;
	private MyAdapter adapter;
	private static final int DIALOG = 1;
	private PopupWindow popupWindow;
	private TextView pop_title,processName,cpuMem,total_size,cachesize,datasize,codesize,other,datadir,version;
	public static int RUNPROCESSNUM = 0;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		adapter = new MyAdapter();
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		listView = (ListView) findViewById(R.id.file_list);
		//当前运行进程
		title = (TextView) findViewById(R.id.title_text);
		back = (ImageButton) findViewById(R.id.title_back);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				Bundle bd = new Bundle();
				bd.putSerializable("value", (AppInfo)parent.getItemAtPosition(position));
				bd.putInt("position", position);
				showDialog(DIALOG, bd);
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		packageUtil = new PackageUtil(RunProcessActivity.this);
		processMemoryUtil = new ProcessMemoryUtil();
		packageManager = getPackageManager();
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(getResources().getString(R.string.progress_tips_content));
		pd.show();
		handler = new RefreshHandler();
		RefreshThread thread = new RefreshThread();
		thread.start();// 耗时操作，需要开启一个线程
		initPop();
	}
	
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

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		switch (id) {
		case DIALOG:
			buildDialog(this, bundle);
			break;
		}
		return super.onCreateDialog(id, bundle);
	}



	private Dialog buildDialog(RunProcessActivity runProcessActivity,
			Bundle bundle) {
		// TODO Auto-generated method stub
		final CharSequence[] items = getResources().getStringArray(R.array.run_process);
		final AppInfo pro = (AppInfo) bundle.getSerializable("value");
		final int position = bundle.getInt("position");
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setItems(items, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch(which){
					case 0:
						new AlertDialog.Builder(RunProcessActivity.this)
						.setTitle(getResources().getString(R.string.kill_process))
						.setMessage(getResources().getString(R.string.kill_process_text))  
						.setPositiveButton(getResources().getString(R.string.confirm), new OnClickListener() {  
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//杀死该进程，释放进程占用的空间  
							mActivityManager.killBackgroundProcesses(pro.getProcessName());  
							//刷新界面  
							list.remove(position);
							adapter.notifyDataSetChanged();
							title.setText(list.size() + getResources().getString(R.string.process_number));
							RUNPROCESSNUM = list.size();
						}  
						}).setNegativeButton(getResources().getString(R.string.cancel), new OnClickListener() {  
						
						@Override  
						public void onClick(DialogInterface dialog, int which) {  
							// TODO Auto-generated method stub  
							dialog.cancel() ;  
						}  
						}).create().show() ;
						break;
					case 1:
						System.out.println("pro.getPkgName():"+pro.getPkgName());
						showInstalledAppDetails(RunProcessActivity.this, pro.getPkgName());
						break;
					case 2:
						openPopupwin(pro);
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


	class RefreshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			refreshListItems();
			RUNPROCESSNUM = list.size();
			title.setText(list.size() + getResources().getString(R.string.process_number));
			pd.dismiss();// 关闭进度条
		}
	}

	class RefreshThread extends Thread {
		@Override
		public void run() {
			getRunningAppProcesses();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}
	}
	
	private void refreshListItems() {
		list = getRunningAppProcesses();
		listView.setAdapter(adapter);
	}

	private List<AppInfo> getRunningAppProcesses() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> procList = activityManager.getRunningAppProcesses();
		List list = new ArrayList<AppInfo>();
		for (Iterator<RunningAppProcessInfo> iterator = procList.iterator(); iterator.hasNext();) {
			RunningAppProcessInfo procInfo = iterator.next();
			// 进程ID号  
            int pid = procInfo.pid;  
            // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等  
            int uid = procInfo.uid;  
            // 获得该进程占用的内存  
            int[] myMempid = new int[] { pid }; 
            // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息  
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(myMempid);  
            // 获取进程占内存用信息 kb单位  
            int memSize = memoryInfo[0].dalvikPrivateDirty;  
  
			AppInfo AppInfo = buildProgramUtilSimpleInfo(pid, procInfo.processName);
			AppInfo.setMemSize(memSize);
			list.add(AppInfo);
		}
		return list;
	}
	
	private void returnToHome() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
	
	
	
	public AppInfo buildProgramUtilSimpleInfo(int procId, String procNameString) {
		AppInfo programUtil = new AppInfo();
        programUtil.setProcessName(procNameString);
        // 根据进程名，获取应用程序的ApplicationInfo对象
        ApplicationInfo tempAppInfo = packageUtil.getApplicationInfo(procNameString);

        if (tempAppInfo != null) {
            // 为进程加载图标和程序名称
            programUtil.setAppIcon(tempAppInfo.loadIcon(packageManager));
            programUtil.setAppLabel(tempAppInfo.loadLabel(packageManager).toString());
            programUtil.setDataDir(tempAppInfo.dataDir);
            programUtil.setTargetSdkVersion(tempAppInfo.targetSdkVersion);
            programUtil.setPkgName(tempAppInfo.packageName);
			queryPacakgeSize(tempAppInfo.packageName, programUtil);
        } 
        else {
            // 如果获取失败，则使用默认的图标和程序名
            programUtil.setAppIcon(getApplicationContext().getResources().getDrawable(R.drawable.apk));
            programUtil.setAppLabel(procNameString);
        }
        programUtil.setCup(processMemoryUtil.getCPUSizeByPid(procId));
        return programUtil;
    }
	
	public void  queryPacakgeSize(String pkgName, AppInfo programUtil){  
        if ( pkgName != null){  
            //使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo  
            PackageManager pm = getPackageManager();  //得到pm对象  
            try {  
                /**
                 * 通过反射机制获得该隐藏函数  
                 * 原函数是public abstract void getPackageSizeInfo(String packageName,IPackageStatsObserver observer);
                 */
                Method getPackageSizeInfo = pm.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);  
                //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数  
                getPackageSizeInfo.invoke(pm, pkgName,new PkgSizeObserver(programUtil));  
            }   
            catch(Exception ex){  
                Log.e("RunProcessActivity", "NoSuchMethodException") ;  
                ex.printStackTrace() ;  
            }   
        }  
    }  
     
    //aidl文件形成的Bindler机制服务类  
    public class PkgSizeObserver extends IPackageStatsObserver.Stub{ 
    	AppInfo programUtil;
    	public PkgSizeObserver(AppInfo programUtil){
    		this.programUtil = programUtil;
    	}

    	/*** 回调函数， 
         * @param pStatus ,返回数据封装在PackageStats对象中 
         * @param succeeded  代表回调成功 
         */ 
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			programUtil.setCachesize(pStats.cacheSize);
			programUtil.setDatasize(pStats.dataSize);
			programUtil.setCodesize(pStats.codeSize);
		}  
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
		popupWindow.showAtLocation(findViewById(R.id.main_layout),
				Gravity.CENTER | Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
    
    
    /**
     * 返回应用请求权限列表
     * Array of all {@link android.R.styleable#AndroidManifestUsesPermission
     * &lt;uses-permission&gt;} tags included under &lt;manifest&gt;,
     * or null if there were none
     * @param pkgName
     * @return
     */
    private String[] getRequestedPermissions(String pkgName){
    	List<PackageInfo> infos = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
    	for(PackageInfo info:infos){
    		if(!info.packageName.equals(pkgName)){
    			continue;
    		}else{
    			return info.requestedPermissions;
    		}
    	}
    	return null;
    }
    
    /**
     * Array of all {@link android.R.styleable#AndroidManifestPermission
     * &lt;permission&gt;} tags included under &lt;manifest&gt;,
     * or null if there were none.
     * @param pkgName
     * @return
     */
    private PermissionInfo[] getPermissions(String pkgName){
    	List<PackageInfo> infos = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
    	for(PackageInfo info:infos){
    		if(!info.packageName.equals(pkgName)){
    			continue;
    		}else{
    			return info.permissions;
    		}
    	}
    	return null;
    }
    
	
	class MyAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		
		public MyAdapter(){
			this.inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AppInfo bp = list.get(position);
			View v = convertView;
			ViewHolder viewHolder = null;
			if(v==null){
				v = inflater.inflate(R.layout.proc_list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.img = (ImageView)v.findViewById(R.id.icon);
				viewHolder.tv1 =  (TextView)v.findViewById(R.id.programName);
				viewHolder.tv2 =  (TextView)v.findViewById(R.id.processSize);
				viewHolder.tv3 =  (TextView)v.findViewById(R.id.cpuMemString);
				viewHolder.tv4 = (TextView) v.findViewById(R.id.pkgSize);
				v.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) v.getTag();
			}
			viewHolder.img.setBackgroundDrawable(bp.getAppIcon());
			viewHolder.tv1.setText(bp.getAppLabel());
			viewHolder.tv2.setText(bp.getProcessName());
			viewHolder.tv3.setText("CPU:"+bp.getCup()+" "+getResources().getString(R.string.memory)+":"+getDefaultSize(bp.getMemSize()));
			viewHolder.tv4.setText(getDefaultSize(bp.getTotalSize()));
			return v;
		}
	}
	
	static class ViewHolder{
		private ImageView img;
		private TextView tv1;
		private TextView tv2;
		private TextView tv3;
		private TextView tv4;
	}

}
