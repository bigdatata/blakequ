package com.cp;

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

/**
 * 模拟现实世界中的两个物体
 * 屏幕绘制的图形与Box2D无关！Box2D引擎只负责提供物理世界的模拟数据
 * 注意：是模拟现实数据*****
 * @author Administrator
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	// ---添加物理世界----->>
	final float RATE = 30;// 屏幕到现实世界的比例 30px：1m;
	World world;// 声明一个物理世界对象
	AABB aabb;// 声明一个物理世界的范围对象
	Vec2 gravity;// 声明一个重力向量对象
	float timeStep = 1f / 60f;// 物理世界模拟的的频率
	int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低
	
	// ----在物理世界中添加一个多边形--->>
	Body body;// 声明物体对象
	float polygonX = 5;// 声明矩形X坐标
	float polygonY = 10;// 声明矩形Y坐标
	float polygonWidth = 50;// 声明矩形宽度
	float polygonHeight = 50;// 声明矩形高度
	// ----在物理世界中添加一个多边形--->>
	Body body2;// 声明物体对象
	float polygonX2 = 5;// 声明矩形X坐标
	float polygonY2 = 160;// 声明矩形Y坐标
	float polygonWidth2 = 50;// 声明矩形宽度
	float polygonHeight2 = 50;// 声明矩形高度

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		setFocusable(true);
		// --添加一个物理世界--->>
		aabb = new AABB();// 实例化物理世界的范围对象
		gravity = new Vec2(0, 10);// 实例化物理世界重力向量对象
		aabb.lowerBound.set(-100, -100);// 设置物理世界范围的左上角坐标
		aabb.upperBound.set(100, 100);// 设置物理世界范围的右下角坐标
		world = new World(aabb, gravity, true);// 实例化物理世界对象 

		// ----在物理世界中添加一个多边形--->>
		body = createPolygon(polygonX, polygonY, polygonWidth, polygonHeight,
				false);// 创建一个多边形
		// ----在物理世界中添加一个多边形--->>
		body2 = createPolygon(polygonX2, polygonY2, polygonWidth2,
				polygonHeight2, true);// 创建一个多边形
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	/**
	 * World创建一个物体的步骤则分为以下三步：
	 * 1.首先创建物体皮肤。
	 * 2.然后创建物体刚体。
	 * 3.最后通过皮肤与刚体信息去创建一个物体。
	 * 质量（density）：当物体质量设置为0时，此物体视为"静态物体"；所谓"静态物体"表示不需要运动的物体；
	 * 					比如现实生活中的山、房门等这些没有外力不会发生运动的物体则认为是静态不运动的。
	 * 摩擦力（friction）：取值通常设置在0~1之间，0意味着没有摩擦，1会产生最强摩擦。
	 * 恢复力（restitution）：取值也通常设置在 0 ~ 1 之间，0 表示物体没有恢复力，1表示物体拥有最大恢复力。
	 * 刚体设置坐标的时候，需要传入现实生活中的"米"做为参数单位，所以这里除以比例"RATE"，将像素单位转换为"米"。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @return
	 */
	public Body createPolygon(float x, float y, float width, float height,
			boolean isStatic) {
		// -------------1.创建多边形皮肤--------------------------
		PolygonDef pd = new PolygonDef(); // 实例一个多边形的皮肤
		if (isStatic) {
			pd.density = 0; // 设置多边形为静态
		} else {
			pd.density = 1; // 设置多边形的质量
		}
		pd.friction = 0.8f; // 设置多边形的摩擦力
		pd.restitution = 0.3f; // 设置多边形的恢复力
		// 设置多边形快捷成盒子(矩形)
		/**
		 *  两个参数为多边形宽高的一半,这里除以RATE就是将屏幕中的像素转换为现实中的长度m
		 *  30px/1m = RATE = 屏幕像素高度/实际高度 ---> 实际高度 = 屏幕像素高度/RATE
		 */
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE); //这是是先转换为现实实际高度在设置
		
		// ---------------2.创建刚体--------------------------------
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		/**
		 * 设置Body相对于物理世界的坐标,这里是将现实转换为屏幕适合 30px:1m, 通过RATE转换
		 * 物理世界中创建出的物体默认放置的位置是以物理中心点为锚点，那么为了让其与手机屏幕绘制图形位置重合，
		 * 需要将其物理的X位置加上其宽的一半，其物体的Y位置加上其高的一半，
		 * 这样就相当于将其Body的锚点设置成了左上角,具体图见:现实与屏幕坐标关系.jpg
		 */
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// 设置刚体的坐标(使用实际高宽，非屏幕像素)
		
		// ---------------3.创建Body（物体）--------------------------
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
				canvas.drawRect(polygonX, polygonY, polygonX + polygonWidth,
						polygonY + polygonHeight, paint);// 绘画矩形
				canvas.drawRect(polygonX2, polygonY2,
						polygonX2 + polygonWidth2, polygonY2 + polygonHeight2,
						paint);// 绘画矩形
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		// ----物理世界进行模拟(会再下落的过程中模拟现实世界，如重力，加速度，摩擦力等)
		world.step(timeStep, iterations);
		Vec2 position = body.getPosition();
		//由于取出来的坐标是m为单位（现实世界），故而要乘以RATE转换为像素单位
		//至于为什么要减polygonWidth / 2 见图片
		polygonX = position.x * RATE - polygonWidth / 2;
		polygonY = position.y * RATE - polygonHeight / 2;
		Vec2 position2 = body2.getPosition();
		polygonX2 = position2.x * RATE - polygonWidth2 / 2;
		polygonY2 = position2.y * RATE - polygonHeight2 / 2;
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
