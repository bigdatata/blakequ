package com.hao;


import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.RevoluteJoint;

import com.hao.util.CreateBody;
import com.hao.util.CreateJoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * һ��SurfaceView�Ķ������
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
    
    //----���һ����������----
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	int iterations = 10;
	
	//����һ������ؽ�
	DistanceJoint dj;
	Body body1, body2;
	float body1x = 16, body1y = 50, body1w = 70, body1h = 10, body2x = 106, body2y = 20, body2w = 40, body2h = 30;
	
	
	//�Լ���װ��Body��Joint����
	CreateBody createBody;
	CreateJoint createJoint;
    
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
		gravity = new Vec2(0, 10);
		aabb.lowerBound.set(-100, -100);
		aabb.upperBound.set(100, 100);
		world = new World(aabb, gravity, true);
		
		createBody = new CreateBody(world, RATE);
		createJoint = new CreateJoint(world, RATE);
		/******************����ؽ�*******************/
		//������������
		body1 = createBody.createRectangle(body1x, body1y, body1w, body1h, false, new MyRect(body1x, body1y, body1w, body1h));
		body2 = createBody.createRectangle(body2x, body2y, body2w, body2h, false, new MyRect(body2x, body2y, body2w, body2h));
		//����һ���̶�����
		createBody.createRectangle(0, 100, 80, 2, true, new MyRect(0, 100, 80, 2));
		//�����ؽ�
		dj = createJoint.createDistanceJoint(body1, body2);
		
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
	 * ����ͼƬ
	 */
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					((MyRect) body.m_userData).draw(canvas, paint);
					body = body.m_next;
				}
				canvas.drawLine(dj.getAnchor1().x * RATE, dj.getAnchor1().y * RATE, dj.getAnchor2().x * RATE, dj.getAnchor2().y * RATE, paint);
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
	 * �����߼�����
	 */
	private void logic(){
		world.step(timeStep, iterations);// �����������ģ��
		// ȡ��body�����ͷ
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// ����MyCircle��X��Y�����Լ�angle�Ƕ�
			MyRect mc = (MyRect) body.m_userData;
			mc.setX(body.getPosition().x * RATE - mc.w / 2);
			mc.setY(body.getPosition().y * RATE - mc.h / 2);
			mc.setAngle((float) (body.getAngle() * 180 / Math.PI));
			// ������ָ��ָ����һ��bodyԪ��
			body = body.m_next;
		}
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
//				if((t2-t1 < 100))
//					Thread.sleep(100 - (t2 -t1));
				Thread.sleep((long) timeStep * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "thread fail!");
			}
		}
	}
	

}
