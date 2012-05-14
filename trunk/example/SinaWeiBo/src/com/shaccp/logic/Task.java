package com.shaccp.logic;

import java.util.Map;

public class Task {

	private int taskId;
	private Map params;
	
	public static final int TASK_LOGIN = 1;
	public static final int TASK_GET_TIMELINE = 2;
	public static final int TASK_NEW_WEIBO = 3;
	public static final int TASK_GET_USER_ICON=4;
	
	public Task() {
		super();
	}

	public Task(int taskId, Map params) {
		super();
		this.taskId = taskId;
		this.params = params;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

}
