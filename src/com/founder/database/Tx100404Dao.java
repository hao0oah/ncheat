package com.founder.database;

import java.util.List;
import java.util.Map;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100404;
import cfca.safeguard.tx.Transaction;
import cfca.safeguard.tx.business.bank.TxInvolvedAccount_Account;

public interface Tx100404Dao {
	
	/**
	 * 获取涉案账户信息<br>
	 * 条件：通过定期账户扫描发现存量账户开户人信息在涉案账户；
	 *    需将账号信息上报，平台上线时扫描，后期3-6个月扫描一次，报送6个月内交易数据。
	 */
	Tx100404 getF2001grCust(Map<String,?> param);
	
	
	/**
	 * 获取涉案账户所有账号信息
	 */
	List<TxInvolvedAccount_Account> getF2001grAcntList(Map<String,?> param);
	
	
	/**
	 * 获取涉案账户账号(近30天)的交易信息
	 */
	List<Transaction> getF2001grTransList(Map<String,?> param);
	
}
