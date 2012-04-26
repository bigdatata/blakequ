package com.pulleyj;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.PulleyJoint;
import org.jbox2d.dynamics.joints.PulleyJointDef;

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
	// ----���һ����������---->>
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	int iterations = 10;
	// ------>>�ƶ��ؽ�
	Body body1, body2;
	float body1x = 40, body1y = 200, body1w = 80, body1h = 80, body2x = 120,
			body2y = 200, body2w = 80, body2h = 80;
	float anchor1x=100,anchor1y=45,anchor2x=200 ,anchor2y=45 ;
	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		this.setFocusable(true);
		// --���һ����������--->>
		aabb = new AABB();
		gravity = new Vec2(0, 10);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		// ----�����������������������Body
		body1 = createPolygon(body1x, body1y, body1w, body1h,1);
		body2 = createPolygon(body2x, body2y, body2w, body2h,2);
		// ���û��ֹؽ�
		createPulleyJointDef();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	// ���ֹؽ�(���������� һ������ê����л���)
	public PulleyJoint createPulleyJointDef() {
		//�������ֹؽ�����ʵ��
		PulleyJointDef pjd = new PulleyJointDef();
		Vec2 ga1 = new Vec2(anchor1x/ RATE,anchor1y/ RATE);
		Vec2 ga2 = new Vec2(anchor2x/ RATE,anchor2y/ RATE); 
		//��ʼ�����ֹؽ�����
		//body���������ֵ�ê�㣬����body��ê�㣬����ϵ��
		pjd.initialize(body1, body2, ga1, ga2, body1.getWorldCenter(), body2
				.getWorldCenter(), 1f);
		PulleyJoint pj = (PulleyJoint) world.createJoint(pjd);
		return pj;
	}
 
	public Body createPolygon(float x, float y, float width, float height,
			float density) {
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
				//�����Բ���൱������СԲ��ê����൱������СԲ�����ĵ㣬Ϊ�˱�����ʾ
//				canvas.drawCircle(anchor1x + (anchor2x - anchor1x)/2, anchor1y, 50, paint);
				canvas.drawCircle(anchor1x, anchor1y, 10, paint);
				canvas.drawCircle(anchor2x, anchor2y, 10, paint);
				canvas.drawLine(anchor1x, anchor1y, anchor2x, anchor2y, paint);
				canvas.drawLine(anchor1x, anchor1y, body1.getPosition().x*RATE, body1.getPosition().y*RATE, paint);
				canvas.drawLine(anchor2x, anchor2y, body2.getPosition().x*RATE, body2.getPosition().y*RATE, paint);
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
		paint.setStyle(Style.STROKE);
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
