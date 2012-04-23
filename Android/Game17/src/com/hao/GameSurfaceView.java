package com.hao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hao.item.Enemy;
import com.hao.item.GameBackground;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 游戏主界面
 * @author Administrator
 *
 */
public class GameSurfaceView extends SurfaceView implements Callback, Runnable {
	/**********************通用框架************************************/
	public static int screenWidth, screenHeight;	//屏幕高宽
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	
	/**********************定义游戏状态常量*****************************/
	public static final int GAME_MENU = 0;			//游戏菜单
	public static final int GAMEING = 1;			//游戏中
	public static final int GAME_WIN = 2;			//游戏胜利
	public static final int GAME_LOST = 3;			//游戏失败
	public static final int GAME_PAUSE = -1;		//游戏菜单
	public static int gameState = GAME_MENU;		//当前游戏状态(默认初始在游戏菜单界面)
	
	/**********************定义游戏资源文件*****************************/
	private Resources res = this.getResources();
	private Bitmap bmpBackGround;					//游戏背景
	private Bitmap bmpBoom;							//爆炸效果
	private Bitmap bmpBoosBoom;						//Boos爆炸效果
	private Bitmap bmpButton;						//游戏开始按钮
	private Bitmap bmpButtonPress;					//游戏开始按钮被点击
	private Bitmap bmpEnemyDuck;					//怪物鸭子
	private Bitmap bmpEnemyFly;						//怪物苍蝇
	private Bitmap bmpEnemyBoos;					//怪物猪头Boos
	private Bitmap bmpGameWin;						//游戏胜利背景
	private Bitmap bmpGameLost;						//游戏失败背景
	private Bitmap bmpPlayer;						//游戏主角飞机
	private Bitmap bmpPlayerHp;						//主角飞机血量
	private Bitmap bmpMenu;							//菜单背景
	public static Bitmap bmpBullet;					//子弹
	public static Bitmap bmpEnemyBullet;			//敌机子弹
	public static Bitmap bmpBossBullet;				//Boss子弹
	
	/**********************定义游戏所有对象*****************************/
	private GameMenu gameMenu;						//声明一个菜单对象
	private GameBackground gameBg;					//游戏背景
	private List<Enemy> enemys;						//敌机容器
	private static int CREATE_ENEMY_TIME = 50;		//每次生成敌机的时间(毫秒)
	private int count = 0;							//敌机计数器
	/**敌人数组：1和2表示敌机的种类，-1表示Boss，二维数组的每一维都是一组怪物**/
	private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 }, { 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 2, 1 },
			{ 1, 3 }, { 2, 1 }, { -1 } };
	private int enemyArrayIndex = 0;				//当前取出一维数组的下标
	private boolean isBoss;							//是否出现Boss标识位
	private Random random;							//随机库，为创建的敌机赋予随即坐标
	
	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);				//必须设置这个，不然keyDown,keyUp不能响应
		this.setKeepScreenOn(true);					//设置背景长亮
		random = new Random();
	}
	
	/**
	 * 初始化游戏，载入资源文件
	 * 防止游戏切入后台重新进入游戏时，游戏被重置!当游戏状态处于菜单时，才会重置游戏
	 */
	private void initGame(){
		if(gameState == GAME_MENU){
			//加载游戏资源
			bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.background);
			bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
			bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
			bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
			bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
			bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck);
			bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
			bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.enemy_pig);
			bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
			bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelost);
			bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
			bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
			bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
			bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
			bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
			bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);
			
			gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress);
			gameBg = new GameBackground(bmpBackGround);
			enemys = new ArrayList<Enemy>();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screenWidth = getWidth();
		screenHeight = getHeight();
		initGame();
		flag = true;
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//处理返回键
		if(keyCode == KeyEvent.KEYCODE_BACK){		//游戏胜利、失败、进行时都默认返回菜单
			if(gameState == GAME_LOST || gameState == GAME_PAUSE
					||gameState == GAME_WIN || gameState == GAMEING){
				gameState = GAME_MENU;
				initGame();									//重置游戏
				isBoss = false;								//初始化boss为false
				enemyArrayIndex = 0;						//初始化敌机数组
			}else if(gameState == GAME_MENU){
				PlaneGame.instance.finish();
				System.exit(0);								//当前游戏状态在菜单界面，默认返回按键退出游戏
			}
			return true;									//表示此按键已处理，不再交给系统处理，从而避免游戏被切入后台
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {				//游戏胜利、失败、进行时都默认返回菜单
			if (gameState == GAMEING || gameState == GAME_PAUSE
					|| gameState == GAME_WIN || gameState == GAME_LOST) {
				gameState = GAME_MENU;
			}
			return true;									//表示此按键已处理，不再交给系统处理，从而避免游戏被切入后台
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//触屏监听事件函数根据游戏状态不同进行不同监听
		switch(gameState){
			case GAME_MENU:
				gameMenu.onTouchEvent(event);
				break;
			case GAME_PAUSE:
				break;
			case GAME_LOST:
				break;
			case GAME_WIN:
				break;
			case GAMEING:
				break;
		}
		return true;
	}

	/**
	 * 逻辑处理函数,不同状态不同的处理逻辑
	 */
	private void logic(){
		switch(gameState){
			case GAME_MENU:
				break;
			case GAME_PAUSE:
				break;
			case GAME_LOST:
				break;
			case GAME_WIN:
				break;
			case GAMEING:
				gameBg.logic();							//游戏背景逻辑
				/***************************普通敌机逻辑*********************************/
				if(!isBoss){
					for(int i=0; i<enemys.size(); i++){
						Enemy enemy = enemys.get(i);
						if(enemy.isDead()){				//如果敌机已经死亡，则移出数组
							enemys.remove(i);
						}else{
							enemy.logic();
						}
					}
					//----------------------添加敌机-------------------------------------
					count ++ ;
					if(count % CREATE_ENEMY_TIME == 0){	//利用count来计数，实现每隔50ms生成一架敌机（**为了不使用线程睡眠）
						for(int i=0; i<enemyArray[enemyArrayIndex].length; i++){
							switch(enemyArray[enemyArrayIndex][i]){
								case Enemy.TYPE_FLY:								//苍蝇
									System.out.println("fly----------size:"+enemys.size());
									int x = random.nextInt(screenWidth - 100) + 50; //随机生成初始x坐标
									enemys.add(new Enemy(Enemy.TYPE_FLY, bmpEnemyFly, x, -50));
									break;
								case Enemy.TYPE_DUCKL:								//左鸭子
									System.out.println("duck----------size:"+enemys.size());
									int y = random.nextInt(20);						//随机生成初始y坐标
									enemys.add(new Enemy(Enemy.TYPE_DUCKL, bmpEnemyDuck, -10, y));
									break;
								case Enemy.TYPE_DUCKR:								//右鸭子(向左运动，故而初始x坐标在屏幕右)
									int y1 = random.nextInt(20);
									enemys.add(new Enemy(Enemy.TYPE_DUCKR, bmpEnemyDuck, screenWidth + 50, y1));
									break;
							}
						}
						if(enemyArrayIndex == enemyArray.length - 1){				//这里判断下一组是否为最后一组(Boss)
							isBoss = true;
						}else{
							enemyArrayIndex ++;
						}
					}
				}
				/***************************BOSS敌机逻辑*********************************/
				else{									//boss逻辑
					
				}
				break;
	}
	}
	
	/**
	 * 绘制函数
	 */
	private void draw(){
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE);
			if(canvas != null){
				switch(gameState){
					case GAME_MENU:
						gameMenu.draw(canvas, paint);	//游戏菜单
						break;
					case GAME_PAUSE:
						break;
					case GAME_LOST:
						canvas.drawBitmap(bmpGameLost, 0, 0, paint);
						break;
					case GAME_WIN:
						canvas.drawBitmap(bmpGameWin, 0, 0, paint);
						break;
					case GAMEING:
						gameBg.draw(canvas, paint);		//游戏背景
						if(!isBoss){							//如果是普通的敌机，遍历绘制
							for(int i=0; i<enemys.size(); i++){
								enemys.get(i).draw(canvas, paint);
							}
						}else{									//绘制boss
							
						}
						break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i(PlaneGame.TAG, "can not draw!");
		} finally{
			try {
				if(canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {
				// TODO: handle exception
				Log.i(PlaneGame.TAG, "unlockCanvasAndPost fail!");
			}
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
				//这样保证了最多100ms刷新一次
				if(t2 - t1 < 100){
					Thread.sleep(100 - (t2-t1));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
