package cm.commons.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Computer entity. @author MyEclipse Persistence Tools
 */

public class Computer implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private Integer state;
	private String os;
	private Station station;
	private Set computerLogs = new HashSet(0);

	// Constructors

	/** default constructor */
	public Computer() {
	}

	/** minimal constructor */
	public Computer(String ip) {
		this.ip = ip;
	}

	/** full constructor */
	public Computer(String ip, Integer state, String os, Station station) {
		this.ip = ip;
		this.state = state;
		this.os = os;
		this.station = station;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", ip=" + ip + ", os=" + os + ", state="
				+ state + ", station=" + station + "]";
	}

	public Set getComputerLogs() {
		return computerLogs;
	}

	public void setComputerLogs(Set computerLogs) {
		this.computerLogs = computerLogs;
	}
	
	
}