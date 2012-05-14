package com.hao;

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
import android.os.Environment;

/**
 * 
 * @author qh
 *
 */
public class ImageUtil {
	
	/**
	 * �Ŵ���СͼƬ
	 * @param bitmap the image
	 * @param w width
	 * @param h height
	 * @return  the image after handle
	 */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        System.out.println("width:"+width+" height:"+height);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    /**
     * ��Drawableת��ΪBitmap
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
     * ��ô���Ӱ��ͼƬ����
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
          * ͼƬȥɫ,���ػҶ�ͼƬ  
          * @param bmpOriginal �����ͼƬ  
         * @return ȥɫ���ͼƬ  
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
         * ȥɫͬʱ��Բ��  
         * @param bmpOriginal ԭͼ  
         * @param pixels Բ�ǻ���  
         * @return �޸ĺ��ͼƬ  
         */   
        public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {   
            return toRoundCorner(toGrayscale(bmpOriginal), pixels);   
        }   
           
        /**  
         * ��ͼƬ���Բ��  
         * @param bitmap ��Ҫ�޸ĵ�ͼƬ  
         * @param pixels Բ�ǵĻ���  
         * @return Բ��ͼƬ  
         */   
        public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {   
       
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);   
            Canvas canvas = new Canvas(output);   
       
            final int color = 0xff424242;   
            final Paint paint = new Paint();   
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());   
            final RectF rectF = new RectF(rect);   
            final float roundPx = pixels;   
            //���ÿ���ݱ�־
            paint.setAntiAlias(true);   
            canvas.drawARGB(0, 0, 0, 0);   
            paint.setColor(color);   
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);   
       
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));   
            canvas.drawBitmap(bitmap, rect, rect, paint);   
       
            return output;   
        }   
       
           
       /**  
         * ʹԲ�ǹ���֧��BitampDrawable  
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
         * save inputStream to application folder
         * @param inputStream 
         * @param name file name
         * @return
         */
        public static boolean saveInputStreamToAppPic(InputStream inputStream, String name){
        	return saveInputStreamToSdcard(inputStream, FileUtil.getAppPicFolder(), name);
        }
        
        /**
         * save inputStream to Sdcard
         * @param inputStream
         * @param path
         * @param name
         * @return
         */
        public static boolean saveInputStreamToSdcard(InputStream inputStream, String path, String name){
        	try {
				FileOutputStream fos = new FileOutputStream(new File(createJpgFilePath(path, name)));
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = inputStream.read(buffer)) != -1){
					fos.write(buffer, 0, length);
				}
				fos.flush();
				fos.close();
				inputStream.close();
				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return false;
        }
        
        /**
         * save image to Sdcard 
         * @param bitmap image
         * @param name image name
         * @return
         */
        public static boolean saveBitmapToAppPic(Bitmap bitmap, String name){
        	return saveBitmapToSdcard(bitmap, FileUtil.getAppPicFolder(), name);
        }
        
        /**
         * save image to application folder
         * @param bitmap
         * @param path
         * @param name
         * @return
         */
        public static boolean saveBitmapToSdcard(Bitmap bitmap, String path, String name){
        	boolean b = true;
        	if(FileUtil.isDirectoryAvailable(path)){
        		if(!FileUtil.isImageFileExists(new File(path), name, "jpg")){
        			File file = new File(createJpgFilePath(path, name));
        			createJpgFilePath(path, name);
        			try {
						BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(file));
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bof);
						bof.flush();
						bof.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						b = false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						b = false;
					}
        		}
        	}else{
        		b = false;
        	}
        	return b;
        }
        

        /**
         * get bitmap from application picture folder
         * @param name
         * @return
         */
        public static Bitmap readBitmapFromAppPic(String name){
        	return readBitmapFromSdcard(FileUtil.getAppPicFolder(), name);
        }
        /**
         * get bitmap from Sdcard
         * @param path
         * @param name
         * @return
         */
        public static Bitmap readBitmapFromSdcard(String path, String name){
        	String dir = createJpgFilePath(path, name);
        	Bitmap bitmap = null;
        	if(dir == null || dir.equals("")){ 
        		return null; 
        	} 
        	if(FileUtil.isImageFileExists(new File(path), name, "jpg")){
        		bitmap = BitmapFactory.decodeFile(dir); 
        	}
        	return bitmap;
        }
        
        
        public static Bitmap zoonBitmapByWindowScale(Bitmap bitmap, int w, int h){
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
         * create the path of jpg file
         * @param name the jpg file name
         * @return the path of file
         */
        public static String createJpgFilePath(String name){
        	return createJpgFilePath(FileUtil.getAppPicFolder(), name);
        }
        
        /**
         * create the path of jpg file
         * @param path
         * @param name
         * @return
         */
        public static String createJpgFilePath(String path, String name){
        	return new StringBuilder(path).append(File.separatorChar).append(name).append(".jpg").toString();
        }
        
        /**
         * create the path of png file
         * @param name the png file name
         * @return the path of file
         */
        public static String createPngFilePath(String name){
        	return createPngFilePath(FileUtil.getAppPicFolder(), name);
        }
        
        /**
         * create the path of png file
         * @param path
         * @param name
         * @return
         */
        public static String createPngFilePath(String path, String name){
        	return new StringBuilder(path).append(File.separatorChar).append(name).append(".png").toString();
        }

        /**
         * get the drawable from application picture folder
         * @param name the file name
         * @return
         */
        public static Drawable getDrawable(String name) {
    		return Drawable.createFromPath(createJpgFilePath(name));
    	}
        
        /**
         * get the drawable from application picture folder
         * @param path the image path
         * @param name the file name
         * @return
         */
        public static Drawable getDrawable(String path, String name){
        	return Drawable.createFromPath(createJpgFilePath(path, name));
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
         * �ƶ������У��ڴ���Դ�ܱ��󣬶��ҶԼ���ͼƬ�ڴ�ռ�Ҳ�����ƣ��������ǻ��ڼ���ͼƬ��ͼƬ������Ӧ�Ĵ�����ʱΪ�������Ӧ�ٶȣ���ǿ�û����飬�����ڼ��ش�ͼƬʱ���ȼ���ͼƬ������ͼ��������ԭͼ����������Ҫ��ͼƬ���չ̶���Сȡ����ͼ��һ��ȡ����ͼ�ķ�����ʹ��BitmapFactory��decodeFile������Ȼ��ͨ�����ݽ�ȥ BitmapFactory.Option���͵Ĳ�������ȡ����ͼ����Option�У�����ֵinSampleSize��ʾ����ͼ��СΪԭʼͼƬ��С�ļ���֮һ����������ֵΪ2����ȡ��������ͼ�Ŀ�͸߶���ԭʼͼƬ��1/2��ͼƬ��С��Ϊԭʼ��С��1/4��



Ȼ�������������ȡ�̶���С������ͼ�ͱȽ������ˣ����磬�����뽫��ͬ��С��ͼƬȥ����������ͼ�߶ȶ�Ϊ200px������Ҫ��֤ͼƬ��ʧ�棬����ô��?�����ܲ��ܽ�ԭʼͼƬ���ص��ڴ����ٽ������Ŵ���ɣ�Ҫ֪�����ƶ������У��ڴ����൱����ģ�����һ��100K��ͼƬ����������ռ�õ��ڴ��ֹ 100K?�����о������֣�Options���и�����inJustDecodeBounds���о���һ�£�����������ʲô��˼�ˣ�SDK�е�E������ô˵��If set to true, the decoder will return null (no bitmap), but the out... fields will still be set, allowing the caller to query the bitmap without having to allocate the memory for its pixels.��˼����˵�����ֵ��Ϊtrue��ô��������ʵ�ʵ�bitmap����������ڴ�ռ������ֻ����һЩ����߽���Ϣ��ͼƬ��С��Ϣ����ô��Ӧ�ķ���Ҳ�ͳ����ˣ�ͨ������inJustDecodeBoundsΪtrue����ȡ��outHeight(ͼƬԭʼ�߶�)�� outWidth(ͼƬ��ԭʼ���)��Ȼ�����һ��inSampleSize(����ֵ)��Ȼ��Ϳ���ȡͼƬ�ˣ�����Ҫע����ǣ�inSampleSize ����С��0���������жϡ�����������£�

FrameLayout fr=(FrameLayout)findViewById(R.id.FrameLayout01);        

BitmapFactory.Options options = new BitmapFactory.Options();        

options.inJustDecodeBounds = true;        // ��ȡ���ͼƬ�Ŀ�͸�        

Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test.jpg", options); //��ʱ����bmΪ��        

options.inJustDecodeBounds = false;         //�������ű�       

 int be = (int)(options.outHeight / (float)200);       

 if (be <= 0)            

be = 1;        

options.inSampleSize = be;        //���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ falseŶ        bitmap=BitmapFactory.decodeFile("/sdcard/test.jpg",options);       

 int w = bitmap.getWidth();       

 int h = bitmap.getHeight();       

 System.out.println(w+"   "+h);        

ImageView iv=new ImageView(this);     

 iv.setImageBitmap(bitmap);�������ǾͿ��Զ�ȡ�ϴ��ͼƬ�ͻ�����ڴ�����ˡ���������ѹ�����ͼƬ������Sdcard�ϵĻ��ͺܼ��ˣ�File file=new File("/sdcard/feng.png");      

  try {            FileOutputStream out=new FileOutputStream(file);           

 if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){               

 out.flush();               

 out.close();           

 }        

} catch (FileNotFoundException e) {            

e.printStackTrace();        

} catch (IOException e) {            

e.printStackTrace();       

 }
         */
}
