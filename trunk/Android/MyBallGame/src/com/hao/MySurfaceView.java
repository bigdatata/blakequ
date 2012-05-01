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
 * 一个SurfaceView的动画框架
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
    
    //----添加一个物理世界----
    private final float RATE = 30;
    private World world;
    private AABB aabb;
    private Vec2 gravity;
    private float timeStep = 1f / 60f;
    private int iterations = 10;
	
	//自己封装的Body和Joint工具
    private CreateBody createBody;
	
	//游戏Body对象
    private Body bodyBall;								//游戏小球
    private Body bodyLost1, bodyLost2, bodyWin;			//游戏胜利与失败
	
	//游戏状态
    private final static int GAME_MENU = 1;
    private final static int GAME_RUNNING = 2;
    private final static int GAME_HELP = 3;
    private int gameState = GAME_MENU;
    private boolean isPause, isLost, isWin;
    
    //游戏图片资源
    private Bitmap bmpMenu_help, bmpMenu_play, bmpMenu_exit, bmpMenu_resume, bmpMenu_replay, bmp_menubg, bmp_gamebg, bmpMenuBack, bmp_smallbg, bmpMenu_menu,
				   bmp_helpbg, bmpBody_lost, bmpBody_win, bmpWinbg, bmpLostbg;
	private Bitmap bmpH, bmpS, bmpSh, bmpSs, bmpBall;	//Body图片资源(h,s,sh, ss表示边界图片)
	
    //游戏按钮
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
		//在进入游戏前就应该初始化所有资源
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
	 * 绘制图片
	 */
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.BLACK);
				switch(gameState){
					case GAME_MENU:
						//绘制menu背景和三个按钮
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
						// 当游戏暂停或失败或成功时画一个半透明黑色矩形，突出界面
						if(isLost || isPause || isWin){
							Paint p = new Paint();
							p.setAlpha(0x77);
							canvas.drawRect(0, 0, screenW, screenH, p);
						}
						//游戏暂停
						if(isPause){
							canvas.drawBitmap(bmp_smallbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbResume.draw(canvas, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						//游戏失败
						if(isLost){
							canvas.drawBitmap(bmpLostbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						//游戏胜利
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
	 * 用以逻辑处理
	 */
	private void logic(){
		switch(gameState){
			case GAME_HELP:
			case GAME_MENU:
				break;
			case GAME_RUNNING:
				//只有当游戏正在进行的时候我们才进行数据模拟，否则暂停模拟
				if(!isLost && !isPause && !isWin){
					world.step(timeStep, iterations);// 物理世界进行模拟
					// 取出body链表表头
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
						// 将链表指针指向下一个body元素
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
	 * 主要是处理按钮事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(gameState){
			case GAME_HELP:
				//返回键按下返回menu菜单
				if(hbBack.isPreesed(event)){
					gameState = GAME_MENU;
				}
				break;
			case GAME_MENU:
				//点击开始游戏
				if(hbPlay.isPreesed(event)){
					gameState = GAME_RUNNING;
					isLost = false;
					isWin = false;
					isPause = false;
				}
				//帮助按钮
				else if(hbHelp.isPreesed(event)){
					gameState = GAME_HELP;
				}
				//退出系统
				else if(hbExit.isPreesed(event)){
					MainActivity.main.exit();
				}
				break;
			case GAME_RUNNING:
				//当运行的时候对返回，胜利，失败时的按键处理
				if(isLost || isWin || isPause){
					if(hbResume.isPreesed(event)){
						isPause = false;	
					}
					else if(hbReplay.isPreesed(event)){
						//此时需要重置游戏一些属性
						//因为在重置前小球可能拥有力，所以重置游戏要先使用putToSleep()方法
						//让其Body进入睡眠，并让Body停止模拟，速度置为0
						bodyBall.putToSleep();
						// 然后对小球的坐标进行重置(中心)
						bodyBall.setXForm(new Vec2((bmpH.getHeight() + bmpBall.getWidth() / 2 + 2) / RATE, (bmpH.getHeight() + bmpBall.getWidth() / 2 + 2) / RATE), 0);
						//并且设置默认重力方向为向下
						world.setGravity(new Vec2(0, 10));
						//唤醒body按照新的属性开始模拟
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
	 * 主要是控制小球移动
	 * 以后可以换成传感器
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 当前游戏状态不处于正在游戏中时，屏蔽“返回”实体按键,避免程序进入后台;
		if (keyCode == KeyEvent.KEYCODE_BACK && gameState != GAME_RUNNING) {
			return true;
		}
		switch(gameState){
			case GAME_HELP:
			case GAME_MENU:
				break;
			case GAME_RUNNING:
				// 游戏没有暂停、失败、胜利
				if (!isPause && !isLost && !isWin) {
					//如果方向键左键被按下
					if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
						//设置物理世界的重力方向向左 
						world.setGravity(new Vec2(-10, 2));
					//如果方向键右键被按下
					else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
						//设置物理世界的重力方向向右
						world.setGravity(new Vec2(10, 2));
					//如果方向键上键被按下
					else if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
						//设置物理世界的重力方向向上
						world.setGravity(new Vec2(0, -10));
					//如果方向键下键被按下
					else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
						//设置物理世界的重力方向向下
						world.setGravity(new Vec2(0, 10));
					//如果返回键被按下
					else if (keyCode == KeyEvent.KEYCODE_BACK) {
						//进入游戏暂停界面
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
			//首先判断没有进入其他界面，如果进入，就设置为true，这样logic()中就暂停模拟数据,游戏就处于暂停状态
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
	 * 初始化资源文件
	 */
	private void initResourse(){
		bmpH = BitmapFactory.decodeResource(getResources(), R.drawable.h);
		bmpS = BitmapFactory.decodeResource(getResources(), R.drawable.s);
		bmpSh = BitmapFactory.decodeResource(getResources(), R.drawable.sh);
		bmpSs = BitmapFactory.decodeResource(getResources(), R.drawable.ss);
		bmpBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		// 实例菜单、按钮、游戏背景图片资源
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
	 * 初始化游戏
	 */
	private void initGame(){
		if(gameState == GAME_MENU){
			screenH = getHeight();
			screenW = getWidth();
			
			//初始化按钮
			hbPlay = new MyButton((screenW - bmpMenu_play.getWidth())/2, (screenH - bmpMenu_play.getHeight())/2 - 50, bmpMenu_play);
			hbHelp = new MyButton(hbPlay.getX(), hbPlay.getY() + 50, bmpMenu_help);
			hbExit = new MyButton(hbPlay.getX(), hbHelp.getY() + 50, bmpMenu_exit);
			
			hbResume = new MyButton((screenW - bmpMenu_resume.getWidth())/2, (screenH - bmpMenu_resume.getHeight())/2 - 20, bmpMenu_resume);
			hbReplay = new MyButton(hbResume.getX(), hbResume.getY() + 50, bmpMenu_replay);
			hbMenu = new MyButton(hbReplay.getX(), hbReplay.getY() + 50, bmpMenu_menu);
			
			hbBack = new MyButton((screenW - bmpMenuBack.getWidth())/2, screenH - bmpMenu_help.getHeight(), bmpMenuBack);
			
			//创建Body
			bodyBall = createBody.createCircle(bmpH.getHeight(), bmpH.getHeight(), bmpBall.getWidth()/2, 
					5f, false, new MyCircle(bmpBall, bmpH.getHeight(), bmpH.getHeight(), bmpBall.getWidth()/2));
			bodyLost1 = createBody.createCircle(screenW - bmpH.getHeight() - bmpBody_lost.getWidth(), bmpH.getHeight(), bmpBody_lost.getWidth()/2,
					5f, true, new MyCircle(bmpBody_lost, screenW - bmpH.getHeight()- bmpBody_lost.getWidth(), bmpH.getHeight(), bmpBody_lost.getWidth()/2));
			bodyLost2 = createBody.createCircle(bmpH.getHeight(), screenH - bmpH.getHeight() - bmpBody_lost.getWidth(), bmpBody_lost.getWidth()/2, 
					5f, true, new MyCircle(bmpBody_lost, bmpH.getHeight(), screenH - bmpH.getHeight() - bmpBody_lost.getWidth(), bmpBody_lost.getWidth()/2));
			bodyWin = createBody.createCircle(screenW - bmpH.getHeight() - bmpBody_win.getWidth(), screenH - bmpH.getHeight() - bmpBody_win.getWidth(),
					bmpBody_win.getWidth()/2, 5f, true, new MyCircle(bmpBody_win, screenW - bmpH.getHeight() - bmpBody_win.getWidth(), screenH - bmpH.getHeight() - bmpBody_win.getWidth(), bmpBody_win.getWidth()/2));
			//设置body为传感器，因为传感器碰撞后没有碰撞效果，相当于能感受到碰撞即可
			bodyLost1.getShapeList().m_isSensor = true;
			bodyLost2.getShapeList().m_isSensor = true;
			bodyWin.getShapeList().m_isSensor = true;
			
			//创建边界
			createBody.createRectangle(0, 0, bmpH.getWidth(), bmpH.getHeight(), true, new MyRect(bmpH, 0, 0));	//上
			createBody.createRectangle(0, screenH - bmpH.getHeight(), bmpH.getWidth(), bmpH.getHeight(), true, new MyRect(bmpH, 0, screenH - bmpH.getHeight()));	//下
			createBody.createRectangle(0, 0, bmpS.getWidth(), bmpS.getHeight(), true, new MyRect(bmpS, 0, 0));	//左
			createBody.createRectangle(screenW - bmpS.getWidth(), 0, bmpS.getWidth(), bmpS.getHeight(), true, new MyRect(bmpS, screenW - bmpS.getWidth(), 0));
			
			//创建障碍物
			createBody.createRectangle(0, 90, bmpSh.getWidth(), bmpSh.getHeight(), true, new MyRect(bmpSh, 0, 90));
			createBody.createRectangle(110, 170, bmpSh.getWidth(), bmpSh.getHeight(), true, new MyRect(bmpSh, 110, 170));
			createBody.createRectangle(110, 170, bmpSs.getWidth(), bmpSs.getHeight(), true, new MyRect(bmpSs, 110, 170));
			createBody.createRectangle(getWidth() - 102, screenH - bmpSs.getHeight(), bmpSs.getWidth(), bmpSs.getHeight(), true, new MyRect(bmpSs, getWidth() - 102, screenH - bmpSs.getHeight()));
			
			//绑定监听器
			world.setContactListener(this);
		}
	}
}
