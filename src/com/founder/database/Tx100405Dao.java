package com.founder.database;

import java.util.Map;

import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Account;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Accounts;

public interface Tx100405Dao {
	
	/**
	 * 根据账号查询客户信息
	 */
	TxExceptionalEvent_Accounts getF405grCust(Map<String,?> param);
	
	
	/**
	 * 根据账号查询账号信息
	 */
	TxExceptionalEvent_Account getF405grAcnt(Map<String,?> param);
	
	/**
	 * 根据提供的条件，查询交易流水信息
	 */
//	List<TxExceptionalEvent_Transaction> listgrTransactions(Map<String,?> param);
	
	/**
	 * 根据流水号，查询交易流水信息
	 */
//	TxExceptionalEvent_Transaction getTransaction(Map<String,?> param);
	
	
	/**
	 * 获取所有的符合3006、3007的交易记录
	 */
//	List<TxExceptionalEvent_Transaction> getgrTransactions(Map<String,?> param);
	
	
	/**
	 * 获取n个工作日内转入大于m笔的[交易流水记录、总金额] - 分散转入 3006
	 */
//	List<Map<String,Object>> listgr3006flows(Map<String,?> param);
	
	/**
	 * 是否是集中转出的情况[转出笔数<count、0.9*amount<转出金额<1.1*amount] - 集中转出 3006
	 */
//	Integer hasgr3006flows(Map<String,?> param);
	
	
	/**
	 * 获取n个工作日内转出大于m笔的[交易流水记录、总金额] - 分散转出 3007
	 */
//	List<Map<String,Object>> listgr3007flows(Map<String,?> param);
	
	/**
	 * 是否是集中转入的情况[转入笔数<count、0.9*amount<转入金额<1.1*amount] - 集中转入 3007
	 */
//	Integer hasgr3007flows(Map<String,?> param);
	
	


}
