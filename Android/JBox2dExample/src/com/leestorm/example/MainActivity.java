package com.leestorm.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
    public static MainActivity instance;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        instance = this;
        
        //设置为无标题栏   
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //设置为全屏模式   
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //基于libgdx的BOX2D
//        MyView2 view = new MyView2(this);
        
        //基于JBOX2D-Android
        MyView3 view = new MyView3(this);
        
        //基于JBOX2D
//        MyView view = new MyView(this);
        
        setContentView(view);
        
    }
    
}
