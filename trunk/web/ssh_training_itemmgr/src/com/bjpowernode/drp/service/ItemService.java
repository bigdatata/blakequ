package com.bjpowernode.drp.service;

import com.bjpowernode.drp.PageModel;
import com.bjpowernode.drp.domain.Item;

/**
 * ItemService�ӿڶ���
 * @author Administrator
 *
 */
public interface ItemService {

	/**
	 * �������
	 * @param item
	 */
	public void addItem(Item item);
	
	/**
	 * ��ҳ��ѯ
	 * @param queryString
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageModel findAllItem( String queryString,
			int pageNo, int pageSize);
	
	/**
	 * �޸�����
	 * @param item
	 */
	public void modifyItem(Item item);
	
	/**
	 * ɾ������
	 * @param itemNos
	 */
	public void delItem(String[] itemNos);
	
	/**
	 * �������ϴ����ѯ
	 * @param itemNo
	 * @return
	 */
	public Item findItemById(String itemNo);
	
	/**
	 * �����ϴ��ļ�
	 * @param itemNo
	 * @param uploadFileName
	 */
	public void modifyUploadFileNameField(String itemNo, String uploadFileName);
}
