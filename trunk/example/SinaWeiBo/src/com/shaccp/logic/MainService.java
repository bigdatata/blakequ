package com.shaccp.logic;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo4andriod.Paging;
import weibo4andriod.Status;
import weibo4andriod.User;
import weibo4andriod.Weibo;
import weibo4andriod.WeiboException;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.shaccp.ui.Home;
import com.shaccp.ui.NewWeibo;
import com.shaccp.util.NetUtil;

public class MainService extends Service implements Runnable {

	public static Weibo weibo = new Weibo();
	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<Task> allTask = new ArrayList<Task>();

	public static HashMap<Integer, BitmapDrawable> alluserIcon = new HashMap<Integer, BitmapDrawable>();
	public static User nowUser;

	public boolean isrun = true;

	public static Activity getActivityByName(String name) {

		for (Activity ac : allActivity) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}

		return null;
	}

	// 启动线程
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public static void newTask(Task task) {
		allTask.add(task);
	}

	// 执行任务，业务逻辑调用，执行完后移出任务
	public void doTask(Task task) {

		Message mess = handler.obtainMessage();
		mess.what = task.getTaskId();
		System.out.println("#####----doTask-----");
		try {
			switch (task.getTaskId()) {

			case Task.TASK_LOGIN:
				weibo.setToken("89c6dc2b03274c37512dc0c9f1d390bb",
						"1efab4c1be9d4c57ab8172561185981e");
				weibo.setUserId((String) task.getParams().get("user"));
				weibo.setPassword((String) task.getParams().get("pass"));
				try {
					User u = weibo.verifyCredentials();
					nowUser = u;
					mess.obj = u;

				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mess.obj = null;
				}

				break;

			case Task.TASK_GET_TIMELINE:
				// task.getParams().get("nowpage");
				// task.getParams().get("pagesize");
				Log.d("nowpage",
						((Integer) task.getParams().get("nowpage")).toString());
				Paging p = new Paging(
						(Integer) task.getParams().get("nowpage"),
						(Integer) task.getParams().get("pagesize"));
				List<Status> allStatus = weibo.getFriendsTimeline();
				System.out.println(allStatus);

				if (alluserIcon == null) {
					alluserIcon = new HashMap<Integer, BitmapDrawable>();
				}

				for (Status st : allStatus) {
					BitmapDrawable bd = alluserIcon.get(st.getUser().getId());
					if(bd == null){
						//获取用户的头像
						HashMap param = new HashMap();
						param.put("uid", st.getUser().getId());
						param.put("url", st.getUser().getProfileImageURL());
						
						Task ts = new Task(Task.TASK_GET_USER_ICON,param);
						MainService.newTask(ts);
						
					}

				}

				mess.obj = allStatus;

				break;
				
			case Task.TASK_GET_USER_ICON:
				int uid = (Integer)task.getParams().get("uid");
				BitmapDrawable bd = NetUtil.getImageFromUrl(
						(URL)task.getParams().get("url"));
				
				alluserIcon.put(uid, bd);
				
				break;

			case Task.TASK_NEW_WEIBO:
				weibo.updateStatus((String) task.getParams().get("blog"));
				break;
			}
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		handler.sendMessage(mess); // 发送消息
		allTask.remove(task); // 执行任务结束，移出任务

	}

	// 侦听任务
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isrun) {
			System.out.println("------RUN-----------");
			Task lastTask = null;
			if (allTask.size() > 0) {
				lastTask = allTask.get(0);
				doTask(lastTask);

			}

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	// 更新UI
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			System.out.println("~~~~Handler~~~~~~~~~");
			switch (msg.what) {
			case Task.TASK_LOGIN:
				IWeiboActivity ia = (IWeiboActivity) getActivityByName("Login");
				ia.refresh(msg.what, msg.obj);
				break;

			case Task.TASK_GET_TIMELINE:
				IWeiboActivity ia2 = (IWeiboActivity) getActivityByName("Home");
				ia2.refresh(new Integer(Home.REFRESH_WEIBO), msg.obj);
				break;
				
			case Task.TASK_GET_USER_ICON:
				
				IWeiboActivity ia4 = (IWeiboActivity) getActivityByName("Home");
				ia4.refresh(new Integer(Home.REFRESH_ICON), msg.obj);
				
				break;

			case Task.TASK_NEW_WEIBO:
				IWeiboActivity ia3 = (IWeiboActivity) getActivityByName("NewWeibo");
				ia3.refresh(new Integer(NewWeibo.NEW_WEIBO), msg.obj);
				break;
			}
		}

	};

	public static void exitApp(Context con) {
		for (Activity ac : allActivity) {
			ac.finish();
		}
		//
		Intent it = new Intent("com.shaccp.logic.MainService");
		con.stopService(it);
	}

}
