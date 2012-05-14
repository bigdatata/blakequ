package cm.linju;

import java.util.ArrayList;

import cm.linju.beans.ActivityInfo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCreateActivity extends ListActivity{
	private MyAdapter myAdapter;
	private TextView moreItem;
	private TextView createActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlist);
		
		final ArrayList<ActivityInfo> al = getActivityList();
		myAdapter = new MyAdapter(this, al);
		
		final ListView listView = this.getListView();
		LayoutInflater inflater = LayoutInflater.from(MyCreateActivity.this);
		//�����ʵ��
		View vHead = inflater.inflate(R.layout.moreitemsview, null);
		listView.addHeaderView(vHead);
		//����ʵ��
		final View vTail = inflater.inflate(R.layout.moreitemsview, null);
		listView.addFooterView(vTail);
		
		moreItem = (TextView)vTail.findViewById(R.id.moreItem);
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
					listView.removeFooterView(vTail);
					moreItem.setVisibility(View.GONE);
				}
			}
		});
		
		createActivity = (TextView)vHead.findViewById(R.id.moreItem);
		createActivity.setText(getResources().getString(R.string.createActivity));
		createActivity.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyCreateActivity.this, ActivityCreate.class);
				startActivity(intent);
			}
		});
		
		setListAdapter(myAdapter);
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
