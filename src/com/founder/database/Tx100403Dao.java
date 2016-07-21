package com.founder.database;

import java.util.List;
import java.util.Map;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100403;
import cfca.safeguard.tx.business.bank.TxUnusualOpencard_Accounts;

public interface Tx100403Dao {
	
	/**
	 * 获取所有频繁开户客户号，进行轮询发送
	 * 条件：同一身份证件在5个工作日内累计开户大于等于3张卡
	 */
	List<String> getF1001grCusts(Map<String,?> param);
	List<String> getF1001dgCusts(Map<String,?> param);
	
	/**
	 * 获取累计开户数超过指定数目的开户日期
	 */
	List<String> getF1001grDays(Map<String,?> param);
	List<String> getF1001dgDays(Map<String,?> param);
	
	/**
	 * 获取户可疑客户信息<br>
	 */
	Tx100403 getFgrCust(Map<String,?> param);
	Tx100403 getFdgCust(Map<String,?> param);
	
	
	/**
	 * 1001 - 获取可疑客户卡号信息
	 */
	List<TxUnusualOpencard_Accounts> getF1001grAcntList(Map<String,?> param);
	
	
	/**
	 * 根据身份证号获取可疑客户卡号信息 
	 */
	List<TxUnusualOpencard_Accounts> getFgrAcntList(Map<String,?> param);
	List<TxUnusualOpencard_Accounts> getFdgAcntList(Map<String,?> param);
	
	
	/**
	 * 获取所有累计开户超过指定数目的客户身份证号，进行轮询发送
	 * 条件：同一身份证件在同一法人银行累计开户大于等于10张卡
	 */
	List<String> getF1002grCusts(Map<String,?> param);
	List<String> getF1002dgCusts(Map<String,?> param);

	
	/**
	 * 获取当日开卡的客户身份证号
	 */
	List<String> getFgrCards(Map<String,?> param);
	
	/**
	 * 根据客户身份证号，获取总共开卡数
	 */
	int getFgrCount(String zjbh);
	
	/**
	 * 获取某日某客户是否开卡
	 */
	int getFgrIsCount(Map<String,?> param);
}
