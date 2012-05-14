package com.hao.layout;

import com.hao.R;
import com.hao.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/** 
 * ������Զ����TextView. 
 * ������Ҫ���ع��췽����onDraw���� 
 * �����Զ����View���û���Լ����ص����ԣ�����ֱ����xml�ļ���ʹ�þͿ����� 
 * ��������Լ����ص����ԣ���ô����Ҫ�ڹ��캯���л�ȡ�����ļ�attrs.xml���Զ������Ե����� 
 * ��������Ҫ�趨Ĭ��ֵ��������xml�ļ���û�ж��塣 
 * ���ʹ���Զ������ԣ���ô��Ӧ��xml�ļ�����Ҫ�����µ�schemas�� 
 * ����������xmlns:my="http://schemas.android.com/apk/res/demo.view.my" 
 * ����xmlns��ġ�my�����Զ�������Ե�ǰ׺��res����������Զ���View���ڵİ� 
 * @author Administrator 
 * 
 */  
public class MyView extends View {  
      
    Paint mPaint; //����,�����˻�����ͼ�Ρ��ı��ȵ���ʽ����ɫ��Ϣ  
    Matrix mMatrix = new Matrix();
    private  Bitmap mBitmap;
    public MyView(Context context) {  
        super(context);  
          
    }  
      
    public MyView(Context context, AttributeSet attrs){  
        super(context, attrs);  
        mPaint = new Paint();  
        //TypedArray��һ�����������context.obtainStyledAttributes��õ����Ե�����  
        //��ʹ����ɺ�һ��Ҫ����recycle����  
        //���Ե�������styleable�е�����+��_��+��������  
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);  
        int textColor = array.getColor(R.styleable.MyView_textColor, 0XFF00FF00); //�ṩĬ��ֵ������δָ��  
        float textSize = array.getDimension(R.styleable.MyView_textSize, 36);  
        mPaint.setColor(textColor);  
        mPaint.setTextSize(textSize);  
          
        initialize();
        array.recycle(); //һ��Ҫ���ã�������ε��趨����´ε�ʹ�����Ӱ��  
    }  
      
    public void onDraw(Canvas canvas){  
        super.onDraw(canvas);  
        //Canvas�к��кܶ໭ͼ�Ľӿڣ�������Щ�ӿڣ����ǿ��Ի���������Ҫ��ͼ��  
        //mPaint = new Paint();  
        //mPaint.setColor(Color.RED);  
        mPaint.setStyle(Style.FILL); //�������  
        canvas.drawRect(10, 10, 100, 100, mPaint); //���ƾ���  
          
        mPaint.setColor(Color.BLUE);  
        canvas.drawText("���Ǳ���������", 10, 120, mPaint);  
        canvas.drawBitmap(mBitmap, mMatrix, null );
//        mMatrix.postRotate(30);
//        mMatrix.postTranslate(100f, 100f);
    }  
    
    /**
     * http://blog.csdn.net/chenjie19891104/article/details/6315837
     * ������
     */
	private void initialize() {
		mBitmap = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.sample_4)).getBitmap();
		float cosValue = (float) Math.cos(-Math.PI / 6);
		float sinValue = (float) Math.sin(-Math.PI / 6);
		mMatrix.setValues(
		new float[] {
		cosValue, -sinValue, 100,
		sinValue, cosValue, 200,
		0, 0, 2 });

	}  
}  
