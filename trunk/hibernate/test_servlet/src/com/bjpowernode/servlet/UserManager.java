package com.bjpowernode.servlet;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

	public void add(String username) {
		System.out.println("UserManager.add() -->> usernamne:" + username);
	}
	
	public void del(String username) {
		System.out.println("UserManager.del() -->> usernamne:" + username);
	}
	
	public void modify(String username) {
		System.out.println("UserManager.modify() -->> usernamne:" + username);
	}
	
	public List query(String username) {
		System.out.println("UserManager.query() -->> usernamne:" + username);
		
		List userList = new ArrayList();
		userList.add("a");
		userList.add("b");
		userList.add("c");
		return userList;
	}
	
}
