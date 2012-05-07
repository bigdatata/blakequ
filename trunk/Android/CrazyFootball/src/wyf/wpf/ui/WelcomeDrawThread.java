package wyf.wpf.ui;					//���������
import android.graphics.Canvas;				//���������
import android.view.SurfaceHolder;			//���������
/*
 * ����̳���Thread����Ҫ����ʱˢ��WelcomeView
 */
public class WelcomeDrawThread extends Thread{
	WelcomeView father;					//WelcomeView���������
	public SurfaceHolder surfaceHolder;		//WelcomeView�����SurfaceHolder
	public int sleepSpan = 100;				//����ʱ��
	public boolean flag;						//�߳�ִ�б�־λ
	//����������ʼ����Ҫ�ĳ�Ա����
	public WelcomeDrawThread(WelcomeView father,SurfaceHolder surfaceHolder){
		this.father = father;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;
	}
	//�������߳�ִ�з���
	public void run(){
		Canvas canvas = null;			//����һ��Canvas����
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);	//Ϊ��������
				synchronized(surfaceHolder){
					father.doDraw(canvas);				//���»�����Ļ
				}
			}
			catch(Exception e){
				e.printStackTrace();		//�����쳣����ӡ
			}
			finally{
				if(canvas != null){//�ͷŻ��������䴫��
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			try{
				Thread.sleep(sleepSpan);		//����һ��ʱ��
			}
			catch(Exception e){
				e.printStackTrace();			//�����쳣����ӡ
			}
		}
	}
}