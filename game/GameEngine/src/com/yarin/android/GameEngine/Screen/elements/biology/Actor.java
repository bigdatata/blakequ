package com.yarin.android.GameEngine.Screen.elements.biology;

import java.util.Vector;

import com.yarin.android.GameEngine.Material.Movement;
import com.yarin.android.GameEngine.Screen.GameModel;
import com.yarin.android.GameEngine.Screen.elements.Property.Property;
import com.yarin.android.GameEngine.Screen.elements.Property.PropertyManager;
import com.yarin.android.GameEngine.Util.StringExtension;

public class Actor extends Animal
{
	//������
	private PropertyManager propBox=null;
	//��Ϸģʽ
	private GameModel gameModel=null;
	//֡�л����飬��Ӧ�����ǵ��ϡ��¡������ĸ������֡ͼƬ
	private int[] frameSwtichSequence=null;
	
	public Actor(){
		super();
		gameModel=new GameModel();
		gameModel.setModelType(GameModel.FREEMOVE_MODEL);
	}
	
	/**
	 * ���ظ����loadProperties����ȡ֡�л�����
	 */
	public void loadProperties(Vector v){
		super.loadProperties(v);
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
	 * ������ǵĵ�����
	 * @return ���߹������
	 */
	public PropertyManager getPropertyBox(){
		return propBox;
	}
	
	/**
	 * �������ǵĵ�����
	 * @param ���߹������
	 */
	public void setPropertyBox(PropertyManager propBox){
		this.propBox=propBox;
	}
	
	/**
	 * װ�����ߣ��ӵ�������ע��
	 * @param prop Ҫװ���ĵ���
	 */
	public void equipProperty(Property prop){
		switch(prop.getType()){
		case Property.MEDICINE_PROP:
			increaseLife(prop.getLifeEffect());
			break;
		case Property.ATTACK_WEAPON_PROP:
			increaseAttack(prop.getAttackEffect());
			break;
		case Property.DEFENCE_WEAPON_PROP:
			increaseDefence(prop.getDefenceEffect());
		}
		propBox.unRegisterProperty(prop.getId());
	}
	
	/**
	 * ���ص�ǰ����Ϸģʽ
	 * @return ��Ϸģʽ
	 */
	public GameModel getGameModel() {
		return gameModel;
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
		if (frameSwtichSequence!=null){
			StringBuffer tmp=new StringBuffer();
			for(int i=0;i<frameSwtichSequence.length;i++){
				if (i<frameSwtichSequence.length-1){
					tmp.append(frameSwtichSequence[i]+",");
				}
				else{
					tmp.append(frameSwtichSequence[i]);
				}
			}
			return super.toString()+" frameSwtichSequence="+tmp.toString();
		}
		else{
			return super.toString();
		}
	}
}

