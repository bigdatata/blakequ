package com.albert.event;

import java.util.Vector;

import com.albert.IGameObject;
import com.albert.IProperty;
/**
 * 事件类:对于对话类事件，一个事件对应着一个消息队列MessageQueue
 * @author AlbertQu
 *
 */
public class Event extends IGameObject
{
	//对话类型事件：当需要主角和NPC对话时触发此事件
	public static final int TALK_EVENT=1;
	//战斗类型事件：当需要主角和NPC作战时触发此事件
	public static final int FIGHT_EVENT=2;
	
	//事件发起者
	private String invoker=null;
	//事件响应者
	private String responser=null;
	//事件类型
	private int type=0;
	//参数
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
事件示例：
EVENT01：HERO NPC01 TALK
说明：
EVENT01表示eventID，即注册事件ID；
HERO表示事件发生的主体；
NPC01表示参与事件的被动体；
TALK表示对话类型事件
**/
