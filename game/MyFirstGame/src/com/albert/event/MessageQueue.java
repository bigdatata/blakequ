package com.albert.event;

import com.albert.IGameObjectQueue;

/**
 * ��Ϣ�����ࣺ������ʾ��ɫ���Ժ�NPC���ԡ�ϵͳ����Ϣ
 * @author AlbertQu
 *
 */
public class MessageQueue extends IGameObjectQueue
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

