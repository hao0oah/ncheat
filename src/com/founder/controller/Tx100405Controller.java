package com.founder.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100405;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Account;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Accounts;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Transaction;

import com.founder.beans.Com;
import com.founder.beans.FeedBack;
import com.founder.beans.ShowPage;
import com.founder.beans.Teller;
import com.founder.beans.Tx405Item;
import com.founder.service.MainService;
import com.founder.service.Tx100405Service;
import com.founder.tools.DateUtil;
import com.founder.tools.PropetyUtil;
import com.founder.tools.SessionUtils;
import com.founder.tools.XMLUtil;

@Controller
public class Tx100405Controller {
	public final Logger log = Logger.getLogger(this.getClass());

	@Resource(name="tx100405")
	private Tx100405Service tx405service;
	
	@Resource(name="mainService")
	private MainService userService;
	
	/**
	 * 上报可疑事件
	 */
	@ResponseBody
	@RequestMapping(value = "/report405Case",method=RequestMethod.POST)
	public Object reportCase(@RequestBody String params,HttpServletRequest request){
		log.info("[URL=/report405Case,params="+params+"]");
		FeedBack feedBack = new FeedBack();
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		String transflow = jbj.optString("mtransflow");
		String transdate = jbj.optString("mtransdate");
		double amount = jbj.optDouble("mamount");
		String payno = jbj.optString("mpayno");
		String peyno = jbj.optString("mpeyno");
		String apptype = jbj.optString("apptype").trim();
		String AccountName = jbj.optString("AccountName").trim();
		String cardNumber = jbj.optString("cardNumber").trim();
		
		try {
			Tx100405 tx100405 = new Tx100405();
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("khzh", cardNumber);
			param.put("transflow", transflow);
			param.put("transdate", transdate);
			param.put("payno", payno);
			param.put("peyno", peyno);
			param.put("amount", amount);
			TxExceptionalEvent_Accounts accounts = tx405service.getF405grCust(param);
			
			if(accounts == null){
				feedBack.setSuc(false);
				feedBack.setMsg("账号不存在");
				log.info("[账号不存在]");
				return feedBack;
			}
			if(!AccountName.equals(accounts.getAccountName().trim())){
				feedBack.setSuc(false);
				feedBack.setMsg("账号和账户名不符");
				log.info("[账号和账户名不符]");
				return feedBack;
			}
			
			TxExceptionalEvent_Account account = tx405service.getF405grAcnt(param);
			
			TxExceptionalEvent_Transaction tran = null;
			if(transflow!=null && !"".equals(transflow)){
				tran = tx405service.getTransaction(param);
			}else {
				tran = new TxExceptionalEvent_Transaction();
			}
			tran.setFeatureCode(apptype);
			List<TxExceptionalEvent_Transaction> trans = new ArrayList<TxExceptionalEvent_Transaction>();
			trans.add(tran);
			account.setTransactionList(trans);
			
			List<TxExceptionalEvent_Account> list = new ArrayList<TxExceptionalEvent_Account>();
			list.add(account);
			accounts.setAccountList(list);
			
			List<TxExceptionalEvent_Accounts> lists = new ArrayList<TxExceptionalEvent_Accounts>();
			lists.add(accounts);
			tx100405.setAccountsList(lists);
			
			// 上报时直接发走
			tx405service.execute2(tx100405);
			feedBack.setSuc(true);
			feedBack.setMsg("上报成功");
			log.info("[上报成功]");
			
			// 上报记录
			Map<String,Object> para = new HashMap<String, Object>();
			para.put("type", Com.T0001);
			para.put("feature", apptype);
			para.put("trsdate", DateUtil.getNowDate());
			para.put("userid", teller.getUserid());
			para.put("optdate", DateUtil.getNowDate());
			para.put("opttime", DateUtil.getNowTime());
			userService.recordOpt(para);
		} catch (Exception e) {
			e.printStackTrace();
			feedBack.setSuc(false);
			feedBack.setMsg("上报出错");
			log.error("[上报出错]");
		}
		
		return feedBack;
	}
	
	/**
	 * 根据条件查询流水
	 */
	@ResponseBody
	@RequestMapping(value = "/list405Trans",method=RequestMethod.POST)
	public Object listTrans(@RequestBody String params){
		log.info("[URL=/listTrans,params="+params+"]");
		FeedBack feedBack = new FeedBack();
		
		JSONObject jbj = JSONObject.fromObject(params);
		Object transflow = jbj.opt("transflow");
		int begin = jbj.optInt("begin");
		int limit = jbj.optInt("limit");
		
		Map<String,Object> param = new HashMap<String,Object>();
		if(transflow!=null && !"".equals(transflow)){
			param.put("transflow", transflow.toString());
		}else{
			param.put("transdate", jbj.opt("transdate"));
			param.put("amount", jbj.opt("amount"));
			param.put("payno", jbj.opt("payno"));
			param.put("peyno", jbj.opt("peyno"));
		}
		param.put("begin", begin);
		param.put("limit", limit);
		
		Integer count = tx405service.getgrTransactionsCount(param);
		log.info("[count="+count+"]");
		
		if(count == null || count == 0){
			feedBack.setSuc(false);
			feedBack.setMsg("没有查到记录");
			log.info("[没有查到记录]");
		}else{
			List<TxExceptionalEvent_Transaction> trans = tx405service.listgrTransactions(param);

			ShowPage<TxExceptionalEvent_Transaction> page = new ShowPage<TxExceptionalEvent_Transaction>();
			page.setBeanList(trans);
			page.setBegin(begin);
			page.setLimit(limit);
			page.setCount(count);
			
			feedBack.setSuc(true);
			feedBack.setMsg("查询成功");
			feedBack.setData(page);
			log.info("[查询成功]");
		}
		
		return feedBack;
	}
	
	/**
	 * 列出执行结果，根据行号列出
	 */
	@ResponseBody
	@RequestMapping(value = "/listItems",method=RequestMethod.POST)
	public Object listItems(@RequestBody String params,HttpServletRequest request){
		log.info("[URL=/listItems,params="+params+"]");
		FeedBack feedBack = new FeedBack();
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		String transDate = jbj.optString("transDate");
		String transType = jbj.optString("transType");
		String status = jbj.optString("status");
		Integer begin = jbj.optInt("begin");
		Integer limit = jbj.optInt("limit");
		Integer bankid = jbj.optInt("bankid");
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("transdate", transDate);
		param.put("transtype", transType);
		param.put("status", status);
		param.put("begin", begin);
		param.put("limit", limit);
		
		// 如果是管理员，则根据条件查询
		if(teller.getRoleid()==1000){
			param.put("bankid", bankid);
		}else{
			param.put("bankid", teller.getUserid());
		}
		
		ShowPage<Tx405Item> page = null;
		int count = tx405service.queryItemsCount(param);
		if(count>0){
			List<Tx405Item> items = tx405service.queryItemsByPage(param);
			
			page = new ShowPage<Tx405Item>();
			page.setBeanList(items);
			page.setBegin(begin);
			page.setLimit(limit);
			page.setCount(count);
			
			feedBack.setSuc(true);
			feedBack.setMsg("查询成功");
			feedBack.setData(page);
		}else{
			feedBack.setSuc(false);
			feedBack.setMsg("没有查到数据");
		}
		
		return feedBack;
	}
	
	/**
	 * 改变状态
	 */
	@ResponseBody
	@RequestMapping(value = "/chgItemStat",method=RequestMethod.POST)
	public Object changeItemStatus(@RequestBody String params){
		log.info("[URL=/chgItemStat,params="+params+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		long id = jbj.optLong("id");
//		int status = jbj.optInt("status");
		
		FeedBack feedBack = new FeedBack();
		feedBack.setSuc(true);
		feedBack.setMsg("更新成功");
		try {
			feedBack.setData(tx405service.updateItem(id));
		} catch (Exception e) {
			feedBack.setSuc(false);
			feedBack.setMsg("更新失败");
		}
		
		return feedBack;
	}
	
	/**
	 * 管理员手动发送
	 */
	@ResponseBody
	@RequestMapping(value = "/batchReport",method=RequestMethod.POST)
	public Object batchReport(@RequestBody String params,HttpServletRequest request){
		log.info("[URL=/batchReport,params="+params+"]");
		FeedBack feedBack = new FeedBack();
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		String transDate = jbj.optString("transDate");
		String transType = jbj.optString("transType");

		// 上报记录
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0004);
		para.put("feature", transType);
		para.put("trsdate", transDate);
		para.put("userid", teller.getUserid());
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());

		if(userService.isExist(para)){
			log.info("[今日已操作，不能重复上报]");
			return false;
		}
		
		PropetyUtil prop = new PropetyUtil(Com.FILE_PATH);
		String filepath = prop.getValue("filepath");
		filepath += transDate+File.separator+transType;
		
		File file = new File(filepath);
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(pathname.isDirectory())
					return false;
				return true;
			}
		});
		
		int count = 0;
		int error = 0;
		try {
			for (File file2 : files) {
				String tmp = null;
				StringBuffer sb = new StringBuffer();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "GBK"));
				while((tmp = br.readLine()) != null){
					sb.append(tmp);
				}
				
				log.info("发送报文：\n"+XMLUtil.formatXml(sb.toString()));
				boolean result = tx405service.manualReport(sb.toString());
				if(result){
					count++;
					log.info("[文件"+file2.getName()+" 发送成功]");
				}else{
					error++;
					log.info("[文件"+file2.getName()+" 发送失败]");
				}
				br.close();
				//TODO: 将发送成功和失败的情况再区分开来，放到error目录下
			}
			feedBack.setSuc(true);
			feedBack.setMsg("上报成功"+count+"条，上报失败"+error+"条");
			
			// 将上报记录存入数据库
			userService.recordOpt(para);
			
		} catch (Exception e) {
			feedBack.setSuc(false);
			feedBack.setMsg("发送失败");
			log.error("[上报出错]");
		}
		
		return feedBack;
	}
	
	/**
	 * 记录柜员提交
	 */
	@ResponseBody
	@RequestMapping(value = "/optReport",method=RequestMethod.POST)
	public Object optReport(@RequestBody String params,HttpServletRequest request){
		log.info("[URL=/optReport,params="+params+"]");
		FeedBack feedBack = new FeedBack();
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		String transDate = jbj.optString("transDate");
		String transType = jbj.optString("transType");

		// 记录提交
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0005);
		para.put("feature", transType);
		para.put("trsdate", transDate);
		para.put("userid", teller.getUserid());
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		
		try {
			userService.recordOpt(para);
			feedBack.setSuc(true);
			feedBack.setMsg("提交成功");
		} catch (Exception e) {
			feedBack.setSuc(false);
			feedBack.setMsg("提交失败");
		}
		
		return feedBack;
	}
	
	/**
	 * 查询明细
	 */
	@ResponseBody
	@RequestMapping(value = "/listDetail",method=RequestMethod.POST)
	public Object listDetail(@RequestBody String params){
		log.info("[URL=/listDetail,params="+params+"]");
		FeedBack feedBack = new FeedBack();
		
		JSONObject jbj = JSONObject.fromObject(params);
		long id = jbj.optLong("id");
		
		try {
			feedBack.setSuc(true);
			feedBack.setMsg("查询成功");
			feedBack.setData(tx405service.queryItemDetail(id));
		} catch (Exception e) {
			feedBack.setSuc(false);
			feedBack.setMsg("查询失败");
		}
		
		return feedBack;
	}
	
}
