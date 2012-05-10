package com.yarin.android.GameEngine.Screen.elements.biology;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yarin.android.GameEngine.GameActivity;
import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.Events.EventQueue;
import com.yarin.android.GameEngine.Material.Movement;
import com.yarin.android.GameEngine.Screen.animation.Animator;
import com.yarin.android.GameEngine.Util.Coordinates;
//ʵ��Biology�ӿڵĶ�����
public class Animal extends GameObject implements Biology 
{
	//����
	private String name=null;
	//����ֵ
	private int life=0;
	//����ֵ
	private int attack=0;
	//����ֵ
	private int defence=0;
	//�����Ƿ�����
	private boolean alive=true;
	//ͼƬ/����URL
	private String imgURL=null;
	//����ͼƬURL
	private String faceURL=null;
	//�¼����У���������NPC������ײʱ��ʹ���¼�ID�����¼������е�ĳ���¼���
	private EventQueue eventQueue=null;
	//����
	private Coordinates co=null;
	//�˶�������ٶ�
	private Movement movement=null;
	//��������
	private Animator ani=null;
	
	public Animal(){
		super();
	}
	
	/**
	 * ֻʹ��ǰ14������
	 */
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.name=(String)v.elementAt(1);
		this.life=Integer.parseInt((String)v.elementAt(2));
		this.attack=Integer.parseInt((String)v.elementAt(3));
		this.defence=Integer.parseInt((String)v.elementAt(4));
		this.imgURL=(String)v.elementAt(5);
		this.faceURL=(String)v.elementAt(6);
		int col=Integer.parseInt((String)v.elementAt(7));
		int row=Integer.parseInt((String)v.elementAt(8));
		this.co=new Coordinates(col,row);
		int stepSpeed=Integer.parseInt((String)v.elementAt(9));
		int moveDirection=Integer.parseInt((String)v.elementAt(10));
		this.movement=new Movement(stepSpeed,moveDirection);
		this.alive=true;
		int animationLoopTime=Integer.parseInt((String)v.elementAt(11));
		int frameWidth=Integer.parseInt((String)v.elementAt(12));
		int frameHeight=Integer.parseInt((String)v.elementAt(13));
		
		try{
			Bitmap img=BitmapFactory.decodeResource(GameActivity.mContext.getResources(), Integer.parseInt(this.imgURL));
			this.ani=new Animator(img,frameWidth,frameHeight,animationLoopTime);
			this.ani.setRefPixelPosition(col*frameWidth,row*frameHeight);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * �޸�����
	 * @param name
	 */
	public void changeName(String name){
		this.name=name;
	}
	
	/**
	 * ���ض��������
	 * @return ���������
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * �޸Ķ����ͼƬ/����URL
	 * @param imgURL
	 */
	public void changeImgURL(String imgURL){
		this.imgURL=imgURL;
	}
	
	/**
	 * ����ͼƬURL
	 * @return ͼƬURL
	 */
	public String getImgURL(){
		return this.imgURL;
	}
	
	/**
	 * �����ܵ��˺����������ؼ��ٶ�������������������ֵС��MINLIFE�������������������
	 * @param detaLife ������������
	 * @return ���ض����������
	 */
	public int decreaseLife(int detaLife){
		if (alive){
			if (life>MINLIFE){
				life-=detaLife;
				if (life<MINLIFE){
					alive=false;
				}
			}
		}
		return life;
	}
	
	/**
	 * ����ʹ����Ʒ������ԭ�򣬶��������������
	 * @param detaLife ������������
	 * @return �����������
	 */
	public int increaseLife(int detaLife){
		if (alive){
			if ((life+detaLife)<=MAXLIFE){
				life+=detaLife;
			}
			else{
				life=MAXLIFE;
			}
		}
		return life;
	}
	
	/**
	 * �ж϶����Ƿ�����
	 * @return ���������ţ�����true�����򷵻�false
	 */
	public boolean isAlive(){
		return alive;
	}

	/**
	 * ����˶��ٶȺͷ���
	 * @return Movement����
	 */
	public Movement getMovement() {
		return movement;
	}
	
	/**
	 * �����˶��ٶȺͷ��� 
	 * @param movement Movement����
	 */
	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	/**
	 * ��ö��ﵱǰ������
	 * @return ���ﵱǰ������
	 */
	public Coordinates getCoordinates() {
		return co;
	}

	public int getAttack() {
		return attack;
	}
	
	public int increaseAttack(int detaAttack){
		this.attack+=detaAttack;
		return this.attack;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public int increaseDefence(int detaDefence){
		this.defence+=detaDefence;
		return this.defence;
	}

	public EventQueue getEventQueue() {
		return eventQueue;
	}

	public String getFaceURL() {
		return faceURL;
	}

	public void setCoordinate(Coordinates co) {
		this.co = co;
	}


	public Animator getAnimator() {
		return ani;
	}

	public void setEventQueue(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}
	
	public int getLife(){
		return this.life;
	}
	
	public String toString(){
		return super.toString()
			+" name="+name+" life="+life
			+" attack="+attack+" defence="+defence
			+" imgURL="+imgURL+" faceURL="+faceURL
			+" col="+co.getX()+" row="+co.getY()
			+" speed="+movement.getStepSpeed()+" direction="+movement.getMoveDirection()
			+" eventQueue="+eventQueue+" animator="+ani;

	}
}

