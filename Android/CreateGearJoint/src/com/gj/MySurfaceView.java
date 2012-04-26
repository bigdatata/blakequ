package com.gj;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.GearJoint;
import org.jbox2d.dynamics.joints.GearJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

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
	// ------>>齿轮关节
	Body body1, body2;
	float body1x = 40, body1y = 20, body1w = 80, body1h = 80, body2x = 200, body2y = 20, body2w = 80, body2h = 80;
	RevoluteJoint rj1, rj2;

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
		body1 = createPolygon(body1x, body1y, body1w, body1h, 1);
		body2 = createPolygon(body2x, body2y, body2w, body2h, 1);
		rj1 = createRevoluteJoint1();
		rj2 = createRevoluteJoint2();
		// 设置齿轮关节
		createGearJoint();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	// 齿轮关节
	public GearJoint createGearJoint() {
		//创建齿轮关节数据实例
		GearJointDef gjd = new GearJointDef();
		//设置齿轮关节的两个Body
		gjd.body1 = body1;
		gjd.body2 = body2;
		//设置齿轮关节绑定的两个旋转关节
		gjd.joint1 = rj1;
		gjd.joint2 = rj2;
		//设置旋转角度比
		gjd.ratio = 1; //为10表示一个旋转10次一个才一次
		//通过world创建一个齿轮关节
		GearJoint gj = (GearJoint) world.createJoint(gjd);
		return gj;
	}

	//创建第一个旋转关节
	public RevoluteJoint createRevoluteJoint1() {
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.initialize(world.getGroundBody(), body1, body1.getWorldCenter());
		rjd.maxMotorTorque = 20;
		rjd.motorSpeed = 20;
		rjd.enableMotor = true;
		RevoluteJoint rj = (RevoluteJoint) world.createJoint(rjd);
		return rj;
	}

	//创建第二个旋转关节
	public RevoluteJoint createRevoluteJoint2() {
		RevoluteJointDef rj = new RevoluteJointDef();
		rj.initialize(world.getGroundBody(), body2, body2.getWorldCenter());
		return (RevoluteJoint) world.createJoint(rj);
	}

	public Body createPolygon(float x, float y, float width, float height, float density) {
		PolygonDef pd = new PolygonDef();
		pd.density = density;
		pd.friction = 0.8f;
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
