package com.agb;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.Shape;
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
	// ---记录AABB中body的数量
	private int bodySize;
	private float touchX, touchY;
	private int range = 10;

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		this.setFocusable(true);
		// --添加一个物理世界--->>
		aabb = new AABB();// 实例化物理世界的范围对象
		gravity = new Vec2(0, 10);// 实例化物理世界重力向量对象
		aabb.lowerBound.set(-100, -100);// 设置物理世界范围的左上角坐标
		aabb.upperBound.set(100, 100);// 设置物理世界范围的右下角坐标
		world = new World(aabb, gravity, true);// 实例化物理世界对象
		// ----在物理世界中添加多个圆形Body

		for (int i = 0; i < 10; i++) {
			createCircle(130, 10 + i * 17, 10, false);
		}
		for (int i = 0; i < 5; i++) {
			createCircle(112 + i * 20, 300, 10, true);
		}

	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	/**
	 * 
	 * @param x 获取body的范围中心点x坐标
	 * @param y 获取body的范围中心点y坐标
	 * @param range 以xy位置为中心点的的范围值[x-range,y-range][x+range,y+range]
	 * @param maxCount 限制最大返回Body的个数
	 * @return
	 */
	public Shape[] getBodies(float x, float y, float range, int maxCount) {
		AABB aabbBody = new AABB();
		aabbBody.lowerBound.set((x - range) / RATE, (y - range) / RATE);
		aabbBody.upperBound.set((x + range) / RATE, (y + range) / RATE);
		Shape[] shapes = world.query(aabbBody, maxCount);
		// 遍历此aabb范围中的body，筛选操作
		for (int i = 0; i < shapes.length; i++) {
			if (shapes[i].getBody().isStatic()) {
				// ...判定物体是否为静态
			}
			if (shapes[i].getBody().isSleeping()) {
				// ...判定物体是否进入休眠
			}
		}
		return shapes;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Shape[] sp = getBodies(touchX = event.getX(), touchY = event.getY(),
				range, 10);
		bodySize = sp.length;

		return true;
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

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				paint.setColor(Color.BLACK);
				Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					((MyCircle) body.m_userData).draw(canvas, paint);
					body = body.m_next;
				}
				canvas.drawText("与指定AABB范围相交了" + bodySize + "个Body！", 20, 50,
						paint);
				Paint paintAABB = new Paint();
				paintAABB.setStyle(Style.STROKE);
				paintAABB.setColor(0xffff0000);
				canvas.drawCircle(touchX, touchY, range, paintAABB);
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
			mc.setX(body.getPosition().x * RATE - mc.r);
			mc.setY(body.getPosition().y * RATE - mc.r);
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
