package com.yarin.android.GameEngine.Screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Graphics
{
	public static void fillRect(Canvas g, Rect rect, Paint paint)
	{
		paint.setStyle(Paint.Style.FILL);
		g.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
	}


	public static void drawRect(Canvas g, Rect rect, Paint paint)
	{
		paint.setStyle(Paint.Style.STROKE);
		g.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
	}


	public static void SETAEERECT(Rect rect, int x, int y, int w, int h)
	{
		rect.left = x;
		rect.top = y;
		rect.right = x + w;
		rect.bottom = y + h;
	}


	public static void fillRect(Canvas g, int x, int y, int w, int h, Paint paint)
	{
		paint.setStyle(Paint.Style.FILL);
		g.drawRect(x, y, x + w, y + h, paint);
	}


	public static void drawRect(Canvas g, int x, int y, int w, int h, Paint paint)
	{
		paint.setStyle(Paint.Style.STROKE);
		g.drawRect(x, y, x + w, y + h, paint);
	}


	/*------------------------------------
	 * ����ͼƬ
	 *
	 * @param		x ��Ļ�ϵ�x����	
	 * @param		y ��Ļ�ϵ�y����
	 * @param		w Ҫ���Ƶ�ͼƬ�Ŀ��	
	 * @param		h Ҫ���Ƶ�ͼƬ�ĸ߶�
	 * @param		bxͼƬ�ϵ�x����
	 * @param		byͼƬ�ϵ�y����
	 *
	 * @return		null
	 ------------------------------------*/
	public static void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w, int h, int bx, int by)
	{
		Rect src = new Rect();// ͼƬ
		Rect dst = new Rect();// ��Ļ
		src.left = bx;
		src.top = by;
		src.right = bx + w;
		src.bottom = by + h;
		dst.left = x;
		dst.top = y;
		dst.right = x + w;
		dst.bottom = y + h;
		canvas.drawBitmap(blt, src, dst, null);

		src = null;
		dst = null;
	}

	public static void drawImage(Canvas canvas, Bitmap bitmap, int x, int y)
	{
		canvas.drawBitmap(bitmap, x, y, null);
	}
}

