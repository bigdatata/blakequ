package com.hao;

import android.app.Activity;
import android.os.Bundle;

public class Game13Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this, null));
    }
}