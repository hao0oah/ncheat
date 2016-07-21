package com.founder.database2;

import java.util.List;
import java.util.Map;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100401;
import cfca.safeguard.tx.business.bank.TxCaseReport_Transaction;

public interface Tx100401Dao2 {
	
	/**
	 * 根据受害人提供的条件，查询交易流水信息，
	 */
	List<TxCaseReport_Transaction> listgrTransactions(Map<String,?> param);
	Integer getgrTransactionsCount(Map<String,?> param);
	
	/**
	 * 根据多个交易流水号，查询多个交易信息
	 */
	List<TxCaseReport_Transaction> getgrTransactionsByFlows(List<String> param);
	
	/**
	 * 根据条件查询客户信息
	 */
	Tx100401 getFgrCust(Map<String,?> param);

}
