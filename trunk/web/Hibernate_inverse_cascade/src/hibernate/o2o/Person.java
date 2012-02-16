package hibernate.o2o;

import java.io.Serializable;

public class Person implements Serializable {
	
	private Integer id;
	private String name;
	private IdCard idCard;
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
	public IdCard getIdCard() {
		return idCard;
	}
	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", idCard=" + idCard + ", name=" + name
				+ "]";
	}

	
}
