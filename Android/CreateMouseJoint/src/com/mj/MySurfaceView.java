package com.mj;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

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
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	int iterations = 10;
	// ------>>旋转关节
	Body body1;
	float body1x = 52, body1y = 10, body1w = 10, body1h = 70;
	float touchX=body1x+body1w/2, touchY=body1y+body1h/2;
	MouseJoint mouseJoint;
	boolean bodyFlag;
	Body test = null;

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		// --添加一个物理世界--->>
		aabb = new AABB();
		gravity = new Vec2(0, 10);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		// ----在物理世界中添加一个矩形Body
		body1 = createPolygon(body1x + body1w / 2 - body1w / 2, body1y + body1h / 2 - body1h / 2, body1w, body1h, false);
		// 设置鼠标关节
		mouseJoint = createMouseJoint();
		mouseJoint.m_gamma = 100;// 弹性度
	}

	public void surfaceCreated(SurfaceHolder holder) {
		//添加边界
		createPolygon(5, 5, this.getWidth() - 10, 2, true);
		createPolygon(5, this.getHeight() - 10, this.getWidth() - 10, 2, true);
		createPolygon(5, 5, 2, this.getHeight() - 10, true);
		createPolygon(this.getWidth() - 10, 5, 2, this.getHeight() - 10, true);
		flag = true;
		th = new Thread(this);
		th.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		touchX = event.getX();
		touchY = event.getY();
		//唤醒body1
		body1.wakeUp();
		mouseJoint.m_target.set(event.getX() / RATE, event.getY() / RATE);
		return true;
	}

	// 鼠标关节
	public MouseJoint createMouseJoint() {
		MouseJointDef mjd = new MouseJointDef();
		//设置鼠标关节的第一个Body实例（默认使用接地Body）
		mjd.body1 = world.getGroundBody();
		//设置鼠标关节的第二个Body实例(被拖拽的Body)
		mjd.body2 = body1;
		//设置目标点的X坐标
		mjd.target.x = body1.getPosition().x;
		//设置目标点的Y坐标
		mjd.target.y = body1.getPosition().y;
		// body1.allowSleeping(false);//设置body1永不休眠
		// 设置鼠标关节的目标位置
		mjd.maxForce = 100;// 拉力
		//由World来创建鼠标关节
		MouseJoint mj = (MouseJoint) world.createJoint(mjd);
		return mj;
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

	public Body createPolygon(float x, float y, float width, float height, boolean isStatic) {
		PolygonDef pd = new PolygonDef();
		if (isStatic) {
			pd.density = 0;
		} else {
			pd.density = 1;
		}
		pd.friction = 0.8f;
		pd.restitution = 0.3f;
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		BodyDef bd = new BodyDef();
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);
		Body body = world.createBody(bd);
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
				canvas.drawLine(body1.getPosition().x * RATE, body1.getPosition().y * RATE, touchX, touchY, paint);
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
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
