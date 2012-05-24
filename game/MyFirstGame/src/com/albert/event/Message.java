package com.albert.event;

import java.util.Vector;

import com.albert.IGameObject;
import com.albert.IProperty;

/**
 * œ˚œ¢¿‡
 * @author AlbertQu
 *
 */
public class Message extends IGameObject
{
	private String msgContent=null;
	
	public Message(){
		super();
	}
	
	public Message(String msgID,String msgContent){
		super();
		this.msgContent=msgContent;
		this.setId(msgID);
	}
	
	@Override
	public void loadProperties(IProperty property) {
		// TODO Auto-generated method stub
		super.loadProperties(property);
		MessageProperty mp = (MessageProperty) property;
		this.msgContent = mp.msgContent;
		this.setId(mp.id+"");
	}
	
	public String getMsgContent() {
		return msgContent;
	}

	public String toString(){
		return super.toString()
			+" msgContent="+msgContent;
	}
}

