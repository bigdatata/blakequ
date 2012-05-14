package com.entity;

import java.util.List;

public class RouteDetail {
	String status;//����״̬()
	/**
	 * OK ��ʾ��Ӧ����һ����Ч�� result��
	 * NOT_FOUND ��ʾ������һ�����������㡢Ŀ�ĵػ�·����ָ����λ���޷����е�ַ������
	 * ZERO_RESULTS ��ʾ�޷��������յ�֮���ҵ�·�ߡ�
	 * MAX_WAYPOINTS_EXCEEDED ��ʾ�����а�������� waypoints���������� waypoints ��Ϊ 8���ټ�������Ŀ�ĵء���Google Maps Premier �ͻ��������������ἰ��� 23 ��·�ꡣ��
	 * INVALID_REQUEST ��ʾ�ṩ��������Ч��
	 * OVER_QUERY_LIMIT ��ʾ�÷����������ʱ����ڴ�����Ӧ�ó����յ��˹��������
	 * REQUEST_DENIED ��ʾ�÷����Ѿܾ�����Ӧ�ó���ʹ��·�߷���
	 * UNKNOWN_ERROR ��ʾ·�������������������޷��õ��������������һ�Σ���������ܻ�ɹ�
	 */
	String summary;//·������˵��
	int stepNum;//·����
	List<SingleStep> steps;//��ʾ����·��
	String travel_mode;//��ʻģʽ(driving,walking)
	String duration_value;//��ʾ��·�̳���ʱ�䣨����Ϊ��λ����
	String duration_text;//������·�̳���ʱ��Ŀ��˹���ȡ�ı�ʾ��ʽ��
	String distance_value;//��ʾ��·�̾��루����Ϊ��λ��
	String distance_text;//��������Ŀ��˹���ȡ�ı�ʾ��ʽ����������õĵ�λ�Լ�������ָ����������ʾ��
	
	String start_address;//������ӳ��·�̵� start_location �Ŀ��˹���ȡ�ĵ�ַ��ͨ��Ϊ�ֵ���ַ����
	String end_address;//������ӳ��·�̵� end_location �Ŀ��˹���ȡ�ĵ�ַ��ͨ��Ϊ�ֵ���ַ����
	
	String points;//����·�ߵı���
	String warning;//������Ϣ
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
