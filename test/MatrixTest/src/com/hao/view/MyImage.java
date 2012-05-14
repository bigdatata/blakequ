package com.hao.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.ho.R;

public class MyImage extends View {
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Bitmap mBitmap;
	private float[] array = new float[20];

	private float mAngle;

	public MyImage(Context context, AttributeSet attrs) {
		super(context, attrs);

		mBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.sample_4);
		invalidate();
	}

	public void setValues(float[] a) {
		for (int i = 0; i < 20; i++) {
			array[i] = a[i];
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = mPaint;

		paint.setColorFilter(null);
		canvas.drawBitmap(mBitmap, 0, 0, paint);

		ColorMatrix cm = new ColorMatrix();
		// ÉèÖÃÑÕÉ«¾ØÕó
		cm.set(array);
		// ÑÕÉ«ÂË¾µ£¬½«ÑÕÉ«¾ØÕóÓ¦ÓÃÓÚÍ¼Æ¬
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		// »æÍ¼
		canvas.drawBitmap(mBitmap, 0, 0, paint);
		Log.i("CMatrix", "--------->onDraw");
	}
}
