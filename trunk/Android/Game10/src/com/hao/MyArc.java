package com.hao;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 自定义圆形类
 * @author Administrator
 *
 */
public class MyArc {
	private int arc_x, arc_y, arc_r;				//圆形的X,Y坐标和半径  
    private float speed_x = 1.2f, speed_y = 1.2f;	//小球的x、y的速度  
    private float vertical_speed;					//垂直加速度  
    private float horizontal_speed;					//水平加速度,大家自己试着添加吧  
    private final float ACC = 0.135f;				//为了模拟加速度的偏移值  
    private final float RECESSION = 0.2f;			//每次弹起的衰退系数   
    private boolean isDown = true;					//是否处于下落  状态  
    private Random ran;								//随即数库
    private int max = 0;							//最大高度
    
    /**
     * 
     * @param x 小球x坐标
     * @param y 小球y坐标
     * @param r 小球半径
     * @param maxHeight 屏幕区域最大高度
     */
    public MyArc(int x, int y, int r, int maxHeight){
    	this.arc_x = x;
    	this.arc_y = y;
    	this.arc_r = r;
    	this.max = maxHeight;
    	ran = new Random();
    }
    
    /**
     * 绘制圆形
     * @param canvas
     * @param paint
     */
    public void drawArc(Canvas canvas, Paint paint){
    	paint.setColor(getRandomColor());
    	//arc_x-----arc_x+2*arc_r 球的矩形左右边，球的右边就是x坐标加直径, 然后speed_x，speed_y是改变上下位置
    	canvas.drawArc(new RectF(arc_x + speed_x, arc_y + speed_y, arc_x + 2 *  
                arc_r + speed_x, arc_y + 2 * arc_r + speed_y), 
    			0, 360, true, paint);
    }
    
    /**
     * 圆形的运动逻辑
     * v1 = v0 + a*t(v1是最终速度，v0是初速度，a是加速度，t是时间)
     */
    public void arcMoveMethod(){
    	if(isDown){
    		speed_y += vertical_speed;				//圆形的Y轴速度加上加速度  
            int count = (int) vertical_speed++;  	//这里拿另外一个变量记下当前速度偏移量  
            /**
             * 如果下面的for (int i = 0; i < vertical_speed++; i++) {}这样就就死循环了 - -
             * 自由落体的时候，速度是越来越快的，这是受到加速度的影响，
             * 所以这里我们对原有的圆形y速度基础上再加上加速度！
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
    		vertical_speed -= vertical_speed * RECESSION;//每次碰撞都会衰减反弹的加速度
    	}
    }
    
    
    /**  
     * @return  
     * @返回一个随即颜色  
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
     * 判断是否小球到达底部
     * @return
     */
    private boolean isCollision(){
    	return arc_y + 2*arc_r + speed_y > max;
    }
}
