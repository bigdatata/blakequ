package cm.exchange.entity;

import java.io.Serializable;

/**
 * 
 * @author qh
 *
 */
public class Goods implements Serializable{

	private int id; // the id number of goods
	private String name;//goods name
	private String descript;//the content of goods description
	private String createData; //the create time of goods item
	private String saleData; // the sale time of goods
	private int goodsState; //0: saled, 1:saling
	private int catagory; // the catagory of goods
	private int price; //the price of goods
	private boolean isDiscussing; //the price can discussing or not
	private boolean havePicture; //have picture or not
	private String pictureAddress; //if have picture and the address
	private String longitude; //the longitude of goods position
	private String latitude; // the latitude of goods positon
	//new
	private int distance; //the distance of current position and goods position
	private int commentNum; // the number of comment
	private String createUser;
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getCreateData() {
		return createData;
	}
	public void setCreateData(String createData) {
		this.createData = createData;
	}
	public String getSaleData() {
		return saleData;
	}
	public void setSaleData(String saleData) {
		this.saleData = saleData;
	}
	public int getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(int goodsState) {
		this.goodsState = goodsState;
	}
	public int getCatagory() {
		return catagory;
	}
	public void setCatagory(int catagory) {
		this.catagory = catagory;
	}
	public boolean isDiscussing() {
		return isDiscussing;
	}
	public void setDiscussing(boolean isDiscussing) {
		this.isDiscussing = isDiscussing;
	}
	public boolean isHavePicture() {
		return havePicture;
	}
	public void setHavePicture(boolean havePicture) {
		this.havePicture = havePicture;
	}
	public String getPictureAddress() {
		return pictureAddress;
	}
	public void setPictureAddress(String pictureAddress) {
		this.pictureAddress = pictureAddress;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
