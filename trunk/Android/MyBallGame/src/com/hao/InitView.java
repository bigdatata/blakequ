package com.hao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class InitView extends View {

	private Paint paint;
	private Bitmap bmp;
	
	public InitView(Context context) {
		super(context);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setTextSize(20);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bmp, 0, 0, paint);
		canvas.drawText("正在加载资源...", getWidth()/3, getHeight()/2, paint);
	}
	
	

}
