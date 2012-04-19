package com.hao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.hao.R;

public class MyView extends View {

	private Paint paint;
	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		hot(canvas);
	}

	/**
	 * 一个耗费空间和时间的测试
	 * @param canvas
	 */
	public void hot(Canvas canvas) {  
        for (int i = 1; i < 100; i++) {  
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),  R.drawable.ic_launcher);  
            canvas.drawBitmap(bmp, i += 2, i += 2, paint);  
        }  
    } 
}
