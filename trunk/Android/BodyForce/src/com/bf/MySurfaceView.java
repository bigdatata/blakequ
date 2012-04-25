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
	// ----���һ����������---->>
	final float RATE = 30;// ��Ļ����ʵ����ı��� 30px��1m;
	World world;// ����һ�������������
	AABB aabb;// ����һ����������ķ�Χ����
	Vec2 gravity;// ����һ��������������
	float timeStep = 1f / 60f;// ��������ģ��ĵ�Ƶ��
	int iterations = 10;// ����ֵ������Խ��ģ��Խ��ȷ��������Խ��
	// --->>����һ��Body������
	Body body1;
	float currX, currY;//��ǰ��ʵʱXY����

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
		// --���һ����������--->>
		aabb = new AABB();// ʵ������������ķ�Χ����
		gravity = new Vec2(0, 10);// ʵ������������������������
		aabb.lowerBound.set(-100, -100);// �����������緶Χ�����Ͻ�����
		aabb.upperBound.set(100, 100);// �����������緶Χ�����½�����
		world = new World(aabb, gravity, true);// ʵ���������������
		// ----��������������Ӷ����̬Բ��Body
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				// ȡ����һ��bodyʵ��
				body1 = createCircle(70, 350, 10, false);
			} else {
				createCircle(200, 100 + i * 17, 10, false);
			}
		}
		// �����Ļ�·���Ӷ����̬����
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
		//����С��ʹ���λ�ã�ʵʱ�ı����ķ����ĸ���
		//ע������ϵ���жϣ���ͼ
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
		//��ʾʸ��������x���������150��y������-150����λ��ţN
		//ע��yΪ����ʾ���ϣ�Ϊ������
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
		// --��ʼģ����������--->>
		world.step(timeStep, iterations);// �����������ģ��
		// ȡ��body�����ͷ
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// ����MyCircle��X��Y����
			MyCircle mc = (MyCircle) body.m_userData;
			currX = body.getPosition().x * RATE - mc.r;
			currY = body.getPosition().y * RATE - mc.r;
			mc.setX(currX);
			mc.setY(currY);
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
