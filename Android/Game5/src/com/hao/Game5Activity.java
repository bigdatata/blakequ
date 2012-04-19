package com.hao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class Game5Activity extends Activity {
	 private byte[] lock; 
	    private final int TIME = 50;//��ע1     
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
	        setContentView(R.layout.main);  
	        lock = new byte[0];//����Object����Ϊ�˼��ٿռ��˷�
	    }  
	    
	    @Override  
	    public boolean onTouchEvent(MotionEvent event) {
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {  
	            Log.v("Himi", "ACTION_DOWN");  
	        } else if (event.getAction() == MotionEvent.ACTION_UP) {  
	            Log.v("Himi", "ACTION_UP");  
	        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {  
	            Log.v("Himi", "ACTION_MOVE");  
	        }  
	        synchronized (lock) {//��ע2  �൱��ÿ��50ms����һ�δ����¼������û���������Ƶ����Ӱ������
	            try {  
	            	lock.wait(TIME);  //���˯�ߵ�ʱ����ʵ����涨��ʱ��Ҫ��΢�ĳ�һЩ
	            } catch (InterruptedException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	        return true;//����һ��Ҫ����true��ԭ���뿴��Android2D��Ϸ����֮�š�  
	    }  
}

/**
 * ����������Ǻǣ���Ҫ���Ǳ�ע2�ĵط���
 һ�� ǰ�ԣ�
    ��λͯЬ�϶���֪����ģ�����У����ǵ���굱���һ��ģ������ĻȻ���ͷţ��ȴ��� ACTION_DOWN Ȼ�� ACTION_UP ��
    ���������Ļ���ƶ���ô�Żᴥ�� ACTION_MOVE �Ķ�����OK���ܶԡ�������Ҫ֪������ֻ��ģ�������������� 
    
���������ģ����������
    �����ǵ�С�û���˵���û��Ҿ������ҽ�MT���еİ�ҹ���Ǿ侭��̨�ʣ��װ��Ŀͻ��������۵������ȿȣ��ص����⣻
    �����ǵ��û��������ǵ���Ϸ��ʱ��������RPG�������͵ģ��û��϶���Ҫ�᳤ʱ���ȥ���������ǵ����ⰴ����
    �������ǻ�����Ļ�ϻ���һ�����ⷽ��������������~��ô��ʵ ACTION_MOVE ����¼��ᱻAndroidһֱ����Ӧ���� 
    
���� Ϊʲô��һֱ��Ӧ ACTION_MOVE ��������أ� ����û�û���ƶ���ָ���Ǿ�ֹ����Ҳ��һֱ��Ӧ��
     ԭ�������㣺��һ������Ϊ��Android ���ڴ����¼������У��ڶ��㣺��Ȼ���ǵ���ָ�о��Ǿ�ֹû���ƶ�����ʵ��ʵ������ˣ�
     �����ǵ���ָ�������ֻ���Ļ��֮�󣬸о���ֹû������ʵ��ָ�ڲ�ͣ��΢�����𶯡����������Ծ�ֹ����ָ���ǲ���΢΢�����ٺ�~ 
    so~ ���Ǿ�Ҫ�����ˣ����ACTION_MOVE��ʱ��һֱ��Android os һֱ��ͣ����Ӧ���������ɶ�������Ϸ�����������˲��ٵĸ���!
    ����������Ϸ�̻߳�ͼʱ��ÿ������100ms,��ô����ָ������Ļ������ݵ�0.1���ڴ�Ż����10�����ҵ�MotionEvent ,
    ����ϵͳ�ᾡ���ܿ�İ���Щevent���������̣߳� �����Ļ�����һ��ʱ����cpu�ͻ�æ�ڴ���onTouchEvent�Ӷ����ߵ�Ļ�����ɻ���һ��һ���ġ�
    ��ô������ʵ�����ò��Ű�����Ӧ��ô��Σ�������Ҫ������ÿ�λ�ͼ�󣬻��߻�ͼǰ����һ���û�������OK�ˣ�
    ��������֡�ʲ������½���̫��������ô����so~����Ҫ�������ʱ�䣬�������������������ǵĻ�ͼʱ��һ��������~�������ܼ�����ϵͳ�̵߳ĸ�����
    
 ��ע2:
    �����е�ͯЬ����Ϊʲô����sleep()�ķ�������ʵ�������ֻ�������߳�����ָ��ʱ��Ļ�������sleep()������
    �������û����Դ�������ơ���Object��wait,notify����ͨ������ʱ�䲻�����������Ƶȴ������ұ���д��ͬ��������С�so~
����ͯЬ���ʣ�Ϊʲô���õ�ǰ���object��ʹ�ã�this.wait(),����new һ��object����
synchronized �е�Object ��ʾObject ����wait����ӵ�иö���ļ���������ǰ��������object��������Ҫ��object����wait~
��ע1��
    ����ı�����Ҷ�֪����ʵ���������õ����ߵ�ʱ�䣬��ô���������ó��������˵�¹������ʱ��Ķ�ֵ��
    ��������Ҳ��˵�����ǵ���Ϸ��ֻҪ���������ǵĻ�ͼ�̵߳�ʱ��һ�����ɣ���Ȼ�����Ǹ����ǵ�����ʱ�䣡���������Ϸ���������֡��
    ��ô���ǿ�������������֡���������趨���˯��ʱ��Ҳ���൱���ʵģ��Ͼ�����һ֡˵���߼�ִ����һ���˺Ǻ�~������Ǹ��ݴ����Ϸ�����������~

ע�⣺Object.wait(long timeout)�������Ҳ��Ҫ���ã�
    ԭ������Ϊ���Է��֣����˯�ߵ�ʱ����ʵ����涨��ʱ��Ҫ��΢�ĳ�һЩ,�������Ǻ�����ƺ�ʱ�仹��û����ġ�
 ���䣺
    1.������ͯЬ��//��ע2�����ܲ�����this��Ҳ���ǵ�ǰ��object�����ǿ��Եģ�����Ҫע����ò�Ҫ�����ã�
    ԭ��������������ط�Ҳ��Ҫ�뵱ǰ��Object����ͬ���Ļ��п��ܳ��������������һ���µ�object��ԭ��Ҳ���ǿ����ô����иø�ʲô�͸�ʲô,
    ����Ӱ�죬
    2.//��ע2������ʵ���ǿ��Զ����Ż�,�Ͼ�һ��Object�Ƚ��˷ѣ�������ʵֻ��Ҫһ���ֽھ��㹻�ˣ�so~���ǿ�����������һ����
    byte[] lock = new byte[0]; ������������������~
 */