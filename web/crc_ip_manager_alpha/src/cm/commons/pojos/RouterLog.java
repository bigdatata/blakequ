package cm.commons.pojos;

import java.util.Date;


/**
 * RouterLog entity. @author MyEclipse Persistence Tools
 */

public class RouterLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Router router;
	private Float cpuRate;
	private Float memRate;
	private String routerInfo;
	private Date currTime;

	// Constructors

	/** default constructor */
	public RouterLog() {
	}

	/** minimal constructor */
	public RouterLog(Router router) {
		this.router = router;
	}

	/** full constructor */
	public RouterLog(Router router, Float cpuRate,
			Float memRate, String routerInfo,
			Date currTime) {
		this.router = router;
		this.cpuRate = cpuRate;
		this.memRate = memRate;
		this.routerInfo = routerInfo;
		this.currTime = currTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Router getRouter() {
		return this.router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public Float getCpuRate() {
		return this.cpuRate;
	}

	public void setCpuRate(Float cpuRate) {
		this.cpuRate = cpuRate;
	}


	public Float getMemRate() {
		return this.memRate;
	}

	public void setMemRate(Float memRate) {
		this.memRate = memRate;
	}

	public String getRouterInfo() {
		return this.routerInfo;
	}

	public void setRouterInfo(String routerInfo) {
		this.routerInfo = routerInfo;
	}

	public Date getCurrTime() {
		return this.currTime;
	}

	public void setCurrTime(Date currTime) {
		this.currTime = currTime;
	}

	@Override
	public String toString() {
		return "RouterLog [cpuRate=" + cpuRate + ", currTime=" + currTime
				+ ", id=" + id + ", memRate="
				+ memRate + ", router=" + router + ", routerInfo=" + routerInfo
				+ "]";
	}

}