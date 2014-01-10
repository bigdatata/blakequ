package cm.exchange.entity;

import java.io.Serializable;

public class User implements Serializable{
	private int id;
	private String username;
	private String location;
	private String telephone;
	private String qq;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", location=" + location
				+ ", qq=" + qq + ", telephone=" + telephone + ", username="
				+ username + "]";
	}
	
}
