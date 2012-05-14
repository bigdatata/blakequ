package com.hao.video;

import java.io.Serializable;

public class VideoInfo implements Serializable{
	//info
	String name;
	String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
