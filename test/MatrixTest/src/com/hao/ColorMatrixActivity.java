package com.hao;

import com.hao.view.MyImage;
import com.ho.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ho.*;

public class ColorMatrixActivity extends Activity {
	private Button change;
	private EditText[] et = new EditText[20];
	private float[] carray = new float[20];
	private MyImage sv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		change = (Button) findViewById(R.id.set);
		sv = (MyImage) findViewById(R.id.MyImage);
		
		for (int i = 0; i < 20; i++) {
			int id = R.id.indexa0+i;
			et[i] = (EditText) findViewById(id);
			carray[i] = Float.valueOf(et[i].getText().toString());
		}
		change.setOnClickListener(l);
	}

	private Button.OnClickListener l = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getValues();
			sv.setValues(carray);
			sv.invalidate();
		}
	};

	public void getValues() {
		for (int i = 0; i < 20; i++) {
			carray[i] = Float.valueOf(et[i].getText().toString());
		}
	}
}
