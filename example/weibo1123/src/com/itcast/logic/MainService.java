package com.itcast.logic;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.itcast.db.OAuthReadUtil;
import com.itcast.ui.HomeActivity;
import com.itcast.ui.LoginActivity;
import com.itcast.ui.MSGActivity;
import com.itcast.ui.MainActivity;
import com.itcast.ui.R;
import com.itcast.util.NetUtil;

import weibo4j.Comment;
import weibo4j.DirectMessage;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.ImageItem;
import weibo4j.http.RequestToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

//系统调度模块
public class MainService extends Service implements Runnable{
	//之所以要用static全局引用，主要是为了节省对象生成的开销
    public static boolean isrun=false;
    public static User nowUser=null;
    public static MainService mainService;//全局静态引用
    public NetUtil netReceiver=new NetUtil();
    // 保存所有任务对象
	private static ArrayList<Task> allTask=new ArrayList<Task>();
	//定义微博处理对象
	public Weibo weibo=new Weibo();
	//保存所有 Activity 

	public static ArrayList<IWeiboActivity> allActivity=new ArrayList<IWeiboActivity>();
	//保存所有的用户头像
	public static HashMap<Integer,BitmapDrawable> allIcon=
		             new HashMap<Integer,BitmapDrawable>();
	//定义错误信息
	public int weiboerror=100;
	public MainService()
	{
		mainService=this;
	}
    @Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    public static IWeiboActivity getActivityByName(String name)
    {   IWeiboActivity ia=null;
    	//功能就是用name和allActivity中的类名一次匹配，如果找到则返回，否则没有
        for(IWeiboActivity ac:allActivity)//ac就是IWeiboActivity， for (int i = 0; i < allActivity.size(); i++)
        {
        	if(ac.getClass().getName().indexOf(name)>=0)
        	{
        		ia=ac;
        	}
        }
    	return ia;
    }
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mainService=this;
		Thread t=new Thread(this);
		t.start();
	   //添加网络状态变化的广播接收器
		
		this.registerReceiver(netReceiver, 
				new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
	}
 	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void run() {
		// TODO Auto-generated method stub
	    if(weibo==null)
	    {   weibo=new Weibo();
	    	AccessToken at=OAuthReadUtil.readToken(this);
			if(at!=null)
	    	{weibo.setToken(at.getToken(), at.getTokenSecret());	
	    	}
	    }
		while(isrun)
		{ Task lasttask=null;
		  Log.d("core Logic", "..............run");
		  synchronized(allTask)
		  {//接任务	
		    if(allTask.size()>0)
		    {
		    	lasttask=allTask.get(0);
		    }
		   //执行任务
		    doTask(lasttask);
		  }
		  try{Thread.sleep(1000);}catch(Exception e){}
		}
	}
    //执行任务
	public void doTask(Task task)
	{Message message=hand.obtainMessage();
	 
	 try{
	  switch(task.getTaskID())	
	  {case Task.TASK_USER_LOGIN://用户登录
		   weibo.setUserId((String)task.getTaskParam().get("user"));
		   weibo.setPassword((String)task.getTaskParam().get("pass"));
		   String result="";
		   try{
		    nowUser= weibo.verifyCredentials();
	        //获取用户头像
//		     HashMap param=new HashMap();
//	         param.put("uid", nowUser.getId());
//	         param.put("url", nowUser.getProfileImageURL());
//	         Log.d("user", nowUser.getId()+"-----"
//	        		 +nowUser.getProfileImageURL());
//	    	 Task ts=new Task(Task.TASK_GET_USER_IMAGE_ICON,param); 
//	    	 MainService.addNewTask(ts);
	    	
		   // 替换为OAuth方式
//			 //1。根据app key第三方应用向新浪获取requestToken
//				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
//	        	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
//	          
//	         RequestToken requestToken = weibo.getOAuthRequestToken();
//             Log.d("rctoken", "获取requestToken"+requestToken.getToken());
//	        	//2。用户从新浪获取verifier_code
//           String vcode=weibo.getAuthorizationURL(
//           		requestToken.getToken(),(String)task.getTaskParam().get("user")
//           		,(String)task.getTaskParam().get("pass"));
//           Log.d("rctoken", "获取 Vcode"+vcode);
//	        
//           //3.使用verifier_code和requestToken获取Access_Token
//           AccessToken accessToken = requestToken.getAccessToken(vcode);
//           Log.d("rctoken", "获取 AccessToken"+accessToken.getToken());
//		   //4 保存数据
//		   OAuthReadUtil.saveToken(getApplicationContext(), accessToken); 
		  
		   result="loginok";
		   }catch(Exception e){
			result="loginerr";   
		   }
		   message.obj=result; //保存登录结果 
		   break;
	  case Task.TASK_GET_USER_HOMETIMEINLINE://获取用户首页微博信息
		  int nowpage=(Integer)task.getTaskParam().get("nowpage");
		  int pagesize=(Integer)task.getTaskParam().get("pagesize");
		  Paging p=new Paging(nowpage,pagesize);//创建分页对象
	      List<Status> alls=weibo.getFriendsTimeline(p);
	      for(Status st:alls)
	      {  //该用户的头像没有获取过
	    	 if(allIcon.get(st.getUser().getId())==null)
	    	 {
	    	 HashMap param=new HashMap();
	         param.put("uid", st.getUser().getId());
	         param.put("url", st.getUser().getProfileImageURL());
	         Log.d("user", st.getUser().getId()+"-----"
	        		 +st.getUser().getProfileImageURL());
	    	 Task ts=new Task(Task.TASK_GET_USER_IMAGE_ICON,param); 
	    	 MainService.addNewTask(ts);
	    	 }
	      }
	      message.obj=alls;
		  break;
	  case Task.TASK_GET_USER_HOMETIMEINLINE_MORE://获取更多用户首页微博信息
		  int nowpage2=(Integer)task.getTaskParam().get("nowpage");
		  int pagesize2=(Integer)task.getTaskParam().get("pagesize");
		  Paging p2=new Paging(nowpage2,pagesize2);//创建分页对象
	      List<Status> alls2=weibo.getFriendsTimeline(p2);
	      for(Status st:alls2)
	      {  //该用户的头像没有获取过
	    	 if(allIcon.get(st.getUser().getId())==null)
	    	 {
	    	 HashMap param=new HashMap();
	         param.put("uid", st.getUser().getId());
	         param.put("url", st.getUser().getProfileImageURL());
	         Log.d("user", st.getUser().getId()+"-----"
	        		 +st.getUser().getProfileImageURL());
	    	 Task ts=new Task(Task.TASK_GET_USER_IMAGE_ICON,param); 
	    	 MainService.addNewTask(ts);
	    	 }
	      }
	      message.obj=alls2;
		  break;
	   case Task.TASK_GET_USER_IMAGE_ICON:
		   URL url=(URL) task.getTaskParam().get("url");
		   //从网络获取用户的头像
		   BitmapDrawable bd=NetUtil.getImageFromURL(url);
		   //得到头像对应的UID
		   Integer uid=(Integer)task.getTaskParam().get("uid");
		   //添加到集合
		   allIcon.put(uid, bd);
		   break;
	   case Task.TASK_NEW_WEIBO://发表普通微博信息
		   Status st= weibo.updateStatus((String)task.getTaskParam().get("msg"));
	       message.obj="微博发表成功";
	       break;
	   case Task.TASK_GET_WEIBO_COMMENT://获取所有微博评论信息
		   List<Comment> allc=weibo.getCommentsByMe();//我发表的所有评论
		   message.obj=allc;
		   break;
	   case Task.TASK_GET_WEIBO_MESSAGE://获取所有私信
		   List<DirectMessage> alldc=weibo.getDirectMessages();//我所有私信
		   message.obj=alldc;
		   break;
	   case Task.TASK_NEW_WEIBO_COMMENT://发表微博评论
		   if((Boolean)task.getTaskParam().get("news"))
		   weibo.updateStatus((String)task.getTaskParam().get("msg"));
	       weibo.updateComment((String)task.getTaskParam().get("msg"),
	    		   (String)task.getTaskParam().get("id"),
	    		   "");
		   message.obj="微博评论发表成功";
	       
		   break;
	   case Task.TASK_NEW_WEIBO_PIC://发表图片微博信息
		   String msg = URLEncoder.encode((String)task.getTaskParam().get("msg")
				   , "UTF-8");
		  ImageItem it=new ImageItem("pic",(byte[])task.getTaskParam().get("picdat"));
		  //发表图片微博
		  Status status = weibo.uploadStatus(msg, it);
          message.obj="微博发表成功";
	       break;
	   case Task.TASK_NEW_WEIBO_GPS://发表GPS微博信息
		  //发表图片微博
		  Status statusgps = weibo.updateStatus(
				  (String)task.getTaskParam().get("msg"), 
				  ((double[])task.getTaskParam().get("gpspoint"))[0],
				  ((double[])task.getTaskParam().get("gpspoint"))[1]);
          message.obj="微博发表成功";
	       break;
	  }
	  message.what=task.getTaskID();
	 }catch(WeiboException e){
	  message.what=e.getStatusCode();
	 }catch(Exception es){
		  message.what=this.weiboerror;
	 }
	  allTask.remove(task);//完成任务	
	  //发送刷新 UI的消息到主线程
	  hand.sendMessage(message);
	}
	//刷新UI
	public Handler hand=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case Task.TASK_USER_LOGIN:
				
				 IWeiboActivity ha=MainService.getActivityByName("LoginActivity");
			     ha.refresh(LoginActivity.REF_LOGIN_RESULT,msg.obj);
			     
				 break;
			case Task.TASK_GET_USER_HOMETIMEINLINE://更新Weibo首页
			     IWeiboActivity hha=MainService.getActivityByName("HomeActivity");
			     hha.refresh(HomeActivity.REF_WEIBO,msg.obj);
			     break;
			case Task.TASK_GET_USER_HOMETIMEINLINE_MORE://更新Weibo首页更多
			     IWeiboActivity hhamore=MainService.getActivityByName("HomeActivity");
			     hhamore.refresh(HomeActivity.REF_WEIBO_MORE,msg.obj);
			     break;
			case Task.TASK_GET_USER_IMAGE_ICON://更新ICON
			     IWeiboActivity ha2=MainService.getActivityByName("HomeActivity");
			     if(ha2!=null)
			     ha2.refresh(HomeActivity.REF_ICON,msg.obj);
			     break;
			case Task.TASK_NEW_WEIBO://发表微博
				 IWeiboActivity ha3=MainService.getActivityByName("HomeActivity");
			     ha3.refresh(HomeActivity.REF_NEW_WEIBO_RESULT,msg.obj);
			     break;
			case Task.TASK_NEW_WEIBO_COMMENT://发表评论
				 MainService.getActivityByName("HomeActivity")
				 .refresh(HomeActivity.REF_NEW_WEIBO_COMM_RESULT,msg.obj);
			     break;
			case Task.TASK_GET_WEIBO_COMMENT://当前用户发表的所有评论
				MainService.getActivityByName("MSGActivity")
				 .refresh(MSGActivity.REF_GET_WEIBO_COMM,msg.obj);
				break;
			case Task.TASK_GET_WEIBO_MESSAGE://当前用户发表的所有评论
				MainService.getActivityByName("MSGActivity")
				 .refresh(MSGActivity.REF_GET_WEIBO_MESSAGE,msg.obj);
			         
			}
		}
		
	};
	//提示用户网络异常
	public static void alertNetError(final Activity context)
	{
	   	AlertDialog.Builder ab=new AlertDialog.Builder(context);
	   	//设定标题
	   	ab.setTitle(context.getResources().getString(R.string.net_err_title));
	  //设定标题
	   	ab.setMessage(context.getResources().getString(R.string.net_err_info));
	   	//设定退出按钮
	   	ab.setNegativeButton(context.getResources().getString(R.string.exit_app),
	     new OnClickListener()
	   	 {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
		       dialog.cancel(); 
  	    	   exitApp(context);		
			}
	   	 }
	   	);
		//网络设置按钮
	   	ab.setPositiveButton(context.getResources().getString(R.string.net_setting),
	   	     new OnClickListener()
	   	   	 {
	   			public void onClick(DialogInterface dialog, int which) {
	   				// TODO Auto-generated method stub
	   		        dialog.dismiss();
	     	    	context.startActivityForResult(
	     	         new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);	
	   			}
	   	   	 }
	   	   	);
	   	 ab.create().show();
	}
	//退出应用程序
    public static void exitApp(Activity context)
    {//退出所有Activity
    	for(int i=0;i<allActivity.size();i++)
    	{
    		((Activity)allActivity.get(i)).finish();
    	}
    	allActivity.clear();
     //退出Service	
    	context.stopService(new Intent("com.itcast.weibo.mainlogic"));
     //关闭子线程
    	MainService.isrun=false;
     //关闭广播接口
    	MainService.mainService.unregisterReceiver(
    			MainService.mainService.netReceiver);
    	
    }
    //添加任务
    public static void addNewTask(Task ts)
    {
    	allTask.add(ts);
    }
    //提示是否退出应用程序
    public static void promptExitApp(final Activity context)
    {
       //创建对话框
    	AlertDialog.Builder ab=new AlertDialog.Builder(context);
    	LayoutInflater li=LayoutInflater.from(context);
    	View msgview=li.inflate(R.layout.exitdialog,null);
//    	LinearLayout lm=((LinearLayout)msgview);
    	ab.setView(msgview);// 设定对话框显示的内容
    	ab.setPositiveButton(R.string.app_exit_ok, 
    		new OnClickListener()
    	     {
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
					 exitApp(context);//退出整个应用
				}
    	     }
    	    );
    	ab.setNegativeButton(R.string.app_exit_cancle, null);
    	ab.show();
    }
}
