package com.hao;

import android.app.Activity;
import android.os.Bundle;

public class Game20_box2d_circleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}