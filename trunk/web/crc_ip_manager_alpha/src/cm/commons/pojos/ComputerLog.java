package cm.commons.pojos;

import java.util.Date;

/**
 * ComputerLog entity. @author MyEclipse Persistence Tools
 */

public class ComputerLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Computer computer;
	private Float memRate;
	private Float cupRate;
	private Date currTime;

	// Constructors

	/** default constructor */
	public ComputerLog() {
	}

	/** minimal constructor */
	public ComputerLog(Computer computer) {
		this.computer = computer;
	}

	/** full constructor */
	public ComputerLog(Computer computer, Float memRate,
			Float cupRate, Date currTime) {
		this.computer = computer;
		this.memRate = memRate;
		this.cupRate = cupRate;
		this.currTime = currTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Computer getComputer() {
		return this.computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public Float getMemRate() {
		return this.memRate;
	}

	public void setMemRate(Float memRate) {
		this.memRate = memRate;
	}

	public Float getCupRate() {
		return this.cupRate;
	}

	public void setCupRate(Float cupRate) {
		this.cupRate = cupRate;
	}

	public Date getCurrTime() {
		return this.currTime;
	}

	public void setCurrTime(Date currTime) {
		this.currTime = currTime;
	}

	@Override
	public String toString() {
		return "ComputerLog [computer=" + computer + ", cupRate=" + cupRate
				+ ", currTime=" + currTime + ", id=" + id + ", memRate="
				+ memRate + "]";
	}

}