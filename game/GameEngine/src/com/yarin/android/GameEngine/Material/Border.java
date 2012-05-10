package com.yarin.android.GameEngine.Material;
//�߽���
public class Border
{
	//��СX����
	private int minX=0;
	//��СY����
	private int minY=0;
	//���X����
	private int maxX=0;
	//���Y����
	private int maxY=0;
	
	public Border(int minx,int miny,int maxx,int maxy){
		this.maxX=maxx;
		this.maxY=maxy;
		this.minX=minx;
		this.minY=miny;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}
}

