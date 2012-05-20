package com.stickycoding.rokon;

import com.stickycoding.rokon.device.OS;

import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * GameThread.java
 * Handles the game thread, should it be separated from the OpenGL
 * 游戏主线程
 * @author Richard
 */
public class GameThread implements Runnable {
	//This should be fine for now, any more and the game must be being very unresponsive anyway
	protected static final int MAX_TRIGGERS = 32; 
	
	private static boolean finished = false, pauseGame = false;
	/**
	 * 用于同步的对象(为了节约空间可以用一位)
	 * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
	 * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
	 * private byte[] lock = new byte[0];　// 特殊的instance变量
	 */
	private static Object pauseLock = new Object();
	private static Object inputLock = new Object();
	
	//触发器变量
	private static MotionTrigger[] motionTrigger;//动作触发器数组
	private static KeyTrigger[] keyTrigger;//按键触发数组
	protected static int motionTriggerCount;//动作触发计数
	protected static boolean hasKeyTrigger;//按键触发数组
	
	private long lastTime;
	
	protected static boolean hasRunnable;
	//运行锁
	public static Object runnableLock = new Object();
	
	/**
	 * 触摸触发器(主要记录了最大3个触摸点)
	 * @author AlbertQu
	 *
	 */
	protected class MotionTrigger {
		//最大触摸点是3个
		public static final int MAX_POINTERS = 3;
		
		protected boolean isTouch;
		
		protected float[] x, y;
		protected int[] pointerId;
		protected int action;
		protected int pointerCount;
		
		protected MotionTrigger() {
			x = new float[MAX_POINTERS];
			y = new float[MAX_POINTERS];
			pointerId = new int[MAX_POINTERS];
		}
		
		/**
		 * set the point array of touch
		 * @param event
		 * @param isTouch
		 */
		protected void set(MotionEvent event, boolean isTouch) {
			if(OS.API_LEVEL >= 5) {
				if(OS.API_LEVEL >= 8) {
					action = event.getAction();
					pointerCount = Rokon.motionEvent8.getPointerCount(event);
					for(int i = 0; i < pointerCount; i++) {
						pointerId[i] = Rokon.motionEvent8.getPointerId(event, i);
						x[i] = Rokon.motionEvent8.getX(event, i);
						y[i] = Rokon.motionEvent8.getY(event, i);
					}
				} else {
					action = event.getAction();
					pointerCount = Rokon.motionEvent5.getPointerCount(event);
					for(int i = 0; i < pointerCount; i++) {
						pointerId[i] = Rokon.motionEvent5.getPointerId(event, i);
						x[i] = Rokon.motionEvent5.getX(event, i);
						y[i] = Rokon.motionEvent5.getY(event, i);
					}
				}
			} else {
				// No multitouch, life is simple
				x[0] = event.getX();
				y[0] = event.getY();
				action = event.getAction();
				pointerCount = motionTriggerCount++;
			}
			this.isTouch = isTouch;
		}
		
	}
	
	/**
	 * 按钮触发器
	 * @author AlbertQu
	 *
	 */
	protected class KeyTrigger {
		protected boolean isDown;
		protected int keyCode;
		protected boolean isNull;
		
		protected KeyTrigger() {
			isNull = true;
		}
		
		protected void set(int keyCode, boolean isDown) {
			this.keyCode = keyCode;
			this.isDown = isDown;
			isNull = false;
		}
		
		protected void reset() {
			isNull = true;
		}
	}
	
	//@Override
	public void run() {
		Debug.print("Game thread begins");
		finished = false;
		pauseGame = false;
		hasRunnable = false;
		hasKeyTrigger = false;
		motionTrigger = new MotionTrigger[MAX_TRIGGERS];
		keyTrigger = new KeyTrigger[MAX_TRIGGERS];
		
		for(int i = 0; i < MAX_TRIGGERS; i++) {
			motionTrigger[i] = new MotionTrigger();
			keyTrigger[i] = new KeyTrigger();
		}
		
		while(!finished) {		
			final long startTime = SystemClock.uptimeMillis();
			final long deltaTime = startTime - lastTime;
			long finalDelta = deltaTime;
			
			//Just make sure we aren't going too fast, there's no rush here
			if(deltaTime > 12) {
				lastTime = startTime;
				Time.updateLoop();		
				Scene scene = RokonActivity.currentScene;
				
				if(scene != null) {
					// Before we do anything, make sure we're on a new buffer
					RokonActivity.renderQueueManager.swap(RokonRenderer.singleton);
					
					// First, see if there are any Runnables in the queue that you want me tod o
					synchronized(runnableLock) {
						if(hasRunnable) {
							hasRunnable = false;
							for(int i = 0; i < Scene.MAX_RUNNABLE; i++) {
								if(Scene.gameRunnable[i] != null) {
									if(Time.getLoopTicks() > Scene.gameRunnableTime[i]) {
										Scene.gameRunnable[i].run();
										Scene.gameRunnable[i] = null;
									} else {
										hasRunnable = true;
									}
								}
							}
						}
					}
					
					// Check UI queue
					scene.onUIRunnables();
					
					// Then see if there's any new input
					synchronized (inputLock) {
						if(motionTriggerCount > 0) {
							for(int i = 0; i < motionTriggerCount; i++) {
								if(motionTrigger[i].isTouch) {
									scene.handleTouch(motionTrigger[i].x, motionTrigger[i].y, motionTrigger[i].action, motionTrigger[i].pointerCount, motionTrigger[i].pointerId);
								} else {
									scene.onTrackballEvent(motionTrigger[i].x[0], motionTrigger[i].y[0], motionTrigger[i].action);
								}
							}
							motionTriggerCount = 0;
						}
						if(hasKeyTrigger) {
							for(int i = 0; i < MAX_TRIGGERS; i++) {
								if(!keyTrigger[i].isNull) {
									if(keyTrigger[i].isDown) {
										scene.onKeyDown(keyTrigger[i].keyCode);
									} else {
										scene.onKeyUp(keyTrigger[i].keyCode);
									}
									keyTrigger[i].reset();
								}
							}
							hasKeyTrigger = false;
						}
					}
					
					// Update the physics, if needs be
					if(scene.usePhysics) {
						if(scene.pausePhysics) {
							scene.world.step(0, 1, 1);
						} else {
							float timeStep = Time.getLoopTicksFraction();
							if(timeStep > 0.018f) timeStep = 0.018f;
							scene.world.step(timeStep, 10, 10);
						}
					}		
					
					synchronized(RokonActivity.killLock) {
						if(!finished) { 
							// Run your game loop
							scene.onGameLoop();	
							
							// Stick everything onto the rendering buffer
							scene.render();		
						}
					}
				}
				final long endTime = SystemClock.uptimeMillis();
				finalDelta = endTime - startTime;
			}
			
			FPSCounter.onLoop();
			
			// If we're running above 60fps, chill out, let the other thread do some work
			if(finalDelta < 16) {
				try {
					Thread.sleep(16 - finalDelta);
				} catch (Exception e) {
					
				}
			}
			
			// If we're paused, hang on ...
			synchronized (pauseLock) {
				while(pauseGame) {
					try {
						pauseLock.wait();
					} catch (InterruptedException e) { }
				}
			}
			
		}
		
		RokonActivity.renderQueueManager.emptyQueues(RokonRenderer.singleton);
		
		Debug.print("Game thread over");
	}
	
	public static void pauseGame() {
		synchronized (pauseLock) {
			pauseGame = true;
		}
	}
	
	public static void resumeGame() {
		synchronized (pauseLock) {
			pauseGame = false;
			pauseLock.notifyAll();
		}
	}
	
	public static boolean getPaused() {
		return pauseGame;
	}
	
	public static void stopThread() {
		synchronized (pauseLock) {
			pauseGame = false;
			finished = true;
			pauseLock.notifyAll();
		}
	}
	
	/**
	 * 跟踪触摸动作
	 * @param touch
	 * @param event
	 */
	public static void motionInput(boolean touch, MotionEvent event) {
		synchronized (inputLock) {
			if(motionTriggerCount < MAX_TRIGGERS) {
				motionTrigger[motionTriggerCount].set(event, touch);
				motionTriggerCount++;
			} else {
				Debug.warning("MotionInput queue is full, what's going on? Either I'm broken, or you need to buy a new phone");
			}
		}
	}
	
	/**
	 * 跟踪按钮动作
	 * @param down
	 * @param keyCode
	 * @param event
	 */
	public static void keyInput(boolean down, int keyCode, KeyEvent event) {
		synchronized (inputLock) {
			for(int i = 0; i < MAX_TRIGGERS; i++) {
				if(keyTrigger[i].isNull) {
					keyTrigger[i].set(keyCode, down);
					hasKeyTrigger = true;
					return;
				}
			}
			Debug.warning("Key input queue is full, what's goingo on?");
		}
	}

}
