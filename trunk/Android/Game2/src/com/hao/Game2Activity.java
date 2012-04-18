package com.hao;

import com.hao.view.TouchView;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * 屏幕切换时的测试
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
	 * ******************关键设置
	 * android:configChanges="keyboardHidden|orientation"
	 * android:screenOrientation="landscape"
	 * ******************
     * 在某些游戏中我们可能需要禁止横屏和竖屏切换，其实实现这个要求很简单，
     * 只要在AndroidManifest.xml 里面加入这一行 android :screenOrientation="landscape "（landscape 是横向，portrait 是纵向）。
     * 
     * 在android中每次屏幕的切换动会重启Activity(会调用onCreate,onStart)，所以应该在Activity销毁前保存当前活动的状态，
     * 在Activity再次Create的时候载入配置。
     * *********下面通过在Manifest中配置android:configChanges就不会重启activity(不会调用onCreate,onStart)*******************************
     * 在activity加上android:configChanges="keyboardHidden|orientation"属性(不然不会调用该函数),
     * 就不会重启activity.而是去调用onConfigurationChanged(Configuration newConfig). 这样就可以在这个方法里调整显示方式.
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