package cm.commons.controller.form;

import java.util.Date;

public class PortForm {

	private Integer id;
	private Date getTime;//time
	private String ifDescr;//端口名称
	private String portIp;//ip
	private Integer locIfInCrc;//crc
	private Integer ifOperStatus;//状态
	private Integer locIfInBitsSec;
	private Integer locIfOutBitsSec;
	private Integer ifInOctets;
	private Integer ifOutOctets;
	private String stationName;
	
	public String getIfDescr() {
		return ifDescr;
	}
	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getGetTime() {
		return getTime;
	}
	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
	
	public String getPortIp() {
		return portIp;
	}
	public void setPortIp(String portIp) {
		this.portIp = portIp;
	}
	public Integer getLocIfInCrc() {
		return locIfInCrc;
	}
	public void setLocIfInCrc(Integer locIfInCrc) {
		this.locIfInCrc = locIfInCrc;
	}
	public Integer getIfOperStatus() {
		return ifOperStatus;
	}
	public void setIfOperStatus(Integer ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}
	public Integer getLocIfInBitsSec() {
		return locIfInBitsSec;
	}
	public void setLocIfInBitsSec(Integer locIfInBitsSec) {
		this.locIfInBitsSec = locIfInBitsSec;
	}
	public Integer getLocIfOutBitsSec() {
		return locIfOutBitsSec;
	}
	public void setLocIfOutBitsSec(Integer locIfOutBitsSec) {
		this.locIfOutBitsSec = locIfOutBitsSec;
	}
	public Integer getIfInOctets() {
		return ifInOctets;
	}
	public void setIfInOctets(Integer ifInOctets) {
		this.ifInOctets = ifInOctets;
	}
	public Integer getIfOutOctets() {
		return ifOutOctets;
	}
	public void setIfOutOctets(Integer ifOutOctets) {
		this.ifOutOctets = ifOutOctets;
	}
	@Override
	public String toString() {
		return "PortForm [getTime=" + getTime + ", id=" + id + ", ifInOctets="
				+ ifInOctets + ", ifDescr=" + ifDescr + ", ifOperStatus="
				+ ifOperStatus + ", ifOutOctets=" + ifOutOctets
				+ ", locIfInBitsSec=" + locIfInBitsSec + ", locIfInCrc="
				+ locIfInCrc + ", locIfOutBitsSec=" + locIfOutBitsSec
				+ ", portIp=" + portIp + "]";
	}
	
}
