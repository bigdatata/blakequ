package com.yarin.android.GameEngine.Util;

//��ʱ����
public class Calculagraph
{
	//ѭ���������msΪ��λ��
	private int loopTime=0;
	//�Ƿ��ǵ�һ�μ�ʱ
	private boolean isFirstTime=true;
	//��ʼʱ��
	private long startTime=0;
	//����ʱ��
	private long runTime=0;
	
	public Calculagraph(int loopTime){
		this.loopTime=loopTime;
		isFirstTime=true;
		runTime=0;
		startTime=0;
	}
	
	/**
	 * ��ʱ
	 *
	 */
	public void calculate(){
		if(isFirstTime){
			startTime=System.currentTimeMillis();
			isFirstTime=false;
		}
		else{
			runTime=System.currentTimeMillis();
		}
	}
	
	/**
	 * �Ƿ�ʱ
	 * @return �����ʱ����true�����򷵻�false
	 */
	public boolean isTimeout(){
		return ((runTime-startTime)>loopTime);
	}
	
	/**
	 * ���ü�ʱ��
	 *
	 */
	public void reset(){
		startTime=0;
		runTime=0;
		isFirstTime=true;
	}

	public int getLoopTime() {
		return loopTime;
	}
}

