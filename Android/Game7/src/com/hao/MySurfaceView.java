package com.hao;

import java.io.File;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Runnable, Callback{
	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint p;
	public static Vector<Bitmap> vec_bmp;
	public static Vector<String> vec_string;
	private int col;
	private boolean flag = false;
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		th = new Thread(this);
		sfh = getHolder();
		sfh.addCallback(this);
		p = new Paint();
		p.setAntiAlias(true);
		vec_bmp = new Vector<Bitmap>();
		vec_string = new Vector<String>();
		this.setFocusable(true);
	}

	private void draw(){
		try{
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.BLACK);
				if(vec_bmp != null && vec_bmp.size() != 0){
					for(int i=0 ;i<vec_bmp.size(); i++){
						Bitmap bitmap = vec_bmp.elementAt(i);
						p.setStyle(Style.STROKE);//对下面的矩形描边
						canvas.drawRect((i % col) * 104 + 1, (i / col) * 100 + 1, (i % col) * 104 + 104 - 2, (i / col) * 100 + 100 - 2, p);
						canvas.drawBitmap(bitmap, (i % col) * 100, (i / col) * 100, p);
						p.setColor(Color.YELLOW);
						canvas.drawText(vec_string.elementAt(i), (i % col) * 104 + 10, (i / col) * 100 + 97, p);
						p.setColor(Color.WHITE);
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			draw();
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

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = true;
		col = this.getWidth() / 100;
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

}
