package com.itcast.ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;

public class CamerActivity extends Activity {
	private static final String TAG = "MainActivity";	
    private SurfaceView surfaceView;
    private boolean preview;
    private Camera camera;
    private String picfileName;
    private Intent it;
    private byte dat[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        it=this.getIntent();
    	requestWindowFeature(Window.FEATURE_NO_TITLE);//û�б���
    	window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// ����ȫ��
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//����

        setContentView(R.layout.camer);
        surfaceView = (SurfaceView)this.findViewById(R.id.surfaceView);
        surfaceView.getHolder().setFixedSize(176, 144);	//���÷ֱ���
        /*��������Surface��ά���Լ��Ļ����������ǵȴ���Ļ����Ⱦ���潫�������͵��û���ǰ*/
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
    }
    private final class SurfaceCallback implements Callback{

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open();
				WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				Camera.Parameters parameters = camera.getParameters();
				parameters.setPreviewSize(display.getWidth(), display.getHeight());//����Ԥ����Ƭ�Ĵ�С
				parameters.setPreviewFrameRate(3);//ÿ��3֡
				parameters.setPictureFormat(PixelFormat.JPEG);//������Ƭ�������ʽ
				parameters.set("jpeg-quality", 85);//��Ƭ����
				parameters.setPictureSize(display.getWidth(), display.getHeight());//������Ƭ�Ĵ�С
				camera.setParameters(parameters);
				camera.setPreviewDisplay(surfaceView.getHolder());//ͨ��SurfaceView��ʾȡ������
				camera.startPreview();//��ʼԤ��
				preview = true;
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			if(camera!=null){
				if(preview) camera.stopPreview();
				camera.release();
			}
		}
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getRepeatCount()==0 && camera!=null){
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				try{
					Log.d("dat", "-------------------------------"+dat.length);
					//it.getExtras().putByteArray("picdat", dat);
					//setResult(0,it);
					finish();
					}catch(Exception e){
						e.printStackTrace();
					}
			   return true;
			case KeyEvent.KEYCODE_SEARCH:
				camera.autoFocus(null);
				return true;

			case KeyEvent.KEYCODE_CAMERA:				
			case KeyEvent.KEYCODE_DPAD_CENTER:
				camera.takePicture(null, null, new TakePictureCallback());
						return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    
	private final class TakePictureCallback implements PictureCallback{
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				picfileName=System.currentTimeMillis()+".jpg";
				File file = new File(Environment.getExternalStorageDirectory(), 
						picfileName);
				OutputStream outStream = new FileOutputStream(file);
				bitmap.compress(CompressFormat.JPEG, 100, outStream);
				outStream.close();
				camera.stopPreview();
//				camera.startPreview();//��ʼԤ��
				dat=new byte[data.length];
				System.arraycopy(data, 0,dat, 0, data.length);
//				dat=data;
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
		}		
	}
    
}