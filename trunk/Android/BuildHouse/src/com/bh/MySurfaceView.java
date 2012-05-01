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
	// ---添加物理世界
	private float RATE = 30;
	private World world;
	private float stepTime = 1f / 60f;
	private int iteraTions = 10;
	// 背景图
	private Bitmap bmpBg;
	// 添加砖块
	private Bitmap bmp, bmpTile1, bmpTile2, bmpTile3;
	// 随机图块
	private Random random;
	private int ran;
	//声明绑定在距离关节上的固定Body坐标与宽高
	private int bodyWallX;
	private int bodyWallY;
	private int bodyWallW;
	private int bodyWallH;
	//与距离关节绑定的固定Body
	private Body bodyWall;
	//初始化游戏时绑定在距离关节的砖块
	private Body bodyHouse;
	//创建临时Body，用于记录上一次绑定在距离关节上的砖块
	private Body bodyTemp;
	// ---线条的颜色值
	private int[] lineColor = { 0xFFDACDC5, 0xFF825B56, 0xFF3A3E59 };
	// ---添加一个距离关节
	private DistanceJoint dj;
	// ---添加body和joint的容器
	private boolean isDown;
	private int isDownCount = 0;//下落计时器
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
		// ---添加物理世界
		AABB aabb = new AABB();
		aabb.lowerBound = new Vec2(-100, -100);
		aabb.upperBound = new Vec2(100, 100);
		Vec2 gravity = new Vec2(0, 10);
		world = new World(aabb, gravity, true);
		// --实例背景图
		bmpBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		// 实例砖块图片
		bmpTile1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile1);
		bmpTile2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile2);
		bmpTile3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile3);

	}

	//创建view与游戏初始化
	public void surfaceCreated(SurfaceHolder holder) {
		bodyWallW = 40;
		bodyWallH = 2;
		bodyWallX = getWidth() / 2 - bodyWallW / 2;
		bodyWallY = 1;
		// 实例游戏初始两个绑定距离关节的Body
		bodyWall = createPolygon(bodyWallX, bodyWallY, bodyWallW, bodyWallH, 0, 0);
		bodyHouse = createMyTile(bodyWallX / 2, bodyWallY + bodyWallH + 50, bmpTile1.getWidth(), bmpTile1.getHeight(), 0, 1);
		//实例临时Body
		bodyTemp = bodyHouse;
		// 添加一个静态的下边界用于阻挡掉落的body
		createPolygon(0, getHeight(), getWidth() - 10, 10, 0, 0);
		// ---添加一个距离关节
		dj = createDistanceJoint(bodyWall, bodyHouse);
		th = new Thread(this);
		flag = true;
		th.start();
	}

	//创建距离关节
	public DistanceJoint createDistanceJoint(Body body1, Body body2) {
		//创建距离关节信息
		DistanceJointDef dj = new DistanceJointDef();
		dj.body1 = body1;
		dj.body2 = body2;
		//初始化距离关节
		dj.initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter());
		//通过World创建一个距离关节对象
		return (DistanceJoint) world.createJoint(dj);
	}

	//创建装块
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
		//实例随机库
		random = new Random();
		//取出0-2的随机整数
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

	//创建矩形Body
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

	//绘制函数
	public void draw() {
		try {
			//获取画布实例
			canvas = sfh.lockCanvas();
			//刷屏
			canvas.drawColor(Color.BLACK);
			//绘制游戏背景图
			canvas.drawBitmap(bmpBg, 0, -Math.abs(getHeight() - bmpBg.getHeight()), paint);
			//遍历绘制Body 
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
					//设置画笔颜色
					//lineColor:int 数组 ，保存三种颜色值；分别表示不同方砖的悬挂绳颜色
					paint.setColor(lineColor[ran]);
					//设置画笔的粗细程度
					paint.setStrokeWidth(3);
					//绘制关节
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

	//逻辑处理函数
	private void logic() {
		//物理世界模拟
		world.step(stepTime, iteraTions);
		///遍历Body，进行Body与图形之间的传递数据
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			//判定m_userData中的数据是否为MyRect实例
			if ((body.m_userData) instanceof MyRect) {
				MyRect rect = (MyRect) (body.m_userData);
				rect.setX(body.getPosition().x * RATE - rect.getWidth() / 2);
				rect.setY(body.getPosition().y * RATE - rect.getHeight() / 2);
				rect.setAngle((float) (body.getAngle() * 180 / Math.PI));
			} else if ((body.m_userData) instanceof MyTile) {
				//判定m_userData中的数据是否为MyTile实例
				MyTile tile = (MyTile) (body.m_userData);
				tile.setX(body.getPosition().x * RATE - tile.getWidth() / 2);
				tile.setY(body.getPosition().y * RATE - tile.getHeight() / 2);
				tile.setAngle((float) (body.getAngle() * 180 / Math.PI));
			}
			body = body.m_next;
		}
		if (isDeleteJoint) {
			world.destroyJoint(dj);// 销毁关节
			dj = null;
			isDeleteJoint = false;
		}
		//动态Body是否下落
		if (isDown == true) {
			//计时器计时
			isDownCount++;
			//计时器时间
			if (isDownCount % 120 == 0) {
				//创建新的动态Body-砖块，并且使用临时Body保存最新动态Body
				bodyTemp = createMyTile(bodyWallX / 2, bodyWallY + bodyWallH + 50, bmpTile1.getWidth(), bmpTile1.getHeight(), 0, 1);
				//创建新的距离关节
				dj = createDistanceJoint(bodyWall, bodyTemp);
				//计时器重置
				isDownCount = 0;
				//下落标识重置
				isDown = false;
			}
		}
	}

	//触屏事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isDown == false) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				//删除关节
				isDeleteJoint = true;
				//动态Body下落
				isDown = true;
			}
		}
		return true;
	}

	//线程
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