package com.hao.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 画布旋转等操作所用到的save,restore操作
 * @author Administrator
 *
 */
public class TestCanvasStore extends SurfaceView implements Callback ,Runnable{

	private Thread thread;
	private SurfaceHolder mHolder;   
    private Canvas canvas;
    private boolean flag = true;
    private Paint mPaint, paint;   
    private int mWidth, mHeight;
	public TestCanvasStore(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 mHolder = getHolder();   
         mHolder.addCallback(this);
         thread = new Thread(this);
         mPaint = new Paint();
         mPaint.setColor(Color.BLUE);
         mPaint.setAntiAlias(true);
         paint = new Paint();
         paint.setAntiAlias(true);
         paint.setColor(Color.GRAY);
         
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
		mWidth = this.getWidth();
		mHeight = this.getHeight();
		thread.start();
	}
	
	void draw(int angle){
		canvas = mHolder.lockCanvas();   
		canvas.drawRect(new Rect(0, 0, mWidth, mHeight), paint);//每次从新绘制一张画布,如果不绘制，则会画出很多图2
		//由于是先绘制的矩形1，故而不会对它产生影响
        canvas.drawRect(100, 200, 200, 300, mPaint);   //图1
            
        //在旋转指定的图形对其他部分无影响,即图1在画布上的位置始终不变
        //save和restore之间，往往夹杂的是对Canvas的特殊操作,如果不save，restore则会改变在其后的绘图操作
        canvas.save();   
        canvas.rotate(angle);   
        mPaint.setColor(Color.RED);   
        canvas.drawRect(150, 10, 200, 60, mPaint);   //图2
        canvas.restore();   
        
        //他是绘制在画布旋转之后，故而这个圆要受到影响(没有save，restore)
        canvas.drawCircle(mWidth - 100, mHeight - 100, 10, mPaint);//图3
        mHolder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		while(flag){
			i += 10;
			draw(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
