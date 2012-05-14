package com.hao;

import android.os.Looper;
/**
 * ��������ʱ�����û��looper�͵ȴ���ֱ������run����
 * @author Administrator
 * http://jinguo.iteye.com/blog/1070229
 *http://windywindy.iteye.com/blog/464185
 *��MediaPlaybackActivity.java�У����ǿ��Կ�һ����OnCreate�е������������䣺

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
		Looper.loop();//ѭ��������Ϣ
	}
	
}
