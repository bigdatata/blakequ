package com.yarin.android.GameEngine.Events;
import com.yarin.android.GameEngine.GameObjectQueue;
//��Ϣ�����ࣺ������ʾ��ɫ���Ժ�NPC���ԡ�ϵͳ����Ϣ
public class MessageQueue extends GameObjectQueue
{
	public MessageQueue(){
		super();
	}
	
	/**
	 * ��ѯ��Ϣ
	 * @param msgID ��ϢID
	 * @return ���ص���ʾ��Ϣ�����û�ҵ��򷵻�null
	 */
	public String findContent(String msgID){
		if (this.containsKey(msgID)){
			if (this.get(msgID)==null){
				return null;
			}
			else{
				return this.get(msgID).toString();
			}
		}
		else{
			return null;
		}
	}
}

