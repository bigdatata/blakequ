package com.bjpowernode.drp.domain;


/**
 * 物料信息
 * @author Administrator
 *
 */
public class Item {

	private String itemNo;
	
	private String itemName;
	
	private String spec;
	
	private String pattern;
	
	private ItemCategory itemCategory;
	
	private ItemUnit itemUnit;
	
	private String uploadFileName;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public ItemUnit getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(ItemUnit itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}
