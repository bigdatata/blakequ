package com.hao;

import com.ho.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MatrixTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_one);
        Button one = (Button) findViewById(R.id.button1);
        one.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MatrixTestActivity.this, ColorMatrixActivity.class));
			}
		});
        
        Button two = (Button) findViewById(R.id.button2);
        two.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MatrixTestActivity.this, MoveMatrixActivity.class));
			}
		});
    }
}