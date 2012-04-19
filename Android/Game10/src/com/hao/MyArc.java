package com.hao;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * �Զ���Բ����
 * @author Administrator
 *
 */
public class MyArc {
	private int arc_x, arc_y, arc_r;				//Բ�ε�X,Y����Ͱ뾶  
    private float speed_x = 1.2f, speed_y = 1.2f;	//С���x��y���ٶ�  
    private float vertical_speed;					//��ֱ���ٶ�  
    private float horizontal_speed;					//ˮƽ���ٶ�,����Լ�������Ӱ�  
    private final float ACC = 0.135f;				//Ϊ��ģ����ٶȵ�ƫ��ֵ  
    private final float RECESSION = 0.2f;			//ÿ�ε����˥��ϵ��   
    private boolean isDown = true;					//�Ƿ�������  ״̬  
    private Random ran;								//�漴����
    private int max = 0;							//���߶�
    
    /**
     * 
     * @param x С��x����
     * @param y С��y����
     * @param r С��뾶
     * @param maxHeight ��Ļ�������߶�
     */
    public MyArc(int x, int y, int r, int maxHeight){
    	this.arc_x = x;
    	this.arc_y = y;
    	this.arc_r = r;
    	this.max = maxHeight;
    	ran = new Random();
    }
    
    /**
     * ����Բ��
     * @param canvas
     * @param paint
     */
    public void drawArc(Canvas canvas, Paint paint){
    	paint.setColor(getRandomColor());
    	//arc_x-----arc_x+2*arc_r ��ľ������ұߣ�����ұ߾���x�����ֱ��, Ȼ��speed_x��speed_y�Ǹı�����λ��
    	canvas.drawArc(new RectF(arc_x + speed_x, arc_y + speed_y, arc_x + 2 *  
                arc_r + speed_x, arc_y + 2 * arc_r + speed_y), 
    			0, 360, true, paint);
    }
    
    /**
     * Բ�ε��˶��߼�
     * v1 = v0 + a*t(v1�������ٶȣ�v0�ǳ��ٶȣ�a�Ǽ��ٶȣ�t��ʱ��)
     */
    public void arcMoveMethod(){
    	if(isDown){
    		speed_y += vertical_speed;				//Բ�ε�Y���ٶȼ��ϼ��ٶ�  
            int count = (int) vertical_speed++;  	//����������һ���������µ�ǰ�ٶ�ƫ����  
            /**
             * ��������for (int i = 0; i < vertical_speed++; i++) {}�����;���ѭ���� - -
             * ���������ʱ���ٶ���Խ��Խ��ģ������ܵ����ٶȵ�Ӱ�죬
             * �����������Ƕ�ԭ�е�Բ��y�ٶȻ������ټ��ϼ��ٶȣ�
             */
            for (int i = 0; i < count; i++) {
            	vertical_speed += ACC;  
            }  
    	}else{
    		speed_y -= vertical_speed;  
            int count = (int) vertical_speed--;  
            for (int i = 0; i < count; i++) {  
                vertical_speed -= ACC;  
            }
    	}
    	
    	if(isCollision()){
    		isDown = !isDown;
    		vertical_speed -= vertical_speed * RECESSION;//ÿ����ײ����˥�������ļ��ٶ�
    	}
    }
    
    
    /**  
     * @return  
     * @����һ���漴��ɫ  
     */  
    private int getRandomColor() {  
        int ran_color = ran.nextInt(8);  
        int temp_color = 0;  
        switch (ran_color) {  
	        case 0:  
	            temp_color = Color.WHITE;  
	            break;  
	        case 1:  
	            temp_color = Color.BLUE;  
	            break;  
	        case 2:  
	            temp_color = Color.CYAN;  
	            break;  
	        case 3:  
	            temp_color = Color.DKGRAY;  
	            break;  
	        case 4:  
	            temp_color = Color.RED;  
	            break;  
	        case 6:  
	            temp_color = Color.GREEN;  
	        case 7:  
	            temp_color = Color.GRAY;  
	        case 8:  
	            temp_color = Color.YELLOW;  
	            break;  
        }  
        return temp_color;  
    }  
    
    /**
     * �ж��Ƿ�С�򵽴�ײ�
     * @return
     */
    private boolean isCollision(){
    	return arc_y + 2*arc_r + speed_y > max;
    }
}
