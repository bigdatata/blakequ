package com.bjpowernode.drp;

import java.util.List;

/**
 * ��װ��ҳ�߼�
 * @author Administrator
 *
 */
public class PageModel<T> {
	
	//�����
	private List<T> list;
	
	//��¼��
	private int totalRecords;
	
	//ÿҳ����������
	private int pageSize;
	
	//�ڼ�ҳ
	private int pageNo;

	/**
	 * ������ҳ��
	 * @return
	 */
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}
	
	/**
	 * ��ҳ
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}
	
	/**
	 * ��һҳ 
	 * @return
	 */
	public int getPreviousPageNo() {
		if (this.pageNo <= 1) {
			return 1;
		}
		return this.pageNo - 1;
	}
	
	/**
	 * ��һҳ
	 * @return
	 */
	public int getNextPageNo() {
		if (this.pageNo >= getButtomPageNo()) {
			return getButtomPageNo();
		}
		return this.pageNo + 1;
	}
	
	/**
	 * βҳ
	 * @return
	 */
	public int getButtomPageNo() {
		return getTotalPages();
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
