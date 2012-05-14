package com.hao;

import android.os.Looper;
/**
 * 当创建的时候如果没有looper就等待，直到运行run方法
 * @author Administrator
 * http://jinguo.iteye.com/blog/1070229
 *http://windywindy.iteye.com/blog/464185
 *在MediaPlaybackActivity.java中，我们可以看一下再OnCreate中的有这样的两句：

        mAlbumArtWorker = new Worker("album art worker");
        mAlbumArtHandler = new AlbumArtHandler(mAlbumArtWorker.getLooper());
 */
public class Thread1 implements Runnable {
	 private final Object mLock = new Object();
     private Looper mLooper;
     
     Thread1(String name){
    	 Thread t = new Thread(null, this, name);
    	 t.setPriority(Thread.NORM_PRIORITY);
    	 t.start();
    	 synchronized (mLock) {
    		 if(mLooper == null){
    			 try {
					mLock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
		}
     }
     
     public Looper getLooper(){
    	 return mLooper;
     }
     
     public void quit(){
    	 mLooper.quit();
     }
     
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (mLock) {
			Looper.prepare();
			mLooper = Looper.myLooper();
			mLock.notifyAll();
		}
		Looper.loop();//循环处理消息
	}
	
}
