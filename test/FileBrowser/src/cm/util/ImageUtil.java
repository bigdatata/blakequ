package cm.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 *in common use to handle the image method
 * @author qh
 *
 */
public class ImageUtil {
	/**
	 * 放大缩小图片
	 * @param bitmap the image
	 * @param w width
	 * @param h height
	 * @return  the image after handle
	 */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }
    
    public static Bitmap zoomBitmapByScale(Bitmap bitmap, int w, int h){
    	int width = bitmap.getWidth(); 
    	int height = bitmap.getHeight(); 
    	float scaleWidth = ((float) w) / width; 
    	float scaleHeight = ((float) h) / height; 
    	float minScale = Math.min(scaleWidth, scaleHeight); 
    	Matrix matrix = new Matrix(); 
    	matrix.postScale(minScale, minScale); 
    	bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, 
    	matrix, true);
    	return bitmap;
    }

    /**
     * 将Drawable转化为Bitmap
     * @param drawable resource
     * @return the drawable after reverse
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
    
    /**
	 * convert bitmap to Drawable
	 * @param bitmap
	 * @return
	 */
	public static Drawable BitmapToDrawble(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

  

    /**
     * 获得带倒影的图片方法
     * @param bitmap resource
     * @return the image after handle
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
    
    /**
     * get inputStream of image
     * @param path the url of image
     * @return 
     * @throws Exception
     */
    public static InputStream getRequest(String path) throws Exception {   
        URL url = new URL(path);   
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
        conn.setRequestMethod("GET");   
        conn.setConnectTimeout(5000);   
        if (conn.getResponseCode() == 200){   
            return conn.getInputStream();   
        }   
        return null;   
    }   
   
    /**
     * reverse the inputStram to byte array
     * @param inStream the inputStream of image
     * @return byte array of image
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {   
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();   
        byte[] buffer = new byte[4096];   
        int len = 0;   
        while ((len = inStream.read(buffer)) != -1) {   
            outSteam.write(buffer, 0, len);   
        }   
        outSteam.close();   
        inStream.close();   
        return outSteam.toByteArray();   
    }   
       
    /**
     * load drawable from url
     * @param url
     * @return
     */
    public static Drawable loadImageFromUrl(String url){   
        URL m;   
        InputStream i = null;   
        try {   
            m = new URL(url);   
            i = (InputStream) m.getContent();   
        } catch (MalformedURLException e1) {   
            e1.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        return  Drawable.createFromStream(i, "src");   
    }   
       
    /**
     * get drawable from url
     * @param url
     * @return
     * @throws Exception
     */
    public static Drawable getDrawableFromUrl(String url) throws Exception{   
         return Drawable.createFromStream(getRequest(url),null);   
    }   
       
    /**
     * get bitmap from url
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap getBitmapFromUrl(String url) throws Exception{   
        byte[] bytes = getBytesFromUrl(url);   
        return byteToBitmap(bytes);   
    }   
       
    /**
     * get round angle bitmap from url
     * @param url the url of image
     * @param pixels the angle of image
     * @return
     * @throws Exception
     */
    public static Bitmap getRoundBitmapFromUrl(String url,int pixels) throws Exception{   
        byte[] bytes = getBytesFromUrl(url);   
        Bitmap bitmap = byteToBitmap(bytes);   
        return toRoundCorner(bitmap, pixels);   
    }    
       
    /**
     * get round angle drawable from url
     * @param url the url of image
     * @param pixels the angle of reverse
     * @return
     * @throws Exception
     */
    public static Drawable geRoundDrawableFromUrl(String url,int pixels) throws Exception{   
        byte[] bytes = getBytesFromUrl(url);   
        BitmapDrawable bitmapDrawable = (BitmapDrawable)byteToDrawable(bytes);   
        return toRoundCorner(bitmapDrawable, pixels);   
    }    
       
    /**
     * get byte array from url 
     * @param url
     * @return
     * @throws Exception
     */
    public static byte[] getBytesFromUrl(String url) throws Exception{   
         return readInputStream(getRequest(url));   
    }   
       
    /**
     * reverse byte array to bitmap
     * @param byteArray
     * @return
     */
    public static Bitmap byteToBitmap(byte[] byteArray){   
        if(byteArray.length!=0){    
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);    
        }    
        else {    
            return null;    
        }     
    }   
       
    /**
     * reverse byte array to drawable
     * @param byteArray
     * @return
     */
    public static Drawable byteToDrawable(byte[] byteArray){   
        ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);   
        return Drawable.createFromStream(ins, null);   
    }   
       
    /**
     * reverse bitmap to byte array
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);   
        return baos.toByteArray();   
    }   
       
       
        /**  
          *图片去色,返回灰度图片  
          * @param bmpOriginal 传入的图片  
         * @return 去色后的图片  
         */   
        public static Bitmap toGrayscale(Bitmap bmpOriginal) {   
            int width, height;   
            height = bmpOriginal.getHeight();   
            width = bmpOriginal.getWidth();       
       
            Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);   
            Canvas c = new Canvas(bmpGrayscale);   
            Paint paint = new Paint();   
            ColorMatrix cm = new ColorMatrix();   
            cm.setSaturation(0);   
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);   
            paint.setColorFilter(f);   
            c.drawBitmap(bmpOriginal, 0, 0, paint);   
            return bmpGrayscale;   
        }   
           
           
        /**  
         * 去色同时加圆角  
         * @param bmpOriginal 原图  
         * @param pixels 圆角弧度  
         * @return 修改后的图片  
         */   
        public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {   
            return toRoundCorner(toGrayscale(bmpOriginal), pixels);   
        }   
           
        /**  
         * 把图片变成圆角  
         * @param bitmap 需要修改的图片  
         * @param pixels 圆角的弧度  
         * @return 圆角图片  
         */   
        public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {   
       
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);   
            Canvas canvas = new Canvas(output);   
       
            final int color = 0xff424242;   
            final Paint paint = new Paint();   
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());   
            final RectF rectF = new RectF(rect);   
            final float roundPx = pixels;   
            paint.setAntiAlias(true);   
            canvas.drawARGB(0, 0, 0, 0);   
            paint.setColor(color);   
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);   
       
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));   
            canvas.drawBitmap(bitmap, rect, rect, paint);   
       
            return output;   
        }   
       
           
       /**  
         * 使圆角功能支持BitampDrawable  
         * @param bitmapDrawable   
         * @param pixels   
         * @return    
         */   
        public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable, int pixels) {   
            Bitmap bitmap = bitmapDrawable.getBitmap();   
            bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));   
            return bitmapDrawable;   
        }  
        
        
        /**
         * get bitmap from Sdcard
         * @param path
         * @param name
         * @return
         */
        public static Bitmap getBitmapFromSdcard(String path){
        	Bitmap bitmap = null;
        	if(path == null || path.equals("")){ 
        		return null; 
        	} 
        	bitmap = BitmapFactory.decodeFile(path); 
        	return bitmap;
        }
        

}
