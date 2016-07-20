package com.founder.database;

import java.util.List;
import java.util.Map;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100403;
import cfca.safeguard.tx.business.bank.TxUnusualOpencard_Accounts;

public interface Tx100403Dao {
	/**
	 * 获取频繁开户可疑客户信息<br>
	 * 条件：同一身份证件在5个工作日内累计开户大于等于3张卡
	 */
	Tx100403 getF1001Accnt(Map<String,?> param);
	/**
	 * 获取频繁开户可疑客户卡号信息
	 */
	List<TxUnusualOpencard_Accounts> getF1001List(Map<String,?> param);
	
	
	/**
	 * 获取累计开户可以客户信息<br>
	 * 条件：同一身份证件在同一法人银行累计开户大于等于10张卡
	 */
	Tx100403 getF1002Accnt(Map<String,?> param);
	/**
	 * 获取累计开户可以客户卡号信息
	 */
	List<TxUnusualOpencard_Accounts> getF1002List(Map<String,?> param);
	
}
