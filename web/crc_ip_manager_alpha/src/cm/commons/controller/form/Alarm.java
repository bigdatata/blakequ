package cm.commons.controller.form;

public class Alarm {
	private int rid;//告警所属线路id
	private String name;//如果是站点，则填写站点名称，如果是线段则填写null;
	private int sid;//如果是线段，则填写线段id，如果是线路则填写-1;
	private int state;//故障状态，包括未知，故障，等
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Alarm [name=" + name + ", rid=" + rid + ", sid=" + sid
				+ ", state=" + state + "]";
	}
	
}
