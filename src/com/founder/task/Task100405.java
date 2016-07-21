package com.founder.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100405;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Account;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Accounts;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Transaction;

import com.founder.beans.Com;
import com.founder.service.MainService;
import com.founder.service.Tx100405Service;
import com.founder.tools.DateUtil;

public class Task100405 {
	
	private static final Logger log = Logger.getLogger(Task100405.class);
	
	@Resource(name="tx100405")
	private Tx100405Service tx100405Service;

	@Resource(name="mainService")
	private MainService userService;
	
	/**
	 * 取消上传
	 */
	public void task0000() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，取消上传]");
		tx100405Service.execute(null);
	}
	
	/**
	 * 定时任务，分散入集中出
	 * 条件：短期内（5个工作日内，含5个工作日）资金分散转入、集中转出，需要上报所有交易信息
	 */
	public boolean task3006(String params) throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-分散入集中出 数据]");
		int result = 0;
		long statTime = System.currentTimeMillis();
		
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0002);
		para.put("feature", Com.F3006);
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		
		if(userService.isExist(para)){
			log.info("[今日已操作，不能重复上报]");
			return false;
		}
		
		JSONObject jbj = JSONObject.fromObject(params==null?"{}":params);
		Integer count = jbj.optInt("count");	//大于几笔交易
		Integer last = jbj.optInt("last");		//几个工作日内
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("count", (count==null||count==0)?3:count);
		param.put("last", (last==null||last==0)?5:last);
		
		List<String> accts = tx100405Service.listgr3006Acnt(param);
		if(accts == null || accts.size()==0){
			log.info("[没有需要上报的可疑事件]");
			return true;
		}
		
		for (String acct : accts) {
			Tx100405 tx100405 = new Tx100405();
			param.put("khzh", acct); 
			
			TxExceptionalEvent_Accounts accounts = tx100405Service.getF405grCust(param);
			
			TxExceptionalEvent_Account account = tx100405Service.getF405grAcnt(param);
			
			List<TxExceptionalEvent_Transaction> trans = tx100405Service.getgrTransactions(param);
			
			//TODO: 此处判断是否包含有跑批日的交易
			for (TxExceptionalEvent_Transaction tran : trans) {
				tran.setFeatureCode(Com.F3006);
			}
			account.setTransactionList(trans);
			
			List<TxExceptionalEvent_Account> list = new ArrayList<TxExceptionalEvent_Account>();
			list.add(account);
			accounts.setAccountList(list);
			
			List<TxExceptionalEvent_Accounts> lists = new ArrayList<TxExceptionalEvent_Accounts>();
			lists.add(accounts);
			tx100405.setAccountsList(lists);
			
			tx100405Service.execute(tx100405);
			result++;
		}
		userService.recordOpt(para);
		
		long endTime = System.currentTimeMillis();
		log.info("[用时："+(endTime-statTime)/1000+"秒，上传完成["+result+"]条-分散入集中出 数据]");
		
		return true;
	}

	
	/**
	 * 定时任务，集中入分散出
	 * 条件：短期内（5个工作日内，含5个工作日）资金集中转入、分散转出，需要上报所有交易信息
	 */
	public boolean task3007(String params) throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-集中入分散出 数据]");
		int result = 0;
		long statTime = System.currentTimeMillis();

		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0002);
		para.put("feature", Com.F3007);
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		
		if(userService.isExist(para)){
			log.info("[今日已操作，不能重复上报]");
			return false;
		}

		JSONObject jbj = JSONObject.fromObject(params==null?"{}":params);
		Integer count = jbj.optInt("count");	//大于几笔交易
		Integer last = jbj.optInt("last");		//几个工作日内
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("count", (count==null||count==0)?3:count);
		param.put("last", (last==null||last==0)?5:last);
		
		List<String> accts = tx100405Service.listgr3007Acnt(param);
		if(accts == null){
			log.info("[没有需要上报的可疑事件]");
			return true;
		}
		
		for (String acct : accts) {
			Tx100405 tx100405 = new Tx100405();
			param.put("khzh", acct); 
			
			TxExceptionalEvent_Accounts accounts = tx100405Service.getF405grCust(param);
			
			TxExceptionalEvent_Account account = tx100405Service.getF405grAcnt(param);
			
			List<TxExceptionalEvent_Transaction> trans = tx100405Service.getgrTransactions(param);
			
			//TODO: 此处判断是否包含有跑批日的交易
			for (TxExceptionalEvent_Transaction tran : trans) {
				tran.setFeatureCode(Com.F3007);
			}
			account.setTransactionList(trans);
			
			List<TxExceptionalEvent_Account> list = new ArrayList<TxExceptionalEvent_Account>();
			list.add(account);
			accounts.setAccountList(list);
			
			List<TxExceptionalEvent_Accounts> lists = new ArrayList<TxExceptionalEvent_Accounts>();
			lists.add(accounts);
			tx100405.setAccountsList(lists);
			
			tx100405Service.execute(tx100405);
			result++;
		}
		userService.recordOpt(para);
		
		long endTime = System.currentTimeMillis();
		log.info("[用时："+(endTime-statTime)/1000+"秒，上传完成["+result+"]条-集中入分散出 数据]");
		
		return true;
	}

	
	/**
	 * 定时任务，连续多笔交易
	 * 条件：短期内（5个工作日内，含5个工作日）同一收付款人之间频繁发生数额相近的资金收付，需上报所有交易信息
	 */
	public boolean task3008(String params) throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-连续多笔交易 数据]");
		int result = 0;
		long statTime = System.currentTimeMillis();
		
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0002);
		para.put("feature", Com.F3008);
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		
		if(userService.isExist(para)){
			log.info("[今日已操作，不能重复上报]");
			return false;
		}
		
		JSONObject jbj = JSONObject.fromObject(params==null?"{}":params);
		Integer count = jbj.optInt("count");	//大于几笔交易
		Integer last = jbj.optInt("last");		//几个工作日内
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("count", (count==null||count==0)?3:count);
		param.put("last", (last==null||last==0)?5:last);
		
		List<String> accts = tx100405Service.listgr3008Acnt(param);
		if(accts == null){
			log.info("[没有需要上报的可疑事件]");
			return true;
		}
		
		for (String acct : accts) {
			Tx100405 tx100405 = new Tx100405();
			param.put("khzh", acct); 
			
			TxExceptionalEvent_Accounts accounts = tx100405Service.getF405grCust(param);
			
			TxExceptionalEvent_Account account = tx100405Service.getF405grAcnt(param);
			
			List<TxExceptionalEvent_Transaction> trans = tx100405Service.getgrTransactions(param);
			
			//TODO: 此处判断是否包含有跑批日的交易
			for (TxExceptionalEvent_Transaction tran : trans) {
				tran.setFeatureCode(Com.F3008);
			}
			account.setTransactionList(trans);
			
			List<TxExceptionalEvent_Account> list = new ArrayList<TxExceptionalEvent_Account>();
			list.add(account);
			accounts.setAccountList(list);
			
			List<TxExceptionalEvent_Accounts> lists = new ArrayList<TxExceptionalEvent_Accounts>();
			lists.add(accounts);
			tx100405.setAccountsList(lists);
			
			tx100405Service.execute(tx100405);
			result++;
		}
		userService.recordOpt(para);
		
		long endTime = System.currentTimeMillis();
		log.info("[用时："+(endTime-statTime)/1000+"秒，上传完成["+result+"]条-连续多笔交易 数据]");
		
		return true;
	}

}
