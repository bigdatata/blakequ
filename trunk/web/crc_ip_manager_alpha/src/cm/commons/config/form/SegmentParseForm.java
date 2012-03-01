package cm.commons.config.form;

public class SegmentParseForm {

	private String stationUp;
	private String stationDown;
	public String getStationUp() {
		return stationUp;
	}
	public void setStationUp(String stationUp) {
		this.stationUp = stationUp;
	}
	public String getStationDown() {
		return stationDown;
	}
	public void setStationDown(String stationDown) {
		this.stationDown = stationDown;
	}
	@Override
	public String toString() {
		return "SegmentParseForm [stationDown=" + stationDown + ", stationUp="
				+ stationUp + "]";
	}
	
}
