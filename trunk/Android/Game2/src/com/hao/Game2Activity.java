package com.hao;

import com.hao.view.TouchView;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * ��Ļ�л�ʱ�Ĳ���
 * @author Administrator
 *
 */
public class Game2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        Log.i("test", "onCreate");
        setContentView(new TouchView(this));
    }
    

    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("test", "onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("test", "onResume");
	}


	/**
	 * ******************�ؼ�����
	 * android:configChanges="keyboardHidden|orientation"
	 * android:screenOrientation="landscape"
	 * ******************
     * ��ĳЩ��Ϸ�����ǿ�����Ҫ��ֹ�����������л�����ʵʵ�����Ҫ��ܼ򵥣�
     * ֻҪ��AndroidManifest.xml ���������һ�� android :screenOrientation="landscape "��landscape �Ǻ���portrait �����򣩡�
     * 
     * ��android��ÿ����Ļ���л���������Activity(�����onCreate,onStart)������Ӧ����Activity����ǰ���浱ǰ���״̬��
     * ��Activity�ٴ�Create��ʱ���������á�
     * *********����ͨ����Manifest������android:configChanges�Ͳ�������activity(�������onCreate,onStart)*******************************
     * ��activity����android:configChanges="keyboardHidden|orientation"����(��Ȼ������øú���),
     * �Ͳ�������activity.����ȥ����onConfigurationChanged(Configuration newConfig). �����Ϳ�������������������ʾ��ʽ.
     */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		try {  
            super.onConfigurationChanged(newConfig);  
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {  
                Log.v("Himi", "onConfigurationChanged_ORIENTATION_LANDSCAPE");  
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {  
                Log.v("Himi", "onConfigurationChanged_ORIENTATION_PORTRAIT");  
            }  
        } catch (Exception ex) {  
        	ex.printStackTrace();
        }  
	}
    
    
}