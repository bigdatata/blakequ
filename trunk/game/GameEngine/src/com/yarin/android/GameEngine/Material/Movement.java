package com.yarin.android.GameEngine.Material;
//�˶��ࣺ��¼Player��NPC���˶��ٶȺ��˶�����
public class Movement
{
	//�˶���������
	public static final int LEFT_MOVE=1;
	//�˶���������
	public static final int RIGHT_MOVE=2;
	//�˶���������
	public static final int UP_MOVE=3;
	//�˶���������
	public static final int DOWN_MOVE=4;
	//ÿ���ٶȣ�����0������ÿ���ƶ��ľ��룬����ٶ�x���y��Ĳ�����ͬ
	private int stepSpeed=0;
	//�ƶ�����
	private int moveDirection=0;
	
	public Movement(int stepSpeed,int moveDirection){
		this.stepSpeed=stepSpeed;
		this.moveDirection=moveDirection;
	}

	public int getStepSpeed() {
		return stepSpeed;
	}

	public void setStepSpeed(int stepSpeed) {
		this.stepSpeed = stepSpeed;
	}

	public int getMoveDirection() {
		return moveDirection;
	}

	public void setMoveDirection(int moveDirection) {
		this.moveDirection = moveDirection;
	}
}

