package cm.commons.controller.form;


public class RouterForm {

	private Integer id;
	private Integer stationId;
	private String routerIp;
	private Integer state;
	private Integer portCount;
	private String routerInfo;
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
	public String getRouterIp() {
		return routerIp;
	}
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getPortCount() {
		return portCount;
	}
	public void setPortCount(Integer portCount) {
		this.portCount = portCount;
	}
	public String getRouterInfo() {
		return routerInfo;
	}
	public void setRouterInfo(String routerInfo) {
		this.routerInfo = routerInfo;
	}
	@Override
	public String toString() {
		return "RouterForm [id=" + id + ", portCount=" + portCount
				+ ", routerInfo=" + routerInfo + ", routerIp=" + routerIp
				+ ", state=" + state + ", stationId=" + stationId + "]";
	}
	
	
}
