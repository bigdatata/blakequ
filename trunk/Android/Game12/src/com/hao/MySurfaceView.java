package com.hao;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder sfh;  
    private Paint paint;  
    private boolean flag = true;  
    private Canvas canvas;  
    public static int screenW, screenH;  
    // -----------������SurfaceView��Ϸ���  
    // ���������߳�Ա����(��ʼ�㣬���ƣ������㣩����ֹ�㣬3������)  
    private int startX, startY, controlX, controlY, endX, endY;  
    // Path  
    private Path path;  
    // Ϊ�˲�Ӱ�������ʣ�������Ʊ��������ߵ�����һ���»���  
    private Paint paintQ;  
    // ����⣨�ñ��������߸����ԣ�  
    private Random random; 
    
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setFocusable(true);
		paint = new Paint();
		paint.setAntiAlias(true);
		paintQ = new Paint();
		paintQ.setStyle(Style.STROKE);
		paintQ.setStrokeWidth(4);
		paintQ.setAntiAlias(true);
		paintQ.setColor(Color.RED);
		
		path = new Path();
		sfh = getHolder();
		sfh.addCallback(this);
		random = new Random();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = true;
		screenH = getHeight();
		screenW = getWidth();
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		endX = (int) event.getX();
		endY = (int) event.getY();
		return true;
	}

	private void logic(){
		if(endX != 0 && endY != 0){
			controlX = random.nextInt((endX - startX)/2);
			controlY = random.nextInt((endY - startY)/2);
		}
	}
	
	/**
	 * ���Ʊ���������
	 * @param canvas
	 */
	private void drawQpath(Canvas canvas){
		//����path
		path.reset();
		// ���������ߵ���ʼ�� 
		path.moveTo(startX, startY);
		// ���ñ��������ߵĲ������Լ���ֹ��  
        path.quadTo(controlX, controlY, endX, endY);  
        // ���Ʊ��������ߣ�Path�� 
        canvas.drawPath(path, paintQ);
	}
	
	private void draw(){
		 try {  
	            canvas = sfh.lockCanvas();  
	            if (canvas != null) {  
	                canvas.drawColor(Color.BLACK);  
	                // -----------������SurfaceView��Ϸ���  
	                drawQpath(canvas);  
	            }  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        } finally {  
	            if (canvas != null)  
	                sfh.unlockCanvasAndPost(canvas);  
	        }  
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {  
            long start = System.currentTimeMillis();  
            draw();  
            logic();  
            long end = System.currentTimeMillis();  
            try {  
                if (end - start < 50) {  
                    Thread.sleep(50 - (end - start));  
                }  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
		}
	}
}
