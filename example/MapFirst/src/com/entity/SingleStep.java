package com.entity;

/**
 * steps �����е�ÿ��Ԫ�ض�������·���е�һ��·�Ρ�·����·����·����С��λ������һ��·�ν����йظ��г̵�һ������˵����
 * @author Administrator
 *
 */
public class SingleStep {
	int id;//�ڼ���·��,steps[] ����һ��·�Σ���Щ·������ָʾ�й��г�·�̵�ÿ������·�ε���Ϣ��
	String duration_value;//��ʾ����ʱ�䣨����Ϊ��λ����
	String duration_text;//��������ʱ��Ŀ��˹���ȡ�ı�ʾ��ʽ��
	String distance_value;//��ʾ���루����Ϊ��λ��
	String distance_text;//��������Ŀ��˹���ȡ�ı�ʾ��ʽ����������õĵ�λ�Լ�������ָ����������ʾ��
	String html_instructions;//��·ָʾ�� ������·�ε���ȷ����ʽ��˵������ HTML �ı��ַ�����ʾ��
	String start_location_lat;//��ʼ��
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
