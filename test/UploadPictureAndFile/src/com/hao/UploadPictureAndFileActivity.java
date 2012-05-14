package com.hao;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaRecorder;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import android.content.DialogInterface;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UploadPictureAndFileActivity extends Activity {
	    private String newName = "image.jpg";
	    private String actionUrl = "http://192.168.1.111:8080/pic/my";
	    private Button mButton;
	    private File myRecAudioFile,dir;   
	    private SurfaceView mSurfaceView;       
	    private SurfaceHolder mSurfaceHolder;     
	    private Button buttonStart,buttonStop;   
	    private MediaRecorder recorder;
	    boolean type = true;

	    @Override
	      public void onCreate(Bundle savedInstanceState)
	      {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);       
	        mSurfaceHolder = mSurfaceView.getHolder();      
	        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);     
	        buttonStart=(Button)findViewById(R.id.start);   
	        buttonStop=(Button)findViewById(R.id.stop);    
	        File defaultDir = Environment.getExternalStorageDirectory();   
	        String path = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator;//�����ļ��д����Ƶ    
	        dir = new File(path);   
	        if(!dir.exists()){    
	            dir.mkdir();   
	        }    
	        recorder = new MediaRecorder();   
	            
	        buttonStart.setOnClickListener(new OnClickListener() {   
	            @Override    
	            public void onClick(View v) {   
	            	if(type){
	            		type = false;
	            		recorder();
	            	}
	            }   
	        });    
	            
	        buttonStop.setOnClickListener(new OnClickListener() {    
	            @Override   
	            public void onClick(View v) { 
	            	if(recorder != null){
	            		type = true;
	            		recorder.stop();   
	            		recorder.reset();    
	            		recorder.release();   
	            		recorder=null;    
	            	}
	            }   
	        });    
	      }
	    
	    public void recorder() {   
	        try {    
	            myRecAudioFile = File.createTempFile("video", ".3gp",dir);//������ʱ�ļ�   
	            recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//Ԥ��    
	            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//��ƵԴ   
	            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //¼��ԴΪ��˷�    
	            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//�����ʽΪ3gp   
	            recorder.setVideoSize(800, 480);//��Ƶ�ߴ�    
	            recorder.setVideoFrameRate(15);//��Ƶ֡Ƶ��   
	            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//��Ƶ����    
	            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//��Ƶ����   
	            recorder.setMaxDuration(10000);//�������    
	            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());//����·��   
	            recorder.prepare();    
	            recorder.start();   
	        } catch (IOException e) {    
	            e.printStackTrace();   
	        }    
	    }   

	      /* �ϴ��ļ���Server�ķ��� */
	      private void uploadFile()
	      {
	        String end = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        try
	        {
	          URL url =new URL(actionUrl);
	          HttpURLConnection con=(HttpURLConnection)url.openConnection();
	          /* ����Input��Output����ʹ��Cache */
	          con.setDoInput(true);
	          con.setDoOutput(true);
	          con.setUseCaches(false);
	          /* ���ô��͵�method=POST */
	          con.setRequestMethod("POST");
	          /* setRequestProperty */
	          con.setRequestProperty("Connection", "Keep-Alive");
	          con.setRequestProperty("Charset", "UTF-8");
	          con.setRequestProperty("Content-Type",
	                             "multipart/form-data;boundary="+boundary);
	          /* ����DataOutputStream */
	          DataOutputStream ds = 
	            new DataOutputStream(con.getOutputStream());
	          ds.writeBytes(twoHyphens + boundary + end);
	          ds.writeBytes("Content-Disposition: form-data; " +
	                        "name=\"file1\";filename=\"" +
	                        newName +"\"" + end);
	          ds.writeBytes(end);   

	          /* ȡ���ļ���FileInputStream */
	          FileInputStream fStream = new FileInputStream(myRecAudioFile);
	          /* ����ÿ��д��1024bytes */
	          int bufferSize = 1024;
	          byte[] buffer = new byte[bufferSize];

	          int length = -1;
	          /* ���ļ���ȡ������������ */
	          while((length = fStream.read(buffer)) != -1)
	          {
	            /* ������д��DataOutputStream�� */
	            ds.write(buffer, 0, length);
	          }
	          ds.writeBytes(end);
	          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

	          /* close streams */
	          fStream.close();
	          ds.flush();

	          /* ȡ��Response���� */
	          InputStream is = con.getInputStream();
	          int ch;
	          StringBuffer b =new StringBuffer();
	          while( ( ch = is.read() ) != -1 )
	          {
	            b.append( (char)ch );
	          }
	          /* ��Response��ʾ��Dialog */
	          showDialog("�ϴ��ɹ�"+b.toString().trim());
	          /* �ر�DataOutputStream */
	          ds.close();
	        }
	        catch(Exception e)
	        {
	          showDialog("�ϴ�ʧ��"+e);
	        }
	      }

	      /* ��ʾDialog��method */
	      private void showDialog(String mess)
	      {
	        new AlertDialog.Builder(UploadPictureAndFileActivity.this).setTitle("Message")
	         .setMessage(mess)
	         .setNegativeButton("ȷ��",new DialogInterface.OnClickListener()
	         {
	           public void onClick(DialogInterface dialog, int which)
	           {          
	           }
	         })
	         .show();
	      }
	    }


/**
������servlet����
public void doPost(HttpServletRequest request, HttpServletResponse response)  
           throws ServletException, IOException {  
            
           String temp=request.getSession().getServletContext().getRealPath("/")+"temp";   //��ʱĿ¼
           System.out.println("temp="+temp);
           String loadpath=request.getSession().getServletContext().getRealPath("/")+"Image"; //�ϴ��ļ����Ŀ¼
           System.out.println("loadpath="+loadpath);
           DiskFileUpload fu = new DiskFileUpload();
           fu.setSizeMax(1*1024*1024);   // ���������û��ϴ��ļ���С,��λ:�ֽ� 
           fu.setSizeThreshold(4096);   // �������ֻ�������ڴ��д洢������,��λ:�ֽ� 
           fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼ 
           
           //��ʼ��ȡ�ϴ���Ϣ 
           int index=0;
           List fileItems = null;
                
                         
                                try {
                                        fileItems = fu.parseRequest(request);
                                         System.out.println("fileItems="+fileItems);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                         
                
           Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
           while (iter.hasNext())
           {
               FileItem item = (FileItem)iter.next();// �������������ļ�������б���Ϣ
               if (!item.isFormField())
               {
                   String name = item.getName();//��ȡ�ϴ��ļ���,����·��
                   name=name.substring(name.lastIndexOf("\\")+1);//��ȫ·������ȡ�ļ���
                   long size = item.getSize();
                   if((name==null||name.equals("")) && size==0) 
                         continue; 
                   int point = name.indexOf(".");
                   name=(new Date()).getTime()+name.substring(point,name.length())+index;
                   index++;
                   File fNew= new File(loadpath, name);
                   try {
                                        item.write(fNew);
                                } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                   
                  
               }
               else //ȡ�������ļ�������б���Ϣ
               {
                   String fieldvalue = item.getString();
            //�����������ӦдΪ��(תΪUTF-8����)
                   //String fieldvalue = new String(item.getString().getBytes(),"UTF-8");
               }
           }
           String text1="11";
           response.sendRedirect("result.jsp?text1=" + text1);
    }  
*/