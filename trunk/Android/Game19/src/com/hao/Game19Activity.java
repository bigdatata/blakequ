package com.hao;

import android.app.Activity;
import android.os.Bundle;

public class Game19Activity extends Activity {
	public static Activity instance;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	instance = this;
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}