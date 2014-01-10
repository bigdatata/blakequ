package cm.exchange.entity;

public class Comment {

	private int uid;
	private String username;
	private String time; //comment time
	private String content; //comment content
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Comment [content=" + content + ", time=" + time + ", uid="
				+ uid + ", username=" + username + "]";
	}
	
	
}
