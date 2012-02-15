package cm.commons.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Router entity. @author MyEclipse Persistence Tools
 */

public class Router implements java.io.Serializable {

	// Fields

	private Integer id;
	private String routerIp;
	private Integer state;
	private Integer portCount;
	private String routerInfo;
	private Station station;
	private Set ports = new HashSet(0);
	private Set routerLogs = new HashSet(0);

	// Constructors

	/** default constructor */
	public Router() {
	}

	/** minimal constructor */
	public Router(String routerIp) {
		this.routerIp = routerIp;
	}

	/** full constructor */
	public Router(String routerIp, Integer state, Integer portCount,
			String routerInfo, Set ports, Station station, Set routerLogs) {
		this.routerIp = routerIp;
		this.state = state;
		this.portCount = portCount;
		this.routerInfo = routerInfo;
		this.ports = ports;
		this.station = station;
		this.routerLogs = routerLogs;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRouterIp() {
		return this.routerIp;
	}

	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPortCount() {
		return this.portCount;
	}

	public void setPortCount(Integer portCount) {
		this.portCount = portCount;
	}

	public String getRouterInfo() {
		return this.routerInfo;
	}

	public void setRouterInfo(String routerInfo) {
		this.routerInfo = routerInfo;
	}

	public Set getPorts() {
		return this.ports;
	}

	public void setPorts(Set ports) {
		this.ports = ports;
	}

	public Station getStation() {
		return this.station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Set getRouterLogs() {
		return this.routerLogs;
	}

	public void setRouterLogs(Set routerLogs) {
		this.routerLogs = routerLogs;
	}

}