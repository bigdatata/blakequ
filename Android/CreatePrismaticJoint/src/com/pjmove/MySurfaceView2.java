package com.pjmove;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 通过移动关节绑定两个Body动作
 * @author Administrator
 *
 */
public class MySurfaceView2 extends SurfaceView implements Callback, Runnable {
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
	// ------>>移动关节
	Body body1, body2;
	float body1x = 5, body1y = 40, body1w = 60, body1h = 70;
	float body2x = 15, body2y = 100, body2w = 40, body2h = 40;

	public MySurfaceView2(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		this.setFocusable(true);
		paint.setStyle(Style.STROKE);
		// --添加一个物理世界--->>
		aabb = new AABB();
		gravity = new Vec2(0, 10);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		// ----在物理世界中添加两个矩形Body
		body1 = createPolygon(body1x, body1y, body1w, body1h, false);
		body2 = createPolygon(body2x, body2y, body2w, body2h, false);
		//创建一个下边界(障碍物)
		createPolygon(0, 150, 20, 2, true);
//		createPolygon(0, 200, 60, 2, true);
		createPolygon(0, 350, 350, 2, true);
		// 设置移动关节
		createPrismaticJoint();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	//创建移动关节
	public PrismaticJoint createPrismaticJoint() {
		//创建移动关节数据实例
		PrismaticJointDef pjd = new PrismaticJointDef();
		//预设马达的最大力
		pjd.maxMotorForce = 10;
		//马达的最终力
		pjd.motorSpeed = 10;
		//启动马达
		pjd.enableMotor = true;
		//设置位移最小偏移值
		pjd.lowerTranslation = -10.0f / RATE;
		//设置位移最大偏移值
		pjd.upperTranslation = 10.0f / RATE;
		//启动限制
		pjd.enableLimit = true;
		//初始化移动关节数据(body2只能以body1为中心 沿它的y轴移动)
		pjd.initialize(body1, body2, body1.getWorldCenter(), new Vec2(0, 1));
		//通过world创建一个移动关节
		PrismaticJoint pj = (PrismaticJoint) world.createJoint(pjd);
		return pj;
	}

	public Body createPolygon(float x, float y, float width, float height, boolean isStatic) {
		PolygonDef pd = new PolygonDef();
		if (isStatic) {
			pd.density = 0;
		} else {
			pd.density = 1;
		}
		pd.friction = 0.0f;
		pd.restitution = 0.3f;
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		BodyDef bd = new BodyDef();
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);
		Body body = world.createBody(bd);
		bd.angle = (float) (15 * Math.PI / 180);
		body.m_userData = new MyRect(x, y, width, height);
		body.createShape(pd);
		body.setMassFromShapes();
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
				canvas.drawLine(body1.getWorldCenter().x * RATE, body1.getWorldCenter().y * RATE, body2.getWorldCenter().x * RATE, body2.getWorldCenter().y
						* RATE, paint);
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		world.step(timeStep, iterations);
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			MyRect mc = (MyRect) body.m_userData;
			mc.setX(body.getPosition().x * RATE - mc.w / 2);
			mc.setY(body.getPosition().y * RATE - mc.h / 2);
			mc.setAngle((float) (body.getAngle() * 180 / Math.PI));
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
