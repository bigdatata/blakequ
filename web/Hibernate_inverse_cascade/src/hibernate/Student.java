package hibernate;

import java.io.Serializable;

public class Student implements Serializable {

	private Integer id;
	private String name;
	private Classes classes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	@Override
	public String toString() {
		return "Student [classes=" + classes + ", id=" + id + ", name=" + name
				+ "]";
	}
	
	
}
