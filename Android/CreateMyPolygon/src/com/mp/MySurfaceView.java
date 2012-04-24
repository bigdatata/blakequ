package com.mp;

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
	// ---�����������----->>
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	final int iterations = 10;
	// ----���������������һ��������--->>
	Body body; // �����������
	float myShapeX = 35;// ����������X����
	float myShapeY = 50;// ����������Y����
	float myShapeW = 60;// ���������ο�
	float myShapeH = 60;// ���������θ�
	// ����������body���õ��ĸ�����(����������Ϊ(0,0))
	float[] Poly_Vertices = { -1, -1, 1, -1, 0, 1 };
	
	// ----���������������һ�������--->>
	Body body2;// �����������
	float polygonX = 0;// ��������X����
	float polygonY = 100;// ��������Y����
	float polygonWidth = 50;// �������ο��
	float polygonHeight = 50;// �������θ߶�

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		setFocusable(true);
		// ---�����������-->>
		aabb = new AABB();
		gravity = new Vec2(0f, 10f);
		aabb.lowerBound.set(-100f, -100f);
		aabb.upperBound.set(100f, 100f);
		world = new World(aabb, gravity, true);
		// ----���������������һ��������--->>
		body = createMyShape(Poly_Vertices, myShapeX, myShapeY, myShapeW,
				myShapeH, false);
		// ----���������������һ�������--->>
		body2 = createPolygon(polygonX, polygonY, polygonWidth, polygonHeight,
				true);// ����һ�������
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	/**
	 * ����һ��������
	 * �����Ƥ��ֻ�����ַ�ʽ��һ�Ƕ���μ�ͨ����Ӷඥ��addVertex�ķ�ʽ�����Ǿ���ֱ��setAsBox
	 * @param vertices
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param isStatic
	 * @return
	 */
	public Body createMyShape(float[] vertices, float x, float y, float w,
			float h, boolean isStatic) {
		// ---����������Ƥ��
		PolygonDef cd = new PolygonDef(); // ʵ��һ�������ε�Ƥ��
		if (isStatic) {
			cd.density = 0; // ����������Ϊ��̬
		} else {
			cd.density = 1; // ���������ε�����
		}
		cd.friction = 0.8f; // ���������ε�Ħ����
		cd.restitution = 0.3f; // ���������εĻָ���
		//Ϊ�����������������
		cd.addVertex(new Vec2(vertices[0], vertices[1])); 
		cd.addVertex(new Vec2(vertices[2], vertices[3]));
		cd.addVertex(new Vec2(vertices[4], vertices[5]));
		// ---��������
		BodyDef bd = new BodyDef(); // ʵ��һ���������
		bd.position.set((x + w / 2) / RATE, (y + h / 2) / RATE); // ���ø��������
		// ---����Body�����壩
		Body body = world.createBody(bd); // �������紴������
		body.createShape(cd); // ΪBody���Ƥ��
		body.setMassFromShapes(); // ���������������
		return body;
	}

	/**
	 * ����һ������
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @return
	 */
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
				//����������(ֱ�ӻ��������Σ��û�����ת�Ϳ��Ի������������)
				canvas.save();
				//��Ϊ��getAngle��һ��float�͵����֣�Ҫת��Ϊ�Ƕ�(PI����180��)--> PI/180 = ����Ƕȵ�����a/x --> x=a*180/PI
				canvas.rotate((float) (body.getAngle() * 180 / Math.PI),
						myShapeX + myShapeW / 2, myShapeY + myShapeH / 2);//��������������ת
				canvas.drawLine(myShapeX, myShapeY, myShapeX + 60, myShapeY,
						paint);
				canvas.drawLine(myShapeX + 60, myShapeY, myShapeX + 30,
						myShapeY + 60, paint);
				canvas.drawLine(myShapeX + 30, myShapeY + 60, myShapeX,
						myShapeY, paint);
				canvas.restore();
				//���ƾ���
				canvas.drawRect(polygonX, polygonY, polygonX + polygonWidth,
						polygonY + polygonHeight, paint);
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		// ----�����������ģ��
		world.step(timeStep, iterations);
		Vec2 position = body.getPosition();
		myShapeX = position.x * RATE - myShapeW / 2;
		myShapeY = position.y * RATE - myShapeH / 2;
		Vec2 position2 = body2.getPosition();
		polygonX = position2.x * RATE - polygonWidth / 2;
		polygonY = position2.y * RATE - polygonHeight / 2;

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
