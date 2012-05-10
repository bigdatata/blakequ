package com.yarin.android.GameEngine.Screen.elements.Property;

import java.util.Vector;

import com.yarin.android.GameEngine.GameObject;

//������
public class Property extends GameObject
{
	//���������:���ڻָ�����������
	public static final int MEDICINE_PROP=1;
	//�������������:�������ǵĹ�����
	public static final int ATTACK_WEAPON_PROP=2;
	//�������������:�������ǵķ�����
	public static final int DEFENCE_WEAPON_PROP=3;
	//�������ߣ���Ϊ���ڹ��ص���������Կ�׵�
	public static final int SCENARIO_PROP=4;
	
	//��������
	private String name=null;
	//���ߵ�����
	private String description=null;
	//��������۸���������
	private int buyPrice=0;
	//���������۸����������
	private int salePrice=0;
	//����Ч����������ֵ��Ӱ�죩
	private int lifeEffect=0;
	//����������Ч�����Թ���ֵ��Ӱ�죩
	private int attackEffect=0;
	//����������Ч�����Է���ֵ��Ӱ�죩
	private int defenceEffect=0;
	//���ô�������ҩƷֻ����һ�Σ���С��������
	private int useTimes=0;
	//��������
	private int type=0;
	
	public Property(){
		super();
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.name=((String)v.elementAt(1));
		this.description=((String)v.elementAt(2));
		this.buyPrice=(Integer.parseInt((String)v.elementAt(3)));
		this.salePrice=(Integer.parseInt((String)v.elementAt(4)));
		this.lifeEffect=(Integer.parseInt((String)v.elementAt(5)));
		this.attackEffect=(Integer.parseInt((String)v.elementAt(6)));
		this.defenceEffect=(Integer.parseInt((String)v.elementAt(7)));
		this.useTimes=(Integer.parseInt((String)v.elementAt(8)));
		this.type=(Integer.parseInt((String)v.elementAt(9)));
	}
	
	public int getBuyPrice() {
		return buyPrice;
	}

	public String getDescription() {
		return description;
	}

	public int getLifeEffect() {
		return lifeEffect;
	}

	public String getName() {
		return name;
	}

	public int getSalePrice() {
		return salePrice;
	}
	
	public int getUseTimes() {
		return useTimes;
	}
	
	public int getAttackEffect() {
		return attackEffect;
	}

	public int getDefenceEffect() {
		return defenceEffect;
	}

	public int getType() {
		return type;
	}
	
	public String toString(){
		return super.toString()
			+" name="+name+" description="+description
			+" buyPrice="+buyPrice+" salePrice="+salePrice
			+" lifeEffect="+lifeEffect+" attackEffect="+attackEffect
			+" defenceEffect="+defenceEffect+" useTimes="+useTimes
			+" type="+type;
	}
}

