package com.bjpowernode.drp.web.forms;

import org.apache.struts.action.ActionForm;

/**
 * 登录ActionForm，负责表单收集数据
 * 表单的属性必须和ActionForm中的get和set的属性一致
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LoginActionForm extends ActionForm {
	
	private String username;
	
	private String password;

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
