package cm.linju;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cm.linju.beans.ActivityInfo;

public class MyJoinActivity extends ListActivity{
	private MyAdapter myAdapter;
	private TextView moreItem;
	private ListView listView;
	private ArrayList<ActivityInfo> al;
	private static final int REQUEST_NUM_COUNT = 0;
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlist);
		
		al = getActivityList();
		myAdapter = new MyAdapter(this, al);
		//����ʵ��
		listView = this.getListView();
		LayoutInflater inflater = LayoutInflater.from(MyJoinActivity.this);
		View v = inflater.inflate(R.layout.moreitemsview, null);
		listView.addFooterView(v);
		
		moreItem = (TextView)findViewById(R.id.moreItem);
		moreItem.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myAdapter.count += 6;
				myAdapter.notifyDataSetChanged();
				int currentPage=myAdapter.count/10;
				Toast.makeText(getApplicationContext(), "��"+currentPage+"ҳ", Toast.LENGTH_LONG).show();
				if(al.size()< myAdapter.count)
				{
					//������˽�β���Ƴ�
					listView.removeFooterView(v);
					moreItem.setVisibility(View.GONE);
				}
			}
		});
		
		setListAdapter(myAdapter);
        
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, ActivityDetail.class);
		ActivityInfo ai = al.get(position);
		intent.putExtra("ai", ai);
		startActivityForResult(intent, REQUEST_NUM_COUNT);//���ûظ���Ŀ���������лظ���������ʵʱͳ����ʾ����ҳ
	}
	
	
	/**
	 * ����ظ���Ϣ
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
		case REQUEST_NUM_COUNT:
			//waiting,update home list
			break;
		}
	}

	public ArrayList<ActivityInfo> getActivityList()
	{
		ArrayList<ActivityInfo> list = new ArrayList<ActivityInfo>();
		ActivityInfo alh[] = new ActivityInfo[10];
		//��������
		for(int i=0; i<10; i++)
		{
			alh[i] = new ActivityInfo();
			alh[i].setBeginTime("2011-5-23");
			alh[i].setEndTime("2011-5-24");
			//alh1.setMainPicture(getResources().getResourceTypeName(R.drawable.icon));
			alh[i].setTitle("���ɽһ���Σ��Ͽ����μӰɣ�"); 
			alh[i].setDetail("��������ôô��������ôô��������ôô��������ôô��������ôô");
			alh[i].setType("����");
			alh[i].setPlace("�Ĵ�ʡ�ɶ�������·����ϵ");
			alh[i].setParticipationNum("1"+i);
			alh[i].setReplyNum("1"+i);
			alh[i].setViewNum("1"+i);
			alh[i].setScore("1"+i);
			alh[i].setCreateUser("����");
			alh[i].setSponsor(true);
			alh[i].setOtherPicture(true);
			list.add(alh[i]);
		}
		return list;
		
	}
	

}
