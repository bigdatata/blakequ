package com.yarin.android.GameEngine.Events;

import java.util.Vector;

import com.yarin.android.GameEngine.GameObjectQueue;

//�¼������ࣺ���ڰ���FIFO��˳�򱣴���Ϸ�е��¼�
public class EventQueue extends GameObjectQueue
{
	public EventQueue(){
		super();
	}
	
	/**
	 * ��ѯָ���¼�ID��Ӧ���¼�����
	 * @param eventID ��ѯʹ�õ���ָ���¼�ID
	 * @return ָ���¼�ID��Ӧ���¼�����
	 */
	public Event PollEvent(String eventID){
		if(this.containsKey(eventID)){
			return (Event)this.get(eventID);
		}
		else{
			return null;
		}
	}
	
	/**
	 * ��ѯָ���¼�ID��Ӧ���¼����󣬲���Ϊ�¼����鷵��
	 * @param eventID ��ѯʹ�õ���ָ���¼�ID����
	 * @return ָ���¼�ID��Ӧ���¼��������飬û�з��ֶ�Ӧ���¼��򷵻�null
	 */
	public Event[] PollEvent(String[] eventIDArray){
		Vector polledList=new Vector();
		for(int i=0;i<eventIDArray.length;i++)
		if(this.containsKey(eventIDArray[i])){
			polledList.addElement(this.get(eventIDArray[i]));
		}
		
		if (polledList.size()>0){
			Event[] result=new Event[polledList.size()];
			polledList.copyInto(result);
			return result;
		}
		else{
			return null;
		}
	}
}

