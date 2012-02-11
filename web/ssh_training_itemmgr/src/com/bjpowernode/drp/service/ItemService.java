package com.bjpowernode.drp.service;

import com.bjpowernode.drp.PageModel;
import com.bjpowernode.drp.domain.Item;

/**
 * ItemService接口定义
 * @author Administrator
 *
 */
public interface ItemService {

	/**
	 * 添加物料
	 * @param item
	 */
	public void addItem(Item item);
	
	/**
	 * 分页查询
	 * @param queryString
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageModel findAllItem( String queryString,
			int pageNo, int pageSize);
	
	/**
	 * 修改物料
	 * @param item
	 */
	public void modifyItem(Item item);
	
	/**
	 * 删除物料
	 * @param itemNos
	 */
	public void delItem(String[] itemNos);
	
	/**
	 * 根据物料代码查询
	 * @param itemNo
	 * @return
	 */
	public Item findItemById(String itemNo);
	
	/**
	 * 保存上传文件
	 * @param itemNo
	 * @param uploadFileName
	 */
	public void modifyUploadFileNameField(String itemNo, String uploadFileName);
}
