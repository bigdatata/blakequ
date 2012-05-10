package com.yarin.android.GameEngine.Screen;

import java.util.Vector;

import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.GameObjectQueue;
//�򵥵�ͼ�������ǵ�����·���ͷ�����·�����,����NPC
public class SimpleMap extends GameObject
{
	//ͼ�㼯��(ͼ���˳���������˳��)
	private GameObjectQueue layerSet=null;
	//��ͼ���е�NPC
	private GameObjectQueue npcSet=null;
	//����ͼ��ǰ���ͼ����������һ��map���ٿ�����һ��
	private GameObjectQueue mapLink=null;
	//��ͼ�Ŀ��
	private int width=0;
	//��ͼ�ĸ߶�
	private int height=0;
	//��ͼ����
	private String name=null;
	
	public String getName() {
		return name;
	}

	public SimpleMap(){
		super();
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.name=(String)v.elementAt(1);
		this.width=Integer.parseInt((String)v.elementAt(2));
		this.height=Integer.parseInt((String)v.elementAt(3));
	}
	
	public GameObjectQueue getLayerSet() {
		return layerSet;
	}
	public GameObjectQueue getMapLink() {
		return mapLink;
	}
	public GameObjectQueue getNpcSet() {
		return npcSet;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setLayerSet(GameObjectQueue layerSet) {
		this.layerSet = layerSet;
	}

	public void setMapLink(GameObjectQueue mapLink) {
		this.mapLink = mapLink;
	}

	public void setNpcSet(GameObjectQueue npcSet) {
		this.npcSet = npcSet;
	}
	
	public String toString(){
		return "id="+super.toString()
		+" name="+this.name
		+" width="+this.width
		+" height="+this.height
		+" layerSet size="+this.layerSet.size()
		+" MapLink size="+this.mapLink.size()
		+" NpcSet size="+this.npcSet.size();
	}
}

