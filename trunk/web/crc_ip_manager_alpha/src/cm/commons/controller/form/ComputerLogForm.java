package cm.commons.controller.form;

import java.util.Date;

public class ComputerLogForm {
	
	private int id;
	private int computer_id;
	private Float memRate;
	private Float cupRate;
	private Date currTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getComputer_id() {
		return computer_id;
	}
	public void setComputer_id(int computerId) {
		computer_id = computerId;
	}
	public Float getMemRate() {
		return memRate;
	}
	public void setMemRate(Float memRate) {
		this.memRate = memRate;
	}
	public Float getCupRate() {
		return cupRate;
	}
	public void setCupRate(Float cupRate) {
		this.cupRate = cupRate;
	}
	public Date getCurrTime() {
		return currTime;
	}
	public void setCurrTime(Date currTime) {
		this.currTime = currTime;
	}
	@Override
	public String toString() {
		return "ComputerLogForm [computer_id=" + computer_id + ", cupRate="
				+ cupRate + ", currTime=" + currTime + ", id=" + id
				+ ", memRate=" + memRate + "]";
	}
	
	
}
