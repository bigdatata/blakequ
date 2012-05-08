package com.hao;

import java.util.Vector;

/**
 * boss�Ǳ������ߣ���һ����֪ͨ���еĹ��˿�ʼ����
 * @author Administrator
 *
 */
public class Boss {
	MyListener ml;
	//���˶�����������
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
	 * boss���ˣ�֪ͨ������Ӧ�ù�����
	 */
	public void come(){
		Object[] o = v.toArray();
		for(int i=0; i<o.length; i++)
			((MyListener)o[i]).hasChanged("�����ϰ������ˣ����ǸϽ����ҹ�����");
	}
}
