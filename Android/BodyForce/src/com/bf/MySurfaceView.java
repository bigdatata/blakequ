package com.bf;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	// ----添加一个物理世界---->>
	final float RATE = 30;// 屏幕到现实世界的比例 30px：1m;
	World world;// 声明一个物理世界对象
	AABB aabb;// 声明一个物理世界的范围对象
	Vec2 gravity;// 声明一个重力向量对象
	float timeStep = 1f / 60f;// 物理世界模拟的的频率
	int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低
	// --->>给第一个Body赋予力
	Body body1;
	float currX, currY;//当前的实时XY坐标

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		// --添加一个物理世界--->>
		aabb = new AABB();// 实例化物理世界的范围对象
		gravity = new Vec2(0, 10);// 实例化物理世界重力向量对象
		aabb.lowerBound.set(-100, -100);// 设置物理世界范围的左上角坐标
		aabb.upperBound.set(100, 100);// 设置物理世界范围的右下角坐标
		world = new World(aabb, gravity, true);// 实例化物理世界对象
		// ----在物理世界中添加多个动态圆形Body
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				// 取出第一个body实例
				body1 = createCircle(70, 350, 10, false);
			} else {
				createCircle(200, 100 + i * 17, 10, false);
			}
		}
		// 添加屏幕下方添加多个静态物体
		for (int i = 0; i < 20; i++) {
			createCircle(0+i*20, 400, 10, true);
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	} 
	public Body createCircle(float x, float y, float r, boolean isStatic) {
		CircleDef cd = new CircleDef();
		if (isStatic) {
			cd.density = 0;
		} else {
			cd.density = 1;
		}
		cd.friction = 0.8f;
		cd.restitution = 0.3f;
		cd.radius = r / RATE;
		
		BodyDef bd = new BodyDef();
		bd.position.set((x + r) / RATE, (y + r) / RATE);
		
		Body body = world.createBody(bd);
		body.m_userData = new MyCircle(x, y, r);
		body.createShape(cd);
		body.setMassFromShapes();
		return body;
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//根据小球和触屏位置，实时改变力的方向（四个）
		//注意坐标系，判断，见图
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = currX;
			float y = currY;
			System.out.println("x:"+x+" ,y:"+y);
			float x1 = event.getX();
			float y1 = event.getY();
			Vec2 vForce = null;
			if(x > x1 && y < y1){
				System.out.println("----3----");
				vForce = new Vec2(-100, 100);
			}else if(x >= x1 && y >= y1){
				System.out.println("----2----");
				vForce = new Vec2(-100, -100);
			}else if(x < x1 && y < y1){
				System.out.println("----4----");
				vForce = new Vec2(100, 100);
			}else{
				System.out.println("----1----");
				vForce = new Vec2(100, -100);
			}
			body1.applyForce(vForce, body1.getWorldCenter());
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//表示矢量力，在x方向的力是150，y方向是-150，单位是牛N
		//注意y为负表示向上，为正向下
		Vec2 vForce = new Vec2(150,-150);
		body1.applyForce(vForce, body1.getWorldCenter());
		return super.onKeyDown(keyCode, event);
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					((MyCircle) body.m_userData).draw(canvas, paint);
					body = body.m_next;
				}
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		// --开始模拟物理世界--->>
		world.step(timeStep, iterations);// 物理世界进行模拟
		// 取出body链表表头
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// 设置MyCircle的X，Y坐标
			MyCircle mc = (MyCircle) body.m_userData;
			currX = body.getPosition().x * RATE - mc.r;
			currY = body.getPosition().y * RATE - mc.r;
			mc.setX(currX);
			mc.setY(currY);
			// 将链表指针指向下一个body元素
			body = body.m_next;
		}
	}

	public void run() {
		while (flag) {
			myDraw();
			Logic();
			try {
				Thread.sleep((long) timeStep * 1000);
			} catch (Exception ex) {
				Log.e("Himi", "Thread is Error!");
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
