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
//		this.setFocusable(true);//只有当该View获得焦点时才会调用onKeyDown方法
	}
//	public AnimationView(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * 动画的每种变换其实内部都是一次矩阵运算。在Android 中，Canvas 类中包含当前矩阵，
	 * 当调用 Canvas.drawBitmap (bmp, x, y, Paint) 绘制时，android 会先把 bmp 做一次矩阵运算，
	 * 然后将运算的结果显示在 Canvas 上，然后不断修改 Canvas 的矩阵并刷新屏幕，View 里的对象就会不停的做图形变换，动画就形成了。
	*/
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);    
        paint.setColor(Color.WHITE);    
        canvas.drawText("Himi", x, 50, paint);//备注1    
        canvas.drawText("方向键↑ 渐变透明度动画效果", 80, this.getHeight() - 80, paint);    
        canvas.drawText("方向键↓ 渐变尺寸伸缩动画效果", 80, this.getHeight() - 60, paint);    
        canvas.drawText("方向键← 画面转换位置移动动画效果", 80, this.getHeight() - 40, paint);    
        canvas.drawText("方向键→ 画面转移旋转动画效果", 80, this.getHeight() - 20, paint);    
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
		 * 动画变化的过程就是i不断地调用onDraw方法进行重绘画布
		 * ****************动画的播放是对整个游戏画布进行的操作****************
		 * Animation.ABSOLUTE 相对位置是屏幕左上角,绝对位置! 
		 * Animation.RELATIVE_TO_SELF 相对位置是自身View;取值为0,是自身左上角,取值为1是自身的右下角; 
		 * Animation.RELATIVE_TO_PARENT 相对父类View的位置
		 */
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP){//渐变透明度动画效果
			/**
			 * 第一个参数fromAlpha 为动画开始时候透明度    
			 * 第二个参数toAlpha 为动画结束时候透明度    
			 * 注意：取值范围[0-1];[完全透明-完全不透明]
			 */
			mAlphaAnimation = new AlphaAnimation(0.0f, 1.0f);
			mAlphaAnimation.setDuration(3000);//设置时间持续时间为3000 毫秒=3秒
			this.startAnimation(mAlphaAnimation);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){//渐变尺寸伸缩动画效果
			/**
			 *第一个参数fromX为动画起始时X坐标上的伸缩尺寸    
            //第二个参数toX为动画结束时X坐标上的伸缩尺寸    
            //第三个参数fromY为动画起始时Y坐标上的伸缩尺寸    
            //第四个参数toY 为动画结束时Y 坐标上的伸缩尺寸    
            //注意：    
            //0.0表示收缩到没有    
            //1.0表示正常无伸缩    
            //值小于1.0表示收缩    
            //值大于1.0表示放大    
            //-----我这里1-4参数表明是起始图像大小不变，动画终止的时候图像被放大1.5倍    
            //第五个参数pivotXType 为动画在X 轴相对于物件位置类型    
            //第六个参数pivotXValue 为动画相对于物件的X 坐标的开始位置    
            //第七个参数pivotXType 为动画在Y 轴相对于物件位置类型    
            //第八个参数pivotYValue 为动画相对于物件的Y 坐标的开始位置    
            //提示：位置类型有三种,每种效果大家自己尝试哈~这里偷下懒~    
            //毕竟亲眼看到效果的区别才记忆深刻~    
            //Animation.ABSOLUTE 、Animation.RELATIVE_TO_SELF、Animation.RELATIVE_TO_PARENT 
			 */
			mScaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, 
					Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
			mScaleAnimation.setDuration(3000);
			this.startAnimation(mScaleAnimation);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){//画面转换位置移动动画效果
			//第一个参数fromXDelta为动画起始时X坐标上的移动位置    
            //第二个参数toXDelta为动画结束时X坐标上的移动位置    
            //第三个参数fromYDelta为动画起始时Y坐标上的移动位置    
            //第四个参数toYDelta 为动画结束时Y 坐标上的移动位置
			mTranslateAnimation = new TranslateAnimation(0f, 100f, 0f, 100f);
			mTranslateAnimation.setDuration(3000);
			this.startAnimation(mTranslateAnimation);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			//第一个参数fromDegrees为动画起始时的旋转角度    
            //第二个参数toDegrees 为动画旋转到的角度    
            //第三个参数pivotXType 为动画在X 轴相对于物件位置类型    
            //第四个参数pivotXValue 为动画相对于物件的X 坐标的开始位置    
            //第五个参数pivotXType 为动画在Y 轴相对于物件位置类型    
            //第六个参数pivotYValue 为动画相对于物件的Y 坐标的开始位置 
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
//		return false;//都能获取到touch事件
		return true;//返回true，而且它在上面获取了焦点，则会拦截touch事件，下层的AnimationSurfaceView不能获取到onTouch事件
//		return super.onTouchEvent(event);//都能获取到touch事件
	}
	
}
