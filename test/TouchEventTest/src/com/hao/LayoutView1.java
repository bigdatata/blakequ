package com.hao;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class LayoutView1 extends LinearLayout {
	 private final String TAG = "LayoutView1"; 
     public LayoutView1(Context context, AttributeSet attrs) { 
    	 super(context, attrs); 
    	 Log.e(TAG,TAG); 
     }
     
     @Override 
     public boolean onInterceptTouchEvent(MotionEvent ev) { 
         int action = ev.getAction(); 
         switch(action){ 
         case MotionEvent.ACTION_DOWN: 
              Log.e(TAG,"onInterceptTouchEvent action:ACTION_DOWN"); 
//              return true; ����������ˣ�����ľͲ���õ��¼�
              break;
		case MotionEvent.ACTION_MOVE: 
              Log.e(TAG,"onInterceptTouchEvent action:ACTION_MOVE"); 
              break; 
         case MotionEvent.ACTION_UP: 
              Log.e(TAG,"onInterceptTouchEvent action:ACTION_UP"); 
              break; 
         case MotionEvent.ACTION_CANCEL: 
              Log.e(TAG,"onInterceptTouchEvent action:ACTION_CANCEL"); 
              break; 
         } 
         return false; 
     } 

     @Override 
     public boolean onTouchEvent(MotionEvent ev) { 
         int action = ev.getAction(); 
         switch(action){ 
         case MotionEvent.ACTION_DOWN: 
              Log.e(TAG,"onTouchEvent action:ACTION_DOWN"); 
              break; 
         case MotionEvent.ACTION_MOVE: 
              Log.e(TAG,"onTouchEvent action:ACTION_MOVE"); 
              break; 
         case MotionEvent.ACTION_UP: 
              Log.e(TAG,"onTouchEvent action:ACTION_UP"); 
              break; 
         case MotionEvent.ACTION_CANCEL: 
              Log.e(TAG,"onTouchEvent action:ACTION_CANCEL"); 
              break; 
         } 
         return true; 
//         return false;
     } 
     
     @Override 
     protected void onLayout(boolean changed, int l, int t, int r, int b) { 
         // TODO Auto-generated method stub 
         super.onLayout(changed, l, t, r, b); 
     } 

     @Override 
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
         // TODO Auto-generated method stub 
         super.onMeasure(widthMeasureSpec, heightMeasureSpec); 
     } 

     /**
      * �����Ĺ����ǣ� 
      * 1.down�¼����Ȼᴫ�ݵ�onInterceptTouchEvent()���� 
      * 
      * 2.�����ViewGroup��onInterceptTouchEvent()�ڽ��յ�down�¼��������֮��return false(������)��
      * ��ô������move, up���¼����������ȴ��ݸ���ViewGroup��֮��ź�down�¼�һ�����ݸ����յ�Ŀ��view��onTouchEvent()���� 
      *
      * 3.�����ViewGroup��onInterceptTouchEvent()�ڽ��յ�down�¼��������֮��return true(���أ���ô�����move,up�¼�����Ҫ�ڿ���Ϊ�Ѿ�������
      * ����ֱ����ȥ����onTouchEvent()�Ϳ�����)����ô������move, up���¼������ٴ��ݸ�onInterceptTouchEvent()��
      * ���Ǻ�down�¼�һ�����ݸ���ViewGroup��onTouchEvent()����ע�⣬Ŀ��view�����ղ����κ��¼��� 
      * 1:LayoutView1(31375): onInterceptTouchEvent action:ACTION_DOWN
      * 2:LayoutView2(31375): onInterceptTouchEvent action:ACTION_DOWN
      * 3:LayoutView2(31375): onTouchEvent action:ACTION_DOWN
      * 4:LayoutView1(31375): onInterceptTouchEvent action:ACTION_MOVE
      * 5:LayoutView2(31375): onTouchEvent action:ACTION_MOVE
      * 6:LayoutView1(31375): onInterceptTouchEvent action:ACTION_MOVE
      * 7:LayoutView2(31375): onTouchEvent action:ACTION_MOVE
      * 8:LayoutView1(31375): onInterceptTouchEvent action:ACTION_UP
      * 9:LayoutView2(31375): onTouchEvent action:ACTION_UP
      * ������Ϊ��
      * onInterceptTouchEvent��LayoutView1Ϊfalse,LayoutView2Ϊtrue
      * onTouchEvent��LayoutView2Ϊtrue
      * �ʶ��¼���LayoutView2ʱ�����ز�������������˵������LayoutView2������MOVE,UP���������ھ���onInterceptTouchEvent��ֱ��
      * ����onTouchEvent�������Ҳ��ȷ��ˡ�������LayoutView2��3,5,7,9����һ����onInterceptTouchEvent������1���Ժ󽻸�onTouchEvent��
      * ��LayoutView1������Ҫ����onInterceptTouchEvent(��LayoutView1��4,6,8)
      * 
      * 4.���������Ҫ�����¼���view��onTouchEvent()������false(û�ܴ�������¼������ܶ��ڴ������ø�����)��
      * ��ô���¼���������������һ��ε�view��onTouchEvent()���� 
      * **************************************************************************
      * �о���һ��Ȧ��Ȼ��һֱ����һ���ܴ��������Ϣ���ˣ�����ҵ��˾ͽ�����û�ҵ���ѭ����֪���ص�������Ϣ���Ǹ���
      * û�б�ע��DOWN��ʾ�����¼�onInterceptTouchEvent����ע��onTouchEvent���Ǵ����¼�
      * �����û����: A(DOWN)-->B(DOWN)-->C(onTouchEvent DOWN)-->B(onTouchEvent DOWN)-->A(onTouchEvent DOWN),û��ִ��UP�¼���ע����MOVE�Ļ�����DOWN��UP֮��,����Ķ�һ����
      * B����A(DOWN)-->B(DOWN)-->B(onTouchEvent)-->A(onTouchEvent UP)-->B(onTouchEvent UP)-->(over)
      * ���ӣ�������ײ�������Ϣ�ʹ������ӣ��������Ҫ�����Ϣ�ʹ���(DOWN)������,Ȼ���и���1--����2--�����Դ��ͷ���Ϣ(UP)��
      * Ȼ��������Ӷ������Ϣ��֮�����������Ϣ�ִ��ظ��ף��ɸ���������
      * 11** ����1(LayoutView1������false)---����2(LayoutView2������false)--����(MyTextView��onTouchEvent return true)--����
      * 22** ����1(LayoutView1������false)---����2(LayoutView2������false)--����(MyTextView��onTouchEvent return false)--�ش�������2(onTouchEvent return true)--����
      * 33** ����1(LayoutView1������false)---����2(LayoutView2������false)--����(MyTextView��onTouchEvent return false)--�ش�������2(onTouchEvent return false)--����1(onTouchEvent return true)--����(�����û������ִ��UP ACTION)
      * 44** ����1(LayoutView1����true)--����1(onTouchEvent return true)--����          (DOWN--DOWN(onTouchEvent)--UP(onTouchEvent))
      * 55** ����1(LayoutView1����false)--����2(LayoutView2����true)--����2(onTouchEvent return false)--����1(onTouchEvent return true)--����      (DOWN1--DOWN2--DOWN(2 onTouchEvent)--DOWN(1 onTouchEvent)--UP(1 onTouchEvent))(1:����2,2������2)
      * 
      * ***************************************************************************
      * 5.���������Ҫ�����¼���view ��onTouchEvent()������true����ô�����¼������Լ������ݸ���view��onTouchEvent()���� 
      */
}
