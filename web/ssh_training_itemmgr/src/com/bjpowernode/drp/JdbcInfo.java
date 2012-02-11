package com.bjpowernode.drp;

/**
 * jdbc连接信息类
 * @author Administrator
 *
 */
public class JdbcInfo {

	@Override
	public String toString() {
		return this.getClass().getName() + "{driverName=" + this.driverName + 
			    ", url=" + this.url + 
			    ", username=" + this.username + 
			    ", password=" + this.password + "}";
	}

	private String driverName;
	
	private String url;
	
	private String username;
	
	private String password;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
