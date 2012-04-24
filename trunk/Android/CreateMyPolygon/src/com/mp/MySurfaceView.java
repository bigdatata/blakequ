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
	// ---添加物理世界----->>
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	final int iterations = 10;
	// ----在物理世界中添加一个三角形--->>
	Body body; // 声明物体对象
	float myShapeX = 35;// 声明三角形X坐标
	float myShapeY = 50;// 声明三角形Y坐标
	float myShapeW = 60;// 声明三角形宽
	float myShapeH = 60;// 声明三角形高
	// 创建三角形body所用的四个顶点(三角形中心为(0,0))
	float[] Poly_Vertices = { -1, -1, 1, -1, 0, 1 };
	
	// ----在物理世界中添加一个多边形--->>
	Body body2;// 声明物体对象
	float polygonX = 0;// 声明矩形X坐标
	float polygonY = 100;// 声明矩形Y坐标
	float polygonWidth = 50;// 声明矩形宽度
	float polygonHeight = 50;// 声明矩形高度

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		setFocusable(true);
		// ---添加物理世界-->>
		aabb = new AABB();
		gravity = new Vec2(0f, 10f);
		aabb.lowerBound.set(-100f, -100f);
		aabb.upperBound.set(100f, 100f);
		world = new World(aabb, gravity, true);
		// ----在物理世界中添加一个三角形--->>
		body = createMyShape(Poly_Vertices, myShapeX, myShapeY, myShapeW,
				myShapeH, false);
		// ----在物理世界中添加一个多边形--->>
		body2 = createPolygon(polygonX, polygonY, polygonWidth, polygonHeight,
				true);// 创建一个多边形
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	/**
	 * 创建一个三角形
	 * 多边形皮肤只有两种方式：一是多边形即通过添加多顶点addVertex的方式，二是矩形直接setAsBox
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
		// ---创建三角形皮肤
		PolygonDef cd = new PolygonDef(); // 实例一个三角形的皮肤
		if (isStatic) {
			cd.density = 0; // 设置三角形为静态
		} else {
			cd.density = 1; // 设置三角形的质量
		}
		cd.friction = 0.8f; // 设置三角形的摩擦力
		cd.restitution = 0.3f; // 设置三角形的恢复力
		//为三角形添加三个顶点
		cd.addVertex(new Vec2(vertices[0], vertices[1])); 
		cd.addVertex(new Vec2(vertices[2], vertices[3]));
		cd.addVertex(new Vec2(vertices[4], vertices[5]));
		// ---创建刚体
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		bd.position.set((x + w / 2) / RATE, (y + h / 2) / RATE); // 设置刚体的坐标
		// ---创建Body（物体）
		Body body = world.createBody(bd); // 物理世界创建物体
		body.createShape(cd); // 为Body添加皮肤
		body.setMassFromShapes(); // 将整个物体计算打包
		return body;
	}

	/**
	 * 创建一个矩形
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @return
	 */
	public Body createPolygon(float x, float y, float width, float height,
			boolean isStatic) {
		// ---创建多边形皮肤
		PolygonDef pd = new PolygonDef(); // 实例一个多边形的皮肤
		if (isStatic) {
			pd.density = 0; // 设置多边形为静态
		} else {
			pd.density = 1; // 设置多边形的质量
		}
		pd.friction = 0.8f; // 设置多边形的摩擦力
		pd.restitution = 0.3f; // 设置多边形的恢复力
		// 设置多边形快捷成盒子(矩形)
		// 两个参数为多边形宽高的一半
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---创建刚体
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// 设置刚体的坐标
		// ---创建Body（物体）
		Body body = world.createBody(bd); // 物理世界创建物体
		body.createShape(pd); // 为Body添加皮肤
		body.setMassFromShapes(); // 将整个物体计算打包
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				//绘制三角形(直接绘制三角形，让画布旋转就可以绘出动的三角形)
				canvas.save();
				//因为在getAngle是一个float型的数字，要转换为角度(PI代表180度)--> PI/180 = 代表角度的数字a/x --> x=a*180/PI
				canvas.rotate((float) (body.getAngle() * 180 / Math.PI),
						myShapeX + myShapeW / 2, myShapeY + myShapeH / 2);//以三角形中心旋转
				canvas.drawLine(myShapeX, myShapeY, myShapeX + 60, myShapeY,
						paint);
				canvas.drawLine(myShapeX + 60, myShapeY, myShapeX + 30,
						myShapeY + 60, paint);
				canvas.drawLine(myShapeX + 30, myShapeY + 60, myShapeX,
						myShapeY, paint);
				canvas.restore();
				//绘制矩形
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
		// ----物理世界进行模拟
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
