package com.yarin.android.GameEngine.Util;

//�����ࣺ��ʾ�����λ�ã���������x,y����ϵ���꣬Ҳ��������ͼ���е���������
public class Coordinates
{
	//x�������������
	private int x=0;
	//y�������������
	private int y=0;
	
	public Coordinates(int x,int y){
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

