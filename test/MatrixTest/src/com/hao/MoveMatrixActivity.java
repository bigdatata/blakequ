package com.hao;

import com.hao.view.MyMoveView;
import com.ho.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MoveMatrixActivity extends Activity {
	private Button change;
	private EditText[] et = new EditText[9];
	private float[] carray = new float[9];
	private MyMoveView sv;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_two);

		change = (Button) findViewById(R.id.change);
		sv = (MyMoveView) findViewById(R.id.moveView);

		for (int i = 0; i < 9; i++) {
			int id = R.id.ed1 + i;
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
		for (int i = 0; i < 9; i++) {
			carray[i] = Float.valueOf(et[i].getText().toString());
		}
	}
}
