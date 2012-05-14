package com.hao.audio;

import java.io.Serializable;

/**
 * audio information
 * @author Administrator
 *
 */
public class AudioInfo implements Serializable {
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
