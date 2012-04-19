package com.hao;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Debug;

/**
 * ���ô�����������ƴ���Ч��
 * ���Ż�������ϸ����Android Traceview Ч�ʼ��ӹ���
 * http://xiaominghimi.blog.51cto.com/2614927/606325
 * ʵ�ֵĲ����Ϊ������
 * 1.�����������ǵ�ģ�����д���sdCard ��
 * 2.�����ǵĵ��Դ���Ƕ�빤�̣�
 * 3.����TraceView���۲�ͷ����������; 
 * @author Administrator
 *
 */
public class Game3Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
        Debug.startMethodTracing("tracing");
    }


    /**
     * Google Dev Guide����˵������activity��onCreate()�����Debug.startMethodTracing(), 
     * ����onDestroy()�����Debug.stopMethodTracing()��������ʵ�ʵĲ���ʱ�������ַ�ʽ��ʵ�������ã�
     * ��Ϊͨ����������ǵ�activity��onDestroy()����ϵͳ������ʱ���õģ�
     * ��˿��ܵ��˺ܳ�ʱ�䶼����õ����trace�ļ�����˾�����onStop()��������Debug.stopMethodTracing()��
     * �����������л�������activity���ߵ��home����ʱ��onStop()�ͻᱻ���ã�����Ҳ�Ϳ��Եõ�������trace file��
     */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Debug.stopMethodTracing();
	}
}