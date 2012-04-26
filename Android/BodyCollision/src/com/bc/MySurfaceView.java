package com.bc;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.ContactFilter;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable, ContactListener, ContactFilter {
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
	// --->>����һ��Body������
	Body body1, body2;

	/* Shape ��� m_isSensor ���Ա�ʾ������ײ���ǲ�������ײЧ�� */
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
		// ----���������������������̬Բ��Body
		body1 = createCircle(39, 17, 10, false);
		body2 = createCircle(30, 47, 10, false);
		/*ͨ��ָ��maskBits��categoryBits��ָ��˭��˭��ײ��ע��categoryBits��ȡֵ����categoryBitsֵ�Ǳ���Ϊ2�ı�����
		 * ��������16����Ⱥ����6��ֻ�ܸ�2+4��ԣ�8��ֻ�ܸ�8�Լ����
		 * ��body1:categoryBits=0x0001, ��body2:categoryBits=0x0002, �����body3:maskBits=0x0003�ľͺ�body1,body2������ײ
		 * 
		 * 1.����ľ���body1��body2������ײ,����body1�������Ķ���������ײ����Ϊ������maskBits=2�ʶ�ֻ��body2��ײ����
		 * body2��������������ײ
		 * 
		 * 2.���body1��body2��groupIndex��ͬ��Ϊ����������ͬ�飬������������ײ����ʹbody1:maskBits = 2, body2:categoryBits = 2,12���ǲ�����ײ��
		 * 3.���body1��body2��groupIndex��ͬ��Ϊ����������ͬ�飬��������Զ��ײ����Ϊ��������ײֻ��ͬ��������Ч
		 *  �ڲ�ͬ��֮����Ч
		 * */
		body1.getShapeList().getFilterData().groupIndex = 1;// ����body1����1
		body1.getShapeList().getFilterData().maskBits = 2;// ָ��body1��ײ����Ϊ2
//		body1.getShapeList().getFilterData().categoryBits = 1;
		body2.getShapeList().getFilterData().groupIndex = 2;// ����body2����2
		body2.getShapeList().getFilterData().categoryBits = 2;// ����body2����Ϊ2
		// �����Ļ�·���Ӷ����̬����
		for (int i = 0; i < 5; i++) {
			Body body = createCircle(i * 20, 100, 10, true);
			body.getShapeList().getFilterData().groupIndex = 3;
			body.getShapeList().getFilterData().categoryBits = 4;// ���徲̬body����Ϊ4
		}
		//Ϊ��������������ײ����
		world.setContactListener(this);
		// ��ײɸѡ���ȼ��������ӵľ�����չ�ԣ�������ɸѡ����ʵ�ּ������Ĺ��ܣ�
		// �����������ɶ�̫��һ�㲻�Ƽ�ʹ��
		//	world.setContactFilter(this);
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	// ----->>ContactListener������
	@Override
	public void add(ContactPoint arg0) {
		//ÿ������ײ����ô˺�������µĽӴ���
		System.out.println("��ײ���꣺��"+arg0.position.x+"��"+arg0.position.y+"��");
		System.out.println("��ײ���壺"+arg0.shape1.getFilterData().groupIndex+","+arg0.shape2.getFilterData().groupIndex);
		System.out.println("--------add----------");
	}

	@Override
	public void persist(ContactPoint arg0) {
		// ������ײ���ô˺���
//		System.out.println("---------persist---------");
	}

	@Override
	public void remove(ContactPoint arg0) {
		//������ײ���ô˺�
		System.out.println("-------remove-----------");
	}

	@Override
	public void result(ContactResult arg0) {
		// ������ײ�����µĽӴ��㱻������������ô˺���
		// ������ײʱҲ����ô˺���
//		System.out.println("-------result-----------");
	}

	// -----��ײɸѡ��
	@Override
	public boolean shouldCollide(Shape shape1, Shape shape2) {
		return false;
	}
}
