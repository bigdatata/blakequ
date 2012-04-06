package com.tencent;

public class FileInfo {
	String name;
	String md5;
	public FileInfo(){}
	public FileInfo(String name, String md5) {
		super();
		this.name = name;
		this.md5 = md5;
	}
	public String getName() {
		return name;
	}
	public String getMd5() {
		return md5;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	@Override
	public String toString() {
		return "FileInfo [md5=" + md5 + ", name=" + name + "]";
	}
	
}
