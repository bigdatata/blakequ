package com.tb;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
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
	//-----���εĿ��
	float rectW=10,rectH=10;
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
		
		// ----��������������Ӷ������Body
		for (int i = 0; i < 5; i++) {
			createPolygon(35, 10 + i * 17, rectW, rectH, false);
		}
		for (int i = 0; i < 2; i++) {
			createPolygon(22 + i * 20, 100, rectW, rectH, true);
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createPolygon(float x, float y, float width, float height,
			boolean isStatic) {
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
		body.createShape(pd); // ΪBody���Ƥ��
		body.setMassFromShapes(); // ���������������
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				/*
				 //�õ�Body����ı�ͷ
				Body body = world.getBodyList();
				//ͨ��world.getBodyCount()�õ�ѭ������Body�Ĵ���
				for (int i = 1; i < world.getBodyCount(); i++) {
					//�õ���ǰbody�ĽǶ�
					float angele = (float) (body.getAngle() * 180 / Math.PI);
					//�õ���ǰbody���ʵ�X����
					float bodyX = body.getPosition().x*RATE-rectW/2;
					//�õ���ǰbody���ʵ�Y����
					float bodyY = body.getPosition().y*RATE-rectH/2;
					//����ָ����һ��body
					body = body.m_next;
				}
				 */
				Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					canvas.save();
					canvas.rotate((float) (body.getAngle() * 180 / Math.PI),
							body.getPosition().x * RATE ,
							body.getPosition().y * RATE);
					canvas.drawRect(
							body.getPosition().x*RATE-rectW/2, 
							body.getPosition().y*RATE-rectH/2, 
							body.getPosition().x*RATE-rectW/2+rectW, 
							body.getPosition().y*RATE-rectH/2+rectH, 
							paint);
					body = body.m_next;
					canvas.restore();
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
