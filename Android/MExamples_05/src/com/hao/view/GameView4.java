package com.hao.view;

import com.hao.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * ������ʾ
 * @author Administrator
 *
 */
public class GameView4 extends View
{
	/* ����Alpha���� */
	private Animation	mAnimationAlpha		= null;
	
	/* ����Scale���� */
	private Animation	mAnimationScale		= null;
	
	/* ����Translate���� */
	private Animation	mAnimationTranslate	= null;
	
	/* ����Rotate���� */
	private Animation	mAnimationRotate	= null;
	
	/* ����Bitmap���� */
	Bitmap				mBitQQ				= null;
	
	Context mContext = null;
	public GameView4(Context context)
	{
		super(context);
		
		mContext = context;
		
		/* װ����Դ */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* ����ͼƬ */
		canvas.drawBitmap(mBitQQ, 0, 0, null);
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch ( keyCode )
		{
		case KeyEvent.KEYCODE_DPAD_UP:
			/* װ�ض������� */
			mAnimationAlpha = AnimationUtils.loadAnimation(mContext,R.anim.alpha_animation);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationAlpha);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			/* װ�ض������� */
			mAnimationScale = AnimationUtils.loadAnimation(mContext,R.anim.scale_animation);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationScale);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			/* װ�ض������� */
			mAnimationTranslate = AnimationUtils.loadAnimation(mContext,R.anim.translate_animation);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationTranslate);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			/* װ�ض������� */
			mAnimationRotate = AnimationUtils.loadAnimation(mContext,R.anim.rotate_animation);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationRotate);
			break;
		}
		return true;
	}
}

