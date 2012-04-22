package com.hao;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	private int screenW, screenH;
	//������������ͼ�εĿ������
	private int rectX1 = 10, rectY1 = 10, rectW1 = 40, rectH1 = 40;
	private int rectX2 = 100, rectY2 = 110, rectW2 = 40, rectH2 = 40;
	private Rect rectA, rectB;
	//���ڹ۲��Ƿ�������ײ����һ����ʶλ
	private boolean isCollsion;
	//�����һ�����εľ�����ײ����,���Ǹ��������
	private Rect clipRect1 = new Rect(0, 0, 15, 15);
	private Rect clipRect2 = new Rect(rectW1 - 15, rectH1 - 15, rectW1, rectH1);
	private Rect[] arrayRect1 = new Rect[] { clipRect1, clipRect2 };
	//����ڶ������εľ�����ײ����
	private Rect clipRect3 = new Rect(0, 0, 15, 15);
	private Rect clipRect4 = new Rect(rectW2 - 15, rectH2 - 15, rectW2, rectH2);
	private Rect[] arrayRect2 = new Rect[] { clipRect3, clipRect4 };
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		rectA = new Rect(rectX1, rectY1, rectX1+rectW1, rectY1+rectH1);
		rectB = new Rect(rectX2, rectY2, rectX2+rectW2, rectY2+rectH2);
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusableInTouchMode(true);
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
		screenW = getWidth();
		screenH = getHeight();
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
		if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
			//�þ���1���Ŵ���λ���ƶ�
			rectA.left = (int) (event.getX() - rectA.width()/2);
			rectA.top = (int) (event.getY() - rectA.height()/2);
			rectA.bottom = rectA.top + rectH1;
			rectA.right = rectA.left + rectW1;
			if(isCollisionMoreRect(arrayRect1, arrayRect2)){
				isCollsion = true;
			}else{
				isCollsion = false;
			}
		}
		return true;
	}
	
	/**
	 * �������ײ���
	 * �������ʵ����������������ʵ�ǲ���������(���￴�����������ÿ������������С����)��
	 * Ȼ�󲻹����������÷ָ�����ָ�ɼ���С�����γ�һ����������
	 * Ȼ��ֱ�������������е�С�����ǲ��ǳ�ͻ�ˣ�����ǣ�����Ϊ����������ͻ
	 * @param r1
	 * @param r2
	 * @return
	 */
	private boolean isCollisionMoreRect(Rect[] r1, Rect[] r2){
		Rect rect = null, rect2 = null;
		for(int i=0; i < r1.length; i++){
			//����ȡ����һ�����������ÿ������ʵ��
			rect = r1[i];
			//��ȡ����һ������������ÿ������Ԫ�ص�����ֵ
			int x1 = rect.left + rectA.left;
			int y1 = rect.top + rectA.top;
			int w1 = rect.width();
			int h1 = rect.height();
			for(int j=0; j<r2.length; j++){
				rect2 = r2[j];
				//��ȡ���ڶ�������������ÿ������Ԫ�ص�����ֵ
				int x2 = rect2.left + rectB.left;
				int y2 = rect2.top + rectB.top;
				int w2 = rect2.width();
				int h2 = rect2.height();
				//�����е�δ��ײ״̬�жϳ���
				//����ѭ����������������ײ��������Ԫ��֮���λ�ù�ϵ
				if(x1 >= x2 && x1 >= x2+w2){ 				//(x1, y1)λ��(x2, y2)���ұ�
				}else if(x1 <= x2 && x2 >= x1+w1){			//(x1, y1)λ��(x2, y2)�����
				}else if(y2 >= y1 && y2 >= y1+h1){			//(x1, y1)λ��(x2, y2)���ϱ�
				}else if(y2 <= y1 && y1 >= y2+h2){			//(x1, y1)λ��(x2, y2)���±�
				}else{
					//ֻҪ��һ����ײ������������һ��ײ�������鷢����ײ����Ϊ��ײ
					return true;
				}
			}
		}
		return false;
	}
	
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				paint.setStyle(Style.FILL); 	
				paint.setColor(Color.BLUE);
				if(isCollsion){
					paint.setTextSize(20);
					canvas.drawText("Collision��", 0, 30, paint);
				}
				canvas.drawRect(rectA, paint);
				canvas.drawRect(rectB, paint);
				//������ײ����ʹ�÷���䣬�����û�����ɫ
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.RED);
				//���Ƶ�һ�����ε����о�����ײ����
				for(int i=0; i<arrayRect1.length; i++){
					canvas.drawRect(arrayRect1[i].left+rectA.left, arrayRect1[i].top+rectA.top, 
							arrayRect1[i].right + rectA.left, arrayRect1[i].bottom + rectA.top, paint);
				}
				
				for(int i=0; i<arrayRect2.length; i++){
					canvas.drawRect(arrayRect2[i].left+rectB.left, arrayRect2[i].top+rectB.top, 
							arrayRect2[i].right+rectB.left, arrayRect2[i].bottom+rectB.top, paint);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(canvas != null){
				sfh.unlockCanvasAndPost(canvas);
			}
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

}
