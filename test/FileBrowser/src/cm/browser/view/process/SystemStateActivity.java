package cm.browser.view.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import cm.browser.R;
import cm.browser.ScanFile;
import cm.util.BaseActivity;
import cm.util.app.SystemInfoUtil;

public class SystemStateActivity extends BaseActivity {
	TextView state, phone, store,store_total;
	ScanFile scanFile;
	double[] sdcardSize;
	double videoSize, musicSize, picSize, otherSize;
	int allProcess,runProcess,userProcess,sysProcess;
	String sysdevice,syspro;
	int all=0, user=0, sys=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_state);
		state = (TextView) findViewById(R.id.system_state_text);
		state.setText(getResources().getString(R.string.get_now));
		phone = (TextView) findViewById(R.id.system_phone_text);
		phone.setText(getResources().getString(R.string.get_now));
		store = (TextView) findViewById(R.id.system_store_text);
		store.setText(getResources().getString(R.string.get_now));
		store_total = (TextView) findViewById(R.id.system_store_total);
		store_total.setText(getResources().getString(R.string.get_now));
		scanFile = ScanFile.getInstance();
		new Thread(task).start();
		if(SortProcessActivity.ALLPROCESS == 0){
			new Thread(task2).start();
		}
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Resources res = getResources();
			System.out.println("what："+msg.what);
			switch(msg.what){
			case 0:
				StringBuilder sb;
				store_total.setText(res.getString(R.string.total_size)+":"+getDefaultSize(sdcardSize[0])+"\n"+res.getString(R.string.last_size)+":"+getDefaultSize(sdcardSize[1]));
				sb = new StringBuilder();
				String[] str = getResources().getStringArray(R.array.file_sort);
				sb.append(str[0]+":"+getDefaultSize(videoSize)+" \n");
				sb.append(str[1]+":"+getDefaultSize(musicSize)+" \n");
				sb.append(str[2]+":"+getDefaultSize(picSize)+" \n");
				sb.append(res.getString(R.string.other)+":"+getDefaultSize(otherSize)+" ");
				store.setText(sb.toString());
				sb = new StringBuilder();
				sb.append(res.getString(R.string.process_all)+":"+allProcess+" 个  \n");
				sb.append(res.getString(R.string.run_process)+":"+runProcess+" 个  \n");
				sb.append(res.getString(R.string.process_user)+":"+userProcess+" 个 \n");
				sb.append(res.getString(R.string.process_system)+":"+sysProcess+" 个");
				phone.setText(sb.toString());
				state.setText(syspro);
				break;
			case 1:
				if(all != 0){
					StringBuilder sbs = new StringBuilder();
					sbs.append(res.getString(R.string.process_all)+":"+all+" 个  \n");
					sbs.append(res.getString(R.string.run_process)+":"+runProcess+" 个  \n");
					sbs.append(res.getString(R.string.process_user)+":"+user+" 个 \n");
					sbs.append(res.getString(R.string.process_system)+":"+sys+" 个");
					phone.setText(sbs.toString());
				}
				break;
			}
		}
		
	};
	
	Runnable task = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/**
			 * 获取存储信息
			 */
			sdcardSize = getSizeOfSdcard();
			/**
			 * 获取分类文件大小
			 */
			videoSize = scanFile.getTotalSize(scanFile.getVideoFile());
			musicSize = scanFile.getTotalSize(scanFile.getAudioFile());
			picSize = scanFile.getTotalSize(scanFile.getImageFile());
			ArrayList<String> file = scanFile.getAppFile();
			file.addAll(scanFile.getDocumentFile());
			file.addAll(scanFile.getRarFile());
			file.addAll(scanFile.getOtherFile());
			otherSize = scanFile.getTotalSize(file);
			/**
			 * 获取手机基本信息(安装系统软件数量，应用，总)
			 */
			allProcess = SortProcessActivity.ALLPROCESS;
			runProcess = RunProcessActivity.RUNPROCESSNUM;
			userProcess = SortProcessActivity.USERPROCESS;
			sysProcess = SortProcessActivity.SYSTEMPROCESS;
			/**
			 * 获取手机状态信息(硬件版本，软件等)
			 */
			sysdevice = SystemInfoUtil.getSystemVersion();
			syspro = SystemInfoUtil.getSystemProperty();
			Message msg = handler.obtainMessage();
			msg.what = 0;
			handler.sendMessage(msg);
		}
	};
	
	Runnable task2 = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			queryFilterAppInfo();
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};
	
	
	private void queryFilterAppInfo() {  
		PackageManager pm = this.getPackageManager();  
        // 查询所有已经安装的应用程序  
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);  
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序  
        for(ApplicationInfo app : listAppcations){
        	//所有应用程序
        	all++;
            //系统程序  
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {  
            	sys++;
            }
            
            //非系统程序  
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  
            	user++;
            }   
            //本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了  
            else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){  
            	user++;
            }
        }
	}
}
