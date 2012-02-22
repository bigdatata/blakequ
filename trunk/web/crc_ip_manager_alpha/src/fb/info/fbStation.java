package fb.info;

public class fbStation {

	private int id;
	private String name;
	private double x;
	private double y;
	public fbStation(int id,String name,double x,double y){
		this.id=id;
		this.name=name;
		this.x=x;
		this.y=y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
}
