package cm.commons.config.form;

public class RouteParseForm {

	private String name;
	private String stationNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStationNum() {
		return stationNum;
	}
	public void setStationNum(String stationNum) {
		this.stationNum = stationNum;
	}
	@Override
	public String toString() {
		return "RouteParseForm [name=" + name + ", stationNum=" + stationNum
				+ "]";
	}
	
}
