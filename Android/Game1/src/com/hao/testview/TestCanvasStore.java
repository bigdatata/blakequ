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
 * ������ת�Ȳ������õ���save,restore����
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
		canvas.drawRect(new Rect(0, 0, mWidth, mHeight), paint);//ÿ�δ��»���һ�Ż���,��������ƣ���ử���ܶ�ͼ2
		//�������Ȼ��Ƶľ���1���ʶ������������Ӱ��
        canvas.drawRect(100, 200, 200, 300, mPaint);   //ͼ1
            
        //����תָ����ͼ�ζ�����������Ӱ��,��ͼ1�ڻ����ϵ�λ��ʼ�ղ���
        //save��restore֮�䣬�������ӵ��Ƕ�Canvas���������,�����save��restore���ı������Ļ�ͼ����
        canvas.save();   
        canvas.rotate(angle);   
        mPaint.setColor(Color.RED);   
        canvas.drawRect(150, 10, 200, 60, mPaint);   //ͼ2
        canvas.restore();   
        
        //���ǻ����ڻ�����ת֮�󣬹ʶ����ԲҪ�ܵ�Ӱ��(û��save��restore)
        canvas.drawCircle(mWidth - 100, mHeight - 100, 10, mPaint);//ͼ3
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
