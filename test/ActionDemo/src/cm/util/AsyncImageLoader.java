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
 * ʵ���첽����ͼƬ,��ʼ�õ��������ã�SoftReference�������ã���Ϊ�˸��õ�Ϊ��ϵͳ���ձ���,һ�����������þͻ����ڴ棬��ֹ�ڴ����
 *��ʱ�����õĲ���֮�����ڶ���sina΢������Ƶ���ĸ���ͼƬ���ͻ����������ڴ���գ��Ӷ�ͼƬ�ֱ�����������
 *�ٴλص�ԭ����ʱ����û��ͼƬ�ˣ��ʶ��ô�����ڴ˴�����
 */
public class AsyncImageLoader {
	//hashMap�����Image�ĵ�ַ�Լ���Ӧ��������
    private HashMap<String, Drawable> imageCache;
    public AsyncImageLoader() {
        imageCache = new HashMap<String, Drawable>();
    }
    
    //���һ��������һ���ص��ӿڣ���ʹ�øú�����ʱ���Լ�ʵ��
    public Drawable loadDrawable(final String imageUrl,final ImageView imageView, final ImageCallback imageCallback)
    {
        if (imageCache.containsKey(imageUrl)) {
            //�ӻ����л�ȡ
            Drawable img = imageCache.get(imageUrl);
            if (img != null) {
                return img;
            }
        }
        //����Handler��Ŀ����Ϊ��ʹ���߳�,���̶߳��з���Ϣ�������߳�����ͼƬ
    	/*
    	 * final������ԭ���ж��� ��һ���ѷ�����������ֹ�κμ̳����޸����������ʵ�֡� �ڶ�����Ч��
    	 */
        final Handler handler = new Handler() {
        	//receive messages
            public void handleMessage(Message message) {
            	//���ûص��ӿں���,ÿ��������һ��ͼƬ������ã������̷߳���������Ϣ��,Ȼ��ִ����������������������
            	//ʵ�������û���ɣ��Ӷ�ʵ�ֽ������趨ͼƬ
                imageCallback.imageLoaded((Drawable) message.obj, imageView,imageUrl);
            }
        };
        //������һ���µ��߳�����ͼƬ
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                //��ͼƬ�������������HashMap,����������
                imageCache.put(imageUrl, drawable);
               //ȡ��ʵ������Ϣ���󣬴�Handler
                Message message = handler.obtainMessage(0, drawable);
                //������Ϣ����Handler����,��ͼƬ����������Ҫ�������ڴ���ʱ��ô���ģ��лص�������ɣ��ɵ��õ���ʵ�־��巽����
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
        		is.close();//�ر�������
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return d;
    }
    
    //�ص��ӿڣ����ɵ��������ķ���ʵ�֣�����ķ���������ʱʵ�֣�Ȼ����handMessageִ��,������Ҫ��дimageLoaded����
    /*
     * ����Ҫ��һ����ǣ�ͨ���ӿڣ�interface����ʵ�ֶ���ص�����
     * ���Ĺ��ܾ���button�ļ�����һ��setOnClickListener
     * setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(intent, 1);//��onClick��һ���ӿ�������Ҫʵ�������ķ���
			}
		});
		��OnClickListenerҲ��һ���ص������Ľӿ�,Դ�������£�
		public interface OnClickListener { void onClick(View v);}//��ʹ�õ�ʱ����Ҫʵ��onClick����
		���Ծ���ͬ�����һ����imageLoaded����onClick
     */
    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl);
    }
    
    /**
     * ͼƬԲ��
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
