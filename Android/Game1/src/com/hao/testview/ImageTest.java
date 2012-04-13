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
     * ����Ҳ��Ҫ��ȷ���㣺
     * 1.Clip �Ƕ� canvas �����ã����ǻ��� canvas �����ͼƬ��
     * 2.��һ���Ľ��� Clip �Ƕ� clip ֮��Ļ��� canvas �ϵ�ͼ�����Ӱ�죬
     * ���� clip ֮ǰ��ͼ��û���κ�Ӱ�죬��Ȼ������ָ���� clip ֮��û���� canvas �ϻ��κζ���
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
        
        // ���ñ���ɫ
        canvas.drawColor(Color.CYAN);//����ɫ
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                mOp = Op.DIFFERENCE;//֮ǰ���й���ȥ��ǰҪ���е�����
                break;
            case KeyEvent.KEYCODE_1:
                mOp = Op.INTERSECT;//��ǰҪ���е�������֮ǰ���й��ڲ��Ĳ���(�������෴,����֮��Ĳ���)
                break;
            case KeyEvent.KEYCODE_2:
                mOp = Op.REPLACE;//�õ�ǰҪ���е��������֮ǰ���й�������
                break;
            case KeyEvent.KEYCODE_3:
                mOp = Op.REVERSE_DIFFERENCE;//�� DIFFERENCE �෴���Ե�ǰҪ���е�����Ϊ�������ǰҪ���е������ȥ֮ǰ���й�������
                break;
            case KeyEvent.KEYCODE_4:
                mOp = Op.UNION;//��ǰҪ���е��������֮ǰ���й��ڲ��Ĳ���
                break;
            case KeyEvent.KEYCODE_5:
                mOp = Op.XOR;//��򣬵�ǰҪ���е�������֮ǰ���й��Ľ������
                break;
            default: //��� Op ����Ϊ�գ��� INTERSECT ��Ч��һ������������Ľ���
                mOp = Op.DIFFERENCE;
                break;
        }
        invalidate();
        return super.onKeyDown(keyCode, event);
    }
}

