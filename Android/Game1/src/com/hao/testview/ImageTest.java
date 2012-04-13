package com.hao.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.view.KeyEvent;
import android.widget.ImageView;

public class ImageTest extends ImageView
{

    private Paint mPaint1;
    private Paint mPaint2;
    private Rect rectBlue;
    
    private Op mOp = Op.DIFFERENCE;
    private Rect rectGreen;
    private Rect rectX;

    public ImageTest(Context context)
    {
        super(context);
        init();
    }
    
    private void init()
    {
        mPaint1 = new Paint();
        mPaint1.setColor(Color.GREEN);
        
        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLUE);
        mPaint2.setAlpha(100);
        
        rectGreen = new Rect(40, 40, 140, 140);
        rectBlue = new Rect(100, 100, 200, 200);
        rectX = new Rect(40, 40, 200, 200);
    }

    /**
     * 首先也需要明确几点：
     * 1.Clip 是对 canvas 起作用，而非画在 canvas 上面的图片。
     * 2.进一步的讲， Clip 是对 clip 之后的画在 canvas 上的图像具有影响，
     * 而对 clip 之前的图像没有任何影响，当然，这是指你在 clip 之后没有在 canvas 上画任何东西
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.GRAY);
        
//        canvas.save();
        canvas.clipRect(rectBlue, mOp);
        canvas.clipRect(rectGreen, mOp);
        canvas.clipRect(rectX, mOp);
//        canvas.restore();
        
        canvas.drawRect(rectGreen, mPaint1);
        canvas.drawRect(rectBlue, mPaint2);
        
        // 重置背景色
        canvas.drawColor(Color.CYAN);//蓝绿色
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                mOp = Op.DIFFERENCE;//之前剪切过除去当前要剪切的区域
                break;
            case KeyEvent.KEYCODE_1:
                mOp = Op.INTERSECT;//当前要剪切的区域在之前剪切过内部的部分(与上面相反,剪切之外的部分)
                break;
            case KeyEvent.KEYCODE_2:
                mOp = Op.REPLACE;//用当前要剪切的区域代替之前剪切过的区域
                break;
            case KeyEvent.KEYCODE_3:
                mOp = Op.REVERSE_DIFFERENCE;//与 DIFFERENCE 相反，以当前要剪切的区域为参照物，当前要剪切的区域除去之前剪切过的区域
                break;
            case KeyEvent.KEYCODE_4:
                mOp = Op.UNION;//当前要剪切的区域加上之前剪切过内部的部分
                break;
            case KeyEvent.KEYCODE_5:
                mOp = Op.XOR;//异或，当前要剪切的区域与之前剪切过的进行异或
                break;
            default: //如果 Op 参数为空，与 INTERSECT 的效果一样，两个区域的交集
                mOp = Op.DIFFERENCE;
                break;
        }
        invalidate();
        return super.onKeyDown(keyCode, event);
    }
}

