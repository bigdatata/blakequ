package cm.commons.pojos;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Port entity. @author MyEclipse Persistence Tools
 */

public class Port implements java.io.Serializable {

	// Fields

	private Integer id;
	private Router router;
	private Date getTime;
	private Integer ifIndex;
	private String ifDescr;
	/**
	 * INTEGER {up(1),       
	 * down(2),
	 * testing(3),   
	 * unknown(4),   
	 * dormant(5),
	 * notPresent(6),    
	 * lowerLayerDown(7) 
	 * }
	 */
	private Integer ifOperStatus;
	private Integer ifInOctets;
	private Integer locIfInCrc;
	private String ipRouteDest;
	private String portIp;
	private Integer locIfInBitsSec;
	private Integer locIfOutBitsSec;
	private Integer ifOutOctets;

	// Constructors

	/** default constructor */
	public Port() {
	}


	/** full constructor */
	public Port(Router router, Date getTime, Integer ifIndex,
			String ifDescr, Integer ifOperStatus, Integer ifInOctets,
			Integer locIfInCrc, String ipRouteDest, String portIp,
			Integer locIfInBitsSec, Integer locIfOutBitsSec, Integer ifOutOctets) {
		this.router = router;
		this.getTime = getTime;
		this.ifIndex = ifIndex;
		this.ifDescr = ifDescr;
		this.ifOperStatus = ifOperStatus;
		this.ifInOctets = ifInOctets;
		this.locIfInCrc = locIfInCrc;
		this.ipRouteDest = ipRouteDest;
		this.portIp = portIp;
		this.locIfInBitsSec = locIfInBitsSec;
		this.locIfOutBitsSec = locIfOutBitsSec;
		this.ifOutOctets = ifOutOctets;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Router getRouter() {
		return router;
	}


	public void setRouter(Router router) {
		this.router = router;
	}


	public Date getGetTime() {
		return getTime;
	}


	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}


	public Integer getIfIndex() {
		return this.ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public String getIfDescr() {
		return this.ifDescr;
	}

	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}

	public Integer getIfOperStatus() {
		return this.ifOperStatus;
	}

	public void setIfOperStatus(Integer ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}

	public Integer getIfInOctets() {
		return this.ifInOctets;
	}

	public void setIfInOctets(Integer ifInOctets) {
		this.ifInOctets = ifInOctets;
	}

	public Integer getLocIfInCrc() {
		return this.locIfInCrc;
	}

	public void setLocIfInCrc(Integer locIfInCrc) {
		this.locIfInCrc = locIfInCrc;
	}

	public String getIpRouteDest() {
		return this.ipRouteDest;
	}

	public void setIpRouteDest(String ipRouteDest) {
		this.ipRouteDest = ipRouteDest;
	}

	public String getPortIp() {
		return this.portIp;
	}

	public void setPortIp(String portIp) {
		this.portIp = portIp;
	}

	public Integer getLocIfInBitsSec() {
		return this.locIfInBitsSec;
	}

	public void setLocIfInBitsSec(Integer locIfInBitsSec) {
		this.locIfInBitsSec = locIfInBitsSec;
	}

	public Integer getLocIfOutBitsSec() {
		return this.locIfOutBitsSec;
	}

	public void setLocIfOutBitsSec(Integer locIfOutBitsSec) {
		this.locIfOutBitsSec = locIfOutBitsSec;
	}

	public Integer getIfOutOctets() {
		return this.ifOutOctets;
	}

	public void setIfOutOctets(Integer ifOutOctets) {
		this.ifOutOctets = ifOutOctets;
	}

}