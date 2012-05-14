package com.itcast.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import com.itcast.logic.MainService;
import com.itcast.logic.Task;
import com.itcast.util.GPSPoint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewWeiboActivity extends Activity{
    private EditText et;
    public boolean hasPic=false;
    public boolean hasGPS=false;
    public byte dat[];//ͼƬ����
    public double gps[];//��γ������ 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newblog);
		//�õ��������ֵ��ı���
		et=(EditText)this.findViewById(R.id.etBlog);
		//�õ������İ�ť�趨���غͷ���
		View titleview=this.findViewById(R.id.title);
		//����
		 Button btNewbolg=(Button)titleview.findViewById(R.id.title_bt_left);
		    btNewbolg.setBackgroundResource(R.drawable.title_back);
		    btNewbolg.setText("����");
		    btNewbolg.setOnClickListener(new OnClickListener()
		    {
				public void onClick(View v) {
//	 	    	   NewWeiboActivity.this.setResult(resultCode, data);
	 	    	   finish();
		       }
		    }
		    );
		  //����  
		    Button btRefbolg=(Button)titleview.findViewById(R.id.title_bt_right);
		    btRefbolg.setText("����");
		    btRefbolg.setOnClickListener(new OnClickListener()
		    {    
				 public void onClick(View arg0) {
		    	  HashMap hm=new HashMap();
		    	  hm.put("msg", et.getText().toString());
		    	  if(hasGPS)
		    	  {
		    		  hm.put("gpspoint", gps);
				      Task ts=new Task(Task.TASK_NEW_WEIBO_GPS,hm);
				      MainService.addNewTask(ts);  
		    	  }
		    	  else if(hasPic)
		    	  {
		    	  hm.put("picdat", dat);
			      Task ts=new Task(Task.TASK_NEW_WEIBO_PIC,hm);
			      MainService.addNewTask(ts);
		    	  }else{
		    		  Task ts=new Task(Task.TASK_NEW_WEIBO,hm);
				      MainService.addNewTask(ts);  
		    	  }
			      finish();
				}
		    }
		    );
		  //���ͼƬ��ť
		    Button btaddPic=(Button)this.findViewById(R.id.btGallery);
	        btaddPic.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) {
            		Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
         			startActivityForResult(i, Activity.DEFAULT_KEYS_DIALER);
	            }
	        }
	        );
	        //��Ӿ�γ��
	        Button btaddGPS=(Button)this.findViewById(R.id.btGPS);
	        btaddGPS.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) {
            	 	hasGPS=true;
            	 	gps=GPSPoint.getGPSPoint(NewWeiboActivity.this);
	            }
	        }
	        );
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle extras = data.getExtras();
	    Bitmap b = (Bitmap) extras.get("data");
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
         dat = baos.toByteArray();
//		try{
//       InputStream is=this.getResources().openRawResource(R.raw.b9);
//		dat=new byte[is.available()];
//		is.read(dat);
//		is.close();
		this.hasPic=true;
//		}catch(Exception e){}
	}
	
	
   
}
