package com.itcast.logic;

import java.util.Map;

public class Task {
	private int taskID;// ������
	private Map taskParam;// �������
	public static final int TASK_USER_LOGIN = 6;// �û���¼��֤
	public static final int TASK_GET_USER_INFO = 0;// ��ȡ�û���ϸ��Ϣ
	public static final int TASK_GET_USER_HOMETIMEINLINE = 1;// ��ȡ�û���ҳ����
	public static final int TASK_GET_USER_IMAGE_ICON = 2;// ��ȡ�û�ͷ��ͼƬ
	public static final int TASK_GET_USER_FRIEND = 3;// ��ȡ�û����к���
	public static final int TASK_GET_USER_HOMETIMEINLINE_MORE = 4;// ��ȡ�û���ҳ������һҳ
	public static final int TASK_NEW_WEIBO = 5;// ������΢��
	public static final int TASK_NEW_WEIBO_PIC=7;//����ͼƬ΢��
	public static final int TASK_NEW_WEIBO_GPS=8;//����GPS΢��
	public static final int TASK_NEW_WEIBO_COMMENT = 9;// ����΢������
	public static final int TASK_GET_WEIBO_COMMENT = 10;//��ȡ΢���������б�
	public static final int TASK_GET_WEIBO_MESSAGE = 11;//��ȡ΢����˽���б�
	
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
