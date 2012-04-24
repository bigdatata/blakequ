package com.hao;


import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 一个SurfaceView的动画框架
 * @author Administrator
 *
 */
public class MySurfaceView extends SurfaceView  implements Callback, Runnable {

	private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private boolean flag;  
    public static int screenW, screenH;
    private static final String TAG = "MySurfaceView";
    
    //----------------------添加物理世界-----------------------
    private final float RATE = 30;
    private World world;		//声明一个物理世界对象
    private AABB aabb;			//声明一个物理世界的范围对象
    private Vec2 gravity;		//重力向量对象
    private float timeStep = 1f / 60f;// 物理世界模拟的的频率
    private int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低
    
    //------------------在物理世界中添加一个圆形----------------
    private Body body;			//声明物体对象
    private float x = 0, y = 0;	//圆形x，y坐标
    private float r = 20;		//圆形半径
    
    private Body body2;// 声明物体对象
    private float polygonX2 = 5;// 声明矩形X坐标
    private float polygonY2 = 160;// 声明矩形Y坐标
    private float polygonWidth2 = 50;// 声明矩形宽度
    private float polygonHeight2 = 50;// 声明矩形高度
    
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		aabb = new AABB();
		gravity = new Vec2(0f, 10f);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		
		body = createCircle(x, y, r, false);
		body2 = createPolygon(polygonX2, polygonY2, polygonWidth2, polygonHeight2, true);
	}
	
	/**
	 * 创建一个多边形
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @return
	 */
	public Body createPolygon(float x, float y, float width, float height,
			boolean isStatic) {
		//1.创建皮肤
		PolygonDef pd = new PolygonDef();
		if(isStatic){
			pd.density = 0;
		}else{
			pd.density = 1;		//设置多边形的质量
		}
		pd.friction = 0.8f;		//设置多边形的摩擦力
		pd.restitution = 0.3f;	//设置多边形的恢复力
		pd.setAsBox(width / 2 /RATE, height / 2 / RATE);
		
		//2.创建刚体，骨骼
		BodyDef bd = new BodyDef();
		bd.position.set((x + width/2)/RATE, (y + height/2)/RATE);
		
		//3.创建物体，将皮肤和骨骼组装起来
		Body body = world.createBody(bd);//建立物理世界模型
		body.createShape(pd);
		body.setMassFromShapes();	//将整个物体计算打包
		return body;
	}
	
	
	/**
	 * 创建一个圆形
	 * @param x
	 * @param y
	 * @param r
	 * @param isStatic
	 * @return
	 */
	public Body createCircle(float x, float y, float r, boolean isStatic) {
		CircleDef cd = new CircleDef();
		if(isStatic){
			cd.density = 0;
		}else{
			cd.density = 1;
		}
		cd.friction = 0.8f;
		cd.restitution = 0.3f;
		cd.radius = r/RATE;
		
		BodyDef bd = new BodyDef();
		bd.position.set((x + r)/RATE, (y + r)/RATE);
		
		Body body = world.createBody(bd);
		body.createShape(cd);
		body.setMassFromShapes();
		return body;
	}
	
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = true;
		screenH = getHeight();
		screenW = getWidth();
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}
	
	/**
	 * 绘制图片
	 */
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				paint.setColor(Color.RED);
				RectF rect = new RectF(x, y, x + r * 2,
						y + r * 2);
				canvas.drawArc(rect, 0, 360, true, paint);// 绘画圆形
				canvas.drawRect(polygonX2, polygonY2, polygonX2+polygonWidth2, polygonY2+polygonHeight2, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i(TAG, "can not draw!");
		} finally{
			try {
				if(canvas != null){
					sfh.unlockCanvasAndPost(canvas);
				}
			} catch (Exception e2) {
				// TODO: handle exception
				Log.i(TAG, "unlockCanvasAndPost fail!");
			}
		}
		
	}
	
	/**
	 * 用以逻辑处理
	 */
	private void logic(){
		world.step(timeStep, iterations);
		Vec2 position = body.getPosition();
		x = position.x*RATE - r;
		y = position.y*RATE - r;
		
		position = body2.getPosition();
		polygonX2 = position.x*RATE - polygonWidth2/2;
		polygonY2 = position.y*RATE - polygonHeight2/2;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			long t1 = System.currentTimeMillis();
			logic();
			draw();
			long t2 = System.currentTimeMillis();
			try {
				if((t2-t1 < 100))
					Thread.sleep(100 - (t2 -t1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "thread fail!");
			}
		}
	}
	

}
