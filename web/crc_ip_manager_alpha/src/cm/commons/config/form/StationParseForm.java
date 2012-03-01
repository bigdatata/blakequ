package cm.commons.config.form;

public class StationParseForm {

	private String name;
	private String x;
	private String y;
	private String isMainStation;
	private String segNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getIsMainStation() {
		return isMainStation;
	}
	public void setIsMainStation(String isMainStation) {
		this.isMainStation = isMainStation;
	}
	public String getSegNum() {
		return segNum;
	}
	public void setSegNum(String segNum) {
		this.segNum = segNum;
	}
	@Override
	public String toString() {
		return "StationParseForm [isMainStation=" + isMainStation + ", name="
				+ name + ", segNum=" + segNum + ", x=" + x + ", y=" + y + "]";
	}
	
}
