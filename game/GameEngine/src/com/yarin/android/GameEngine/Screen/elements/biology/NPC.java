package com.yarin.android.GameEngine.Screen.elements.biology;

import java.util.Vector;

import com.yarin.android.GameEngine.Material.Movement;
import com.yarin.android.GameEngine.Screen.elements.Property.PropertyManager;
import com.yarin.android.GameEngine.Util.StringExtension;

public class NPC extends Animal
{
	//������NPC������Ϸ�е��ι����Թ���-��������
	public static final int FUNCTIONAL_NPC=1;
	//�����NPC������Ϸ�Ĺ�����ڷ�������-����ŵ�ʿ��
	public static final int SCENARIO_NPC=2;
	//�ǽ�����NPC������Ϸ��ֻ�����Ǻ�����NPC������ײ���
	public static final int NOINTERACTION_NPC=3;
//	֡�л����飬��Ӧ�����ǵ��ϡ��¡������ĸ������֡ͼƬ
	private int[] frameSwtichSequence=null;
	
	//NPC������
	private int type=0;
	//������
	private PropertyManager propBox=null;
	
	public NPC(){
		super();
	}

	/**
	 * ����NPC�ĵ�����
	 * @return ���߹������
	 */
	public PropertyManager getPropertyBox() {
		return propBox;
	}

	/**
	 * ����NPC������
	 * @return NPC������
	 */
	public int getType() {
		return type;
	}

	public void setPropertyBox(PropertyManager propBox) {
		this.propBox = propBox;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * ���ظ����loadProperties����ȡ֡�л�����
	 */
	public void loadProperties(Vector v){
		super.loadProperties(v);
//		System.out.println("FrameSequenceLength="+this.getAnimator().getFrameSequenceLength());
		Object[] objArray=StringExtension.split(new StringBuffer((String)v.elementAt(14)),
				",",StringExtension.INTEGER_ARRAY,false);
		if (objArray!=null){
			frameSwtichSequence=StringExtension.objectArrayBatchToIntArray(objArray);
		}
		else{
			frameSwtichSequence=null;
		}
	}
	
	/**
	 * �����˶��ķ����ÿ���ٶ��ƶ�,ÿ���ƶ�һ�����ǵĶ������λ��
	 */
	public void move(){
		int x=this.getAnimator().getX();
		int y=this.getAnimator().getY();
		//�ƶ�����λ�ã�ÿ���ƶ�һ�����ǵĶ������λ��
		switch(this.getMovement().getMoveDirection()){
		case Movement.LEFT_MOVE:
			x-=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[2]);
			getAnimator().flushPosition(x,y);
			break;
		case Movement.RIGHT_MOVE:
			x+=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[3]);
			getAnimator().flushPosition(x,y);
			break;
		case Movement.UP_MOVE:
			y-=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[0]);
			getAnimator().flushPosition(x,y);
			break;
		case Movement.DOWN_MOVE:
			y+=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[1]);
			getAnimator().flushPosition(x,y);
			break;
		}
		
	}
	
	/**
	 * �����˶��ķ����ÿ���ٶȷ����ƶ���������ǰ��ȡ���ƶ�Ч����Ŀ��
	 */
	public void undoMove(){
		int x=this.getAnimator().getX();
		int y=this.getAnimator().getY();
		//�ƶ�����λ��
		switch(this.getMovement().getMoveDirection()){
		case Movement.LEFT_MOVE:
			x+=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[2]);
			getAnimator().flushPosition(x,y);
			break;
		case Movement.RIGHT_MOVE:
			x-=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[3]);
			getAnimator().flushPosition(x,y);
			break;
		case Movement.UP_MOVE:
			y+=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[0]);
			getAnimator().flushPosition(x,y);
			break;
		case Movement.DOWN_MOVE:
			y-=this.getMovement().getStepSpeed();
			getAnimator().setFrame(frameSwtichSequence[1]);
			getAnimator().flushPosition(x,y);
			break;
		}
	}
	
	public String toString(){
		return super.toString()
			+" propBox="+propBox;
	}
}

