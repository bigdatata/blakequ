package cm.commons.controller.form;

public class SegmentForm {
	private int id;//线段的id号（供监听故障变色处理使用）
	private int startX;//该线段起始站点的x坐标
	private int startY; //该线段起始站点的y坐标
	private int endX; //该线段结束站点的x坐标
	private int endY; //该线段结束站点的y坐标
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getEndX() {
		return endX;
	}
	public void setEndX(int endX) {
		this.endX = endX;
	}
	public int getEndY() {
		return endY;
	}
	public void setEndY(int endY) {
		this.endY = endY;
	}
	@Override
	public String toString() {
		return "SegmentForm [endX=" + endX + ", endY=" + endY + ", id=" + id
				+ ", startX=" + startX + ", startY=" + startY + "]";
	}
	
	
}
