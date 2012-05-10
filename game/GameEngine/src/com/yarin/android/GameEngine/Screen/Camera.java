package com.yarin.android.GameEngine.Screen;

import java.util.Vector;

import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.Screen.elements.biology.Actor;
import com.yarin.android.GameEngine.Util.Coordinates;

public class Camera extends GameObject
{
	//�ƶ�ģʽ-��������ģʽ
	public static final int TRACK_PLAYER_MODEL=1;
	//ʹ���Զ����С��Camera
	public static final int CUSTOM_SIZE=1;
	//ʹ���뵱ǰ��Ļ��С��ͬ��Camera
	public static final int SCREEN_SIZE=2;
	
	//�ƶ�ģʽ����
	private int type=0;
	//�����λ�ã�ָ�ѿ�������ϵ�����Ͻ�����λ��
	private Coordinates col=null;
	//�������ͷ���
	private int width=0;
	//�������ͷ����
	private int height=0;
	//ʹ���Զ����С�ı�־
	private int CustomSizeFlag=0;
	
	public Camera(){
		super();
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		int col=Integer.parseInt((String)v.elementAt(1));
		int row=Integer.parseInt((String)v.elementAt(2));
		this.col=new Coordinates(col,row);
		this.width=Integer.parseInt((String)v.elementAt(3));
		this.height=Integer.parseInt((String)v.elementAt(4));
		this.type=Integer.parseInt((String)v.elementAt(5));
		this.CustomSizeFlag=Integer.parseInt((String)v.elementAt(6));
	}
	
	public Coordinates getCoordinates(){
		return col;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public void setCoorindates(Coordinates col) {
		this.col = col;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void move(Actor actor,int mapWidth,int mapHeight){
		int x=0;
		int y=0;
		switch(type){
		case TRACK_PLAYER_MODEL:
			x=actor.getAnimator().getX()+actor.getAnimator().getWidth()/2-width/2;
			y=actor.getAnimator().getY()+actor.getAnimator().getHeight()/2-height/2;
			if (x<0){
				x=0;
			}
			else if ((x+width)>mapWidth){
				x=mapWidth-width;
			}
			if (y<0){
				y=0;
			}
			else if ((y+height)>mapHeight){
				y=mapHeight-height;
			}
			this.getCoordinates().setX(x);
			this.getCoordinates().setY(y);
			break;
		}
		System.out.println("Actor x="+actor.getAnimator().getX()+" y="+actor.getAnimator().getY());
		System.out.println("Camera x="+this.getCoordinates().getX()+" y="+this.getCoordinates().getY());
	}

	public int getCustomSizeFlag() {
		return CustomSizeFlag;
	}

	public void setCustomSizeFlag(int customSizeFlag) {
		CustomSizeFlag = customSizeFlag;
	}
	
	public String toString(){
		return "id="+super.toString()
		+" x="+this.col.getX()
		+" y="+this.col.getY()
		+" width="+this.width
		+" height="+this.height
		+" moveType="+this.type
		+" customSize="+this.CustomSizeFlag;
	}
}

