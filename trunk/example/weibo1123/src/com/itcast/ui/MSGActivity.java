package com.itcast.ui;

import java.util.List;

import weibo4j.Comment;
import weibo4j.DirectMessage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itcast.logic.IWeiboActivity;
import com.itcast.logic.MainService;
import com.itcast.logic.Task;

public class MSGActivity extends Activity implements IWeiboActivity{
    public static final int REF_GET_WEIBO_COMM=1;//ˢ������������Ϣ
    public static final int REF_GET_WEIBO_ATME=2;//ˢ�������ᵽ�ҵ���Ϣ
    public static final int REF_GET_WEIBO_MESSAGE=3;//ˢ������˽����Ϣ
    private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mes);
		init();
		//��ȡ���е�������Ϣ
		Task tsGetComm=new Task(Task.TASK_GET_WEIBO_COMMENT
				  ,null);
		MainService.addNewTask(tsGetComm);
		lv=(ListView)this.findViewById(R.id.listView);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.d("keydown", ".................."+keyCode);
		if(keyCode==4)//����û������˷��ؼ�
		{
			MainService.promptExitApp(this);
		}
		return true;
	}
	public void init() {
		// TODO Auto-generated method stub
        MainService.allActivity.add(this);		
	}

	public void refresh(Object... param) {
		switch((Integer)param[0])
		{case REF_GET_WEIBO_COMM://�����б�
			 List<Comment> allc=
				    (List<Comment>)param[1];
			 //��������������
			 MsgAdapter ma=
				 new MsgAdapter(this,allc);
			 lv.setAdapter(ma);
			  break;
		 case REF_GET_WEIBO_ATME://�ᵽ�ҵ�
			 
			 break;
		 case REF_GET_WEIBO_MESSAGE://˽���б�
			 List<DirectMessage> alldm=
				    (List<DirectMessage>)param[1];
			 //��������������
			 DirectMsgAdapter dma=
				 new DirectMsgAdapter(this,alldm);
			 lv.setAdapter(dma);
			
		}
	}
	//��ȡ���е�������Ϣ
	public static void getAllComment()
	{
	Task tsGetComm=new Task(Task.TASK_GET_WEIBO_COMMENT
			  ,null);
	MainService.addNewTask(tsGetComm);
	}
	//��ȡ����˽��
	public static void getAlldirectMessage()
	{
		//��ȡ���е�������Ϣ
		Task tsGetComm=new Task(Task.TASK_GET_WEIBO_MESSAGE
				  ,null);
		MainService.addNewTask(tsGetComm);
	}

}
 class MsgAdapter extends BaseAdapter
{   public Context context; 
    public List<Comment> allc;
    public MsgAdapter(Context c,List<Comment> lc)
    {
    	context=c;
    	allc=lc;
    }
	public int getCount() {
		// TODO Auto-generated method stub
		return allc.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView itemview=null;
		if(convertView!=null)
		{
		   itemview=(TextView)convertView;	
		}else{
			itemview=new TextView(context);
		}
		//�趨��������
		itemview.setHeight(48);
		itemview.setText(allc.get(position).getUser().getName()+
				" ����ʵ��:"+allc.get(position).getCreatedAt()
				+" ��������"+allc.get(position).getText());
		return itemview;
	}
	
}
 class DirectMsgAdapter extends BaseAdapter
 {   public Context context; 
     public List<DirectMessage> allc;
     public DirectMsgAdapter(Context c,List<DirectMessage> lc)
     {
     	context=c;
     	allc=lc;
     }
 	public int getCount() {
 		// TODO Auto-generated method stub
 		return allc.size();
 	}

 	public Object getItem(int position) {
 		// TODO Auto-generated method stub
 		return null;
 	}

 	public long getItemId(int position) {
 		// TODO Auto-generated method stub
 		return 0;
 	}

 	public View getView(int position, View convertView, ViewGroup parent) {
 		TextView itemview=null;
 		if(convertView!=null)
 		{
 		   itemview=(TextView)convertView;	
 		}else{
 			itemview=new TextView(context);
 		}
 		//�趨��������
 	   String txt=allc.get(position).getSender().getName()+
		" ����˵:"+allc.get(position).getText()
			+" ʱ��:"+allc.get(position).getCreatedAt();
 		itemview.setText(Html.fromHtml(txt));
 		return itemview;
 	}
 	
 }


