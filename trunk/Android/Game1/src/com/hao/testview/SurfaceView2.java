package com.hao.testview;

import com.hao.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class SurfaceView2 extends SurfaceView implements Callback ,Runnable{

	private Thread th = new Thread(this);    
    private SurfaceHolder sfh;    
    private int SH, SW;    
    private Canvas canvas;    
    private Paint p;    
    private Paint p2;    
    private Resources res;    
    private Bitmap bmp;    
    private int bmp_x = 100, bmp_y = 100;    
    private boolean UP, DOWN, LEFT, RIGHT;    
    private int animation_up[] = { 3, 4, 5 };    
    private int animation_down[] = { 0, 1, 2 };//��1,2,3��ͼ���������ߵ�    
    private int animation_left[] = { 6, 7, 8 };    
    private int animation_right[] = { 9, 10, 11 };    
    private int animation_init[] = animation_down;    
    private int frame_count;    
    private boolean flag = true;
    public SurfaceView2(Context context) {    
        super(context);    
        this.setKeepScreenOn(true);    
        res = this.getResources();    
        bmp = BitmapFactory.decodeResource(res, R.drawable.enemy1);    
        sfh = this.getHolder();    
        sfh.addCallback(this);    
        p = new Paint();    
        p.setColor(Color.YELLOW);    
        p2 = new Paint();    
        p2.setColor(Color.RED);    
        p.setAntiAlias(true);    
        /**
         * �˷�����������Ӧ������������Լ�����һ���̳���View����,����ʵ��onKeyDown������,
         * ֻ�е���View��ý���ʱ�Ż����onKeyDown����,Actvity�е�onKeyDown�����ǵ����пؼ�
         * ��û�д���ð����¼�ʱ,�Ż����.
         */
        setFocusable(true);  //��ע1  
    }    
    public void surfaceCreated(SurfaceHolder holder) {   
    	flag = true;
        SH = this.getHeight();    
        SW = this.getWidth();    
        th.start();    
    }    
    
    /**
     * canvas.save();��canvas.restore();�������໥ƥ����ֵģ��������������滭����״̬��ȡ�������״̬�ġ�
     * ������΢����һ�£������ǶԻ���������ת�����ţ�ƽ�ƵȲ�����ʱ����ʵ����������ض���Ԫ�ؽ��в�����
     * ����ͼƬ��һ�����εȣ����ǵ�����canvas�ķ�����������Щ������ʱ����ʵ�Ƕ��������������˲�����
     * ��ô֮���ڻ����ϵ�Ԫ�ض����ܵ�Ӱ�죬���������ڲ���֮ǰ����canvas.save()�����滭����ǰ��״̬��
     * ������֮��ȡ��֮ǰ�������״̬�������Ͳ����������Ԫ�ؽ���Ӱ�졣
     */
    public void draw() {    
        canvas = sfh.lockCanvas();    
        //ÿ�α���Ҫ���»��ƻ���
        canvas.drawRect(0, 0, SW, SH, p);   //��ע2  
        canvas.save();   //��ע3  
        canvas.drawText("Himi", bmp_x-2, bmp_y-10, p2); 
        canvas.clipRect(bmp_x, bmp_y, bmp_x + bmp.getWidth()/13, bmp_y+bmp.getHeight());//�и���������� 
        //����������������л�ͼ
        if (animation_init == animation_up) {
        	//ע��left��top��ָbmpͼƬ�ϵ�λ�ã������ǻ���
            canvas.drawBitmap(bmp, bmp_x - animation_up[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        } else if (animation_init == animation_down) {    
            canvas.drawBitmap(bmp, bmp_x - animation_down[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        } else if (animation_init == animation_left) {    
            canvas.drawBitmap(bmp, bmp_x - animation_left[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        } else if (animation_init == animation_right) {    
            canvas.drawBitmap(bmp, bmp_x - animation_right[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        }    
        canvas.restore();  //��ע3  
        sfh.unlockCanvasAndPost(canvas);    
    }    
    
    /**
     * ��Ҫ�ж��Ƿ��߳��߽�
     */
    public void cycle() {    
        if (DOWN) {    
        	if(bmp_y<SH)
        		bmp_y += 5;
        	else
        		bmp_y = 0;
        } else if (UP) { 
        	if(bmp_y>0)
        		bmp_y -= 5;
        	else
        		bmp_y = SH;
        } else if (LEFT) { 
        	if(bmp_x>0)
        		bmp_x -= 5;    
        	else
        		bmp_x = SW;
        } else if (RIGHT) {
        	if(bmp_x<SW)
        		bmp_x += 5;    
        	else
        		bmp_x = 0;
        }    
        if (DOWN || UP || LEFT || RIGHT) {    
            if (frame_count < 2) {    
                frame_count++;    
            } else {    
                frame_count = 0;    
            }    
        }    
        if (DOWN == false && UP == false && LEFT == false && RIGHT == false) {    
            frame_count = 0;    
        }    
    }    
    
    @Override    
    public boolean onKeyDown(int key, KeyEvent event) {    
        if (key == KeyEvent.KEYCODE_DPAD_UP) {    
            if (UP == false) {    
                animation_init = animation_up;    
            }    
            UP = true;    
        } else if (key == KeyEvent.KEYCODE_DPAD_DOWN) {    
            if (DOWN == false) {    
                animation_init = animation_down;    
            }    
            DOWN = true;    
        } else if (key == KeyEvent.KEYCODE_DPAD_LEFT) {    
            if (LEFT == false) {    
                animation_init = animation_left;    
            }    
            LEFT = true;    
        } else if (key == KeyEvent.KEYCODE_DPAD_RIGHT) {    
            if (RIGHT == false) {    
                animation_init = animation_right;    
            }    
            RIGHT = true;    
        }    
        return super.onKeyDown(key, event);    
    }    
    /* (non-Javadoc)   
     * @see android.view.View#onKeyUp(int, android.view.KeyEvent)   
     */    
    @Override    
    public boolean onKeyUp(int keyCode, KeyEvent event) {    
        if (DOWN) {    
            DOWN = false;    
        } else if (UP) {    
            UP = false;    
        } else if (LEFT) {    
            LEFT = false;    
        } else if (RIGHT) {    
            RIGHT = false;    
        }    
        return super.onKeyUp(keyCode, event);    
    }    
    @Override    
    public void run() {    
        // TODO Auto-generated method stub    
        while (flag) {    
            draw();    
            cycle();    
            try {    
                Thread.sleep(100);    
            } catch (Exception ex) {    
            }    
        }    
    }    
    @Override    
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {    
        // TODO Auto-generated method stub 
    	flag = true;
    }    
    @Override    
    public void surfaceDestroyed(SurfaceHolder holder) {    
        // TODO Auto-generated method stub    
    	flag = false;
    }    

}
