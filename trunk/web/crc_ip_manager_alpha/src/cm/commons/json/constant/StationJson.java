package cm.commons.json.constant;
/**
 * 站点数据
 * @author Administrator
 *
 */
public class StationJson {

	private String station_name;//站点名称
	private String flag_value;//****特殊文件内容0（异常）、1（正常），空（错误）用于判断站点异常与否
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String stationName) {
		station_name = stationName;
	}
	public String getFlag_value() {
		return flag_value;
	}
	public void setFlag_value(String flagValue) {
		flag_value = flagValue;
	}
	@Override
	public String toString() {
		return "StationJson [flag_value=" + flag_value + ", station_name="
				+ station_name + "]";
	}
	
	
}
