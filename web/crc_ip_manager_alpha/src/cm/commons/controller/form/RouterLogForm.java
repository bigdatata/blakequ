package cm.commons.controller.form;

import java.util.Date;

public class RouterLogForm {

	private int id;
	private int routerId;
	private Float cpuRate;
	private Float memRate;
	private String routerInfo;
	private Date currTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRouterId() {
		return routerId;
	}
	public void setRouterId(int routerId) {
		this.routerId = routerId;
	}
	public Float getCpuRate() {
		return cpuRate;
	}
	public void setCpuRate(Float cpuRate) {
		this.cpuRate = cpuRate;
	}
	public Float getMemRate() {
		return memRate;
	}
	public void setMemRate(Float memRate) {
		this.memRate = memRate;
	}
	public String getRouterInfo() {
		return routerInfo;
	}
	public void setRouterInfo(String routerInfo) {
		this.routerInfo = routerInfo;
	}
	public Date getCurrTime() {
		return currTime;
	}
	public void setCurrTime(Date currTime) {
		this.currTime = currTime;
	}
	@Override
	public String toString() {
		return "RouterLogForm [cpuRate=" + cpuRate + ", currTime=" + currTime
				+ ", id=" + id + ", memRate="
				+ memRate + ", routerId=" + routerId + ", routerInfo="
				+ routerInfo + "]";
	}
	
	
}
