package com.hao.view;

import com.ho.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;

public class MyMoveView extends View {
	public MyMoveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.music);
		invalidate();
	}

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Bitmap mBitmap;
	private float[] array = new float[9];

	public void setValues(float[] a) {
		for (int i = 0; i < 9; i++) {
			array[i] = a[i];
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = mPaint;
		canvas.drawBitmap(mBitmap, 0, 0, paint);
		// new һ������任����
		Matrix cm = new Matrix();
		// Ϊ����任����������Ӧ��ֵ
		cm.setValues(array);
		// ��������任�����������ͼ
		canvas.drawBitmap(mBitmap, cm, paint);
		Log.i("CMatrix", "--------->onDraw");
	}
}
