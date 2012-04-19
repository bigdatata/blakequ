package com.hao.view;

import com.hao.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationView extends View {
	public static AnimationView view;
	private Paint paint;    
    private Bitmap bmp;    
    private int x = 50;    
    private Animation mAlphaAnimation;    
    private Animation mScaleAnimation; 
    private Animation mTranslateAnimation;    
    private Animation mRotateAnimation;
	public AnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		view = this;
		paint = new Paint();
		paint.setAntiAlias(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//		this.setFocusable(true);//ֻ�е���View��ý���ʱ�Ż����onKeyDown����
	}
//	public AnimationView(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * ������ÿ�ֱ任��ʵ�ڲ�����һ�ξ������㡣��Android �У�Canvas ���а�����ǰ����
	 * ������ Canvas.drawBitmap (bmp, x, y, Paint) ����ʱ��android ���Ȱ� bmp ��һ�ξ������㣬
	 * Ȼ������Ľ����ʾ�� Canvas �ϣ�Ȼ�󲻶��޸� Canvas �ľ���ˢ����Ļ��View ��Ķ���ͻ᲻ͣ����ͼ�α任���������γ��ˡ�
	*/
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);    
        paint.setColor(Color.WHITE);    
        canvas.drawText("Himi", x, 50, paint);//��ע1    
        canvas.drawText("������� ����͸���ȶ���Ч��", 80, this.getHeight() - 80, paint);    
        canvas.drawText("������� ����ߴ���������Ч��", 80, this.getHeight() - 60, paint);    
        canvas.drawText("������� ����ת��λ���ƶ�����Ч��", 80, this.getHeight() - 40, paint);    
        canvas.drawText("������� ����ת����ת����Ч��", 80, this.getHeight() - 20, paint);    
        canvas.drawBitmap(bmp, this.getWidth() / 2 - bmp.getWidth() / 2,     
                this.getHeight() / 2 - bmp.getHeight() / 2, paint);    
        x += 1;   
        if(x>this.getWidth()) x = 50;
        System.out.println("----------------AnimationView onDraw-----------------:"+x);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.v("AnimationView", "onKeyDown");
		/**
		 * �����仯�Ĺ��̾���i���ϵص���onDraw���������ػ滭��
		 * ****************�����Ĳ����Ƕ�������Ϸ�������еĲ���****************
		 * Animation.ABSOLUTE ���λ������Ļ���Ͻ�,����λ��! 
		 * Animation.RELATIVE_TO_SELF ���λ��������View;ȡֵΪ0,���������Ͻ�,ȡֵΪ1����������½�; 
		 * Animation.RELATIVE_TO_PARENT ��Ը���View��λ��
		 */
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP){//����͸���ȶ���Ч��
			/**
			 * ��һ������fromAlpha Ϊ������ʼʱ��͸����    
			 * �ڶ�������toAlpha Ϊ��������ʱ��͸����    
			 * ע�⣺ȡֵ��Χ[0-1];[��ȫ͸��-��ȫ��͸��]
			 */
			mAlphaAnimation = new AlphaAnimation(0.0f, 1.0f);
			mAlphaAnimation.setDuration(3000);//����ʱ�����ʱ��Ϊ3000 ����=3��
			this.startAnimation(mAlphaAnimation);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){//����ߴ���������Ч��
			/**
			 *��һ������fromXΪ������ʼʱX�����ϵ������ߴ�    
            //�ڶ�������toXΪ��������ʱX�����ϵ������ߴ�    
            //����������fromYΪ������ʼʱY�����ϵ������ߴ�    
            //���ĸ�����toY Ϊ��������ʱY �����ϵ������ߴ�    
            //ע�⣺    
            //0.0��ʾ������û��    
            //1.0��ʾ����������    
            //ֵС��1.0��ʾ����    
            //ֵ����1.0��ʾ�Ŵ�    
            //-----������1-4������������ʼͼ���С���䣬������ֹ��ʱ��ͼ�񱻷Ŵ�1.5��    
            //���������pivotXType Ϊ������X ����������λ������    
            //����������pivotXValue Ϊ��������������X ����Ŀ�ʼλ��    
            //���߸�����pivotXType Ϊ������Y ����������λ������    
            //�ڰ˸�����pivotYValue Ϊ��������������Y ����Ŀ�ʼλ��    
            //��ʾ��λ������������,ÿ��Ч������Լ����Թ�~����͵����~    
            //�Ͼ����ۿ���Ч��������ż������~    
            //Animation.ABSOLUTE ��Animation.RELATIVE_TO_SELF��Animation.RELATIVE_TO_PARENT 
			 */
			mScaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, 
					Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
			mScaleAnimation.setDuration(3000);
			this.startAnimation(mScaleAnimation);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){//����ת��λ���ƶ�����Ч��
			//��һ������fromXDeltaΪ������ʼʱX�����ϵ��ƶ�λ��    
            //�ڶ�������toXDeltaΪ��������ʱX�����ϵ��ƶ�λ��    
            //����������fromYDeltaΪ������ʼʱY�����ϵ��ƶ�λ��    
            //���ĸ�����toYDelta Ϊ��������ʱY �����ϵ��ƶ�λ��
			mTranslateAnimation = new TranslateAnimation(0f, 100f, 0f, 100f);
			mTranslateAnimation.setDuration(3000);
			this.startAnimation(mTranslateAnimation);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			//��һ������fromDegreesΪ������ʼʱ����ת�Ƕ�    
            //�ڶ�������toDegrees Ϊ������ת���ĽǶ�    
            //����������pivotXType Ϊ������X ����������λ������    
            //���ĸ�����pivotXValue Ϊ��������������X ����Ŀ�ʼλ��    
            //���������pivotXType Ϊ������Y ����������λ������    
            //����������pivotYValue Ϊ��������������Y ����Ŀ�ʼλ�� 
			mRotateAnimation = new RotateAnimation(0f, 360f, 
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			mRotateAnimation.setDuration(3000);
			this.startAnimation(mRotateAnimation);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("AnimationView", "onTouchEvent");
//		return false;//���ܻ�ȡ��touch�¼�
		return true;//����true���������������ȡ�˽��㣬�������touch�¼����²��AnimationSurfaceView���ܻ�ȡ��onTouch�¼�
//		return super.onTouchEvent(event);//���ܻ�ȡ��touch�¼�
	}
	
}
