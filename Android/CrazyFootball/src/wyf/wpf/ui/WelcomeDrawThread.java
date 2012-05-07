package wyf.wpf.ui;					//声明包语句
import android.graphics.Canvas;				//引入相关类
import android.view.SurfaceHolder;			//引入相关类
/*
 * 该类继承自Thread，主要负责定时刷新WelcomeView
 */
public class WelcomeDrawThread extends Thread{
	WelcomeView father;					//WelcomeView对象的引用
	public SurfaceHolder surfaceHolder;		//WelcomeView对象的SurfaceHolder
	public int sleepSpan = 100;				//休眠时间
	public boolean flag;						//线程执行标志位
	//构造器：初始化主要的成员变量
	public WelcomeDrawThread(WelcomeView father,SurfaceHolder surfaceHolder){
		this.father = father;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;
	}
	//方法：线程执行方法
	public void run(){
		Canvas canvas = null;			//创建一个Canvas对象
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);	//为画布加锁
				/**
				 * 这里设置了对于对象surfaceHolder的同步，这样为了防止对于在
				 * 绘制welcome和loading，game的游戏画面时使用surfaceHolder的
				 * 异步绘图，这是为了防止绘制线程没有正常停止造成都调用draw方法
				 * 造成异常的情况（正常的情况下，welcome,loading等都是顺序执行，不会
				 * 都同时使用surfaceHolder对象进行绘制）
				 */
				synchronized(surfaceHolder){
					father.doDraw(canvas);				//重新绘制屏幕
				}
			}
			catch(Exception e){
				e.printStackTrace();		//捕获异常并打印
			}
			finally{
				if(canvas != null){//释放画布并将其传回
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			try{
				Thread.sleep(sleepSpan);		//休眠一段时间
			}
			catch(Exception e){
				e.printStackTrace();			//捕获异常并打印
			}
		}
	}
}