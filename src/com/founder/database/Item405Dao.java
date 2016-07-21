package com.founder.database;

import java.util.List;
import java.util.Map;

import com.founder.beans.Tx405Detail;
import com.founder.beans.Tx405Item;

public interface Item405Dao {
	
	/**
	 * 将筛选结果插入数据库
	 */
	void addItem(Tx405Item item);
	
	/**
	 * 分页查询筛选结果
	 */
	List<Tx405Item> queryItemsByPage(Map<String,?> param);
	
	/**
	 * 查询总个数
	 */
	Integer queryItemsCount(Map<String,?> param);
	
	/**
	 * 根据ID查询
	 */
	Tx405Item queryItemsById(long id);
	
	/**
	 * 更新筛选结果，确定是否上传
	 */
	void updateItem(Tx405Item item);

	/**
	 * 更新筛选结果，确定是否上传
	 */
	void updateItemStatus(Tx405Item item);

	
	/**
	 * 插入交易流水详细
	 */
	void addItemDetail(Tx405Detail detail);

	/**
	 * 根据ItemID查询交易流水明细
	 */
	List<Tx405Detail> queryItemDetail(long id);
	
	/**
	 * 根据内部账号查询开户行号
	 */
	Integer getOpenBankId(String account);
}
