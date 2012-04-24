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
 * 截屏功能
 * @author Administrator
 *
 */
public class ScreenShot {
	
	/**
	 * 获取屏幕截图
	 * @param activity
	 * @param width	截取的高度
	 * @param height截取的宽度
	 * @param x 	开始x坐标
	 * @param y		开始y坐标
	 * @return
	 */
	public static Bitmap takeScreenShot(Activity activity, int x, int y, int width, int height) {  
		// View是你需要截图的View
        View view = activity.getWindow().getDecorView();  					
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap b1 = view.getDrawingCache();  
        /*下面是获取整个屏幕的截屏*/
        // 获取状态栏高度  
        Rect frame = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
        //获取标题栏高度(这个高度是:标题栏+状态栏)
        int contentViewTop= activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;
//        System.out.println("statusBarHeight:"+statusBarHeight);  
          //获取屏幕长和高  
//        int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
//        int height = activity.getWindowManager().getDefaultDisplay().getHeight();  
        Bitmap b = Bitmap.createBitmap(b1, x, y+contentViewTop, width, height);
        view.destroyDrawingCache();  
        return b;  
    }   
  
	/**
	 * 保存图片到Sdcard
	 * @param b			  图片	
	 * @param strFileName 文件名
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
     * 截屏并保存到SDcard上
     * @param activity
     * @param x1 起始x坐标
     * @param y1 起始y坐标
     * @param x2 结束x坐标
     * @param y2 结束y坐标
     * @param fileName 保存的文件名
     * @return
     * @throws Exception
     * 注：需要有Sdcard读写权限
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
