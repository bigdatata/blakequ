package com.hao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * ���ڴ����¼��Ͱ����¼����Զ���View�е�ʵ��
 * @author Administrator
 *
 */
public class TouchView extends View{

	private Paint p;
	private int bmp_y = 0;
	private int bmp_x = 0;
	public TouchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		/**
		 * ��surfaceview�����ǵ�onKeyDown ��Ȼ����д��view�ĺ�����������Ȼ��Ҫ�ڳ�ʼ����ʱ��ȥ������ȡ���㣬
		 * setFocusable(true); ��������ô˷�������ô����ɰ�����Ч��ԭ������Ϊ������Լ�����һ���̳���View����,
		 * ����ʵ��onKeyDown������,ֻ�е���View��ý���ʱ�Ż����onKeyDown����,Actvity�е�onKeyDown�����ǵ�
		 * ���пؼ���û�д���ð����¼�ʱ,�Ż����.
		 */
		System.out.println("----"+isFocused()+","+isFocusable()+","+isFocusableInTouchMode());
		setFocusable(true);//������֮��Ż����onFocusChanged
		setFocusableInTouchMode(true);//�ɽ��մ�������
		System.out.println("----"+isFocused()+","+isFocusable()+","+isFocusableInTouchMode());
		p = new Paint();
		p.setColor(Color.RED);
		p.setAntiAlias(true);
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawText("��ð�", 0, 3, getWidth()/2, getHeight()/2, p);
	}



	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		Log.v("test", "onFocusChanged");  
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.v("test", "onKeyDown");  
        bmp_x++;
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ������Ӧ�ĺ�����onTouchEvent()�� ��д�˺�����ʱ��Ĭ�����һ�������ջ���ķ��ط�ʽ��
	 * return super.onTouchEvent(event)�� Ȼ������������ȥ�ж� MotionEvent.ACTION_MOVE��MotionEvent.ACTION_DOWN��
	 * MotionEvent.ACTION_UP ���Ӧ���������� �϶������¡�̧�𣻶Դ�һ�ж�����ȷ�ģ�
	 * ���������ĵ���������Ŀ��ʱ���� Log.v("Himi", "ACTION_MOVE"); ����log��"ACTION_MOVE"����Զ����ִ�У�����
	 * Ϊ�����ҵ��˽����������ô�Ƚ�����Ϊʲô����ִ�����������ͣ�onTouchEvent()��Ԥ��ʹ��Oeverride���������
	 * ͨ����r��ȥ����super.onTouchEvent()�����ز���ֵ����������Ҫע��һ�㣬Ԥ�����ȥ����super.onTouchEvent()
	 * �t���п���super���沢û���κ��£����һش�false�؁�һ���ش�false�؁��ܿ��ܺ����event (���磺Action_Move��Action_Up) 
	 * �����ղ����ˣ�����Ϊ��ȷ��������event������յ���Ҫע���Ƿ�Ҫֱ�Ӻ�super.TouchEvent()��
	 * 
	 * �������Ҳ�ǵ�ʱ�õ��˺�����ʱ���ֵģ����˺ܶ����ϲ��ҵ�����͡������Ժ�ʹ��onTouchEvent����������ʱ������
	 * return super.onTouchEvent(event);һ��Ҫ��Ϊreturn true;
	 * 
	 * ���Ҫע��һ�㣺�ڳ�ʼ����ʱ��Ҫ����setFocusableInTouchMode��true��;����ģʽ��ȡ���㣬
	 * �Ƚ����� setFocusable(true);����setFocusable(true);//�˷�����������Ӧ������
	 * ������Լ�����һ���̳���View����,����ʵ��onKeyDown������,ֻ�е���View��ý���ʱ�Ż����onKeyDown����,
	 * Actvity�е�onKeyDown�����ǵ����пؼ���û�д���ð����¼�ʱ,�Ż����.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("test", "onTouchEvent");  
        bmp_y++;  
        if (event.getAction() == MotionEvent.ACTION_MOVE) {  
            Log.v("Himi", "ACTION_MOVE");  
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {  
            Log.v("Himi", "ACTION_DOWN");  
        } else if (event.getAction() == MotionEvent.ACTION_UP) {  
            Log.v("Himi", "ACTION_UP");  
        }  
        return true; 
//		return super.onTouchEvent(event);
	}
	
	

}
