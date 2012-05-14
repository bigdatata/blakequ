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
    public static final String REF_WEIBO="REF_WEIBO";//更新微博 
    public static final String REF_ICON="REF_ICON";//更新头像
    public static final String REF_WEIBO_MORE="REF_WEIBO_MORE";//更多信息
    public static final String REF_NEW_WEIBO_RESULT="REF_NEW_WEIBO_RESULT";//发表微博的结果
    public static final String REF_NEW_WEIBO_COMM_RESULT="REF_NEW_WEIBO_RESULT";//发表微博的结果
    ListView lv;
    View process;
    public int pagesize=5;
    public int nowpage=1;
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//请求当前用户的微博主页 
		HashMap hm=new HashMap();
		hm.put("pagesize", pagesize);
		hm.put("nowpage", nowpage);
		Task tsHome=new Task(Task.TASK_GET_USER_HOMETIMEINLINE,hm);
	    MainService.addNewTask(tsHome);
	    
	    MainService.allActivity.add(this);
	    //测试代码
	     //lv=new ListView(this);
	    this.setContentView(R.layout.home);
	    //获取标题
	    View titleview=this.findViewById(R.id.freelook_title);
	    TextView tv=(TextView)titleview.findViewById(R.id.textView);
	    //当前用户的昵称
	    tv.setText(MainService.nowUser.getName());
	    Button btNewbolg=(Button)titleview.findViewById(R.id.title_bt_left);
	    btNewbolg.setBackgroundResource(R.drawable.title_new);
	    btNewbolg.setOnClickListener(new OnClickListener()
	    {
			public void onClick(View v) {
		    //进入发表微博窗口
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
		     		//添加获取首页信息任务
	    	HashMap hm=new HashMap();
			hm.put("pagesize", pagesize);
			hm.put("nowpage", nowpage);
	    	Task tsHome=new Task(Task.TASK_GET_USER_HOMETIMEINLINE,hm);
	    	MainService.addNewTask(tsHome);
		    
			}
	    }
	    );
	    //获取列表对象和进度条对象
	    process=this.findViewById(R.id.progress);
	    lv=(ListView)this.findViewById(R.id.freelook_listview);
	    lv.setClickable(true);
	    //对ListView进行事件处理
	    lv.setOnItemClickListener(new OnItemClickListener()
	    {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeActivity.this, "您选中了"+arg2, 1000).show();
				if(arg3==-1)//更多
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
	    //注册上下文菜单
        this.registerForContextMenu(lv);
	}
    
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo am=(AdapterContextMenuInfo)item.getMenuInfo();
		
		switch(item.getItemId())
	    {case 1://
	    	Toast.makeText(this, "您要转发"+am.id,1000).show();
	    	break;
	    case 2://
	    	Toast.makeText(this, "您要评论列表第"+am.position+"项",1000).show();
	    	break;
	    case 3://
	    	Toast.makeText(this, "您要收藏",1000).show();
	    	break;
	    case 4://
	    	Toast.makeText(this, "您要查看信息",1000).show();
	    	break;
	    }
		return super.onContextItemSelected(item);
	
	
	}
	@Override
	public void onContextMenuClosed(Menu menu) {
   }
	
	//初始化上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo am=(AdapterContextMenuInfo)menuInfo;
		
		if(am.position==0)//刷新
		{
			return;
		}
		if(am.id==-1)//更多
		{
			return;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("微博功能操作");
		//      组编号 项编号 排列顺序
		menu.add(1, 1, 1, "转发");
		menu.add(1, 2, 2, "评论");
		menu.add(1, 3, 3, "收藏");
		menu.add(1, 4, 4, "查看用户信息");
		
	}
	public void init() {
		// TODO Auto-generated method stub
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.d("keydown", ".................."+keyCode);
		if(keyCode==4)//如果用户按下了返回键
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
			lv.setAdapter(ma);//更新列表
			process.setVisibility(View.GONE);
		}else if(((String)param[0]).equals(REF_ICON))
		{   //让数据适配器getView方法重新调用
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
//测试Adapter
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
		//返回这条微薄对象
		else return allstatus.get(position-1);
		
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(position==0)//刷新
		{
		   return 0;	
		}else if(position>0&&position<this.getCount()-1)//微博
		{//返回微博的ID
		  return allstatus.get(position-1).getId();	
		}else
		{ //选中了更多
		  return -1;
		}
	}
	public void addMoreData(List<Status> adddata)
	{
	  this.allstatus.addAll(adddata);	
	  this.notifyDataSetChanged();
	}
	private static class ViewHolder{
		ImageView ivItemPortrait;//头像 有默认值
		TextView tvItemName;//昵称
		ImageView ivItemV;//新浪认证 默认gone
		TextView tvItemDate;//时间
		ImageView ivItemPic;//时间图片 不用修改
		TextView tvItemContent;//内容
		ImageView contentPic;//自己增加的内容图片显示的imgView
		View subLayout;//回复默认gone
		TextView tvItemSubContent;//回复内容 subLayout显示才可以显示
		ImageView subContentPic;//自己增加的主要显示回复内容的图片。subLayout显示才可以显示
		}
	private  ViewHolder vh=new ViewHolder();
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(position==0)//刷新
		{
	    View weiboitem=LayoutInflater.from(context).inflate(R.layout.moreitemsview,null);				
	 	TextView tv=(TextView)weiboitem.findViewById(R.id.tvItemContent);
	     tv.setText("刷新");	
		 return weiboitem;
		}else if(position==this.getCount()-1)//更多
		{
			 View weiboitem=LayoutInflater.from(context).inflate(R.layout.moreitemsview,null);				
			 	TextView tv=(TextView)weiboitem.findViewById(R.id.tvItemContent);
			     tv.setText("更多");	
				 return weiboitem;
		}else
		{
		View weiboitem=null;
		
		if(convertView!=null&&convertView.findViewById(R.id.tvItemName)!=null)
	     {Log.d("getview", "doGetView-------get TextView-----------"+position);
			weiboitem=convertView;
	      }else
		{Log.d("getview", "doGetView-------new TextView-----------"+position);
		//把xml布局文件变成View对象
		  weiboitem=LayoutInflater.from(context).inflate(R.layout.itemview, null);
		  }
		  //昵称
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
	    //微博内容
//	    TextViewLink.addURLSpan(allstatus.get(position-1).getText(), vh.tvItemContent);
     //转发内容
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
		//是否实名认证
	    if(allstatus.get(position-1).getUser().isVerified())
	    {  Log.d("ok","ok isVerified");
	    	vh.ivItemV.setVisibility(View.VISIBLE); 
	    }else
	    {
	       vh.ivItemV.setVisibility(View.GONE);	
	    }
	    //判断有没有图片
	    if(allstatus.get(position-1).getThumbnail_pic()!=null)
	    {
	    	vh.ivItemPic.setVisibility(View.VISIBLE);
	    }else
	    {
	    	vh.ivItemPic.setVisibility(View.GONE);
	    }
	    //头像
	    //如果头像已经下载
	    if(MainService.allIcon.get(allstatus.get(position-1).getUser().getId())!=null)
        {	
	     vh.ivItemPortrait.setImageDrawable(MainService.allIcon.get(
	    		 allstatus.get(position-1).getUser().getId()));
        }else
        {// 设定缺省的图片
         vh.ivItemPortrait.setImageResource(R.drawable.portrait);	
        }
	    return weiboitem;
		}
	}
	
}
