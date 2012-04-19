package com.hao;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnTouchListener;

/**
 *  �ܽ᣺
 *   1.������һֱ�����������ݱ�˳��onDown->onShowPress->onLongPress��
 *   2.������һֱ�������ƶ���onScroll/���ƶ���onFling ����ָ�뿪��Ļ��
 *   ע�� ��������һֱ�����ƶ��������ָ���뿪��Ļһֱ����onScroll,�������ƶ����ٶȶ��,��Զ������onFling��
 * @author Administrator
 *
 */
public class GestrueView extends SurfaceView implements Runnable, Callback, 
		OnGestureListener, OnTouchListener{
	private Thread th = new Thread(this);  
    private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private Bitmap bmp;  
    private GestureDetector gd;  
    private int bmp_x, bmp_y;  
    private boolean isChagePage;  
    private Vector<String> v_str;// ��ע1
    private boolean flag = true;
	public GestrueView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		v_str = new Vector<String>();
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.himi_dream);
		gd = new GestureDetector(this);
		this.setOnTouchListener(this);
		gd.setIsLongpressEnabled(true);
		// setLongClickable( true )�Ǳ���ģ���Ϊ ֻ��������  
        // ���ǵ�ǰ��SurfaceView(view)���ܹ�����ͬ�ڴ�����ʽ;  
        // ���磺ACTION_MOVE�����߶��ACTION_DOWN  
		this.setLongClickable(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			this.draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ��ϵͳ�����˴˷����Ŵ�����view�������������ȡ��view�Ŀ�ߣ�����ЩͯЬ���ǰѶ��������ڳ�ʼ�������  
	 * �߳���÷�����������������Ϊ���ڳ�ʼ����Ļ�����view��û����,�����ύ����unlockCanvasAndPost��ʱ����쳣����  
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		bmp_x = (getWidth() - bmp.getWidth())>>2;
		bmp_y = (getHeight()- bmp.getHeight())>>2;
		flag = true;
		th.start();
	}
	
	public void draw() {  
        try {  
            canvas = sfh.lockCanvas();  
            if (canvas != null) {  
                canvas.drawColor(Color.WHITE);// ����ˢ��  
                canvas.drawBitmap(bmp, bmp_x, bmp_y, paint);   
                paint.setTextSize(20);// �������ִ�С   
                paint.setColor(Color.WHITE);  
                //���ﻭ��һ�����η���ͯЬ�ǿ������Ʋ������õĺ���������Щ  
                canvas.drawRect(50, 30, 175,120, paint);  
                paint.setColor(Color.RED);// ����������ɫ  
                if (v_str != null) {  
                    for (int i = 0; i < v_str.size(); i++) {  
                        canvas.drawText(v_str.elementAt(i), 50, 50 + i * 30,  
                                paint);  
                    }  
                }  
            }  
        } catch (Exception e) {  
            Log.v("Himi", "draw is Error!");  
        } finally {  
            sfh.unlockCanvasAndPost(canvas);  
        }  
    }  

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

	// --------------������ʹ��OnGestureListener���Ƽ�����ʱ����д�ĺ���---------  
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onDown");
		return false;
	}

	/**  
     * @���·����еĲ������ͣ�  
     * @e1����1���� ACTION_DOWN MotionEvent ���µĶ���  
     * @e2����һ����ACTION_UP MotionEvent ̧��Ķ���(����Ҫ���±�ע5�Ľ���)  
     * @velocityX��X���ϵ��ƶ��ٶȣ�����/��  
     * @velocityY��Y���ϵ��ƶ��ٶȣ�����/��  
     */
	 // ACTION_DOWN ���컬���� ACTION_UP
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
//		System.out.println("-----------length:"+(e1.getX()-e2.getX()));
		v_str.add("onFling");  
        //-------��ע5----------  
		/**
		 * ���Խ������һ����MotionEvent.ACTION_DOWN �ڶ�����MotionEvent.ACTION_UP!
		 * ����������֪���û����׻����ľ���ȵ��ˣ������e2.getX()-e1.getX()
		 */
         if(e1.getAction()==MotionEvent.ACTION_MOVE){  
        	 v_str.add("onFling move");  
         }else if(e1.getAction()==MotionEvent.ACTION_DOWN){  
        	 v_str.add("onFling down");  
         }else if(e1.getAction()==MotionEvent.ACTION_UP){  
        	 v_str.add("onFling up");  
         }  
         if(e2.getAction()==MotionEvent.ACTION_MOVE){  
        	 v_str.add("onFling move");  
         }else if(e2.getAction()==MotionEvent.ACTION_DOWN){  
        	 v_str.add("onFling down");  
         }else if(e2.getAction()==MotionEvent.ACTION_UP){  
        	 v_str.add("onFling up");  
         }  
        if (isChagePage)  
            bmp = BitmapFactory.decodeResource(getResources(),  
                    R.drawable.himi_dream);  
        else  
            bmp = BitmapFactory.decodeResource(getResources(),  
                    R.drawable.himi_warm);  
        isChagePage = !isChagePage; 
		return false;
	}

	// ACTION_DOWN ������������
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onLongPress");
	}

	// ACTION_DOWN ��������
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
//		System.out.println("-----------length:"+(e1.getX()-e2.getX()));
		v_str.add("onScroll");
		return false;
	}

	// ACTION_DOWN ���̰����ƶ� 
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onShowPress");
	}

	// �̰�ACTION_DOWN��ACTION_UP
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		v_str.add("onSingleTapUp");
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v_str != null)  
            v_str.removeAllElements();
		return gd.onTouchEvent(event);
	}

}
