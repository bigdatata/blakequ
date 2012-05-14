package com.itcast.ui;

import java.util.HashMap;
import java.util.List;

import weibo4j.Status;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.itcast.logic.IWeiboActivity;
import com.itcast.logic.MainService;
import com.itcast.logic.Task;
import com.itcast.util.DateUtil;
import com.itcast.util.TextViewLink;

public class HomeActivity extends Activity implements IWeiboActivity{
    public static final String REF_WEIBO="REF_WEIBO";//����΢�� 
    public static final String REF_ICON="REF_ICON";//����ͷ��
    public static final String REF_WEIBO_MORE="REF_WEIBO_MORE";//������Ϣ
    public static final String REF_NEW_WEIBO_RESULT="REF_NEW_WEIBO_RESULT";//����΢���Ľ��
    public static final String REF_NEW_WEIBO_COMM_RESULT="REF_NEW_WEIBO_RESULT";//����΢���Ľ��
    ListView lv;
    View process;
    public int pagesize=5;
    public int nowpage=1;
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//����ǰ�û���΢����ҳ 
		HashMap hm=new HashMap();
		hm.put("pagesize", pagesize);
		hm.put("nowpage", nowpage);
		Task tsHome=new Task(Task.TASK_GET_USER_HOMETIMEINLINE,hm);
	    MainService.addNewTask(tsHome);
	    
	    MainService.allActivity.add(this);
	    //���Դ���
	     //lv=new ListView(this);
	    this.setContentView(R.layout.home);
	    //��ȡ����
	    View titleview=this.findViewById(R.id.freelook_title);
	    TextView tv=(TextView)titleview.findViewById(R.id.textView);
	    //��ǰ�û����ǳ�
	    tv.setText(MainService.nowUser.getName());
	    Button btNewbolg=(Button)titleview.findViewById(R.id.title_bt_left);
	    btNewbolg.setBackgroundResource(R.drawable.title_new);
	    btNewbolg.setOnClickListener(new OnClickListener()
	    {
			public void onClick(View v) {
		    //���뷢��΢������
 	    	   Intent itnewblog=new Intent(HomeActivity.this,NewWeiboActivity.class);
 	    	  HomeActivity.this.startActivityForResult(itnewblog, 1);
 	       }
	    }
	    );
	    
	    Button btRefbolg=(Button)titleview.findViewById(R.id.title_bt_right);
	    btRefbolg.setBackgroundResource(R.drawable.title_reload);
	    btRefbolg.setOnClickListener(new OnClickListener()
	    {    
			 public void onClick(View arg0) {
		     		//��ӻ�ȡ��ҳ��Ϣ����
	    	HashMap hm=new HashMap();
			hm.put("pagesize", pagesize);
			hm.put("nowpage", nowpage);
	    	Task tsHome=new Task(Task.TASK_GET_USER_HOMETIMEINLINE,hm);
	    	MainService.addNewTask(tsHome);
		    
			}
	    }
	    );
	    //��ȡ�б����ͽ���������
	    process=this.findViewById(R.id.progress);
	    lv=(ListView)this.findViewById(R.id.freelook_listview);
	    lv.setClickable(true);
	    //��ListView�����¼�����
	    lv.setOnItemClickListener(new OnItemClickListener()
	    {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeActivity.this, "��ѡ����"+arg2, 1000).show();
				if(arg3==-1)//����
				{   nowpage++;
					HashMap hm=new HashMap();
					hm.put("pagesize", pagesize);
					hm.put("nowpage", nowpage);
					Task tsHome=new Task(Task.TASK_GET_USER_HOMETIMEINLINE_MORE,hm);
					MainService.addNewTask(tsHome);
				}
				else if(arg3==0)
				{
					HashMap hm=new HashMap();
					hm.put("pagesize", pagesize);
					hm.put("nowpage", nowpage);
			    	Task tsHome=new Task(Task.TASK_GET_USER_HOMETIMEINLINE,hm);
			    	MainService.addNewTask(tsHome);
				}else
				{
				    Intent it=new Intent(HomeActivity.this,WeiboStatusActivity.class);
				   try{
				    MyAdapter ma=(MyAdapter)lv.getAdapter();
				    Status statu=(Status)ma.allstatus.get(arg2-1);
				    it.putExtra("status",statu);
				    }catch(Exception e){
				    	Log.e("e","error"+e);
				    }
//				    
				    HomeActivity.this.startActivityForResult(it, 0);
				}
				
			}
	    	
	    });
	    //ע�������Ĳ˵�
        this.registerForContextMenu(lv);
	}
    
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo am=(AdapterContextMenuInfo)item.getMenuInfo();
		
		switch(item.getItemId())
	    {case 1://
	    	Toast.makeText(this, "��Ҫת��"+am.id,1000).show();
	    	break;
	    case 2://
	    	Toast.makeText(this, "��Ҫ�����б��"+am.position+"��",1000).show();
	    	break;
	    case 3://
	    	Toast.makeText(this, "��Ҫ�ղ�",1000).show();
	    	break;
	    case 4://
	    	Toast.makeText(this, "��Ҫ�鿴��Ϣ",1000).show();
	    	break;
	    }
		return super.onContextItemSelected(item);
	
	
	}
	@Override
	public void onContextMenuClosed(Menu menu) {
   }
	
	//��ʼ�������Ĳ˵�
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo am=(AdapterContextMenuInfo)menuInfo;
		
		if(am.position==0)//ˢ��
		{
			return;
		}
		if(am.id==-1)//����
		{
			return;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("΢�����ܲ���");
		//      ���� ���� ����˳��
		menu.add(1, 1, 1, "ת��");
		menu.add(1, 2, 2, "����");
		menu.add(1, 3, 3, "�ղ�");
		menu.add(1, 4, 4, "�鿴�û���Ϣ");
		
	}
	public void init() {
		// TODO Auto-generated method stub
		
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
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		Log.d("ref", (String)param[0]);
		if(((String)param[0]).equals("REF_WEIBO"))
	    {
			List<Status> alls=(List<Status>)param[1];
			MyAdapter ma=new MyAdapter(this,alls);
			lv.setAdapter(ma);//�����б�
			process.setVisibility(View.GONE);
		}else if(((String)param[0]).equals(REF_ICON))
		{   //������������getView�������µ���
			((MyAdapter)lv.getAdapter()).notifyDataSetChanged();
		}
		else if(((String)param[0]).equals(REF_WEIBO_MORE))
		{   List<Status> alls=(List<Status>)param[1];
			((MyAdapter)lv.getAdapter()).addMoreData(alls);
		}
		else if(((String)param[0]).equals(REF_NEW_WEIBO_RESULT))
		{   String result=(String)param[1];
			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		}else if(((String)param[0]).equals(REF_NEW_WEIBO_COMM_RESULT))
		{   String result=(String)param[1];
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	}
	}

}
//����Adapter
class MyAdapter extends BaseAdapter
{   
	List<Status> allstatus;
	Context context;
	public MyAdapter(Context con,List<Status> list)
	{
		this.allstatus=list;
		this.context=con;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return allstatus.size()+2;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(position==0)
			return null;
		else if(position==this.getCount()-1) 
			return null;
		//��������΢������
		else return allstatus.get(position-1);
		
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(position==0)//ˢ��
		{
		   return 0;	
		}else if(position>0&&position<this.getCount()-1)//΢��
		{//����΢����ID
		  return allstatus.get(position-1).getId();	
		}else
		{ //ѡ���˸���
		  return -1;
		}
	}
	public void addMoreData(List<Status> adddata)
	{
	  this.allstatus.addAll(adddata);	
	  this.notifyDataSetChanged();
	}
	private static class ViewHolder{
		ImageView ivItemPortrait;//ͷ�� ��Ĭ��ֵ
		TextView tvItemName;//�ǳ�
		ImageView ivItemV;//������֤ Ĭ��gone
		TextView tvItemDate;//ʱ��
		ImageView ivItemPic;//ʱ��ͼƬ �����޸�
		TextView tvItemContent;//����
		ImageView contentPic;//�Լ����ӵ�����ͼƬ��ʾ��imgView
		View subLayout;//�ظ�Ĭ��gone
		TextView tvItemSubContent;//�ظ����� subLayout��ʾ�ſ�����ʾ
		ImageView subContentPic;//�Լ����ӵ���Ҫ��ʾ�ظ����ݵ�ͼƬ��subLayout��ʾ�ſ�����ʾ
		}
	private  ViewHolder vh=new ViewHolder();
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(position==0)//ˢ��
		{
	    View weiboitem=LayoutInflater.from(context).inflate(R.layout.moreitemsview,null);				
	 	TextView tv=(TextView)weiboitem.findViewById(R.id.tvItemContent);
	     tv.setText("ˢ��");	
		 return weiboitem;
		}else if(position==this.getCount()-1)//����
		{
			 View weiboitem=LayoutInflater.from(context).inflate(R.layout.moreitemsview,null);				
			 	TextView tv=(TextView)weiboitem.findViewById(R.id.tvItemContent);
			     tv.setText("����");	
				 return weiboitem;
		}else
		{
		View weiboitem=null;
		
		if(convertView!=null&&convertView.findViewById(R.id.tvItemName)!=null)
	     {Log.d("getview", "doGetView-------get TextView-----------"+position);
			weiboitem=convertView;
	      }else
		{Log.d("getview", "doGetView-------new TextView-----------"+position);
		//��xml�����ļ����View����
		  weiboitem=LayoutInflater.from(context).inflate(R.layout.itemview, null);
		  }
		  //�ǳ�
			vh.ivItemPortrait = (ImageView)weiboitem.findViewById(R.id.ivItemPortrait);
			vh.tvItemName = (TextView)weiboitem.findViewById(R.id.tvItemName);
			vh.ivItemV = (ImageView)weiboitem.findViewById(R.id.ivItemV);
			vh.tvItemDate = (TextView)weiboitem.findViewById(R.id.tvItemDate);
			vh.ivItemPic = (ImageView)weiboitem.findViewById(R.id.ivItemPic);
			vh.tvItemContent = (TextView)weiboitem.findViewById(R.id.tvItemContent);
			vh.contentPic = (ImageView)weiboitem.findViewById(R.id.contentPic);
			vh.subLayout = weiboitem.findViewById(R.id.subLayout);
			vh.tvItemSubContent = (TextView)vh.subLayout.findViewById(R.id.tvItemSubContent);
			vh.subContentPic = (ImageView)vh.subLayout.findViewById(R.id.subContentPic);
			//vh.ivItemV =(ImageView)vh.subLayout.findViewById(R.id.ivItemV);
			
//		tv.setText(+":"
//				+allstatus.get(position).getText());
	
	    vh.tvItemName.setText(allstatus.get(position-1).getUser().getName());
	    vh.tvItemContent.setText(allstatus.get(position-1).getText());
	    //΢������
//	    TextViewLink.addURLSpan(allstatus.get(position-1).getText(), vh.tvItemContent);
     //ת������
	    if(allstatus.get(position-1).getRetweeted_status()!=null){
			vh.subLayout.setVisibility(View.VISIBLE);
			String txt=allstatus.get(position-1)
			.getRetweeted_status().getText();
			int len=0;
			if(txt.length()>15)
			{len=14;}else{
				len=txt.length();
			}
			vh.tvItemSubContent.setText(txt.substring(0, len));
//			TextViewLink.addURLSpan(" "
//					+allstatus.get(position-1)
//					.getRetweeted_status().getText(), vh.tvItemSubContent);
//			 vh.tvItemSubContent.setFocusable(false);
//			
		}else{
			vh.subLayout.setVisibility(View.GONE);
		}
	    vh.tvItemDate.setText(DateUtil.getCreateAt(allstatus.get(position-1).getCreatedAt()));
		//�Ƿ�ʵ����֤
	    if(allstatus.get(position-1).getUser().isVerified())
	    {  Log.d("ok","ok isVerified");
	    	vh.ivItemV.setVisibility(View.VISIBLE); 
	    }else
	    {
	       vh.ivItemV.setVisibility(View.GONE);	
	    }
	    //�ж���û��ͼƬ
	    if(allstatus.get(position-1).getThumbnail_pic()!=null)
	    {
	    	vh.ivItemPic.setVisibility(View.VISIBLE);
	    }else
	    {
	    	vh.ivItemPic.setVisibility(View.GONE);
	    }
	    //ͷ��
	    //���ͷ���Ѿ�����
	    if(MainService.allIcon.get(allstatus.get(position-1).getUser().getId())!=null)
        {	
	     vh.ivItemPortrait.setImageDrawable(MainService.allIcon.get(
	    		 allstatus.get(position-1).getUser().getId()));
        }else
        {// �趨ȱʡ��ͼƬ
         vh.ivItemPortrait.setImageResource(R.drawable.portrait);	
        }
	    return weiboitem;
		}
	}
	
}
