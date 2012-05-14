package cm.linju.beans;

import java.io.Serializable;

public class ActivityInfo implements Serializable{
	private String id;//�id
	private String beginTime;//���ʼʱ��
	private String endTime;//����ʱ��
	private String title;//���Ŀ
	private String place;//��ص�
	private String type;//�����
	private String detail;//���������
	private String visibleType;//��ɼ���
	private String createUser;//������
	private String viewNum;//�����������
	private String replyNum;//�ظ���
	private String participationNum;//��������	
	private String mainPicture;//�������ͼƬ
	private String score;//�����
	
	
	private boolean isOtherPicture;//�Ƿ��л����ͼƬ
	private String otherPicture;//�����ͼƬ
	
	private boolean isSponsor;//�Ƿ�������
	private String sponsorName;//������
	private boolean groupVisible;//����ɼ���
	
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
