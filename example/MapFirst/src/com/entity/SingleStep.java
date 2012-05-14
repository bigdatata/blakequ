package com.entity;

/**
 * steps 数组中的每个元素定义计算的路线中的一个路段。路段是路线线路的最小单位，包含一个路段介绍有关该行程的一个具体说明。
 * @author Administrator
 *
 */
public class SingleStep {
	int id;//第几个路段,steps[] 包含一组路段，这些路段用于指示有关行程路程的每个单独路段的信息。
	String duration_value;//表示持续时间（以秒为单位）。
	String duration_text;//包含持续时间的可人工读取的表示形式。
	String distance_value;//表示距离（以米为单位）
	String distance_text;//包含距离的可人工读取的表示形式，以起点所用的单位以及请求中指定的语言显示。
	String html_instructions;//走路指示， 包含此路段的已确定格式的说明（以 HTML 文本字符串表示）
	String start_location_lat;//开始点
	String start_location_lng;
	
	public int getId() {
		return id;
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
	public String getHtml_instructions() {
		return html_instructions;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setHtml_instructions(String htmlInstructions) {
		html_instructions = htmlInstructions;
	}
	
	public String getStart_location_lat() {
		return start_location_lat;
	}
	public String getStart_location_lng() {
		return start_location_lng;
	}
	public void setStart_location_lat(String startLocationLat) {
		start_location_lat = startLocationLat;
	}
	public void setStart_location_lng(String startLocationLng) {
		start_location_lng = startLocationLng;
	}
	@Override
	public String toString() {
		return "SingleStep [distance_text=" + distance_text
				+ ", distance_value=" + distance_value + ", duration_text="
				+ duration_text + ", duration_value=" + duration_value
				+ ", html_instructions=" + html_instructions + ", id=" + id
				+ ", start_location_lat=" + start_location_lat
				+ ", start_location_lng=" + start_location_lng + "]";
	}
	
}
