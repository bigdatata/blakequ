package cm.hao.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {
	
	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField
	private String username;
	
	@DatabaseField
	private int age;
	
	public int getId() {
		return id;
	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [age=" + age + ", id=" + id + ", username=" + username
				+ "]";
	}
	
}
