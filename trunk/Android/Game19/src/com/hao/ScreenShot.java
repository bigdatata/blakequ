package com.hao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.view.View;
import android.view.Window;

/**
 * ��������
 * @author Administrator
 *
 */
public class ScreenShot {
	
	/**
	 * ��ȡ��Ļ��ͼ
	 * @param activity
	 * @param width	��ȡ�ĸ߶�
	 * @param height��ȡ�Ŀ��
	 * @param x 	��ʼx����
	 * @param y		��ʼy����
	 * @return
	 */
	public static Bitmap takeScreenShot(Activity activity, int x, int y, int width, int height) {  
		// View������Ҫ��ͼ��View
        View view = activity.getWindow().getDecorView();  					
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap b1 = view.getDrawingCache();  
        /*�����ǻ�ȡ������Ļ�Ľ���*/
        // ��ȡ״̬���߶�  
        Rect frame = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
        //��ȡ�������߶�(����߶���:������+״̬��)
        int contentViewTop= activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;
//        System.out.println("statusBarHeight:"+statusBarHeight);  
          //��ȡ��Ļ���͸�  
//        int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
//        int height = activity.getWindowManager().getDefaultDisplay().getHeight();  
        Bitmap b = Bitmap.createBitmap(b1, x, y+contentViewTop, width, height);
        view.destroyDrawingCache();  
        return b;  
    }   
  
	/**
	 * ����ͼƬ��Sdcard
	 * @param b			  ͼƬ	
	 * @param strFileName �ļ���
	 */
    private static void savePicToSdcard(Bitmap b, String strFileName) {  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(strFileName);  
            if (null != fos) {  
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);  
                fos.flush();  
                fos.close();  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /**
     * ���������浽SDcard��
     * @param activity
     * @param x1 ��ʼx����
     * @param y1 ��ʼy����
     * @param x2 ����x����
     * @param y2 ����y����
     * @param fileName ������ļ���
     * @return
     * @throws Exception
     * ע����Ҫ��Sdcard��дȨ��
     */
    public static Bitmap shootAndSave(Activity activity, int x1, int y1, int x2, int y2, String fileName) throws Exception{  
    	Bitmap bt = null;
    	int width = Math.abs(x2 - x1);
    	int height = Math.abs(y2 - y1);
    	if(x1 < x2 && y1 < y2){
    		bt = takeScreenShot(activity, x1, y1, width, height);
    	}else{
    		bt = takeScreenShot(activity, x2, y2, width, height);
    	}
    	if(Environment.isExternalStorageRemovable()){
    		String path = Environment.getExternalStorageDirectory().getPath()+File.separatorChar+fileName+".png";
    		ScreenShot.savePicToSdcard(bt, path);  
    	}else{
    		new Exception("Sdcard not available!");
    	}
        return bt;
    }  
}
