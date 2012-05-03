/*
 * 文 件 名:  MyView2.java
 * 版    权:  LeeStorm
 * 描    述:  <描述>
 * 修 改 人:  LeeStorm
 * 修改时间:  2012-1-12
 */
package com.leestorm.example;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

class MyView2 extends SurfaceView implements Runnable, SurfaceHolder.Callback
{
    
    private SurfaceHolder surfaceHolder;
    
    private boolean threadRun=true;
    
    /**
     * BOX2D物理世界
     */
    private World world;
    
    private ArrayList<BoxObject> boxList = new ArrayList<BoxObject>();
    
    private byte[] lock = new byte[0];
    
    public MyView2(Context context)
    {
        super(context);
        
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        this.setFocusable(true);
        
        initWorld();
    }
    
    private void initWorld()
    {
        //重力
        world = new World(new Vector2(0, 9.8f), true);
        //边界
        world.QueryAABB(null, -100, -100, 100, 100);
        createBox(160, 460, 160, 10, true);
    }
    
    public void createBox(float pixelX, float pixelY, float pixelHalfWidth,
            float pixelHalfHeight, boolean isStatic)
    {
        synchronized (lock)
        {
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(pixelX, pixelY);
            bodyDef.allowSleep = true;//允许休眠
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.friction = 0.8f;
            fixtureDef.restitution = 0.3f;
            fixtureDef.density = 2.0f;//密度
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(pixelHalfWidth, pixelHalfHeight);
            fixtureDef.shape = shape;
            
            if (isStatic)
            {
                bodyDef.type = BodyDef.BodyType.StaticBody;
            }
            else
            {
                bodyDef.type = BodyDef.BodyType.DynamicBody;
            }
            
            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            boxList.add(new BoxObject(body, pixelHalfWidth, pixelHalfHeight));
        }
    }
    
    public void surfaceCreated(SurfaceHolder holder)
    {
        new Thread(this).start();
        
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {
        
    }
    
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            threadRun = false;
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            createBox(event.getX(), event.getY(), 10, 10, false);
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            
        }
        
        return super.onTouchEvent(event);
    }
    
    private void doDraw(Canvas canvas)
    {
        super.draw(canvas);
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, 320, 480, paint);
        paint.setColor(Color.BLACK);
        for (BoxObject box : boxList)
        {
            canvas.drawRect(box.getPixelX() - box.getPixelHalfWidth(),
                    box.getPixelY() - box.getPixelHalfHeight(),
                    box.getPixelX() + box.getPixelHalfWidth(),
                    box.getPixelY() + box.getPixelHalfHeight(),
                    paint);
        }
        
    }
    
    public void run()
    {
        long timeStart = 0;
        long timeEnd = 0;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        
        while (threadRun)
        {
            timeStart = System.currentTimeMillis();
            Canvas canvas = surfaceHolder.lockCanvas();
            
            if (canvas == null)
            {
                break;
            }
            
            synchronized (lock)
            {
                world.step(1.0f / 15.0f, 4, 4);
                
                doDraw(canvas);
            }
            
            timeEnd = System.currentTimeMillis();
            long fps = 1000 / (timeEnd - timeStart);
            canvas.drawText(String.valueOf(fps), 10, 10, paint);
            
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
        
    }
    
    public class BoxObject
    {
        
        private Body body;
        
        private float pixelHalfWidth;
        
        private float pixelHalfHeight;
        
        public float getPixelX()
        {
            return body.getPosition().x;
        }
        
        public float getPixelY()
        {
            return body.getPosition().y;
        }
        
        public float getPixelHalfWidth()
        {
            return pixelHalfWidth;
        }
        
        public float getPixelHalfHeight()
        {
            return pixelHalfHeight;
        }
        
        public BoxObject(Body body, float halfWidth, float halfHeight)
        {
            super();
            this.body = body;
            this.pixelHalfWidth = halfWidth;
            this.pixelHalfHeight = halfHeight;
        }
        
    }
}
