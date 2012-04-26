package com.dj;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
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
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	int iterations = 10;
	// ------>>距离关节
	//声明一个距离关节
	DistanceJoint dj;
	Body body1, body2;
	float body1x = 16, body1y = 50, body1w = 70, body1h = 10, body2x = 106, body2y = 20, body2w = 40, body2h = 30;

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);

		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		this.setFocusable(true);
		// --添加一个物理世界--->>
		aabb = new AABB();
		gravity = new Vec2(0, 10);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		// ----在物理世界中添加两个矩形Body
		body1 = createPolygon(body1x, body1y, body1w, body1h, false);
		body2 = createPolygon(body2x, body2y, body2w, body2h, false);
		createPolygon(0, 100, 80, 2, true);
		// 设置距离关节
		dj = createDistanceJoint();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	// 距离关节
	public DistanceJoint createDistanceJoint() {
		//创建一个距离关节数据实例
		DistanceJointDef dje = new DistanceJointDef();
		// 初始化关节数据
		dje.initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter());
		// dje.collideConnected=true;
		//利用世界通过传入的距离关节数据创建一个关节
		DistanceJoint dj = (DistanceJoint) world.createJoint(dje);
		return dj;
	}

	public Body createPolygon(float x, float y, float width, float height, boolean isStatic) {
		// ---创建多边形皮肤
		PolygonDef pd = new PolygonDef(); // 实例一个多边形的皮肤
		if (isStatic) {
			pd.density = 0; // 设置多边形为静态
		} else {
			pd.density = 1; // 设置多边形的质量
		}
		pd.friction = 0.8f; // 设置多边形的摩擦力
		pd.restitution = 0.3f; // 设置多边形的恢复力
		// 设置多边形快捷成盒子(矩形)
		// 两个参数为多边形宽高的一半
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---创建刚体
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// 设置刚体的坐标
		// ---创建Body（物体）
		Body body = world.createBody(bd); // 物理世界创建物体
		body.m_userData = new MyRect(x, y, width, height);
		body.createShape(pd); // 为Body添加皮肤
		body.setMassFromShapes(); // 将整个物体计算打包
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					((MyRect) body.m_userData).draw(canvas, paint);
					body = body.m_next;
				}
				canvas.drawLine(dj.getAnchor1().x * RATE, dj.getAnchor1().y * RATE, dj.getAnchor2().x * RATE, dj.getAnchor2().y * RATE, paint);
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
			// 设置MyCircle的X，Y坐标以及angle角度
			MyRect mc = (MyRect) body.m_userData;
			mc.setX(body.getPosition().x * RATE - mc.w / 2);
			mc.setY(body.getPosition().y * RATE - mc.h / 2);
			mc.setAngle((float) (body.getAngle() * 180 / Math.PI));
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
