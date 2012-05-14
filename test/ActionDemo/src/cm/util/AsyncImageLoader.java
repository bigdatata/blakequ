package cm.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
/**
 * 实现异步载入图片,开始用的是软引用，SoftReference是软引用，是为了更好的为了系统回收变量,一旦对象不再引用就回收内存，防止内存溢出
 *当时软引用的不好之处，在对于sina微博这种频繁的更新图片，就会很容易造成内存回收，从而图片又必须重新下载
 *再次回到原来的时候，又没有图片了，故而用存对象在此处更好
 */
public class AsyncImageLoader {
	//hashMap存的是Image的地址以及对应的软引用
    private HashMap<String, Drawable> imageCache;
    public AsyncImageLoader() {
        imageCache = new HashMap<String, Drawable>();
    }
    
    //最后一个参数是一个回调接口，当使用该函数的时候自己实现
    public Drawable loadDrawable(final String imageUrl,final ImageView imageView, final ImageCallback imageCallback)
    {
        if (imageCache.containsKey(imageUrl)) {
            //从缓存中获取
            Drawable img = imageCache.get(imageUrl);
            if (img != null) {
                return img;
            }
        }
        //建立Handler的目的是为了使用线程,给线程队列发消息，启动线程下载图片
    	/*
    	 * final方法的原因有二： 第一、把方法锁定，防止任何继承类修改它的意义和实现。 第二、高效。
    	 */
        final Handler handler = new Handler() {
        	//receive messages
            public void handleMessage(Message message) {
            	//调用回调接口函数,每次下载完一个图片都会调用（处理线程发过来的消息）,然后执行这个方法，而这个方法的
            	//实现是由用户完成，从而实现交互，设定图片
                imageCallback.imageLoaded((Drawable) message.obj, imageView,imageUrl);
            }
        };
        //建立新一个新的线程下载图片
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                //将图片下载下来后放入HashMap,并设置引用
                imageCache.put(imageUrl, drawable);
               //取得实例化消息对象，从Handler
                Message message = handler.obtainMessage(0, drawable);
                //发送消息，由Handler接收,即图片下载完后就需要处理，至于处理时怎么样的？有回调函数完成（由调用的类实现具体方法）
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }
    
    public static Drawable loadImageFromUrl(String url){
        URL m = null;
        InputStream is = null;
        try {
            m = new URL(url);
            is = (InputStream) m.getContent();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(is, "src");
        try {
        	if(is!=null)
        	{
        		is.close();//关闭输入流
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return d;
    }
    
    //回调接口，它由调用这个类的方法实现，具体的方法当调用时实现，然后在handMessage执行,并且需要重写imageLoaded函数
    /*
     * 最重要的一点就是：通过接口（interface）来实现定义回调函数
     * 他的功能就像button的监听器一样setOnClickListener
     * setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(intent, 1);//在onClick是一个接口里面需要实现其具体的方法
			}
		});
		在OnClickListener也是一个回调函数的接口,源代码如下：
		public interface OnClickListener { void onClick(View v);}//在使用的时候是要实现onClick方法
		所以就如同下面的一样，imageLoaded就像onClick
     */
    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl);
    }
    
    /**
     * 图片圆角
     * @param bitmap
     * @return
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) { 
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), 
            bitmap.getHeight(), Config.ARGB_8888); 
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242; 
        final Paint paint = new Paint(); 
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
        final RectF rectF = new RectF(rect); 
        final float roundPx = 12;

        paint.setAntiAlias(true); 
        canvas.drawARGB(0, 0, 0, 0); 
        paint.setColor(color); 
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
        canvas.drawBitmap(bitmap, rect, rect, paint); 
        return output; 
    }
    
}
