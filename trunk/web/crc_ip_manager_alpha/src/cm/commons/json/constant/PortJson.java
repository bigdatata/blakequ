package cm.commons.json.constant;

/**
 * 一个路由器有四个端口
 * @author Administrator
 *
 */
public class PortJson {
	private String routerIp;//这个标识此端口属于哪个路由器
	private String ifIndex;
	private String ifDescr;
	/**
	 * ifOperStatus取值
	 * INTEGER {
	 * up(1),       
	 * down(2),
	 * testing(3),   
	 * unknown(4),   
	 * dormant(5),
	 * notPresent(6),    
	 * lowerLayerDown(7) }
	 */
	private String ifOperStatus;//10,11,20,21(表示)
	private String ifInOctets;
	private String locIfInCRC;
	private String ipRouteDest;
	private String portIp;
	private String locIfInBitsSec;
	private String locIfOutBitsSec;
	private String ifOutOctets;
	private String warnStation;
	
	public PortJson(){}
	public PortJson(String routerIp, String ifIndex, String ifDescr,
			String ifOperStatus, String ifInOctets, String locIfInCRC,
			String ipRouteDest, String portIp, String locIfInBitsSec,
			String locIfOutBitsSec, String ifOutOctets, String warnStation) {
		super();
		this.routerIp = routerIp;
		this.ifIndex = ifIndex;
		this.ifDescr = ifDescr;
		this.ifOperStatus = ifOperStatus;
		this.ifInOctets = ifInOctets;
		this.locIfInCRC = locIfInCRC;
		this.ipRouteDest = ipRouteDest;
		this.portIp = portIp;
		this.locIfInBitsSec = locIfInBitsSec;
		this.locIfOutBitsSec = locIfOutBitsSec;
		this.ifOutOctets = ifOutOctets;
		this.warnStation = warnStation;
	}
	public String getRouterIp() {
		return routerIp;
	}
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}
	public String getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}
	public String getIfDescr() {
		return ifDescr;
	}
	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}
	public String getIfOperStatus() {
		return ifOperStatus;
	}
	public void setIfOperStatus(String ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}
	public String getIfInOctets() {
		return ifInOctets;
	}
	public void setIfInOctets(String ifInOctets) {
		this.ifInOctets = ifInOctets;
	}
	public String getLocIfInCRC() {
		return locIfInCRC;
	}
	public void setLocIfInCRC(String locIfInCRC) {
		this.locIfInCRC = locIfInCRC;
	}
	public String getIpRouteDest() {
		return ipRouteDest;
	}
	public void setIpRouteDest(String ipRouteDest) {
		this.ipRouteDest = ipRouteDest;
	}
	public String getPortIp() {
		return portIp;
	}
	public void setPortIp(String portIp) {
		this.portIp = portIp;
	}
	public String getLocIfInBitsSec() {
		return locIfInBitsSec;
	}
	public void setLocIfInBitsSec(String locIfInBitsSec) {
		this.locIfInBitsSec = locIfInBitsSec;
	}
	public String getLocIfOutBitsSec() {
		return locIfOutBitsSec;
	}
	public void setLocIfOutBitsSec(String locIfOutBitsSec) {
		this.locIfOutBitsSec = locIfOutBitsSec;
	}
	public String getIfOutOctets() {
		return ifOutOctets;
	}
	public void setIfOutOctets(String ifOutOctets) {
		this.ifOutOctets = ifOutOctets;
	}
	
	public String getWarnStation() {
		return warnStation;
	}
	public void setWarnStation(String warnStation) {
		this.warnStation = warnStation;
	}
	@Override
	public String toString() {
		return "PortJson [ifDescr=" + ifDescr + ", ifInOctets=" + ifInOctets
				+ ", ifIndex=" + ifIndex + ", ifOperStatus=" + ifOperStatus
				+ ", ifOutOctets=" + ifOutOctets + ", ipRouteDest="
				+ ipRouteDest + ", locIfInBitsSec=" + locIfInBitsSec
				+ ", locIfInCRC=" + locIfInCRC + ", locIfOutBitsSec="
				+ locIfOutBitsSec + ", portIp=" + portIp + ", routerIp="
				+ routerIp + "]";
	}
	
	
}
