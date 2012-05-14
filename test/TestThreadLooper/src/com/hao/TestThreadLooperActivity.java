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
				 * ����handler���̼߳�ͨѶ
				 */
				myThread = new MyThread();
				myThread.start();
				
				/**
				 * ����handler����ڲ�ͨѶ
				 */
				/**
				Looper looper = Looper.myLooper();
				myHandler = new MyHandler(looper);
				//buton�����������mHandler����Ϣ����looper��,�ٷ���messageQueue��,ͬʱmHandlerҲ���Խ�������looper��Ϣ
				myHandler.removeMessages(0);
				String s = "����ڲ�ͨ��Ϣ";
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
     * Android���Զ������߳̽���Message Queue����������߳��ﲢû�н���Message Queue��
     * ���ԣ�myLooperֵΪnull����mainLooper��ָ�����߳����Looper�����ǣ�ִ�е��� mHandler = new MyHandler (mainLooper); 
     * ��mHandler�������̡߳� mHandler.sendMessage(m); �ͽ�m��Ϣ���뵽���̵߳�Message Queue�
     * mainLooper����Message Queue����ѶϢ���ͻ������������������߳�ִ�е�mHandler��handleMessage()��������Ϣ�� 
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
				//��ʱhandler�������̵߳���Ϣ���н��й�ͨ��������Ϣ�ᵽ���̣߳�Ȼ�����߳�ִ��
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