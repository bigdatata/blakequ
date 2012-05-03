///*
// * 文 件 名:  MyView.java
// * 版    权:  LeeStorm
// * 描    述:  <描述>
// * 修 改 人:  LeeStorm 
// * 修改时间:  2012-1-12
// */
//package com.leestorm.example;
//
//import java.util.ArrayList;
//
//import org.jbox2d.collision.AABB;
//import org.jbox2d.collision.PolygonDef;
//import org.jbox2d.common.Vec2;
//import org.jbox2d.dynamics.Body;
//import org.jbox2d.dynamics.BodyDef;
//import org.jbox2d.dynamics.World;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PaintFlagsDrawFilter;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//class MyView extends SurfaceView implements Runnable, SurfaceHolder.Callback
//{
//    
//    private final static int RATE = 10;//屏幕到现实世界的比例 10px：1m;
//    
//    private boolean threadRun = true;
//    
//    private SurfaceHolder surfaceHolder;
//    
//    /**
//     * BOX2D物理世界
//     */
//    private World world;
//    
//    private ArrayList<BoxObject> boxList = new ArrayList<BoxObject>();
//    
//    private byte[] lock = new byte[0];
//    
//    public MyView(Context context)
//    {
//        super(context);
//        
//        surfaceHolder = this.getHolder();
//        surfaceHolder.addCallback(this);
//        this.setFocusable(true);
//        
//        initWorld();
//    }
//    
//    private void initWorld()
//    {
//        AABB aabb = new AABB();
//        aabb.lowerBound = new Vec2(-100f, -100f);
//        aabb.upperBound = new Vec2(100f, 100f);
//        world = new World(aabb, new Vec2(0f, 9.8f), true);
//        createBox(160, 460, 160, 10, true);
//    }
//    
//    public void createBox(float pixelX, float pixelY, float pixelHalfWidth, float pixelHalfHeight,
//            boolean isStatic)
//    {
//        synchronized (lock)
//        {
//            
//            PolygonDef shape = new PolygonDef();
//            if (isStatic)
//            {
//                shape.density = 0;
//            }
//            else
//            {
//                shape.density = 2.0f;
//            }
//            shape.friction = 0.8f;
//            shape.restitution = 0.3f;
//            shape.setAsBox(pixelHalfWidth / RATE, pixelHalfHeight / RATE);
//            
//            BodyDef bodyDef = new BodyDef();
//            bodyDef.position.set(pixelX / RATE, pixelY / RATE);
//            Body body1 = world.createBody(bodyDef);
//            body1.createShape(shape);
//            body1.setMassFromShapes();
//            boxList.add(new BoxObject(body1, pixelHalfWidth, pixelHalfHeight));
//        }
//    }
//    
//    public void surfaceCreated(SurfaceHolder holder)
//    {
//        new Thread(this).start();
//        
//    }
//    
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
//    {
//        
//    }
//    
//    public void surfaceDestroyed(SurfaceHolder holder)
//    {
//        
//    }
//    
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            threadRun = false;
//            android.os.Process.killProcess(android.os.Process.myPid());
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//    
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        if (event.getAction() == MotionEvent.ACTION_DOWN)
//        {
//            createBox(event.getX(), event.getY(), 10, 10, false);
//        }
//        else if (event.getAction() == MotionEvent.ACTION_MOVE)
//        {
//        }
//        else if (event.getAction() == MotionEvent.ACTION_UP)
//        {
//            
//        }
//        
//        return super.onTouchEvent(event);
//    }
//    
//    private void doDraw(Canvas canvas)
//    {
//        super.draw(canvas);
//        //抗锯齿
//        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
//                | Paint.FILTER_BITMAP_FLAG));
//        
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        canvas.drawRect(0, 0, 320, 480, paint);
//        paint.setColor(Color.BLACK);
//        for (BoxObject box : boxList)
//        {
//            canvas.drawRect(box.getPixelX() - box.getPixelHalfWidth(),
//                    box.getPixelY() - box.getPixelHalfHeight(),
//                    box.getPixelX() + box.getPixelHalfWidth(),
//                    box.getPixelY() + box.getPixelHalfHeight(),
//                    paint);
//        }
//        
//    }
//    
//    public void run()
//    {
//        long timeStart = 0;
//        long timeEnd = 0;
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        
//        while (threadRun)
//        {
//            timeStart = System.currentTimeMillis();
//            Canvas canvas = surfaceHolder.lockCanvas();
//            
//            if (canvas == null)
//            {
//                break;
//            }
//            
//            synchronized (lock)
//            {
//                world.step(1.0f / 15.0f, 4);
//                
//                doDraw(canvas);
//            }
//            
//            timeEnd = System.currentTimeMillis();
//            long fps = 1000 / (timeEnd - timeStart);
//            canvas.drawText(String.valueOf(fps), 10, 10, paint);
//            
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }
//        
//    }
//    
//    public class BoxObject
//    {
//        
//        private Body body;
//        
//        private float pixelHalfWidth;
//        
//        private float pixelHalfHeight;
//        
//        public float getPixelX()
//        {
//            return body.getPosition().x * RATE;
//        }
//        
//        public float getPixelY()
//        {
//            return body.getPosition().y * RATE;
//        }
//        
//        public float getPixelHalfWidth()
//        {
//            return pixelHalfWidth;
//        }
//        
//        public float getPixelHalfHeight()
//        {
//            return pixelHalfHeight;
//        }
//        
//        public BoxObject(Body body, float halfWidth, float halfHeight)
//        {
//            super();
//            this.body = body;
//            this.pixelHalfWidth = halfWidth;
//            this.pixelHalfHeight = halfHeight;
//        }
//        
//    }
//}
