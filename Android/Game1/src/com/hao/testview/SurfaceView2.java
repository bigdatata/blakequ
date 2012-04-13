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
    private int animation_down[] = { 0, 1, 2 };    
    private int animation_left[] = { 6, 7, 8 };    
    private int animation_right[] = { 9, 10, 11 };    
    private int animation_init[] = animation_down;    
    private int frame_count;    
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
         * 此方法是用来响应按键！如果是自己定义一个继承自View的类,重新实现onKeyDown方法后,
         * 只有当该View获得焦点时才会调用onKeyDown方法,Actvity中的onKeyDown方法是当所有控件
         * 均没有处理该按键事件时,才会调用.
         */
        setFocusable(true);  //备注1  
    }    
    public void surfaceCreated(SurfaceHolder holder) {    
        SH = this.getHeight();    
        SW = this.getWidth();    
        th.start();    
    }    
    
    /**
     * canvas.save();和canvas.restore();是两个相互匹配出现的，作用是用来保存画布的状态和取出保存的状态的。
     * 这里稍微解释一下，当我们对画布进行旋转，缩放，平移等操作的时候其实我们是想对特定的元素进行操作，
     * 比如图片，一个矩形等，但是当你用canvas的方法来进行这些操作的时候，其实是对整个画布进行了操作，
     * 那么之后在画布上的元素都会受到影响，所以我们在操作之前调用canvas.save()来保存画布当前的状态，
     * 当操作之后取出之前保存过的状态，这样就不会对其他的元素进行影响。
     */
    public void draw() {    
        canvas = sfh.lockCanvas();    
        canvas.drawRect(0, 0, SW, SH, p);   //备注2  
        canvas.save();   //备注3  
        canvas.drawText("Himi", bmp_x-2, bmp_y-10, p2); 
        canvas.clipRect(bmp_x, bmp_y, bmp_x + bmp.getWidth()/13, bmp_y+bmp.getHeight());//交集  
        if (animation_init == animation_up) {    
            canvas.drawBitmap(bmp, bmp_x - animation_up[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        } else if (animation_init == animation_down) {    
            canvas.drawBitmap(bmp, bmp_x - animation_down[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        } else if (animation_init == animation_left) {    
            canvas.drawBitmap(bmp, bmp_x - animation_left[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        } else if (animation_init == animation_right) {    
            canvas.drawBitmap(bmp, bmp_x - animation_right[frame_count] * (bmp.getWidth()/13), bmp_y, p);    
        }    
        canvas.restore();  //备注3  
        sfh.unlockCanvasAndPost(canvas);    
    }    
    public void cycle() {    
        if (DOWN) {    
            bmp_y += 5;    
        } else if (UP) {    
            bmp_y -= 5;    
        } else if (LEFT) {    
            bmp_x -= 5;    
        } else if (RIGHT) {    
            bmp_x += 5;    
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
        while (true) {    
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
    }    
    @Override    
    public void surfaceDestroyed(SurfaceHolder holder) {    
        // TODO Auto-generated method stub    
    }    

}
