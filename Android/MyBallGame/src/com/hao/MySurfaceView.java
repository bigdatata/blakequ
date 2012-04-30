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
import com.hao.util.CreateBody;
import com.hao.util.CreateJoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
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
				canvas.drawColor(Color.WHITE);
				Body body = world.getBodyList();
//				for (int i = 1; i < world.getBodyCount(); i++) {
//					((MyRect) body.m_userData).draw(canvas, paint);
//					body = body.m_next;
//				}
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
		world.step(timeStep, iterations);// 物理世界进行模拟
		// 取出body链表表头
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// 设置MyCircle的X，Y坐标以及angle角度
//			MyRect mc = (MyRect) body.m_userData;
//			mc.setX(body.getPosition().x * RATE - mc.w / 2);
//			mc.setY(body.getPosition().y * RATE - mc.h / 2);
//			mc.setAngle((float) (body.getAngle() * 180 / Math.PI));
			// 将链表指针指向下一个body元素
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
				Thread.sleep((long) timeStep * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "thread fail!");
			}
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
			hbPlay = new MyButton((screenW - bmpMenu_play.getWidth())/2, (screenH - bmpMenu_play.getHeight())/2, bmpMenu_play);
			hbHelp = new MyButton(hbPlay.getX(), hbPlay.getY() + 50, bmpMenu_help);
			hbExit = new MyButton(hbPlay.getX(), hbHelp.getY() + 50, bmpMenu_exit);
			
			hbResume = new MyButton((screenW - bmpMenu_resume.getWidth())/2, (screenH - bmpMenu_resume.getHeight())/2 + 50, bmpMenu_resume);
			hbReplay = new MyButton(hbResume.getX(), hbResume.getY() + 50, bmpMenu_replay);
			hbMenu = new MyButton(hbReplay.getX(), hbReplay.getY() + 50, bmpMenu_menu);
			
			hbBack = new MyButton(0, screenH - bmpMenu_help.getHeight(), bmpMenuBack);
			
			//创建Body
			bodyBall = createBody.createCircle(bmpH.getHeight(), bmpH.getHeight(), bmpBall.getWidth()/2, 
					5f, false, new MyCircle(bmpBall, bmpH.getHeight(), bmpH.getHeight(), bmpBall.getWidth()/2));
			bodyLost1 = createBody.createCircle(screenW - bmpH.getWidth() - bmpBody_lost.getWidth(), bmpH.getHeight(), bmpBody_lost.getWidth()/2,
					5f, true, new MyCircle(bmpBody_lost, screenW - bmpH.getWidth()- bmpBody_lost.getWidth(), bmpH.getHeight(), bmpBody_lost.getWidth()/2));
			
		}
	}
}
