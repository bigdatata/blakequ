package com.hao;

import com.hao.view.AnimationSurfaceView;
import com.hao.view.AnimationView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 主要是在同个Activity中有两个View重叠时的处理(游戏中如前面操作界面和后面的背景view)
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
        
        //这里是当程序运行的时候我们默认让我们的MyView(View)来响应按键。通过类名调用对应的View实例，然后设置获取焦点的函数；
        /**
         * 注：将AnimationSurfaceView和AnimationView中的设置焦点注释掉，在MainActivity中决定谁获取焦点
         * 我在两个View中都对获取按键焦点注释掉了，而是在别的类中的调用其View的静态实例对象就可以任意类中对其设置！
         * 这样就可以很容易去控制到底谁来响应按键了。
         * 
         * 这里还要强调一下：当xml中注册多个 View的时候，当我们点击按键之后，Android会先判定哪个View setFocusable(true)设置焦点了,
         * 如果都设置了，那么Android 会默认响应在xml中第一个注册的view ,而不是两个都会响应。那么为什么不同时响应呢？我解释下： 
         * 上面这截图是Android SDK Api的树状图(SurfaceView 继承与View),很明显SurfaceView继承了View,它俩是基继承关系，那么不管是子类还是基类一旦响应了按键，
         * 其基类或者父类就不会再去响应；
         */
        AnimationSurfaceView.view.setFocusable(false);
        AnimationView.view.setFocusable(true);
        //上面的是AnimationView获取焦点，故而能处理AnimationView中实现的onKeyDown事件（动画），不能实现AnimationSurfaceView的事件
        //下面是AnimationSurfaceView获取焦点，能响应AnimationSurfaceView的onKeyDown事件，而不能响应AnimationView的事件
//        AnimationSurfaceView.view.setFocusable(true);
//        AnimationView.view.setFocusable(false);
        //故而在View有上有两个视图AnimationSurfaceView+AnimationView，如果想按键控制哪个视图，关键是让谁获取焦点
    }
    
    // 这里要注意:不管你在xml中注册了多少个View ，也不管View是否都设置了获取焦点，
    //只要你在 MainActivity 中重写onKeyDown（）函数，Android 就会调用此函数。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//备注2  
    	Log.v("Activity", "onKeyDown");
        return super.onKeyDown(keyCode, event);  
    }  
}