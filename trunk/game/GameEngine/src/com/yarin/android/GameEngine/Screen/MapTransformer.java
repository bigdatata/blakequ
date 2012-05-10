package com.yarin.android.GameEngine.Screen;

import java.util.Vector;

import javax.microedition.lcdui.game.Sprite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yarin.android.GameEngine.GameActivity;
import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.Util.Coordinates;
//��ͼת����������Ļ�ĵ�ͼ��һ���л�����һ����Ŀǰ��֧��2����ͼ֮����л�
//��ͼ���л���Player���ͼת����֮�����ײ��������ͼת������ͼƬ������͸����ͼƬ����������Ӱ�컭���Ч��
public class MapTransformer extends GameObject
{
	//��һ���ĵ�ͼID
	private String nextMapID=null;
	//��һ���ؿ�ID
	private String nextLevelID=null;
	//��ǰ��ͼ�е�λ��
	private Coordinates location=null;
	//��һ����ͼ�����λ��
	private Coordinates nextMapEntry=null;
	//����
	private Sprite body=null;
	
	public MapTransformer(){
		super();
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.nextLevelID=(String)v.elementAt(1);
		this.nextMapID=(String)v.elementAt(2);
		int col=Integer.parseInt((String)v.elementAt(3));
		int row=Integer.parseInt((String)v.elementAt(4));
		this.location=new Coordinates(col,row);
		int nextMapCol=Integer.parseInt((String)v.elementAt(5));
		int nextMapRow=Integer.parseInt((String)v.elementAt(6));
		this.nextMapEntry=new Coordinates(nextMapCol,nextMapRow);
		try{
			String imgUrl=(String)v.elementAt(7);
			this.body=new Sprite(BitmapFactory.decodeResource(GameActivity.mContext.getResources(), Integer.parseInt(imgUrl)));
			int tileWidth=Integer.parseInt((String)v.elementAt(8));
			int tileHeight=Integer.parseInt((String)v.elementAt(9));
			this.body.setRefPixelPosition(col*tileWidth,row*tileHeight);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public Sprite getSprite() {
		return body;
	}

	public void setBody(Sprite sprite) {
		this.body = sprite;
	}

	public Coordinates getLocation() {
		return location;
	}

	public Coordinates getNextMapEntry() {
		return nextMapEntry;
	}

	public String getNextMapID() {
		return nextMapID;
	}

	public String toString(){
		return "id="+super.toString()
		+" nextLevel="+this.nextLevelID
		+" nextMap="+this.nextMapID
		+" location_col="+this.location.getX()
		+" location_row="+this.location.getY()
		+" nextMapEntry_col="+this.nextMapEntry.getX()
		+" nextMapEntry_row="+this.nextMapEntry.getY()
		+" sprite x="+this.body.getX()+" y="+this.body.getY();
	}

	public String getNextLevelID() {
		return nextLevelID;
	}
}

