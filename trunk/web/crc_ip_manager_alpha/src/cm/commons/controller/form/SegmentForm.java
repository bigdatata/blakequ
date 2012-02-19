package cm.commons.controller.form;

public class SegmentForm {
	private int id;//线段的id号（供监听故障变色处理使用）
	private double startX;//该线段起始站点的x坐标
	private double startY; //该线段起始站点的y坐标
	private double endX; //该线段结束站点的x坐标
	private double endY; //该线段结束站点的y坐标
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getStartX() {
		return startX;
	}
	public void setStartX(double startX) {
		this.startX = startX;
	}
	public double getStartY() {
		return startY;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public double getEndX() {
		return endX;
	}
	public void setEndX(double endX) {
		this.endX = endX;
	}
	public double getEndY() {
		return endY;
	}
	public void setEndY(double endY) {
		this.endY = endY;
	}
	@Override
	public String toString() {
		return "SegmentForm [endX=" + endX + ", endY=" + endY + ", id=" + id
				+ ", startX=" + startX + ", startY=" + startY + "]";
	}
	
	
}
