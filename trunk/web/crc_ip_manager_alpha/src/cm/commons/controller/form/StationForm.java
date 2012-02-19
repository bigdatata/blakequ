package cm.commons.controller.form;

import java.util.HashSet;
import java.util.Set;

import cm.commons.pojos.Station;

public class StationForm {
	private int id;
	private String name; //站点名称
	private double x;//该站点x坐标
	private double y;//该站点y坐标
	private int state;//站点状态
	private int segmentNum;//连接的线段数目
	private Set station1 = new HashSet(0);//上行站点
	private Set station2 = new HashSet(0);//下行站点
	private Boolean isMainStation;//是否是主站点(TDCS)
	
	public Boolean getIsMainStation() {
		return isMainStation;
	}
	public void setIsMainStation(Boolean isMainStation) {
		this.isMainStation = isMainStation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getSegmentNum() {
		return segmentNum;
	}
	public void setSegmentNum(int segmentNum) {
		this.segmentNum = segmentNum;
	}
	
	
	public Set getStation1() {
		return station1;
	}
	public void setStation1(Set station1) {
		this.station1 = station1;
	}
	public Set getStation2() {
		return station2;
	}
	public void setStation2(Set station2) {
		this.station2 = station2;
	}
	@Override
	public String toString() {
		return "StationForm [name=" + name + ", x=" + x + ", y=" + y + "]";
	}
	
	
}
