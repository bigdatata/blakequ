package util;

public class FileLogInfo {
	private Long id;//the id of file
	private String path;//the path of file
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public FileLogInfo(Long id, String path) {
		this.id = id;
		this.path = path;
	}	
}
