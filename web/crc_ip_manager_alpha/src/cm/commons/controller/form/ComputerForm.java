package cm.commons.controller.form;

import cm.commons.pojos.Station;

public class ComputerForm {
	
	private Integer id;
	private Integer stationId;
	private String ip;
	private Integer state;
	private String os;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	@Override
	public String toString() {
		return "ComputerForm [stationId=" + stationId + ", id=" + id
				+ ", ip=" + ip + ", os=" + os + ", state=" + state + "]";
	}
	
	
	
}
