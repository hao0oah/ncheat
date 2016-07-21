package com.founder.database;

import java.util.List;
import java.util.Map;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100405;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Account;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Accounts;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Transaction;

public interface Tx100405Dao {
	
	/**
	 * 获取诈骗未遂[收款账户]信息<br>
	 * 条件：行内通过各种渠道发现涉嫌欺诈的转账交易，并有效阻断的，将收款账户信息（账户名称、账户号码）上报平台
	 * @param param Map[cardNumber:收款卡号]
	 */
	Tx100405 getF3001grAcnt(Map<String,?> param);
	
	/**
	 * 获取诈骗未遂主账户信息
	 */
	List<TxExceptionalEvent_Accounts> getF3001grAcntsList(Map<String,?> param);
	
	/**
	 * 获取诈骗未遂子账户信息
	 */
	List<TxExceptionalEvent_Account> getF3001grAcntList(Map<String,?> param);
	
	/**
	 * 获取诈骗未遂的交易信息
	 */
	List<TxExceptionalEvent_Transaction> getF3001grTransList(Map<String,?> param);
	
}
