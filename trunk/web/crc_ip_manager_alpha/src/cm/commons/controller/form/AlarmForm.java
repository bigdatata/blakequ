package cm.commons.controller.form;

import java.util.Date;

public class AlarmForm {
	private int id;//告警id
	private int station_id;//站点id
	private int sg1;//如果是线段，开始站点
	private int sg2;//如果是线段，终止站点
	private int segment_id;//如果是线段
	private int state;//故障状态，包括未知，故障，0(正常),1(异常),2(未知)等
	private String info;
	private Date time;
	private String stationName;
	
	
	public int getSg1() {
		return sg1;
	}
	public void setSg1(int sg1) {
		this.sg1 = sg1;
	}
	public int getSg2() {
		return sg2;
	}
	public void setSg2(int sg2) {
		this.sg2 = sg2;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public int getStation_id() {
		return station_id;
	}
	public void setStation_id(int stationId) {
		station_id = stationId;
	}
	public int getSegment_id() {
		return segment_id;
	}
	public void setSegment_id(int segmentId) {
		segment_id = segmentId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "AlarmForm [info=" + info + ", segment_id=" + segment_id
				+ ", state=" + state + ", station_id=" + station_id + ", time="
				+ time + "]";
	}

	
}
