package com.itcast.logic;

import java.util.Map;

public class Task {
	private int taskID;// 任务编号
	private Map taskParam;// 任务参数
	public static final int TASK_USER_LOGIN = 6;// 用户登录验证
	public static final int TASK_GET_USER_INFO = 0;// 获取用户详细信息
	public static final int TASK_GET_USER_HOMETIMEINLINE = 1;// 获取用户首页博客
	public static final int TASK_GET_USER_IMAGE_ICON = 2;// 获取用户头象图片
	public static final int TASK_GET_USER_FRIEND = 3;// 获取用户所有好友
	public static final int TASK_GET_USER_HOMETIMEINLINE_MORE = 4;// 获取用户首页博客下一页
	public static final int TASK_NEW_WEIBO = 5;// 发表新微博
	public static final int TASK_NEW_WEIBO_PIC=7;//发表图片微博
	public static final int TASK_NEW_WEIBO_GPS=8;//发表GPS微博
	public static final int TASK_NEW_WEIBO_COMMENT = 9;// 发表微博评论
	public static final int TASK_GET_WEIBO_COMMENT = 10;//获取微博的评论列表
	public static final int TASK_GET_WEIBO_MESSAGE = 11;//获取微博的私信列表
	
	public Task(int id, Map param) {
		this.taskID = id;
		this.taskParam = param;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Map getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map taskParam) {
		this.taskParam = taskParam;
	}
}
