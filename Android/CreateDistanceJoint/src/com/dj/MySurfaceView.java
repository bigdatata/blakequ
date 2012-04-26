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
	// ----���һ����������---->>
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	int iterations = 10;
	// ------>>����ؽ�
	//����һ������ؽ�
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
		// --���һ����������--->>
		aabb = new AABB();
		gravity = new Vec2(0, 10);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		// ----�����������������������Body
		body1 = createPolygon(body1x, body1y, body1w, body1h, false);
		body2 = createPolygon(body2x, body2y, body2w, body2h, false);
		createPolygon(0, 100, 80, 2, true);
		// ���þ���ؽ�
		dj = createDistanceJoint();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	// ����ؽ�
	public DistanceJoint createDistanceJoint() {
		//����һ������ؽ�����ʵ��
		DistanceJointDef dje = new DistanceJointDef();
		// ��ʼ���ؽ�����
		dje.initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter());
		// dje.collideConnected=true;
		//��������ͨ������ľ���ؽ����ݴ���һ���ؽ�
		DistanceJoint dj = (DistanceJoint) world.createJoint(dje);
		return dj;
	}

	public Body createPolygon(float x, float y, float width, float height, boolean isStatic) {
		// ---���������Ƥ��
		PolygonDef pd = new PolygonDef(); // ʵ��һ������ε�Ƥ��
		if (isStatic) {
			pd.density = 0; // ���ö����Ϊ��̬
		} else {
			pd.density = 1; // ���ö���ε�����
		}
		pd.friction = 0.8f; // ���ö���ε�Ħ����
		pd.restitution = 0.3f; // ���ö���εĻָ���
		// ���ö���ο�ݳɺ���(����)
		// ��������Ϊ����ο�ߵ�һ��
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---��������
		BodyDef bd = new BodyDef(); // ʵ��һ���������
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// ���ø��������
		// ---����Body�����壩
		Body body = world.createBody(bd); // �������紴������
		body.m_userData = new MyRect(x, y, width, height);
		body.createShape(pd); // ΪBody���Ƥ��
		body.setMassFromShapes(); // ���������������
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
		// --��ʼģ����������--->>
		world.step(timeStep, iterations);// �����������ģ��
		// ȡ��body�����ͷ
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// ����MyCircle��X��Y�����Լ�angle�Ƕ�
			MyRect mc = (MyRect) body.m_userData;
			mc.setX(body.getPosition().x * RATE - mc.w / 2);
			mc.setY(body.getPosition().y * RATE - mc.h / 2);
			mc.setAngle((float) (body.getAngle() * 180 / Math.PI));
			// ������ָ��ָ����һ��bodyԪ��
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
