package com.tmb;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	// -----ͼƬ
	Bitmap bmp;

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
		// --ͼƬBody��ͼƬ��Դ
		bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.himi);
		// ----��������������Ӷ���Զ���ͼƬBody
		for (int i = 0; i < 3; i++) {
			createImageBody(bmp, 60 + i * 30, 10 + i * bmp.getHeight(), bmp
					.getWidth(), bmp.getHeight(), false);
		}
		createImageBody(bmp, 90, 200, bmp.getWidth(), bmp.getHeight(), true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createImageBody(Bitmap bmp, float x, float y, float width,
			float height, boolean isStatic) {
		// ---����ͼƬBodyƤ��
		PolygonDef pd = new PolygonDef(); // ʵ��һ��ͼƬBody��Ƥ��
		if (isStatic) {
			pd.density = 0; // ����ͼƬBodyΪ��̬
		} else {
			pd.density = 1; // ����ͼƬBody������
		}
		pd.friction = 0.8f; // ����ͼƬBody��Ħ����
		pd.restitution = 0.3f; // ����ͼƬBody�Ļָ���
		// ����ͼƬBody��ݳɺ���(����)
		// ��������ΪͼƬBody��ߵ�һ��
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---��������
		BodyDef bd = new BodyDef(); // ʵ��һ���������
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// ���ø��������
		// ---����Body�����壩
		Body body = world.createBody(bd); // �������紴������
		// ��body�б����Զ�����*******************************
		body.m_userData = new BitmapBody(bmp, x, y);
		body.createShape(pd); // ΪBody���Ƥ��
		body.setMassFromShapes(); // ���������������
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				// �õ�Body����ı�ͷ
				Body body = world.getBodyList();
				// ͨ��world.getBodyCount()�õ�ѭ������Body�Ĵ���
				for (int i = 1; i < world.getBodyCount(); i++) {
					// ��body�л�ȡ���Զ����BitmapBodyʵ��
					BitmapBody bb = (BitmapBody) body.m_userData;
					// �����Զ���ͼƬ���draw����
					bb.draw(canvas, paint);
					// ����ָ����һ��body
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
		// �õ�Body����ı�ͷ
		Body body = world.getBodyList();
		// ͨ��world.getBodyCount()�õ�ѭ������Body�Ĵ���
		for (int i = 1; i < world.getBodyCount(); i++) {
			// ��body�л�ȡ���Զ����BitmapBodyʵ����Ȼ������������״ֵ̬********8
			BitmapBody bb = (BitmapBody) body.m_userData;
			// �õ���ǰbody�ĽǶ�
			float angele = (float) (body.getAngle() * 180 / Math.PI);
			// �õ���ǰbody���ʵ�X����
			float bodyX = body.getPosition().x * RATE - bb.getW() / 2;
			// �õ���ǰbody���ʵ�Y����
			float bodyY = body.getPosition().y * RATE - bb.getH() / 2; // ����ָ����һ��body
			bb.setAngle(angele);
			bb.setX(bodyX);
			bb.setY(bodyY);
			// ����ָ����һ��body
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
