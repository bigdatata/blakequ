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
	// ----���һ����������---->>
	final float RATE = 30;// ��Ļ����ʵ����ı��� 30px��1m;
	World world;// ����һ�������������
	AABB aabb;// ����һ����������ķ�Χ����
	Vec2 gravity;// ����һ��������������
	float timeStep = 1f / 60f;// ��������ģ��ĵ�Ƶ��
	int iterations = 10;// ����ֵ������Խ��ģ��Խ��ȷ��������Խ��
	// ---��¼AABB��body������
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
		// --���һ����������--->>
		aabb = new AABB();// ʵ������������ķ�Χ����
		gravity = new Vec2(0, 10);// ʵ������������������������
		aabb.lowerBound.set(-100, -100);// �����������緶Χ�����Ͻ�����
		aabb.upperBound.set(100, 100);// �����������緶Χ�����½�����
		world = new World(aabb, gravity, true);// ʵ���������������
		// ----��������������Ӷ��Բ��Body

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
	 * @param x ��ȡbody�ķ�Χ���ĵ�x����
	 * @param y ��ȡbody�ķ�Χ���ĵ�y����
	 * @param range ��xyλ��Ϊ���ĵ�ĵķ�Χֵ[x-range,y-range][x+range,y+range]
	 * @param maxCount ������󷵻�Body�ĸ���
	 * @return
	 */
	public Shape[] getBodies(float x, float y, float range, int maxCount) {
		AABB aabbBody = new AABB();
		aabbBody.lowerBound.set((x - range) / RATE, (y - range) / RATE);
		aabbBody.upperBound.set((x + range) / RATE, (y + range) / RATE);
		Shape[] shapes = world.query(aabbBody, maxCount);
		// ������aabb��Χ�е�body��ɸѡ����
		for (int i = 0; i < shapes.length; i++) {
			if (shapes[i].getBody().isStatic()) {
				// ...�ж������Ƿ�Ϊ��̬
			}
			if (shapes[i].getBody().isSleeping()) {
				// ...�ж������Ƿ��������
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
				canvas.drawText("��ָ��AABB��Χ�ཻ��" + bodySize + "��Body��", 20, 50,
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
		// --��ʼģ����������--->>
		world.step(timeStep, iterations);// �����������ģ��
		// ȡ��body�����ͷ
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// ����MyCircle��X��Y����
			MyCircle mc = (MyCircle) body.m_userData;
			mc.setX(body.getPosition().x * RATE - mc.r);
			mc.setY(body.getPosition().y * RATE - mc.r);
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
