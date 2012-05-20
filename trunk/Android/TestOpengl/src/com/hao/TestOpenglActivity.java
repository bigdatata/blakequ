package com.hao;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class TestOpenglActivity extends Activity {
	GLRender renderer = new GLRender();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView gview=new GLSurfaceView(this);
        gview.setRenderer(renderer);
        setContentView(gview);
    }
}