package com.hao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.hao.base.BaseSurfaceView;
import com.hao.item.Boom;
import com.hao.item.Boss;
import com.hao.item.Bullet;
import com.hao.item.Enemy;
import com.hao.item.GameBackground;
import com.hao.item.Player;

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
 * ��Ϸ������
 * @author Administrator
 *
 */
public class GameSurfaceView extends BaseSurfaceView implements Callback, Runnable {
	/**********************ͨ�ÿ��************************************/
	public static int screenWidth, screenHeight;	//��Ļ�߿�
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	
	/**********************������Ϸ״̬����*****************************/
	public static final int GAME_MENU = 0;			//��Ϸ�˵�
	public static final int GAMEING = 1;			//��Ϸ��
	public static final int GAME_WIN = 2;			//��Ϸʤ��
	public static final int GAME_LOST = 3;			//��Ϸʧ��
	public static final int GAME_PAUSE = -1;		//��Ϸ�˵�
	public static int gameState = GAME_MENU;		//��ǰ��Ϸ״̬(Ĭ�ϳ�ʼ����Ϸ�˵�����)
	
	/**********************������Ϸ��Դ�ļ�*****************************/
	private Resources res = this.getResources();
	private Bitmap bmpBackGround;					//��Ϸ����
	private Bitmap bmpBoom;							//��ըЧ��
	private Bitmap bmpBoosBoom;						//Boos��ըЧ��
	private Bitmap bmpButton;						//��Ϸ��ʼ��ť
	private Bitmap bmpButtonPress;					//��Ϸ��ʼ��ť�����
	private Bitmap bmpEnemyDuck;					//����Ѽ��
	private Bitmap bmpEnemyFly;						//�����Ӭ
	private Bitmap bmpEnemyBoos;					//������ͷBoos
	private Bitmap bmpGameWin;						//��Ϸʤ������
	private Bitmap bmpGameLost;						//��Ϸʧ�ܱ���
	private Bitmap bmpPlayer;						//��Ϸ���Ƿɻ�
	private Bitmap bmpPlayerHp;						//���Ƿɻ�Ѫ��
	private Bitmap bmpMenu;							//�˵�����
	public static Bitmap bmpBullet;					//�ӵ�
	public static Bitmap bmpEnemyBullet;			//�л��ӵ�
	public static Bitmap bmpBossBullet;				//Boss�ӵ�
	
	/**********************������Ϸ���ж���*****************************/
	private GameMenu gameMenu;						//����һ���˵�����
	private GameBackground gameBg;					//��Ϸ����
	private List<Enemy> enemys;						//�л�����
	private static int CREATE_ENEMY_TIME = 50;		//ÿ�����ɵл���ʱ��(����)
	private int count = 0;							//�л�������
	/**�������飺1��2��ʾ�л������࣬-1��ʾBoss����ά�����ÿһά����һ�����**/
	private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 }, { 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 2, 1 },
			{ 1, 3 }, { 2, 1 }, { -1 } };
	private int enemyArrayIndex = 0;				//��ǰȡ��һά������±�
	private boolean isBoss;							//�Ƿ����Boss��ʶλ
	private Random random;							//����⣬Ϊ�����ĵл������漴����
	private Player player;							//��Ϸ����
	private List<Boom> booms;						//��ըЧ��
	
	/**********************�ӵ����*************************************/
	private List<Bullet> bulletPlayers;				//�����ӵ�����
	private int countPlayerBullet;					//�����ӵ�����
	private static int CREATE_PLAYER_BULLET = 20;	//���������ӵ�ʱ�䣨ms��
	private List<Bullet> bulletEnemys;				//�л��ӵ�����
	private int countEnemyBullet;					//�л��ӵ�����
	private static int CREATE_ENEMY_BULLET = 40;	//�л������ӵ�ʱ�䣨ms��
	private Boss boss;								//����Boss
	public static List<Bullet> bulletBoss;		//Boss���ӵ�����
	private static int CREATE_BOSS_BULLET = 10;		//Boss�����ӵ�ʱ�䣨ms��
	
	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);				//���������������ȻkeyDown,keyUp������Ӧ
		this.setKeepScreenOn(true);					//���ñ�������
		random = new Random();
	}
	
	/**
	 * ��ʼ����Ϸ��������Դ�ļ�
	 * ��ֹ��Ϸ�����̨���½�����Ϸʱ����Ϸ������!����Ϸ״̬���ڲ˵�ʱ���Ż�������Ϸ
	 */
	private void initGame(){
		if(gameState == GAME_MENU){
			//������Ϸ��Դ
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
			player = new Player(bmpPlayerHp, bmpPlayer);
			booms = new ArrayList<Boom>();
			bulletEnemys = new ArrayList<Bullet>();
			bulletPlayers = new ArrayList<Bullet>();
			bulletBoss = new ArrayList<Bullet>();
			boss = new Boss(bmpEnemyBoos);
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
		//�����ؼ�
		if(keyCode == KeyEvent.KEYCODE_BACK){		//��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if(gameState == GAME_LOST || gameState == GAME_PAUSE
					||gameState == GAME_WIN || gameState == GAMEING){
				gameState = GAME_MENU;
				initGame();									//������Ϸ
				isBoss = false;								//��ʼ��bossΪfalse
				enemyArrayIndex = 0;						//��ʼ���л�����
			}else if(gameState == GAME_MENU){
				PlaneGame.instance.finish();
				System.exit(0);								//��ǰ��Ϸ״̬�ڲ˵����棬Ĭ�Ϸ��ذ����˳���Ϸ
			}
			return true;									//��ʾ�˰����Ѵ������ٽ���ϵͳ�����Ӷ�������Ϸ�������̨
		}
		
		switch (gameState) {
			case GAME_MENU:
				break;
			case GAMEING:
				player.onKeyDown(keyCode, event);			//���ǵİ��������¼�
				break;
			case GAME_PAUSE:
				break;
			case GAME_WIN:
				break;
			case GAME_LOST:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {				//��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMEING || gameState == GAME_PAUSE
					|| gameState == GAME_WIN || gameState == GAME_LOST) {
				gameState = GAME_MENU;
			}
			return true;									//��ʾ�˰����Ѵ������ٽ���ϵͳ�����Ӷ�������Ϸ�������̨
		}
		
		switch (gameState) {
			case GAME_MENU:
				break;
			case GAMEING:
				player.onKeyUp(keyCode, event);				//���ǵİ��������¼�
				break;
			case GAME_PAUSE:
				break;
			case GAME_WIN:
				break;
			case GAME_LOST:
				break;
	}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//���������¼�����������Ϸ״̬��ͬ���в�ͬ����
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
	 * �߼�������,��ͬ״̬��ͬ�Ĵ����߼�
	 */
	public void logic(){
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
				int i=0;
				gameBg.logic();							//��Ϸ�����߼�
				player.logic();							//�����߼�
				/***************************��ͨ�л��߼�*********************************/
				if(!isBoss){
					for(i=0; i<enemys.size(); i++){
						Enemy enemy = enemys.get(i);
						if(enemy.isDead()){				//����л��Ѿ����������Ƴ�����
							enemys.remove(i);
						}else{
							enemy.logic();
						}
					}
					//----------------------��ӵл�-------------------------------------
					count ++ ;
					if(count % CREATE_ENEMY_TIME == 0){	//����count��������ʵ��ÿ��50ms����һ�ܵл���**Ϊ�˲�ʹ���߳�˯�ߣ�
						for(i=0; i<enemyArray[enemyArrayIndex].length; i++){
							switch(enemyArray[enemyArrayIndex][i]){
								case Enemy.TYPE_FLY:								//��Ӭ
									int x = random.nextInt(screenWidth - 100) + 50; //������ɳ�ʼx����
									enemys.add(new Enemy(Enemy.TYPE_FLY, bmpEnemyFly, x, -50));
									break;
								case Enemy.TYPE_DUCKL:								//��Ѽ��
									int y = random.nextInt(20);						//������ɳ�ʼy����
									enemys.add(new Enemy(Enemy.TYPE_DUCKL, bmpEnemyDuck, -10, y));
									break;
								case Enemy.TYPE_DUCKR:								//��Ѽ��(�����˶����ʶ���ʼx��������Ļ��)
									int y1 = random.nextInt(20);
									enemys.add(new Enemy(Enemy.TYPE_DUCKR, bmpEnemyDuck, screenWidth + 50, y1));
									break;
							}
						}
						if(enemyArrayIndex == enemyArray.length - 1){				//�����ж���һ���Ƿ�Ϊ���һ��(Boss)
							isBoss = true;
						}else{
							enemyArrayIndex ++;
						}
					}
					//----------------------�л���������ײ���---------------------------------
					for(i=0; i<enemys.size(); i++){
						Enemy e = enemys.get(i);
						if(player.isCollisionWith(e)){
							player.setPlayerHP(player.getPlayerHP() - 1);			//��������Ѫ��
							if(player.getPlayerHP() < 0){
								gameState = GAME_LOST;
							}
							enemys.remove(i);										//����л���������ײ����ʧ,Ӧ�û��б�ըЧ��
							booms.add(new Boom(bmpBoom, e.x, e.y, Boom.BOOM_FOR_BULLECT));
						}
					}
					//----------------------Ϊ�л�����ӵ�-------------------------------------
					countEnemyBullet ++;
					if(countEnemyBullet >= Integer.MAX_VALUE) countEnemyBullet = 0;
					if(countEnemyBullet % CREATE_ENEMY_BULLET == 0){				//���ݵл���ͬ���ɲ�ͬ�ӵ�
						for(i=0; i<enemys.size(); i++){
							Enemy e = enemys.get(i);
							int bulletType = 0;										//��ͬ���͵л���ͬ���ӵ����й켣
							switch(e.type){
								case Enemy.TYPE_DUCKL:
								case Enemy.TYPE_DUCKR:
									bulletType = Bullet.BULLET_DUCK;
									break;
								case Enemy.TYPE_FLY:
									bulletType = Bullet.BULLET_FLY;
									break;
							}
							bulletEnemys.add(new Bullet(bmpEnemyBullet, e.x, e.y + 10, bulletType));
						}
					}
					//----------------------�л��ӵ��߼�---------------------------------------
					for(i=0; i<bulletEnemys.size(); i++){
						Bullet b = bulletEnemys.get(i);
						if(b.isDead()){
							bulletEnemys.remove(i);
						}else{
							b.logic();
							//----------------------�������ӵ���ײ���---------------------------------
							if(player.isCollisionWith(b)){
								player.setPlayerHP(player.getPlayerHP() - 1);			//��������Ѫ��
								if(player.getPlayerHP() < 0){
									gameState = GAME_LOST;
								}
								bulletEnemys.remove(i);									//����л���������ײ����ʧ,Ӧ�û��б�ըЧ��
							}
						}
					}
					//----------------------�л��������ӵ���ײ���---------------------------------
					for(i=0; i<bulletPlayers.size(); i++){
						Bullet b = bulletPlayers.get(i);
						for(int j=0; j<enemys.size(); j++){
							Enemy e = enemys.get(j);
							if(e.isCollisionWith(b)){								//����ӵ����ел����Ƴ��ӵ��͵л���������ը
								System.out.println("------boom---------");
								enemys.remove(j);
								bulletPlayers.remove(i);
								booms.add(new Boom(bmpBoom, e.x, e.y, Boom.BOOM_FOR_BULLECT));
							}
						}
					}
				}
				/***************************BOSS�л��߼�*********************************/
				else{									//boss�߼�
					boss.logic();
					if(countPlayerBullet % CREATE_BOSS_BULLET == 0){ //Boss��û����֮ǰ����ͨ�ӵ�
						bulletBoss.add(new Bullet(bmpBossBullet, boss.x + 35, boss.y + 40, Bullet.BULLET_FLY));
					}
					//boss�ӵ��߼�
					for(i=0; i<bulletBoss.size(); i++){
						Bullet b = bulletBoss.get(i);
						if(b.isDead()){
							bulletBoss.remove(i);
						}else{
							b.logic();
							//Boss�ӵ������ǵ���ײ
							if(player.isCollisionWith(b)){
								player.setPlayerHP(player.getPlayerHP() - 1);
								if (player.getPlayerHP() <= -1) {
									gameState = GAME_LOST;
								}
								bulletBoss.remove(i);
							}
						}
					}
					
					//Boss�������ӵ����У�������ըЧ��
					for(i=0; i<bulletPlayers.size(); i++){
						Bullet b = bulletPlayers.get(i);
						if(boss.isCollisionWith(b)){
							if (boss.hp <= 0) {
								gameState = GAME_WIN;			//��Ϸʤ��
							} else {
								bulletPlayers.remove(i);		//��ʱɾ��������ײ���ӵ�����ֹ�ظ��ж����ӵ���Boss��ײ
								boss.setHp(boss.hp - 1);		//BossѪ����1
								booms.add(new Boom(bmpBoosBoom, boss.x + 25, boss.y + 30, Boom.BOOM_FOR_BOSS));//��Boss���������Boss��ըЧ��
								booms.add(new Boom(bmpBoosBoom, boss.x + 35, boss.y + 40, Boom.BOOM_FOR_BOSS));
								booms.add(new Boom(bmpBoosBoom, boss.x + 45, boss.y + 50, Boom.BOOM_FOR_BOSS));
							}
						}
					}
				}
				/***************************�����ӵ����*********************************/
				countPlayerBullet ++;
				if(countPlayerBullet >= Integer.MAX_VALUE) countPlayerBullet = 0;
				if(countPlayerBullet % CREATE_PLAYER_BULLET == 0){
					bulletPlayers.add(new Bullet(bmpBullet, player.x + 13, player.y - 20, Bullet.BULLET_PLAYER));
				}
				/***************************�����ӵ��߼�*********************************/
				for(i=0; i<bulletPlayers.size(); i++){
					Bullet b = bulletPlayers.get(i);
					if(b.isDead()){
						bulletPlayers.remove(i);
					}else{
						b.logic();
					}
				}
				/***************************��ըЧ��*************************************/
				for(i=0; i<booms.size(); i++){
					Boom boom = booms.get(i);
					if(boom.isEnd()){
						booms.remove(i);
					}else{
						boom.logic();
					}
				}
				break;
	}
	}
	
	/**
	 * ���ƺ���
	 */
	public void draw(){
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE);
			if(canvas != null){
				switch(gameState){
					case GAME_MENU:
						gameMenu.draw(canvas, paint);	//��Ϸ�˵�
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
						gameBg.draw(canvas, paint);		//��Ϸ����
						player.draw(canvas, paint);		//����
						/********************************�л�����*************************************/
						if(!isBoss){							//�������ͨ�ĵл�����������
							for(int i=0; i<enemys.size(); i++){
								enemys.get(i).draw(canvas, paint);
							}
						}else{									//����boss
							boss.draw(canvas, paint);
							//boss�ӵ�
							for(int i=0; i<bulletBoss.size(); i++){
								bulletBoss.get(i).draw(canvas, paint);
							}
						}
						/********************************�ӵ�����*************************************/
						//�л��ӵ�
						for(int i=0; i<bulletEnemys.size(); i++){
							bulletEnemys.get(i).draw(canvas, paint);
						}
						//�����ӵ�
						for(int i=0; i<bulletPlayers.size(); i++){
							bulletPlayers.get(i).draw(canvas, paint);
						}
						
						/******************************��ըЧ������*************************************/
						for(int i=0; i<booms.size(); i++){
							booms.get(i).draw(canvas, paint);
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
				//������֤�����100msˢ��һ��
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
