package com.itcast.ui;

import java.util.List;

import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itcast.db.OAuthReadUtil;
import com.itcast.logic.IWeiboActivity;
import com.itcast.logic.MainService;
import com.itcast.logic.Task;

public class MainActivity extends TabActivity implements IWeiboActivity{
	public View msgTitle;//��Ϣͷ����ť
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	public void init() {
		// TODO Auto-generated method stub
		
	}
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//����ҳ�ļ���
		this.setContentView(R.layout.maintabs);
		msgTitle=this.findViewById(R.id.msg_title);
		//����Ϣ�Ĵ���
		Button btcomment=(Button)this.findViewById(R.id.bt_group_middle);
		btcomment.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v) {
						// TODO Auto-generated method stub
					MSGActivity.getAllComment();	
					}
			
				});
		Button btdirectmess=(Button)this.findViewById(R.id.bt_group_right);
		btdirectmess.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				// TODO Auto-generated method stub
			MSGActivity.getAlldirectMessage();	
             }
	
		});
		
		
		//��ɸ���ҳ����
		final TabHost th=this.getTabHost();
		th.addTab(th.newTabSpec("TAB_HOME")
		   .setIndicator("TAB_HOME")
		   .setContent(new Intent(this,HomeActivity.class))
		  );
		th.addTab(th.newTabSpec("TAB_MSG")
				   .setIndicator("TAB_MSG")
				   .setContent(new Intent(this,MSGActivity.class))
				  );
		th.addTab(th.newTabSpec("TAB_USER_INFO")
				   .setIndicator("TAB_USER_INFO")
				   .setContent(new Intent(this,UserInfoActivity.class))
				  );
		th.addTab(th.newTabSpec("TAB_SEARCH")
				   .setIndicator("TAB_SEARCH")
				   .setContent(new Intent(this,SearchActivity.class))
				  );
		th.addTab(th.newTabSpec("TAB_MORE")
				   .setIndicator("TAB_MORE")
				   .setContent(new Intent(this,MoreActivity.class))
				  );
		RadioGroup mainGroup=(RadioGroup)this.findViewById(R.id.main_radio);
		mainGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup arg0, int rid) {
				// TODO Auto-generated method stub
			 Log.d("radiou group", "you selected="+rid);
			 switch(rid)
			 {case R.id.radio_button0://��ҳ
				 th.setCurrentTabByTag("TAB_HOME");
				 msgTitle.setVisibility(View.GONE);
				 break;
			 case R.id.radio_button1://��Ϣ
				 th.setCurrentTabByTag("TAB_MSG");
				 msgTitle.setVisibility(View.VISIBLE);
				 break;
			 case R.id.radio_button2://����
				 th.setCurrentTabByTag("TAB_USER_INFO");
				 msgTitle.setVisibility(View.GONE);
				 break;
			 case R.id.radio_button3://����
				 th.setCurrentTabByTag("TAB_SEARCH");
				 msgTitle.setVisibility(View.GONE);
				 break;
			 case R.id.radio_button4://����	
				 msgTitle.setVisibility(View.GONE);
				 th.setCurrentTabByTag("TAB_MORE");
				 
			 }
			}
			
		}
		);
	}


	
	
	
}
