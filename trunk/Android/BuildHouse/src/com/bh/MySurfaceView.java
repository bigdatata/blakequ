package com.bh;

import java.util.Random;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	public Context context;
	// ---�����������
	private float RATE = 30;
	private World world;
	private float stepTime = 1f / 60f;
	private int iteraTions = 10;
	// ����ͼ
	private Bitmap bmpBg;
	// ���ש��
	private Bitmap bmp, bmpTile1, bmpTile2, bmpTile3;
	// ���ͼ��
	private Random random;
	private int ran;
	//�������ھ���ؽ��ϵĹ̶�Body��������
	private int bodyWallX;
	private int bodyWallY;
	private int bodyWallW;
	private int bodyWallH;
	//�����ؽڰ󶨵Ĺ̶�Body
	private Body bodyWall;
	//��ʼ����Ϸʱ���ھ���ؽڵ�ש��
	private Body bodyHouse;
	//������ʱBody�����ڼ�¼��һ�ΰ��ھ���ؽ��ϵ�ש��
	private Body bodyTemp;
	// ---��������ɫֵ
	private int[] lineColor = { 0xFFDACDC5, 0xFF825B56, 0xFF3A3E59 };
	// ---���һ������ؽ�
	private DistanceJoint dj;
	// ---���body��joint������
	private boolean isDown;
	private int isDownCount = 0;//�����ʱ��
	private boolean isDeleteJoint;

	public MySurfaceView(Context context) {
		super(context);
		this.context = context;
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ---�����������
		AABB aabb = new AABB();
		aabb.lowerBound = new Vec2(-100, -100);
		aabb.upperBound = new Vec2(100, 100);
		Vec2 gravity = new Vec2(0, 10);
		world = new World(aabb, gravity, true);
		// --ʵ������ͼ
		bmpBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		// ʵ��ש��ͼƬ
		bmpTile1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile1);
		bmpTile2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile2);
		bmpTile3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile3);

	}

	//����view����Ϸ��ʼ��
	public void surfaceCreated(SurfaceHolder holder) {
		bodyWallW = 40;
		bodyWallH = 2;
		bodyWallX = getWidth() / 2 - bodyWallW / 2;
		bodyWallY = 1;
		// ʵ����Ϸ��ʼ�����󶨾���ؽڵ�Body
		bodyWall = createPolygon(bodyWallX, bodyWallY, bodyWallW, bodyWallH, 0, 0);
		bodyHouse = createMyTile(bodyWallX / 2, bodyWallY + bodyWallH + 50, bmpTile1.getWidth(), bmpTile1.getHeight(), 0, 1);
		//ʵ����ʱBody
		bodyTemp = bodyHouse;
		// ���һ����̬���±߽������赲�����body
		createPolygon(0, getHeight(), getWidth() - 10, 10, 0, 0);
		// ---���һ������ؽ�
		dj = createDistanceJoint(bodyWall, bodyHouse);
		th = new Thread(this);
		flag = true;
		th.start();
	}

	//��������ؽ�
	public DistanceJoint createDistanceJoint(Body body1, Body body2) {
		//��������ؽ���Ϣ
		DistanceJointDef dj = new DistanceJointDef();
		dj.body1 = body1;
		dj.body2 = body2;
		//��ʼ������ؽ�
		dj.initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter());
		//ͨ��World����һ������ؽڶ���
		return (DistanceJoint) world.createJoint(dj);
	}

	//����װ��
	public Body createMyTile(float x, float y, float w, float h, float angle, float density) {
		PolygonDef pd = new PolygonDef();
		pd.density = density;
		pd.friction = 0.8f;
		pd.restitution = 0.3f;
		pd.setAsBox(w / 2 / RATE, h / 2 / RATE);
		pd.filter.groupIndex = 2;
		BodyDef bd = new BodyDef();
		bd.position.set((x + w / 2) / RATE, (y + h / 2) / RATE);
		bd.angle = (float) (angle * Math.PI / 180);
		Body body = world.createBody(bd);
		//ʵ�������
		random = new Random();
		//ȡ��0-2���������
		ran = random.nextInt(3);
		if (ran == 0)
			bmp = bmpTile1;
		else if (ran == 1)
			bmp = bmpTile2;
		else if (ran == 2)
			bmp = bmpTile3;
		body.m_userData = new MyTile(x, y, w, h, bmp);
		body.createShape(pd);
		body.setMassFromShapes();
		return body;
	}

	//��������Body
	public Body createPolygon(float x, float y, float w, float h, float angle, float density) {
		PolygonDef pd = new PolygonDef();
		pd.density = density;
		pd.friction = 0.8f;
		pd.restitution = 0.3f;
		pd.setAsBox(w / 2 / RATE, h / 2 / RATE);
		BodyDef bd = new BodyDef();
		bd.position.set((x + w / 2) / RATE, (y + h / 2) / RATE);
		bd.angle = (float) (angle * Math.PI / 180);
		Body body = world.createBody(bd);
		body.m_userData = new MyRect(x, y, w, h);
		body.createShape(pd);
		body.setMassFromShapes();
		return body;
	}

	//���ƺ���
	public void draw() {
		try {
			//��ȡ����ʵ��
			canvas = sfh.lockCanvas();
			//ˢ��
			canvas.drawColor(Color.BLACK);
			//������Ϸ����ͼ
			canvas.drawBitmap(bmpBg, 0, -Math.abs(getHeight() - bmpBg.getHeight()), paint);
			//��������Body 
			Body body = world.getBodyList();
			for (int i = 1; i < world.getBodyCount(); i++) {
				if ((body.m_userData) instanceof MyRect) {
					MyRect rect = (MyRect) (body.m_userData);
					rect.drawRect(canvas, paint);
				} else if ((body.m_userData) instanceof MyTile) {
					MyTile tile = (MyTile) (body.m_userData);
					tile.drawMyTile(canvas, paint);
				}
				body = body.m_next;
			}
			if (bodyWall != null && bodyHouse != null) {
				if (dj != null) {
					//���û�����ɫ
					//lineColor:int ���� ������������ɫֵ���ֱ��ʾ��ͬ��ש����������ɫ
					paint.setColor(lineColor[ran]);
					//���û��ʵĴ�ϸ�̶�
					paint.setStrokeWidth(3);
					//���ƹؽ�
					canvas.drawLine(bodyWall.getPosition().x * RATE, bodyWall.getPosition().y * RATE, bodyTemp.getPosition().x * RATE, bodyTemp.getPosition().y
							* RATE, paint);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {

			}
		}
	}

	//�߼�������
	private void logic() {
		//��������ģ��
		world.step(stepTime, iteraTions);
		///����Body������Body��ͼ��֮��Ĵ�������
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			//�ж�m_userData�е������Ƿ�ΪMyRectʵ��
			if ((body.m_userData) instanceof MyRect) {
				MyRect rect = (MyRect) (body.m_userData);
				rect.setX(body.getPosition().x * RATE - rect.getWidth() / 2);
				rect.setY(body.getPosition().y * RATE - rect.getHeight() / 2);
				rect.setAngle((float) (body.getAngle() * 180 / Math.PI));
			} else if ((body.m_userData) instanceof MyTile) {
				//�ж�m_userData�е������Ƿ�ΪMyTileʵ��
				MyTile tile = (MyTile) (body.m_userData);
				tile.setX(body.getPosition().x * RATE - tile.getWidth() / 2);
				tile.setY(body.getPosition().y * RATE - tile.getHeight() / 2);
				tile.setAngle((float) (body.getAngle() * 180 / Math.PI));
			}
			body = body.m_next;
		}
		if (isDeleteJoint) {
			world.destroyJoint(dj);// ���ٹؽ�
			dj = null;
			isDeleteJoint = false;
		}
		//��̬Body�Ƿ�����
		if (isDown == true) {
			//��ʱ����ʱ
			isDownCount++;
			//��ʱ��ʱ��
			if (isDownCount % 120 == 0) {
				//�����µĶ�̬Body-ש�飬����ʹ����ʱBody�������¶�̬Body
				bodyTemp = createMyTile(bodyWallX / 2, bodyWallY + bodyWallH + 50, bmpTile1.getWidth(), bmpTile1.getHeight(), 0, 1);
				//�����µľ���ؽ�
				dj = createDistanceJoint(bodyWall, bodyTemp);
				//��ʱ������
				isDownCount = 0;
				//�����ʶ����
				isDown = false;
			}
		}
	}

	//�����¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isDown == false) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				//ɾ���ؽ�
				isDeleteJoint = true;
				//��̬Body����
				isDown = true;
			}
		}
		return true;
	}

	//�߳�
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			logic();
			draw();
			try {
				Thread.sleep((long) stepTime * 1000);
			} catch (Exception ex) {
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v("Himi", "surfaceChanged");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		Log.v("Himi", "surfaceDestroyed");
	}

}