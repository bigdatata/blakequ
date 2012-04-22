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
	//定义两个矩形图形的宽高坐标
	private int rectX1 = 10, rectY1 = 10, rectW1 = 40, rectH1 = 40;
	private int rectX2 = 100, rectY2 = 110, rectW2 = 40, rectH2 = 40;
	private Rect rectA, rectB;
	//便于观察是否发生了碰撞设置一个标识位
	private boolean isCollsion;
	//定义第一个矩形的矩形碰撞数组,这是个相对数组
	private Rect clipRect1 = new Rect(0, 0, 15, 15);
	private Rect clipRect2 = new Rect(rectW1 - 15, rectH1 - 15, rectW1, rectH1);
	private Rect[] arrayRect1 = new Rect[] { clipRect1, clipRect2 };
	//定义第二个矩形的矩形碰撞数组
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
			//让矩形1随着触屏位置移动
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
	 * 多矩阵碰撞检测
	 * 多矩阵：其实就是两个大物体其实是不规则物体(这里看着两个大矩阵，每个里面包含许多小矩阵)，
	 * 然后不规则物体利用分割，将它分割成几个小矩阵，形成一个矩阵数组
	 * 然后分别检测两个大矩阵中的小矩阵是不是冲突了，如果是，则认为两个大矩阵冲突
	 * @param r1
	 * @param r2
	 * @return
	 */
	private boolean isCollisionMoreRect(Rect[] r1, Rect[] r2){
		Rect rect = null, rect2 = null;
		for(int i=0; i < r1.length; i++){
			//依次取出第一个矩形数组的每个矩形实例
			rect = r1[i];
			//获取到第一个矩形数组中每个矩形元素的属性值
			int x1 = rect.left + rectA.left;
			int y1 = rect.top + rectA.top;
			int w1 = rect.width();
			int h1 = rect.height();
			for(int j=0; j<r2.length; j++){
				rect2 = r2[j];
				//获取到第二个矩形数组中每个矩形元素的属性值
				int x2 = rect2.left + rectB.left;
				int y2 = rect2.top + rectB.top;
				int w2 = rect2.width();
				int h2 = rect2.height();
				//将所有的未碰撞状态判断出来
				//进行循环遍历两个矩形碰撞数组所有元素之间的位置关系
				if(x1 >= x2 && x1 >= x2+w2){ 				//(x1, y1)位于(x2, y2)的右边
				}else if(x1 <= x2 && x2 >= x1+w1){			//(x1, y1)位于(x2, y2)的左边
				}else if(y2 >= y1 && y2 >= y1+h1){			//(x1, y1)位于(x2, y2)的上边
				}else if(y2 <= y1 && y1 >= y2+h2){			//(x1, y1)位于(x2, y2)的下边
				}else{
					//只要有一个碰撞矩形数组与另一碰撞矩形数组发生碰撞则认为碰撞
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
					canvas.drawText("Collision！", 0, 30, paint);
				}
				canvas.drawRect(rectA, paint);
				canvas.drawRect(rectB, paint);
				//绘制碰撞区域使用非填充，并设置画笔颜色
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.RED);
				//绘制第一个矩形的所有矩形碰撞区域
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
