package cm.commons.controller.form;

import java.util.List;

public class PageModelForm<E> {

	private int pageNo;//当前页面
	private int pageSize;//每页数量
	private int buttomPageNo;//末页
	private int totalPages;//总页数
	private List<E> data;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getButtomPageNo() {
		return buttomPageNo;
	}
	public void setButtomPageNo(int buttomPageNo) {
		this.buttomPageNo = buttomPageNo;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public List<E> getData() {
		return data;
	}
	public void setData(List<E> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "PageModelForm [buttomPageNo=" + buttomPageNo + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", totalPages=" + totalPages + "]";
	}
	
	
	
}
