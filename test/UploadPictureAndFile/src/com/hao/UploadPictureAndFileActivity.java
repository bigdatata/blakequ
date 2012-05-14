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
	        String path = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator;//创建文件夹存放视频    
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
	            myRecAudioFile = File.createTempFile("video", ".3gp",dir);//创建临时文件   
	            recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//预览    
	            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//视频源   
	            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //录音源为麦克风    
	            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//输出格式为3gp   
	            recorder.setVideoSize(800, 480);//视频尺寸    
	            recorder.setVideoFrameRate(15);//视频帧频率   
	            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//视频编码    
	            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//音频编码   
	            recorder.setMaxDuration(10000);//最大期限    
	            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());//保存路径   
	            recorder.prepare();    
	            recorder.start();   
	        } catch (IOException e) {    
	            e.printStackTrace();   
	        }    
	    }   

	      /* 上传文件至Server的方法 */
	      private void uploadFile()
	      {
	        String end = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        try
	        {
	          URL url =new URL(actionUrl);
	          HttpURLConnection con=(HttpURLConnection)url.openConnection();
	          /* 允许Input、Output，不使用Cache */
	          con.setDoInput(true);
	          con.setDoOutput(true);
	          con.setUseCaches(false);
	          /* 设置传送的method=POST */
	          con.setRequestMethod("POST");
	          /* setRequestProperty */
	          con.setRequestProperty("Connection", "Keep-Alive");
	          con.setRequestProperty("Charset", "UTF-8");
	          con.setRequestProperty("Content-Type",
	                             "multipart/form-data;boundary="+boundary);
	          /* 设置DataOutputStream */
	          DataOutputStream ds = 
	            new DataOutputStream(con.getOutputStream());
	          ds.writeBytes(twoHyphens + boundary + end);
	          ds.writeBytes("Content-Disposition: form-data; " +
	                        "name=\"file1\";filename=\"" +
	                        newName +"\"" + end);
	          ds.writeBytes(end);   

	          /* 取得文件的FileInputStream */
	          FileInputStream fStream = new FileInputStream(myRecAudioFile);
	          /* 设置每次写入1024bytes */
	          int bufferSize = 1024;
	          byte[] buffer = new byte[bufferSize];

	          int length = -1;
	          /* 从文件读取数据至缓冲区 */
	          while((length = fStream.read(buffer)) != -1)
	          {
	            /* 将资料写入DataOutputStream中 */
	            ds.write(buffer, 0, length);
	          }
	          ds.writeBytes(end);
	          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

	          /* close streams */
	          fStream.close();
	          ds.flush();

	          /* 取得Response内容 */
	          InputStream is = con.getInputStream();
	          int ch;
	          StringBuffer b =new StringBuffer();
	          while( ( ch = is.read() ) != -1 )
	          {
	            b.append( (char)ch );
	          }
	          /* 将Response显示于Dialog */
	          showDialog("上传成功"+b.toString().trim());
	          /* 关闭DataOutputStream */
	          ds.close();
	        }
	        catch(Exception e)
	        {
	          showDialog("上传失败"+e);
	        }
	      }

	      /* 显示Dialog的method */
	      private void showDialog(String mess)
	      {
	        new AlertDialog.Builder(UploadPictureAndFileActivity.this).setTitle("Message")
	         .setMessage(mess)
	         .setNegativeButton("确定",new DialogInterface.OnClickListener()
	         {
	           public void onClick(DialogInterface dialog, int which)
	           {          
	           }
	         })
	         .show();
	      }
	    }


/**
服务器servlet代码
public void doPost(HttpServletRequest request, HttpServletResponse response)  
           throws ServletException, IOException {  
            
           String temp=request.getSession().getServletContext().getRealPath("/")+"temp";   //临时目录
           System.out.println("temp="+temp);
           String loadpath=request.getSession().getServletContext().getRealPath("/")+"Image"; //上传文件存放目录
           System.out.println("loadpath="+loadpath);
           DiskFileUpload fu = new DiskFileUpload();
           fu.setSizeMax(1*1024*1024);   // 设置允许用户上传文件大小,单位:字节 
           fu.setSizeThreshold(4096);   // 设置最多只允许在内存中存储的数据,单位:字节 
           fu.setRepositoryPath(temp); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录 
           
           //开始读取上传信息 
           int index=0;
           List fileItems = null;
                
                         
                                try {
                                        fileItems = fu.parseRequest(request);
                                         System.out.println("fileItems="+fileItems);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                         
                
           Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
           while (iter.hasNext())
           {
               FileItem item = (FileItem)iter.next();// 忽略其他不是文件域的所有表单信息
               if (!item.isFormField())
               {
                   String name = item.getName();//获取上传文件名,包括路径
                   name=name.substring(name.lastIndexOf("\\")+1);//从全路径中提取文件名
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
               else //取出不是文件域的所有表单信息
               {
                   String fieldvalue = item.getString();
            //如果包含中文应写为：(转为UTF-8编码)
                   //String fieldvalue = new String(item.getString().getBytes(),"UTF-8");
               }
           }
           String text1="11";
           response.sendRedirect("result.jsp?text1=" + text1);
    }  
*/