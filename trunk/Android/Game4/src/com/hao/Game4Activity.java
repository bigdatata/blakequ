package com.hao;

import com.hao.view.AnimationSurfaceView;
import com.hao.view.AnimationView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/**
 * ��Ҫ����ͬ��Activity��������View�ص�ʱ�Ĵ���(��Ϸ����ǰ���������ͺ���ı���view)
 * http://xiaominghimi.blog.51cto.com/2614927/606768
 * @author Administrator
 *
 */
public class Game4Activity extends Activity {
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new AnimationView(this));
//        setContentView(new AnimationSurfaceView(this));
        setContentView(R.layout.main);
        
        //�����ǵ��������е�ʱ������Ĭ�������ǵ�MyView(View)����Ӧ������ͨ���������ö�Ӧ��Viewʵ����Ȼ�����û�ȡ����ĺ�����
        /**
         * ע����AnimationSurfaceView��AnimationView�е����ý���ע�͵�����MainActivity�о���˭��ȡ����
         * ��������View�ж��Ի�ȡ��������ע�͵��ˣ������ڱ�����еĵ�����View�ľ�̬ʵ������Ϳ����������ж������ã�
         * �����Ϳ��Ժ�����ȥ���Ƶ���˭����Ӧ�����ˡ�
         * 
         * ���ﻹҪǿ��һ�£���xml��ע���� View��ʱ�򣬵����ǵ������֮��Android�����ж��ĸ�View setFocusable(true)���ý�����,
         * ����������ˣ���ôAndroid ��Ĭ����Ӧ��xml�е�һ��ע���view ,����������������Ӧ����ôΪʲô��ͬʱ��Ӧ�أ��ҽ����£� 
         * �������ͼ��Android SDK Api����״ͼ(SurfaceView �̳���View),������SurfaceView�̳���View,�����ǻ��̳й�ϵ����ô���������໹�ǻ���һ����Ӧ�˰�����
         * �������߸���Ͳ�����ȥ��Ӧ��
         */
        AnimationSurfaceView.view.setFocusable(false);
        AnimationView.view.setFocusable(true);
        //�������AnimationView��ȡ���㣬�ʶ��ܴ���AnimationView��ʵ�ֵ�onKeyDown�¼���������������ʵ��AnimationSurfaceView���¼�
        //������AnimationSurfaceView��ȡ���㣬����ӦAnimationSurfaceView��onKeyDown�¼�����������ӦAnimationView���¼�
//        AnimationSurfaceView.view.setFocusable(true);
//        AnimationView.view.setFocusable(false);
        //�ʶ���View������������ͼAnimationSurfaceView+AnimationView������밴�������ĸ���ͼ���ؼ�����˭��ȡ����
    }
    
    // ����Ҫע��:��������xml��ע���˶��ٸ�View ��Ҳ����View�Ƿ������˻�ȡ���㣬
    //ֻҪ���� MainActivity ����дonKeyDown����������Android �ͻ���ô˺�����
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//��ע2  
    	Log.v("Activity", "onKeyDown");
        return super.onKeyDown(keyCode, event);  
    }  
}