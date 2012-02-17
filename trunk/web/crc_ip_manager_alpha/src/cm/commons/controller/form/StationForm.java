package cm.commons.controller.form;

public class StationForm {
	private String name; //站点名称
	private double x;//该站点x坐标
	private double y;//该站点y坐标
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "StationForm [name=" + name + ", x=" + x + ", y=" + y + "]";
	}
	
	
}
