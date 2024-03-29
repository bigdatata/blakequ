package com.yarin.android.GameEngine;
import java.util.Enumeration;
import java.util.Hashtable;
/**
 * 对象的一个队列，用于对象的管理
 * @author Administrator
 *
 */
public class GameObjectQueue extends Hashtable
{
	private String id=null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object find(String gameObjectId){
		if (this.containsKey(gameObjectId)){
			return this.get(gameObjectId);
		}
		else{
			return null;
		}
	}
	public Object[] list(){
		Object[] result=new Object[this.size()];
		Enumeration enumer=this.elements();
		int i=0;
		while(enumer.hasMoreElements()){
			result[i++]=enumer.nextElement();
		}
		return result;
	}
}

