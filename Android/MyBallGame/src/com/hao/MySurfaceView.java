package com.hao;


import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import com.hao.shape.MyButton;
import com.hao.shape.MyCircle;
import com.hao.shape.MyRect;
import com.hao.util.CreateBody;
import com.hao.util.CreateJoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * һ��SurfaceView�Ķ������
 * @author Administrator
 *
 */
public class MySurfaceView extends SurfaceView  implements Callback, Runnable ,ContactListener{

	private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
    private boolean flag;  
    public static int screenW, screenH;
    private static final String TAG = "MySurfaceView";
    
    //----���һ����������----
    private final float RATE = 30;
    private World world;
    private AABB aabb;
    private Vec2 gravity;
    private float timeStep = 1f / 60f;
    private int iterations = 10;
	
	//�Լ���װ��Body��Joint����
    private CreateBody createBody;
	
	//��ϷBody����
    private Body bodyBall;								//��ϷС��
    private Body bodyLost1, bodyLost2, bodyWin;			//��Ϸʤ����ʧ��
	
	//��Ϸ״̬
    private final static int GAME_MENU = 1;
    private final static int GAME_RUNNING = 2;
    private final static int GAME_HELP = 3;
    private int gameState = GAME_MENU;
    private boolean isPause, isLost, isWin;
    
    //��ϷͼƬ��Դ
    private Bitmap bmpMenu_help, bmpMenu_play, bmpMenu_exit, bmpMenu_resume, bmpMenu_replay, bmp_menubg, bmp_gamebg, bmpMenuBack, bmp_smallbg, bmpMenu_menu,
				   bmp_helpbg, bmpBody_lost, bmpBody_win, bmpWinbg, bmpLostbg;
	private Bitmap bmpH, bmpS, bmpSh, bmpSs, bmpBall;	//BodyͼƬ��Դ(h,s,sh, ss��ʾ�߽�ͼƬ)
	
    //��Ϸ��ť
    private MyButton hbHelp, hbPlay, hbExit, hbResume, hbReplay, hbBack, hbMenu;
    
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
		//�ڽ�����Ϸǰ��Ӧ�ó�ʼ��������Դ
		this.initResourse();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		this.initGame();
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
				canvas.drawColor(Color.BLACK);
				switch(gameState){
					case GAME_MENU:
						//����menu������������ť
						canvas.drawBitmap(bmp_menubg, 0, 0, paint);
						hbPlay.draw(canvas, paint);
						hbHelp.draw(canvas, paint);
						hbExit.draw(canvas, paint);
						break;
					case GAME_HELP:
						canvas.drawBitmap(bmp_helpbg, 0, 0, paint);
						hbBack.draw(canvas, paint);
						break;
					case GAME_RUNNING:
						canvas.drawBitmap(bmp_gamebg, 0, 0, paint);
						Body body = world.getBodyList();
						for (int i = 1; i < world.getBodyCount(); i++) {
							Object o = body.m_userData;
							if(o instanceof MyRect){
								((MyRect)o).draw(canvas, paint);
							}else if(o instanceof MyCircle){
								((MyCircle)o).drawArc(canvas, paint);
							}
							body = body.m_next;
						}
						// ����Ϸ��ͣ��ʧ�ܻ�ɹ�ʱ��һ����͸����ɫ���Σ�ͻ������
						if(isLost || isPause || isWin){
							Paint p = new Paint();
							p.setAlpha(0x77);
							canvas.drawRect(0, 0, screenW, screenH, p);
						}
						//��Ϸ��ͣ
						if(isPause){
							canvas.drawBitmap(bmp_smallbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbResume.draw(canvas, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						//��Ϸʧ��
						if(isLost){
							canvas.drawBitmap(bmpLostbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						//��Ϸʤ��
						if(isWin){
							canvas.drawBitmap(bmpWinbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						break;
				}
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
		switch(gameState){
			case GAME_HELP:
			case GAME_MENU:
				break;
			case GAME_RUNNING:
				//ֻ�е���Ϸ���ڽ��е�ʱ�����ǲŽ�������ģ�⣬������ͣģ��
				if(!isLost && !isPause && !isWin){
					world.step(timeStep, iterations);// �����������ģ��
					// ȡ��body�����ͷ
					Body body = world.getBodyList();
					for (int i = 1; i < world.getBodyCount(); i++) {
						Object o = body.m_userData;
						if(o instanceof MyRect){
							MyRect mr = (MyRect) o;
							mr.setX(body.getPosition().x * RATE - mr.w / 2);
							mr.setY(body.getPosition().y * RATE - mr.h / 2);
						}else if(o instanceof MyCircle){
							MyCircle mc = (MyCircle) o;
							mc.setX(body.getPosition().x * RATE - mc.r);
							mc.setY(body.getPosition().y * RATE - mc.r);
							mc.setAngle((float) (body.getAngle() * 180 / Math.PI));
						}
						// ������ָ��ָ����һ��bodyԪ��
						body = body.m_next;
					}
				}
				break;
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
				Thread.sleep((long) timeStep * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "thread fail!");
			}
		}
	}
	
	
	/**
	 * ��Ҫ�Ǵ���ť�¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(gameState){
			case GAME_HELP:
				//���ؼ����·���menu�˵�
				if(hbBack.isPreesed(event)){
					gameState = GAME_MENU;
				}
				break;
			case GAME_MENU:
				//�����ʼ��Ϸ
				if(hbPlay.isPreesed(event)){
					gameState = GAME_RUNNING;
					isLost = false;
					isWin = false;
					isPause = false;
				}
				//������ť
				else if(hbHelp.isPreesed(event)){
					gameState = GAME_HELP;
				}
				//�˳�ϵͳ
				else if(hbExit.isPreesed(event)){
					MainActivity.main.exit();
				}
				break;
			case GAME_RUNNING:
				//�����е�ʱ��Է��أ�ʤ����ʧ��ʱ�İ�������
				if(isLost || isWin || isPause){
					if(hbResume.isPreesed(event)){
						isPause = false;	
					}
					else if(hbReplay.isPreesed(event)){
						//��ʱ��Ҫ������ϷһЩ����
						//��Ϊ������ǰС�����ӵ����������������ϷҪ��ʹ��putToSleep()����
						//����Body����˯�ߣ�����Bodyֹͣģ�⣬�ٶ���Ϊ0
						bodyBall.putToSleep();
						// Ȼ���С��������������(����)
						bodyBall.setXForm(new Vec2((bmpH.getHeight() + bmpBall.getWidth() / 2 + 2) / RATE, (bmpH.getHeight() + bmpBall.getWidth() / 2 + 2) / RATE), 0);
						//��������Ĭ����������Ϊ����
						world.setGravity(new Vec2(0, 10));
						//����body�����µ����Կ�ʼģ��
						bodyBall.wakeUp();
						isLost = false;
						isWin = false;
						isPause = false;
					}
					else if(hbMenu.isPreesed(event)){
						bodyBall.putToSleep();
						bodyBall.setXForm(new Vec2((bmpH.getHeight() + bmpBall.getWidth() / 2 + 2) / RATE, (bmpH.getHeight() + bmpBall.getWidth() / 2 + 2) / RATE), 0);
						world.setGravity(new Vec2(0, 10));
						bodyBall.wakeUp();
						gameState = GAME_MENU;
					}
				}
				break;
		}
		return true;
	}
	
	
	/**
	 * ��Ҫ�ǿ���С���ƶ�
	 * �Ժ���Ի��ɴ�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// ��ǰ��Ϸ״̬������������Ϸ��ʱ�����Ρ����ء�ʵ�尴��,�����������̨;
		if (keyCode == KeyEvent.KEYCODE_BACK && gameState != GAME_RUNNING) {
			return true;
		}
		switch(gameState){
			case GAME_HELP:
			case GAME_MENU:
				break;
			case GAME_RUNNING:
				// ��Ϸû����ͣ��ʧ�ܡ�ʤ��
				if (!isPause && !isLost && !isWin) {
					//�����������������
					if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
						//������������������������� 
						world.setGravity(new Vec2(-10, 2));
					//���������Ҽ�������
					else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
						//�������������������������
						world.setGravity(new Vec2(10, 2));
					//���������ϼ�������
					else if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
						//�������������������������
						world.setGravity(new Vec2(0, -10));
					//���������¼�������
					else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
						//�������������������������
						world.setGravity(new Vec2(0, 10));
					//������ؼ�������
					else if (keyCode == KeyEvent.KEYCODE_BACK) {
						//������Ϸ��ͣ����
						isPause = true;
					}
				}
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void add(ContactPoint point) {
		// TODO Auto-generated method stub
		if(gameState == GAME_RUNNING){
			//�����ж�û�н����������棬������룬������Ϊtrue������logic()�о���ͣģ������,��Ϸ�ʹ�����ͣ״̬
			if(!isLost && !isPause && !isWin){
				if(point.shape1.getBody().equals(bodyBall) && point.shape2.getBody().equals(bodyLost1)){
					isLost = true;
				}else if(point.shape1.getBody().equals(bodyBall) && point.shape2.getBody().equals(bodyLost2)){
					isLost = true;
				}else if(point.shape1.getBody().equals(bodyBall) && point.shape2.getBody().equals(bodyWin)){
					isWin = true;
				}
			}
		}
	}

	@Override
	public void persist(ContactPoint arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(ContactPoint arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void result(ContactResult arg0) {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * ��ʼ����Դ�ļ�
	 */
	private void initResourse(){
		bmpH = BitmapFactory.decodeResource(getResources(), R.drawable.h);
		bmpS = BitmapFactory.decodeResource(getResources(), R.drawable.s);
		bmpSh = BitmapFactory.decodeResource(getResources(), R.drawable.sh);
		bmpSs = BitmapFactory.decodeResource(getResources(), R.drawable.ss);
		bmpBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		// ʵ���˵�����ť����Ϸ����ͼƬ��Դ
		bmpMenu_help = BitmapFactory.decodeResource(getResources(), R.drawable.menu_help);
		bmpMenu_play = BitmapFactory.decodeResource(getResources(), R.drawable.menu_play);
		bmpMenu_exit = BitmapFactory.decodeResource(getResources(), R.drawable.menu_exit);
		bmpMenu_resume = BitmapFactory.decodeResource(getResources(), R.drawable.menu_resume);
		bmpMenu_replay = BitmapFactory.decodeResource(getResources(), R.drawable.menu_replay);
		bmp_menubg = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
		bmp_gamebg = BitmapFactory.decodeResource(getResources(), R.drawable.game_bg);
		bmpMenuBack = BitmapFactory.decodeResource(getResources(), R.drawable.menu_back);
		bmp_smallbg = BitmapFactory.decodeResource(getResources(), R.drawable.smallbg);
		bmpMenu_menu = BitmapFactory.decodeResource(getResources(), R.drawable.menu_menu);
		bmp_helpbg = BitmapFactory.decodeResource(getResources(), R.drawable.helpbg);
		bmpBody_lost = BitmapFactory.decodeResource(getResources(), R.drawable.lostbody);
		bmpBody_win = BitmapFactory.decodeResource(getResources(), R.drawable.winbody);
		bmpWinbg = BitmapFactory.decodeResource(getResources(), R.drawable.gamewin);
		bmpLostbg = BitmapFactory.decodeResource(getResources(), R.drawable.gamelost);
	}
	
	/**
	 * ��ʼ����Ϸ
	 */
	private void initGame(){
		if(gameState == GAME_MENU){
			screenH = getHeight();
			screenW = getWidth();
			
			//��ʼ����ť
			hbPlay = new MyButton((screenW - bmpMenu_play.getWidth())/2, (screenH - bmpMenu_play.getHeight())/2 - 50, bmpMenu_play);
			hbHelp = new MyButton(hbPlay.getX(), hbPlay.getY() + 50, bmpMenu_help);
			hbExit = new MyButton(hbPlay.getX(), hbHelp.getY() + 50, bmpMenu_exit);
			
			hbResume = new MyButton((screenW - bmpMenu_resume.getWidth())/2, (screenH - bmpMenu_resume.getHeight())/2 - 20, bmpMenu_resume);
			hbReplay = new MyButton(hbResume.getX(), hbResume.getY() + 50, bmpMenu_replay);
			hbMenu = new MyButton(hbReplay.getX(), hbReplay.getY() + 50, bmpMenu_menu);
			
			hbBack = new MyButton((screenW - bmpMenuBack.getWidth())/2, screenH - bmpMenu_help.getHeight(), bmpMenuBack);
			
			//����Body
			bodyBall = createBody.createCircle(bmpH.getHeight(), bmpH.getHeight(), bmpBall.getWidth()/2, 
					5f, false, new MyCircle(bmpBall, bmpH.getHeight(), bmpH.getHeight(), bmpBall.getWidth()/2));
			bodyLost1 = createBody.createCircle(screenW - bmpH.getHeight() - bmpBody_lost.getWidth(), bmpH.getHeight(), bmpBody_lost.getWidth()/2,
					5f, true, new MyCircle(bmpBody_lost, screenW - bmpH.getHeight()- bmpBody_lost.getWidth(), bmpH.getHeight(), bmpBody_lost.getWidth()/2));
			bodyLost2 = createBody.createCircle(bmpH.getHeight(), screenH - bmpH.getHeight() - bmpBody_lost.getWidth(), bmpBody_lost.getWidth()/2, 
					5f, true, new MyCircle(bmpBody_lost, bmpH.getHeight(), screenH - bmpH.getHeight() - bmpBody_lost.getWidth(), bmpBody_lost.getWidth()/2));
			bodyWin = createBody.createCircle(screenW - bmpH.getHeight() - bmpBody_win.getWidth(), screenH - bmpH.getHeight() - bmpBody_win.getWidth(),
					bmpBody_win.getWidth()/2, 5f, true, new MyCircle(bmpBody_win, screenW - bmpH.getHeight() - bmpBody_win.getWidth(), screenH - bmpH.getHeight() - bmpBody_win.getWidth(), bmpBody_win.getWidth()/2));
			//����bodyΪ����������Ϊ��������ײ��û����ײЧ�����൱���ܸ��ܵ���ײ����
			bodyLost1.getShapeList().m_isSensor = true;
			bodyLost2.getShapeList().m_isSensor = true;
			bodyWin.getShapeList().m_isSensor = true;
			
			//�����߽�
			createBody.createRectangle(0, 0, bmpH.getWidth(), bmpH.getHeight(), true, new MyRect(bmpH, 0, 0));	//��
			createBody.createRectangle(0, screenH - bmpH.getHeight(), bmpH.getWidth(), bmpH.getHeight(), true, new MyRect(bmpH, 0, screenH - bmpH.getHeight()));	//��
			createBody.createRectangle(0, 0, bmpS.getWidth(), bmpS.getHeight(), true, new MyRect(bmpS, 0, 0));	//��
			createBody.createRectangle(screenW - bmpS.getWidth(), 0, bmpS.getWidth(), bmpS.getHeight(), true, new MyRect(bmpS, screenW - bmpS.getWidth(), 0));
			
			//�����ϰ���
			createBody.createRectangle(0, 90, bmpSh.getWidth(), bmpSh.getHeight(), true, new MyRect(bmpSh, 0, 90));
			createBody.createRectangle(110, 170, bmpSh.getWidth(), bmpSh.getHeight(), true, new MyRect(bmpSh, 110, 170));
			createBody.createRectangle(110, 170, bmpSs.getWidth(), bmpSs.getHeight(), true, new MyRect(bmpSs, 110, 170));
			createBody.createRectangle(getWidth() - 102, screenH - bmpSs.getHeight(), bmpSs.getWidth(), bmpSs.getHeight(), true, new MyRect(bmpSs, getWidth() - 102, screenH - bmpSs.getHeight()));
			
			//�󶨼�����
			world.setContactListener(this);
		}
	}
}
