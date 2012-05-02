package com.hao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.RevoluteJoint;

import com.hao.shape.MyButton;
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
public class MySurfaceView extends SurfaceView  implements Callback, Runnable, ContactListener {

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
	
	//声明一个距离关节
    private DistanceJoint dj;
    private Body body1, bodyTile;
    
  //存储所有下落的body箱子
    private List<Body> tileList;
	
	//自己封装的Body和Joint工具
    private CreateBody createBody;
    private CreateJoint createJoint;
    private Random random;
    private boolean isDown;			//是否下落
    private int downCount = 0;	//下落计时器
    private boolean isDeleteJoint;	//是否删除关节
    
    //游戏图片资源
    private Bitmap bmpMenu_help, bmpMenu_play, bmpMenu_exit, bmpMenu_resume, bmpMenu_replay, bmpMenuBack,  bmpMenu_menu, 
    			   bmp_menubg, bmp_gamebg, bmpWinbg, bmpLostbg, bmp_smallbg;
    private Bitmap bmpB, bmpH;		//边界资源
    private Bitmap bmpTile1, bmpTile2, bmpTile3;
    
    //游戏按钮
    private MyButton hbHelp, hbPlay, hbExit, hbResume, hbReplay, hbBack, hbMenu;
    
    //游戏状态(表示了三种不同的界面)
    private final static int GAMESTATE_MENU = 1;
    private final static int GAMESTATE_HELP = 2;
    private final static int GAMESTATE_RUNNING = 3;
    private int gameState = GAMESTATE_MENU;
    private boolean gameIsPause, gameIsLost, gameIsWin;
    
    
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
		createBody.setFriction(0.8f);
		createBody.setRestitution(0.1f);
		createJoint = new CreateJoint(world, RATE);
		
		tileList = new ArrayList<Body>();
		random = new Random();
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
					case GAMESTATE_HELP:
						canvas.drawBitmap(bmp_menubg, 0, 0, paint);
						canvas.drawText("这是帮助文件", 0, screenH/2, paint);
						hbBack.draw(canvas, paint);
						break;
					case GAMESTATE_MENU:
						canvas.drawBitmap(bmp_menubg, 0,0, paint);
						hbPlay.draw(canvas, paint);
						hbHelp.draw(canvas, paint);
						hbExit.draw(canvas, paint);
						break;
					case GAMESTATE_RUNNING:
						canvas.drawBitmap(bmp_gamebg, 0, -Math.abs(screenH - bmp_gamebg.getHeight()), paint);
						Body body = world.getBodyList();
						for (int i = 1; i < world.getBodyCount(); i++) {
							((MyRect) body.m_userData).draw(canvas, paint);
							body = body.m_next;
						}
						canvas.drawLine(dj.getAnchor1().x * RATE, dj.getAnchor1().y * RATE, dj.getAnchor2().x * RATE, dj.getAnchor2().y * RATE, paint);
						if(gameIsLost || gameIsPause || gameIsWin){
							Paint p = new Paint();
							p.setAlpha(0x77);
							canvas.drawRect(0, 0, screenW, screenH, p);
						}
						if(gameIsLost){
							canvas.drawBitmap(bmpLostbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						if(gameIsPause){
							canvas.drawBitmap(bmp_smallbg, (screenW - bmp_smallbg.getWidth())/2, (screenH - bmp_smallbg.getHeight())/2, paint);
							hbResume.draw(canvas, paint);
							hbReplay.draw(canvas, paint);
							hbMenu.draw(canvas, paint);
						}
						if(gameIsWin){
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
			case GAMESTATE_HELP:
			case GAMESTATE_MENU:
				break;
			case GAMESTATE_RUNNING:
				if(!gameIsLost && !gameIsPause && !gameIsWin){
					world.step(timeStep, iterations);// 物理世界进行模拟
					// 取出body链表表头
					Body body = world.getBodyList();
					for (int i = 1; i < world.getBodyCount(); i++) {
						MyRect mr = (MyRect) body.m_userData;
						mr.setX(body.getPosition().x * RATE - mr.w / 2);
						mr.setY(body.getPosition().y * RATE - mr.h / 2);
						mr.setAngle((float) (body.getAngle() * 180 / Math.PI));
						body = body.m_next;
					}
				}
				//删除关节
				if(isDeleteJoint){
					world.destroyJoint(dj);
					dj = null;
					isDeleteJoint = false;
				}
				if(isDown){
					downCount ++;
					//间隔是2s
					if(downCount % 120 == 0){
						downCount = 0;
						isDown = false;
						int i = random.nextInt(3);
						//随机产生一个箱子
						Bitmap bmp;
						if(i == 0){
							bmp = bmpTile1;
						}else if(i == 1){
							bmp = bmpTile2;
						}else{
							bmp = bmpTile3;
						}
						//创建新的bodyTile和关节
						bodyTile = createBody.createRectangle((screenW - bmp.getWidth())/2 - 15, bmpH.getHeight() + 20, 
								bmp.getWidth(), bmp.getHeight(), false, new MyRect(bmp, (screenW - bmp.getWidth())/2 - 10, bmpH.getHeight() + 20));
						tileList.add(bodyTile);
						//创建关节
						dj = createJoint.createDistanceJoint(body1, bodyTile);
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
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(gameState){
			case GAMESTATE_HELP:
				if(hbBack.isPreesed(event)){
					gameState = GAMESTATE_MENU;
				}
				break;
			case GAMESTATE_MENU:
				if(hbPlay.isPreesed(event)){
					gameIsLost = false;
					gameIsPause = false;
					gameIsWin = false;
					gameState = GAMESTATE_RUNNING;
				}else if(hbHelp.isPreesed(event)){
					gameState = GAMESTATE_HELP;
				}else if(hbExit.isPreesed(event)){
					MyBuildHouseActivity.main.exit();
				}
				break;
			case GAMESTATE_RUNNING:
				if(gameIsLost || gameIsPause || gameIsWin){
					if(hbMenu.isPreesed(event)){
						this.resumeGame();
						gameState = GAMESTATE_MENU;
					}else if(hbReplay.isPreesed(event)){
						this.resumeGame();
						gameIsPause = false;
						gameIsLost = false;
						gameIsWin = false;
					}else if(hbResume.isPreesed(event)){
						gameIsPause = false;
						isDown = false;
					}
				}
				if(!isDown){
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						isDown = true;			//动态下落
						isDeleteJoint = true;   //删除关节
					}
				}
				break;
		}
		return true;
	}
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 当前游戏状态不处于正在游戏中时，屏蔽“返回”实体按键,避免程序进入后台;
		if (keyCode == KeyEvent.KEYCODE_BACK && gameState != GAMESTATE_RUNNING) {
			return true;
		}
		switch(gameState){
			case GAMESTATE_HELP:
			case GAMESTATE_MENU:
				break;
			case GAMESTATE_RUNNING:
				if(!gameIsLost && !gameIsPause && !gameIsWin){
					if(keyCode == KeyEvent.KEYCODE_BACK){
						gameIsPause = true;
					}
				}
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 重置游戏状态
	 */
	private void resumeGame(){
		
	}

	/**
	 * 初始化资源文件
	 */
	private void initResourse(){
		bmpH = BitmapFactory.decodeResource(getResources(), R.drawable.h);
		bmpB = BitmapFactory.decodeResource(getResources(), R.drawable.b);
		bmpTile1 = BitmapFactory.decodeResource(getResources(), R.drawable.tile1);
		bmpTile2 = BitmapFactory.decodeResource(getResources(), R.drawable.tile2);
		bmpTile3 = BitmapFactory.decodeResource(getResources(), R.drawable.tile3);
		// 实例菜单、按钮、游戏背景图片资源
		bmpMenu_help = BitmapFactory.decodeResource(getResources(), R.drawable.menu_help);
		bmpMenu_play = BitmapFactory.decodeResource(getResources(), R.drawable.menu_play);
		bmpMenu_exit = BitmapFactory.decodeResource(getResources(), R.drawable.menu_exit);
		bmpMenu_resume = BitmapFactory.decodeResource(getResources(), R.drawable.menu_resume);
		bmpMenu_replay = BitmapFactory.decodeResource(getResources(), R.drawable.menu_replay);
		bmpMenu_menu = BitmapFactory.decodeResource(getResources(), R.drawable.menu_menu);
		bmpMenuBack = BitmapFactory.decodeResource(getResources(), R.drawable.menu_back);
		bmp_menubg = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
		bmp_gamebg = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		bmp_smallbg = BitmapFactory.decodeResource(getResources(), R.drawable.smallbg);
		bmpWinbg = BitmapFactory.decodeResource(getResources(), R.drawable.gamewin);
		bmpLostbg = BitmapFactory.decodeResource(getResources(), R.drawable.gamelost);
	}
	
	
	/**
	 * 初始化游戏
	 */
	private void initGame(){
		if(gameState == GAMESTATE_MENU){
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
			
			//创建body
			body1 = createBody.createRectangle((screenW - bmpH.getWidth())/2, 0, bmpH.getWidth(), bmpH.getHeight(), true, new MyRect(bmpH, (screenW - bmpH.getWidth())/2, 0));
			bodyTile = createBody.createRectangle((screenW - bmpTile1.getWidth())/2 - 10, bmpH.getHeight() + 20, 
					bmpTile1.getWidth(), bmpTile1.getHeight(), false, new MyRect(bmpTile1, (screenW - bmpTile1.getWidth())/2 - 10, bmpH.getHeight() + 20));
			createBody.createRectangle(0, screenH - bmpB.getHeight(), bmpB.getWidth(), bmpB.getHeight(), true, new MyRect(bmpB, 0, screenH - bmpB.getHeight()));
			tileList.add(bodyTile);
			
			//创建关节
			dj = createJoint.createDistanceJoint(body1, bodyTile);
			
			world.setContactListener(this);
		}
	}

	@Override
	public void add(ContactPoint arg0) {
		// TODO Auto-generated method stub
		
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

}
