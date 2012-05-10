package com.yarin.android.GameEngine.Events;

import java.util.Vector;

import com.yarin.android.GameEngine.GameObject;
//�¼���:���ڶԻ����¼���һ���¼���Ӧ��һ����Ϣ����MessageQueue
public class Event extends GameObject
{
	//�Ի������¼�������Ҫ���Ǻ�NPC�Ի�ʱ�������¼�
	public static final int TALK_EVENT=1;
	//ս�������¼�������Ҫ���Ǻ�NPC��սʱ�������¼�
	public static final int FIGHT_EVENT=2;
	
	//�¼�������
	private String invoker=null;
	//�¼���Ӧ��
	private String responser=null;
	//�¼�����
	private int type=0;
	//����
	private String parameter=null;
	
	public Event(){
		super();
	}
	
	public void loadProperties(Vector attrValueSet){
		this.setId((String)attrValueSet.elementAt(0));
		this.invoker=(String)attrValueSet.elementAt(1);
		this.responser=(String)attrValueSet.elementAt(2);
		this.type=Integer.parseInt((String)attrValueSet.elementAt(3));
		this.parameter=(String)attrValueSet.elementAt(4);
	}

	public String getInvoker() {
		return invoker;
	}

	public String getResponser() {
		return responser;
	}

	public int getType() {
		return type;
	}

	public String getParameter() {
		return parameter;
	}

	public String toString() {
		return super.toString()
			+" invoker="+invoker
			+" responser="+responser
			+" type="+type
			+" parameter="+parameter;
	}
}
/**
�¼�ʾ����
EVENT01��HERO NPC01 TALK
˵����
EVENT01��ʾeventID����ע���¼�ID��
HERO��ʾ�¼����������壻
NPC01��ʾ�����¼��ı����壻
TALK��ʾ�Ի������¼�
**/
