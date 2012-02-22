package cm.commons.controller.form;

public class AlarmForm {
	private int rid;//告警所属线路id
	private String id;//站点id
	private int sid;//如果是线段，则填写线段id，如果是线路则填写-1;
	private int state;//故障状态，包括未知，故障，0(正常),1(异常),2(未知)等
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return "Alarm [rid=" + rid + ", sid=" + sid
				+ ", state=" + state + "]";
	}
	
}
