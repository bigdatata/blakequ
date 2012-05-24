package com.albert.event;

import java.util.Vector;

import com.albert.IGameObject;
import com.albert.IProperty;
/**
 * �¼���:���ڶԻ����¼���һ���¼���Ӧ��һ����Ϣ����MessageQueue
 * @author AlbertQu
 *
 */
public class Event extends IGameObject
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
	
	
	@Override
	public void loadProperties(IProperty property) {
		// TODO Auto-generated method stub
		super.loadProperties(property);
		EventProperty ep = (EventProperty) property;
		this.invoker = ep.invoker;
		this.responser = ep.responser;
		this.parameter = ep.parameter;
		this.type = ep.type;
		this.setId(ep.id+"");
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
