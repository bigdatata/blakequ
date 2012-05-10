package com.yarin.android.GameEngine.Screen;

import java.util.Vector;

import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.GameObjectQueue;
//��Ϸ���磺�ж��map��ɵĴ��͹ؿ�
public class SimpleLevel extends GameObject
{
	//�ؿ�����
	private String levelName=null;
	//�ؿ����õ��ĵ�ͼ,ʹ��Map��id��Ϊ�������Ա�MapTransformer����
	private GameObjectQueue mapSet=null;
	//�ؿ��е�һ����ͼ��ID
	private String firstMapID=null;
	
	public SimpleLevel(){
		super();
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.levelName=(String)v.elementAt(1);
		this.firstMapID=(String)v.elementAt(2);
	}
	
	public void setMapSet(GameObjectQueue mapSet) {
		this.mapSet = mapSet;
	}

	public String getLevelName() {
		return levelName;
	}

	public GameObjectQueue getMapSet() {
		return mapSet;
	}
	
	/**
	 * ����ID��Ӧ�ĵ�ͼ��SimpleMap����
	 * @param mapID ��ͼID 
	 * @return ������ҵ����򷵻ز��ҵ���ID��Ӧ�ĵ�ͼ�����򷵻�null
	 */
	public SimpleMap findMap(String mapID){
		if (mapSet.containsKey(mapID)){
			return (SimpleMap)mapSet.get(mapID);
		}
		else{
			return null;
		}
	}

	public String getFirstMapID() {
		return firstMapID;
	}
	
	public String toString(){
		return "id="+super.toString()
		+" levelName="+levelName
		+" firstMapID="+firstMapID
		+" mapSet size="+mapSet.size();
	}
}

