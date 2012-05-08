package com.hao;

import java.util.Vector;

/**
 * boss是被监听者，他一来就通知所有的工人开始工作
 * @author Administrator
 *
 */
public class Boss {
	MyListener ml;
	//工人都放在这里面
	Vector v;
	
	public Boss(){
		v = new Vector();
	}
	
	public synchronized void addListener(MyListener m){
		if(m == null)
			throw new NullPointerException();
		if(!v.contains(m)){
			v.add(m);
		}
	}
	
	/**
	 * boss来了，通知工人们应该工作了
	 */
	public void come(){
		Object[] o = v.toArray();
		for(int i=0; i<o.length; i++)
			((MyListener)o[i]).hasChanged("靠！老板我来了，你们赶紧给我工作！");
	}
}
