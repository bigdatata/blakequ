package com.hao;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Debug;

/**
 * 利用代码跟踪来改善代码效率
 * （优化处理）详细剖析Android Traceview 效率检视工具
 * http://xiaominghimi.blog.51cto.com/2614927/606325
 * 实现的步骤分为三步：
 * 1.必须先在我们的模拟器中创建sdCard ；
 * 2.将我们的调试代码嵌入工程；
 * 3.利用TraceView来观察和分析代码情况; 
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
     * Google Dev Guide当中说可以在activity的onCreate()中添加Debug.startMethodTracing(), 
     * 而在onDestroy()中添加Debug.stopMethodTracing()，但是在实际的测试时发现这种方式其实并不好用，
     * 因为通常情况下我们的activity的onDestroy()是由系统决定何时调用的，
     * 因此可能等了很长时间都不会得到这个trace文件。因此决定在onStop()中来调用Debug.stopMethodTracing()。
     * 这样当我们切换到其它activity或者点击home键的时候onStop()就会被调用，我们也就可以得到完整的trace file。
     */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Debug.stopMethodTracing();
	}
}