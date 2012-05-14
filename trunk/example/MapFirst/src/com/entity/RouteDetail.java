package com.entity;

import java.util.List;

public class RouteDetail {
	String status;//返回状态()
	/**
	 * OK 表示响应包含一个有效的 result。
	 * NOT_FOUND 表示至少有一个在请求的起点、目的地或路标中指定的位置无法进行地址解析。
	 * ZERO_RESULTS 表示无法在起点和终点之间找到路线。
	 * MAX_WAYPOINTS_EXCEEDED 表示请求中包含过多的 waypoints。允许的最大 waypoints 数为 8，再加上起点和目的地。（Google Maps Premier 客户可以在请求中提及多达 23 个路标。）
	 * INVALID_REQUEST 表示提供的请求无效。
	 * OVER_QUERY_LIMIT 表示该服务在允许的时间段内从您的应用程序收到了过多的请求。
	 * REQUEST_DENIED 表示该服务已拒绝您的应用程序使用路线服务。
	 * UNKNOWN_ERROR 表示路线请求因服务器出错而无法得到处理。如果您再试一次，该请求可能会成功
	 */
	String summary;//路段总体说明
	int stepNum;//路段数
	List<SingleStep> steps;//表示所有路段
	String travel_mode;//驾驶模式(driving,walking)
	String duration_value;//表示总路程持续时间（以秒为单位）。
	String duration_text;//包含总路程持续时间的可人工读取的表示形式。
	String distance_value;//表示总路程距离（以米为单位）
	String distance_text;//包含距离的可人工读取的表示形式，以起点所用的单位以及请求中指定的语言显示。
	
	String start_address;//包含反映此路程的 start_location 的可人工读取的地址（通常为街道地址）。
	String end_address;//包含反映此路程的 end_location 的可人工读取的地址（通常为街道地址）。
	
	String points;//整个路线的编码
	String warning;//警告信息
	public String getStatus() {
		return status;
	}
	public String getSummary() {
		return summary;
	}
	public int getStepNum() {
		return stepNum;
	}
	public List<SingleStep> getSteps() {
		return steps;
	}
	public String getTravel_mode() {
		return travel_mode;
	}
	public String getDuration_value() {
		return duration_value;
	}
	public String getDuration_text() {
		return duration_text;
	}
	public String getDistance_value() {
		return distance_value;
	}
	public String getDistance_text() {
		return distance_text;
	}
	public String getStart_address() {
		return start_address;
	}
	public String getEnd_address() {
		return end_address;
	}
	public String getPoints() {
		return points;
	}
	public String getWarning() {
		return warning;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}
	public void setSteps(List<SingleStep> steps) {
		this.steps = steps;
	}
	public void setTravel_mode(String travelMode) {
		travel_mode = travelMode;
	}
	public void setDuration_value(String durationValue) {
		duration_value = durationValue;
	}
	public void setDuration_text(String durationText) {
		duration_text = durationText;
	}
	public void setDistance_value(String distanceValue) {
		distance_value = distanceValue;
	}
	public void setDistance_text(String distanceText) {
		distance_text = distanceText;
	}
	public void setStart_address(String startAddress) {
		start_address = startAddress;
	}
	public void setEnd_address(String endAddress) {
		end_address = endAddress;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	@Override
	public String toString() {
		return "RouteDetail [distance_text=" + distance_text
				+ ", distance_value=" + distance_value + ", duration_text="
				+ duration_text + ", duration_value=" + duration_value
				+ ", end_address=" + end_address + ", points=" + points
				+ ", start_address=" + start_address + ", status=" + status
				+ ", stepNum=" + stepNum + ", steps=" + steps + ", summary="
				+ summary + ", travel_mode=" + travel_mode + ", warning="
				+ warning + "]";
	}
	
}
