package com.founder.database2;

import java.util.List;
import java.util.Map;

import com.founder.beans.TransFlow01;

public interface TransFlow01Dao {

	/**
	 * 获取个人交易明细
	 */
	List<TransFlow01> listgrTrans01(Map<String,?> param);
	/**
	 * 获取个人交易明细总数
	 */
	Integer listgrTrans01Count(Map<String,?> param);
	
	/**
	 * 获取对公交易明细
	 */
	List<TransFlow01> listdgTrans01(Map<String,?> param);
	/**
	 * 获取对公交易明细总数
	 */
	Integer listdgTrans01Count(Map<String,?> param);
	
	/**
	 * 根据证件号获取所有个人账户的余额
	 * 暂时不管证件相同姓名不一致的问题，只查证件号码
	 */
	Map<String,Object> getgrBalance(Map<String,?> param);
	
	/**
	 * 根据证件号获取所有对公账户的余额
	 * 暂时不管证件相同姓名不一致的问题，只查证件号码
	 */
	Map<String,Object> getdgBalance(Map<String,?> param);
}
