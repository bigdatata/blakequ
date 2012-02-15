package cm.commons.pojos;

import java.util.Date;

/**
 * Warn entity. @author MyEclipse Persistence Tools
 */

public class Warn implements java.io.Serializable {

	// Fields

	private Integer id;
	private String warncontent;
	private Integer warnstate;
	private Date warntime;
	private Integer stationId;

	// Constructors

	/** default constructor */
	public Warn() {
	}

	/** full constructor */
	public Warn(String warncontent, Integer warnstate, Date warntime,
			Integer stationId) {
		this.warncontent = warncontent;
		this.warnstate = warnstate;
		this.warntime = warntime;
		this.stationId = stationId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWarncontent() {
		return this.warncontent;
	}

	public void setWarncontent(String warncontent) {
		this.warncontent = warncontent;
	}

	public Integer getWarnstate() {
		return this.warnstate;
	}

	public void setWarnstate(Integer warnstate) {
		this.warnstate = warnstate;
	}

	public Date getWarntime() {
		return this.warntime;
	}

	public void setWarntime(Date warntime) {
		this.warntime = warntime;
	}

	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	@Override
	public String toString() {
		return "Warn [id=" + id + ", stationId=" + stationId + ", warncontent="
				+ warncontent + ", warnstate=" + warnstate + ", warntime="
				+ warntime + "]";
	}

}