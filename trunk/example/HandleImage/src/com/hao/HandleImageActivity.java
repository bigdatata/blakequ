package com.hao;

import java.io.File;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class HandleImageActivity extends Activity {
	ImageView image;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        image = (ImageView) findViewById(R.id.image);
        byte[] b = new byte[1024];
        
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//    	Bitmap bitmap = ImageUtil.readBitmapFromSdcard(path.toString(), "1.jpg");
        try {
//			 b = ImageUtil.readInputStream(getResources().openRawResource(R.raw.person));
//        	Bitmap bitmap = ImageUtil.drawableToBitmap(ImageUtil.getDrawable("1"));
//        	Bitmap bitmap = ImageUtil.readBitmapFromAppPic("1");
//        	Bitmap bitmap = ImageUtil.readBitmapFromSdcard(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString(), "1.jpg");
//        	boolean type = ImageUtil.saveInputStreamToAppPic(ImageUtil.getRequest("http://192.168.1.55:8080/pic/1.jpg"), "2");
//        	if(type) {
//        		bitmap = ImageUtil.readBitmapFromAppPic("2");
//        	}
//        	Bitmap bitmap = ImageUtil.getBitmapFromUrl("http://192.168.1.55:8080/pic/1.jpg");
        	Bitmap bitmap = ImageUtil.drawableToBitmap(ImageUtil.loadImageFromUrl("http://192.168.1.111:8080/pic/2.jpg"));
        	bitmap = ImageUtil.zoonBitmapByWindowScale(bitmap, 100, 650);
		    	if(bitmap != null){
		    		image.setImageBitmap(bitmap);
		    		if(!FileUtil.isImageFileExists(new File(FileUtil.getAppPicFolder()), "1", "jpg"))
		        		ImageUtil.saveBitmapToSdcard(bitmap, FileUtil.getAppPicFolder(), "1");
		    	}else{
		    		System.out.println("null");
		    	}
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        
        try {
//			b = ImageUtil.readInputStream(getResources().openRawResource(R.raw.person));
//			Bitmap bitmap = ImageUtil.byteToBitmap(b);
//			System.out.println(get);320*431如何取得屏幕高宽？Bitmap和Drawable以及BitmapDrawable的区别？
//			System.out.println(getWindowManager().getDefaultDisplay().getWidth()+" "+getWindowManager().getDefaultDisplay().getHeight());
//			DisplayMetrics dm = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay().getMetrics(dm);
//			System.out.println(dm.widthPixels+" "+ dm.heightPixels);
//			bitmap = ImageUtil.zoomBitmap(bitmap, 200, 330);
//			bitmap = ImageUtil.toRoundCorner(bitmap, 10);
//			bitmap = ImageUtil.toGrayscale(bitmap);
//			bitmap = ImageUtil.createReflectionImageWithOrigin(bitmap);
//			image.setImageBitmap(bitmap);
			
//			Drawable drawable = ImageUtil.byteToDrawable(b);
//			image.setImageDrawable(drawable); //image.setBackgroundDrawable(drawable);
//			Bitmap bitmap = ImageUtil.drawableToBitmap(drawable);
//			bitmap = ImageUtil.zoomBitmap(bitmap, 320, 430);
//			bitmap = ImageUtil.toRoundCorner(bitmap, 10);
//			bitmap = ImageUtil.createReflectionImageWithOrigin(bitmap);
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}