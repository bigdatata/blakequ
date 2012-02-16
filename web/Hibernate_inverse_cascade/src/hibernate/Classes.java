package hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Classes implements Serializable {

	private Integer id;
	private String name;
	private Set students = new HashSet(0);
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
	public Set getStudents() {
		return students;
	}
	public void setStudents(Set students) {
		this.students = students;
	}
//	@Override
//	public String toString() {
//		return "Classes [id=" + id + ", name=" + name + ", students="
//				+ students + "]";
//	}
	
	
}
