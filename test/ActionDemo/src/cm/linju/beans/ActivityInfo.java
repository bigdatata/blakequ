package cm.linju.beans;

import java.io.Serializable;

public class ActivityInfo implements Serializable{
	private String id;//活动id
	private String beginTime;//活动开始时间
	private String endTime;//结束时间
	private String title;//活动题目
	private String place;//活动地点
	private String type;//活动类型
	private String detail;//活动具体内容
	private String visibleType;//活动可见性
	private String createUser;//创建者
	private String viewNum;//浏览过的人数
	private String replyNum;//回复数
	private String participationNum;//参与人数	
	private String mainPicture;//创建活动的图片
	private String score;//活动评分
	
	
	private boolean isOtherPicture;//是否有活动后期图片
	private String otherPicture;//活动后期图片
	
	private boolean isSponsor;//是否有赞助
	private String sponsorName;//赞助商
	private boolean groupVisible;//团体可见性
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getVisibleType() {
		return visibleType;
	}
	public void setVisibleType(String visibleType) {
		this.visibleType = visibleType;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getViewNum() {
		return viewNum;
	}
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}
	public String getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}
	public String getParticipationNum() {
		return participationNum;
	}
	public void setParticipationNum(String participationNum) {
		this.participationNum = participationNum;
	}
	public String getMainPicture() {
		return mainPicture;
	}
	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}
	public boolean isOtherPicture() {
		return isOtherPicture;
	}
	public void setOtherPicture(boolean isOtherPicture) {
		this.isOtherPicture = isOtherPicture;
	}
	public String getOtherPicture() {
		return otherPicture;
	}
	public void setOtherPicture(String otherPicture) {
		this.otherPicture = otherPicture;
	}
	public boolean isSponsor() {
		return isSponsor;
	}
	public void setSponsor(boolean isSponsor) {
		this.isSponsor = isSponsor;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public boolean isGroupVisible() {
		return groupVisible;
	}
	public void setGroupVisible(boolean groupVisible) {
		this.groupVisible = groupVisible;
	}
	
	
	
	

}
