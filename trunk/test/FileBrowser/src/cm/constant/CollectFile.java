package cm.constant;

public class CollectFile {
	private String type;
	private String path;
	private String date;
	private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CollectFile [date=" + date + ", name=" + name + ", path="
				+ path + ", type=" + type + "]";
	}
	
	
}
