package com.hao;


import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
//import android.hardware.Sensor;
//import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * ����������
 * @author Administrator
 *http://xiaominghimi.blog.51cto.com/2614927/606801
 */
public class SensorView extends SurfaceView implements Callback, Runnable {

	private Thread th = new Thread(this);  
    private SurfaceHolder sfh;  
    private Canvas canvas;  
    private Paint paint;  
//    private SensorManager sm;  
    private SensorManagerSimulator mSensorManager;
    private Sensor sensor;  
    private SensorEventListener mySensorListener;  
    private int arc_x, arc_y;// Բ�ε�x,yλ��  
    private float x = 0, y = 0, z = 0;  
    private boolean flag = true;
    
	public SensorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//1.��ȡSensorManager
//		sm = (SensorManager) context.getSystemService(Service.SENSOR_SERVICE);
		//ģ��ʹ�õ�SensorManagerSimulator
		mSensorManager = SensorManagerSimulator.getSystemService(context, context.SENSOR_SERVICE);
		mSensorManager.connectSimulator();
		//TYPE_ACCELEROMETER    ���ٶȴ�����(����������)���͡�  
        //TYPE_ALL              �����������͵Ĵ�������  
        //TYPE_GYROSCOPE        �����Ǵ���������  
        //TYPE_LIGHT            �⴫��������  
        //TYPE_MAGNETIC_FIELD   �㶨�ų����������͡�  
        //TYPE_ORIENTATION      ���򴫸������͡�  
        //TYPE_PRESSURE         ����һ���㶨��ѹ������������  
        //TYPE_PROXIMITY        ���������ͽӽ�������  
        //TYPE_TEMPERATURE      �¶ȴ������������� 
		sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//�õ�һ������������ʵ��
		mySensorListener = new SensorEventListener() {
			//��������ȡֵ�����ı�ʱ����Ӧ�˺���
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				//��������ȡֵ�����ı䣬�ڴ˴���   
                x = event.values[0]; //�ֻ����򷭹�  
                //x>0 ˵����ǰ�ֻ��� x<0�ҷ�       
                y = event.values[1]; //�ֻ����򷭹�  
                //y>0 ˵����ǰ�ֻ��·� y<0�Ϸ�  
                z = event.values[2]; //��Ļ�ĳ���  
                //z>0 �ֻ���Ļ���� z<0 �ֻ���Ļ����  
                arc_x -= x;//��ע2 ��x>0��С�����ƣ��ʶ�Ҫ�� 
                arc_y += y; //�·�(y>0)��С�����ƣ���Ҫ��y
                /***
                 * ���ﻹҪע���㵱ǰ�ֻ����� ���� ���Ǻ�����Ϊ�����Ӱ�����ǵ�X��Y��ʾ����˼!
                 * �����ǰ�ֻ���������Ļ:
                 * x>0 ˵����ǰ�ֻ��� x<0�ҷ�
                 * y>0 ˵����ǰ�ֻ��·� y<0�Ϸ�
                 * �����ǰ�ֻ��Ǻ�����Ļ:
                 * x>0 ˵����ǰ�ֻ��·� x<0�Ϸ�
                 * y>0 ˵����ǰ�ֻ��ҷ� y<0��
                 */
			}
			//�������ľ��ȷ����ı�ʱ��Ӧ�˺���
			@Override
			public void onAccuracyChanged(
					org.openintents.sensorsimulator.hardware.Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		};
		//��һ�������Ǵ��������������ڶ�������Ҫ�����Ĵ���ʵ��  
        //���һ�������Ǽ����Ĵ������������ͣ� һ��һ��������ʽ  
        //SENSOR_DELAY_NORMAL  ����  
        //SENSOR_DELAY_UI  �ʺϽ���  
        //SENSOR_DELAY_GAME  �ʺ���Ϸ  (���Ǳ���ѡ���ѽ �۹���~)  
        //SENSOR_DELAY_FASTEST  ���
		mSensorManager.registerListener(mySensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = true;
		arc_x = this.getWidth() / 2 - 25;  
        arc_y = this.getHeight() / 2 - 25;  
        th.start(); 
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
		mSensorManager.unregisterListener(mySensorListener);
	}
	
	private void draw(){
		try {  
            canvas = sfh.lockCanvas();  
            if (canvas != null) {  
                canvas.drawColor(Color.BLACK);  
                paint.setColor(Color.RED);  
                canvas.drawArc(new RectF(arc_x, arc_y, arc_x + 50,  
                        arc_y + 50), 0, 360, true, paint);  
                paint.setColor(Color.YELLOW);  
                canvas.drawText("��ǰ������������ֵ:", arc_x - 50, arc_y-30, paint);  
                canvas.drawText("x=" + x + ",y=" + y + ",z=" + z,  
                        arc_x - 50, arc_y, paint);  
                String temp_str = "Himi��ʾ�� ";  
                String temp_str2 = "";  
                String temp_str3 = "";  
                if (x < 1 && x > -1 && y < 1 && y > -1) {  
                    temp_str += "��ǰ�ֻ�����ˮƽ���õ�״̬";  
                    if (z > 0) {  
                        temp_str2 += "������Ļ����";  
                    } else {  
                        temp_str2 += "������Ļ����,��ʾ���������ֻ������۾�����Ӵ~";  
                    }  
                } else {  
                    if (x > 1) {  
                        temp_str2 += "��ǰ�ֻ��������󷭵�״̬";  
                    } else if (x < -1) {  
                        temp_str2 += "��ǰ�ֻ��������ҷ���״̬";  
                    }  
                    if (y > 1) {  
                        temp_str2 += "��ǰ�ֻ��������·���״̬";  
                    } else if (y < -1) {  
                        temp_str2 += "��ǰ�ֻ��������Ϸ���״̬";  
                    }  
                    if (z > 0) {  
                        temp_str3 += "������Ļ����";  
                    } else {  
                        temp_str3 += "������Ļ����,��ʾ���������ֻ������۾�����Ӵ~";  
                    }  
                }  
                paint.setTextSize(20);  
                canvas.drawText(temp_str, 0, 50, paint);  
                canvas.drawText(temp_str2, 0, 80, paint);  
                canvas.drawText(temp_str3, 0, 110, paint);  
            }  
        } catch (Exception e) {  
            Log.v("Himi", "draw is Error!");  
        } finally {  
            sfh.unlockCanvasAndPost(canvas);  
        }  
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
