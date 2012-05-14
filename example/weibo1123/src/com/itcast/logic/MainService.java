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

//ϵͳ����ģ��
public class MainService extends Service implements Runnable{
	//֮����Ҫ��staticȫ�����ã���Ҫ��Ϊ�˽�ʡ�������ɵĿ���
    public static boolean isrun=false;
    public static User nowUser=null;
    public static MainService mainService;//ȫ�־�̬����
    public NetUtil netReceiver=new NetUtil();
    // ���������������
	private static ArrayList<Task> allTask=new ArrayList<Task>();
	//����΢���������
	public Weibo weibo=new Weibo();
	//�������� Activity 

	public static ArrayList<IWeiboActivity> allActivity=new ArrayList<IWeiboActivity>();
	//�������е��û�ͷ��
	public static HashMap<Integer,BitmapDrawable> allIcon=
		             new HashMap<Integer,BitmapDrawable>();
	//���������Ϣ
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
    	//���ܾ�����name��allActivity�е�����һ��ƥ�䣬����ҵ��򷵻أ�����û��
        for(IWeiboActivity ac:allActivity)//ac����IWeiboActivity�� for (int i = 0; i < allActivity.size(); i++)
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
	   //�������״̬�仯�Ĺ㲥������
		
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
		  {//������	
		    if(allTask.size()>0)
		    {
		    	lasttask=allTask.get(0);
		    }
		   //ִ������
		    doTask(lasttask);
		  }
		  try{Thread.sleep(1000);}catch(Exception e){}
		}
	}
    //ִ������
	public void doTask(Task task)
	{Message message=hand.obtainMessage();
	 
	 try{
	  switch(task.getTaskID())	
	  {case Task.TASK_USER_LOGIN://�û���¼
		   weibo.setUserId((String)task.getTaskParam().get("user"));
		   weibo.setPassword((String)task.getTaskParam().get("pass"));
		   String result="";
		   try{
		    nowUser= weibo.verifyCredentials();
	        //��ȡ�û�ͷ��
//		     HashMap param=new HashMap();
//	         param.put("uid", nowUser.getId());
//	         param.put("url", nowUser.getProfileImageURL());
//	         Log.d("user", nowUser.getId()+"-----"
//	        		 +nowUser.getProfileImageURL());
//	    	 Task ts=new Task(Task.TASK_GET_USER_IMAGE_ICON,param); 
//	    	 MainService.addNewTask(ts);
	    	
		   // �滻ΪOAuth��ʽ
//			 //1������app key������Ӧ�������˻�ȡrequestToken
//				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
//	        	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
//	          
//	         RequestToken requestToken = weibo.getOAuthRequestToken();
//             Log.d("rctoken", "��ȡrequestToken"+requestToken.getToken());
//	        	//2���û������˻�ȡverifier_code
//           String vcode=weibo.getAuthorizationURL(
//           		requestToken.getToken(),(String)task.getTaskParam().get("user")
//           		,(String)task.getTaskParam().get("pass"));
//           Log.d("rctoken", "��ȡ Vcode"+vcode);
//	        
//           //3.ʹ��verifier_code��requestToken��ȡAccess_Token
//           AccessToken accessToken = requestToken.getAccessToken(vcode);
//           Log.d("rctoken", "��ȡ AccessToken"+accessToken.getToken());
//		   //4 ��������
//		   OAuthReadUtil.saveToken(getApplicationContext(), accessToken); 
		  
		   result="loginok";
		   }catch(Exception e){
			result="loginerr";   
		   }
		   message.obj=result; //�����¼��� 
		   break;
	  case Task.TASK_GET_USER_HOMETIMEINLINE://��ȡ�û���ҳ΢����Ϣ
		  int nowpage=(Integer)task.getTaskParam().get("nowpage");
		  int pagesize=(Integer)task.getTaskParam().get("pagesize");
		  Paging p=new Paging(nowpage,pagesize);//������ҳ����
	      List<Status> alls=weibo.getFriendsTimeline(p);
	      for(Status st:alls)
	      {  //���û���ͷ��û�л�ȡ��
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
	  case Task.TASK_GET_USER_HOMETIMEINLINE_MORE://��ȡ�����û���ҳ΢����Ϣ
		  int nowpage2=(Integer)task.getTaskParam().get("nowpage");
		  int pagesize2=(Integer)task.getTaskParam().get("pagesize");
		  Paging p2=new Paging(nowpage2,pagesize2);//������ҳ����
	      List<Status> alls2=weibo.getFriendsTimeline(p2);
	      for(Status st:alls2)
	      {  //���û���ͷ��û�л�ȡ��
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
		   //�������ȡ�û���ͷ��
		   BitmapDrawable bd=NetUtil.getImageFromURL(url);
		   //�õ�ͷ���Ӧ��UID
		   Integer uid=(Integer)task.getTaskParam().get("uid");
		   //��ӵ�����
		   allIcon.put(uid, bd);
		   break;
	   case Task.TASK_NEW_WEIBO://������ͨ΢����Ϣ
		   Status st= weibo.updateStatus((String)task.getTaskParam().get("msg"));
	       message.obj="΢������ɹ�";
	       break;
	   case Task.TASK_GET_WEIBO_COMMENT://��ȡ����΢��������Ϣ
		   List<Comment> allc=weibo.getCommentsByMe();//�ҷ������������
		   message.obj=allc;
		   break;
	   case Task.TASK_GET_WEIBO_MESSAGE://��ȡ����˽��
		   List<DirectMessage> alldc=weibo.getDirectMessages();//������˽��
		   message.obj=alldc;
		   break;
	   case Task.TASK_NEW_WEIBO_COMMENT://����΢������
		   if((Boolean)task.getTaskParam().get("news"))
		   weibo.updateStatus((String)task.getTaskParam().get("msg"));
	       weibo.updateComment((String)task.getTaskParam().get("msg"),
	    		   (String)task.getTaskParam().get("id"),
	    		   "");
		   message.obj="΢�����۷���ɹ�";
	       
		   break;
	   case Task.TASK_NEW_WEIBO_PIC://����ͼƬ΢����Ϣ
		   String msg = URLEncoder.encode((String)task.getTaskParam().get("msg")
				   , "UTF-8");
		  ImageItem it=new ImageItem("pic",(byte[])task.getTaskParam().get("picdat"));
		  //����ͼƬ΢��
		  Status status = weibo.uploadStatus(msg, it);
          message.obj="΢������ɹ�";
	       break;
	   case Task.TASK_NEW_WEIBO_GPS://����GPS΢����Ϣ
		  //����ͼƬ΢��
		  Status statusgps = weibo.updateStatus(
				  (String)task.getTaskParam().get("msg"), 
				  ((double[])task.getTaskParam().get("gpspoint"))[0],
				  ((double[])task.getTaskParam().get("gpspoint"))[1]);
          message.obj="΢������ɹ�";
	       break;
	  }
	  message.what=task.getTaskID();
	 }catch(WeiboException e){
	  message.what=e.getStatusCode();
	 }catch(Exception es){
		  message.what=this.weiboerror;
	 }
	  allTask.remove(task);//�������	
	  //����ˢ�� UI����Ϣ�����߳�
	  hand.sendMessage(message);
	}
	//ˢ��UI
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
			case Task.TASK_GET_USER_HOMETIMEINLINE://����Weibo��ҳ
			     IWeiboActivity hha=MainService.getActivityByName("HomeActivity");
			     hha.refresh(HomeActivity.REF_WEIBO,msg.obj);
			     break;
			case Task.TASK_GET_USER_HOMETIMEINLINE_MORE://����Weibo��ҳ����
			     IWeiboActivity hhamore=MainService.getActivityByName("HomeActivity");
			     hhamore.refresh(HomeActivity.REF_WEIBO_MORE,msg.obj);
			     break;
			case Task.TASK_GET_USER_IMAGE_ICON://����ICON
			     IWeiboActivity ha2=MainService.getActivityByName("HomeActivity");
			     if(ha2!=null)
			     ha2.refresh(HomeActivity.REF_ICON,msg.obj);
			     break;
			case Task.TASK_NEW_WEIBO://����΢��
				 IWeiboActivity ha3=MainService.getActivityByName("HomeActivity");
			     ha3.refresh(HomeActivity.REF_NEW_WEIBO_RESULT,msg.obj);
			     break;
			case Task.TASK_NEW_WEIBO_COMMENT://��������
				 MainService.getActivityByName("HomeActivity")
				 .refresh(HomeActivity.REF_NEW_WEIBO_COMM_RESULT,msg.obj);
			     break;
			case Task.TASK_GET_WEIBO_COMMENT://��ǰ�û��������������
				MainService.getActivityByName("MSGActivity")
				 .refresh(MSGActivity.REF_GET_WEIBO_COMM,msg.obj);
				break;
			case Task.TASK_GET_WEIBO_MESSAGE://��ǰ�û��������������
				MainService.getActivityByName("MSGActivity")
				 .refresh(MSGActivity.REF_GET_WEIBO_MESSAGE,msg.obj);
			         
			}
		}
		
	};
	//��ʾ�û������쳣
	public static void alertNetError(final Activity context)
	{
	   	AlertDialog.Builder ab=new AlertDialog.Builder(context);
	   	//�趨����
	   	ab.setTitle(context.getResources().getString(R.string.net_err_title));
	  //�趨����
	   	ab.setMessage(context.getResources().getString(R.string.net_err_info));
	   	//�趨�˳���ť
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
		//�������ð�ť
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
	//�˳�Ӧ�ó���
    public static void exitApp(Activity context)
    {//�˳�����Activity
    	for(int i=0;i<allActivity.size();i++)
    	{
    		((Activity)allActivity.get(i)).finish();
    	}
    	allActivity.clear();
     //�˳�Service	
    	context.stopService(new Intent("com.itcast.weibo.mainlogic"));
     //�ر����߳�
    	MainService.isrun=false;
     //�رչ㲥�ӿ�
    	MainService.mainService.unregisterReceiver(
    			MainService.mainService.netReceiver);
    	
    }
    //�������
    public static void addNewTask(Task ts)
    {
    	allTask.add(ts);
    }
    //��ʾ�Ƿ��˳�Ӧ�ó���
    public static void promptExitApp(final Activity context)
    {
       //�����Ի���
    	AlertDialog.Builder ab=new AlertDialog.Builder(context);
    	LayoutInflater li=LayoutInflater.from(context);
    	View msgview=li.inflate(R.layout.exitdialog,null);
//    	LinearLayout lm=((LinearLayout)msgview);
    	ab.setView(msgview);// �趨�Ի�����ʾ������
    	ab.setPositiveButton(R.string.app_exit_ok, 
    		new OnClickListener()
    	     {
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
					 exitApp(context);//�˳�����Ӧ��
				}
    	     }
    	    );
    	ab.setNegativeButton(R.string.app_exit_cancle, null);
    	ab.show();
    }
}
