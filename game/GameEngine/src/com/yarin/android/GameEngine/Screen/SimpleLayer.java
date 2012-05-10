package com.yarin.android.GameEngine.Screen;

import java.util.Vector;

import javax.microedition.lcdui.game.TiledLayer;

import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.Util.StringExtension;

//�򵥵�ͼ�㣺ֻ��ʾһ����С
public class SimpleLayer extends GameObject
{
	//����·������ͼ��
	public static int WALKARENA=1;
	//������·������ͼ��
	public static int NO_WALKARENA=2;
	//��ͼ����
	private int[] mapData=null;
	//��ש�Ŀ��
	private int tileWidth=0;
	//��ש�ĸ߶�
	private int tileHeight=0;
	//��ש������
	private int tileCols=0;
	//��ש������
	private int tileRows=0;
	//ͼ��
	private TiledLayer layer=null;
	//ͼ�����ࣺ���ǵ�����·����������·��
	private int type=0;
	//ͼƬURL
	private String imgURL=null;
	
	public SimpleLayer(){
		super();
	}
	
	/**
	 * ���ַ���ת��Ϊ����
	 * @param s ��ת�����ַ���
	 * @return ת�������������
	 */
	private int[] StringToIntArray(String s){
		//����ȥ���ַ����еĸ�ʽ���ַ�
		s=StringExtension.removeToken(s,new String[]{"\t"," ","\r","\n"});
		Object[] objArr=StringExtension.split(new StringBuffer(s),",",StringExtension.INTEGER_ARRAY,false);
		return StringExtension.objectArrayBatchToIntArray(objArr);
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.tileWidth=Integer.parseInt((String)v.elementAt(1));
		this.tileHeight=Integer.parseInt((String)v.elementAt(2));
		this.tileCols=Integer.parseInt((String)v.elementAt(3));
		this.tileRows=Integer.parseInt((String)v.elementAt(4));
		this.type=Integer.parseInt((String)v.elementAt(5));
		this.imgURL=(String)v.elementAt(6);
		this.mapData=StringToIntArray((String)v.elementAt(7));
	}
	
	public TiledLayer getLayer() {
		return layer;
	}

	public int[] getMapData() {
		return mapData;
	}

	public int getTileCols() {
		return tileCols;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTileRows() {
		return tileRows;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getType() {
		return type;
	}

	public void setLayer(TiledLayer layer) {
		this.layer = layer;
	}

	public String getImgURL() {
		return imgURL;
	}
	
	public String toString(){
		return "id="+super.getId()
		+" tileWidth="+this.tileWidth
		+" tileHeight="+this.tileHeight
		+" tileCols="+this.tileCols
		+" tileRows="+this.tileRows
		+" type="+this.type
		+" imgURL="+this.imgURL
		+" mapData Size="+this.mapData.length;
	}
}

