package com.hao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestThreadLooperActivity extends Activity {
	TextView text;
	Button button;
	MyThread myThread;
	MyHandler myHandler = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 利用handler在线程间通讯
				 */
				myThread = new MyThread();
				myThread.start();
				
				/**
				 * 利用handler组件内部通讯
				 */
				/**
				Looper looper = Looper.myLooper();
				myHandler = new MyHandler(looper);
				//buton等组件可以由mHandler将消息传给looper后,再放入messageQueue中,同时mHandler也可以接受来自looper消息
				myHandler.removeMessages(0);
				String s = "组件内部通信息";
				Message msg = myHandler.obtainMessage(1, 1, 1, s);
				myHandler.sendMessage(msg);
				*/
			}
		});
    }
    
    class MyHandler extends Handler{
    	public MyHandler(Looper looper){
    		super(looper);
    	}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			text.setText("what:"+msg.what+" arg1:"+msg.arg1+" arg2:"+msg.arg2+" obj:"+msg.obj.toString());
		}
    	
    }
    
    /**
     * Android会自动替主线程建立Message Queue。在这个子线程里并没有建立Message Queue。
     * 所以，myLooper值为null，而mainLooper则指向主线程里的Looper。于是，执行到： mHandler = new MyHandler (mainLooper); 
     * 此mHandler属于主线程。 mHandler.sendMessage(m); 就将m消息存入到主线程的Message Queue里。
     * mainLooper看到Message Queue里有讯息，就会作出处理，于是由主线程执行到mHandler的handleMessage()来处理消息。 
     * @author Administrator
     *
     */
    class MyThread extends Thread{
    	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Looper mainLooper = Looper.getMainLooper();
			Looper curLooper = Looper.myLooper();
			String msg;
			if(curLooper == null){
				//此时handler将于主线程的消息队列进行沟通，发送消息会到主线程，然后主线程执行
				myHandler = new MyHandler(mainLooper);
				msg = "current looper is null";
			}else{
				myHandler = new MyHandler(curLooper);
				msg = "this is current looper";
			}
			
			myHandler.removeMessages(0);
			Message m = myHandler.obtainMessage(1,2,3,msg);
			myHandler.sendMessage(m);
		}
    	
    }
}