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
        
        //����Ϊ�ޱ�����   
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //����Ϊȫ��ģʽ   
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //����libgdx��BOX2D
//        MyView2 view = new MyView2(this);
        
        //����JBOX2D-Android
        MyView3 view = new MyView3(this);
        
        //����JBOX2D
//        MyView view = new MyView(this);
        
        setContentView(view);
        
    }
    
}
