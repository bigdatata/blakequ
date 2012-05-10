package com.yarin.android.GameEngine.Screen.elements.Property;

import java.util.Enumeration;

import com.yarin.android.GameEngine.GameObjectQueue;

//���߹���װ��һ�������ĵ���
public class PropertyManager extends GameObjectQueue
{
	public PropertyManager(){
		super();
	}
	
	/**
	 * ���Խ����߷��������
	 * @param prop ���Ա�����ĵ���
	 */
	public void putIntoBox(Property prop){
		this.put(prop.getId(),prop);
	}
	
	/**
	 * ȡ������
	 * @param propID ��ȡ���ĵ���ID
	 * @return ����������д��ڴ˵��ߣ��򷵻ظõ��ߣ����򷵻�null
	 */
	public Property takeFromBox(String propID){
		try{
			return (Property)this.get(propID);
		}
		catch(Exception ex){
			return null;
		}
	}
		
	/**
	 * ע������(��ʹ������߻��߶�������ʱʹ��)
	 * @param propID ��Ҫע���ĵ���ID
	 * @return ע���ɹ�����true�����򷵻�false
	 */
	public boolean unRegisterProperty(String propID){
		try{
			this.remove(propID);
			return true;
		}
		catch(Exception ex){
			return false;
		}
	}
	
	/**
	 * ���ص��������б�
	 * @return ���������б�
	 */
	public Property[] getPropertyList(){
		Property[] prop=new Property[this.size()];
		Enumeration enu=this.elements();
		int i=0;
		while(enu.hasMoreElements()){
			prop[i++]=(Property)enu.nextElement();
		}
		return prop;
	}
}

