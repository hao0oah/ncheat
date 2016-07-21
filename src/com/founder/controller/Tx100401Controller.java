package com.founder.controller;

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

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100401;
import cfca.safeguard.tx.business.bank.TxCaseReport_Transaction;

import com.founder.beans.Com;
import com.founder.beans.FeedBack;
import com.founder.beans.ShowPage;
import com.founder.beans.Teller;
import com.founder.service.MainService;
import com.founder.service.Tx100401Service;
import com.founder.tools.DateUtil;
import com.founder.tools.SessionUtils;

@Controller
public class Tx100401Controller {
	public final Logger log = Logger.getLogger(this.getClass());

	@Resource(name="tx100401")
	private Tx100401Service tx401service;
	
	@Resource(name="mainService")
	private MainService userService;
	
	/**
	 * 诈骗案件上报
	 */
	@ResponseBody
	@RequestMapping(value = "/reportCase",method=RequestMethod.POST)
	public Object reportCase(@RequestBody Tx100401 params,HttpServletRequest request){
		log.info("[URL=/reportCase,params="+params+"]");
		
		FeedBack feedBack = new FeedBack();
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
//		List<String> flows = new ArrayList<String>();
//		List<TxCaseReport_Transaction> trans = params.getTransactionList();
//		if(trans != null){
//			for(TxCaseReport_Transaction tran : trans){
//				flows.add(tran.getId());
//			}
//			trans.clear();
//			trans.addAll(tx401service.getgrTransactionsByFlows(flows));
//			params.setTransactionList(trans);
//		}
		
		try {
			boolean ex = tx401service.execute(params);
			
			// 操作记录
			Map<String,Object> para = new HashMap<String, Object>();
			para.put("type", Com.T0001);
			para.put("feature", Com.N0401);
			para.put("userid", teller.getUserid());
			para.put("optdate", DateUtil.getNowDate());
			para.put("opttime", DateUtil.getNowTime());
			userService.recordOpt(para);
			
			//TODO: 返回结果待确认
			log.info("[401-案件举报返回结果："+ex+"]");
			feedBack.setSuc(ex);
			feedBack.setMsg("举报成功");
		} catch (Exception e) {
			e.printStackTrace();
			feedBack.setSuc(false);
			feedBack.setMsg("操作异常");
		}
		
		return feedBack;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listTrans",method=RequestMethod.POST)
	public Object listTrans(@RequestBody String params){
		log.info("[URL=/listTrans,params="+params+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		
		Map<String,Object> param = new HashMap<String,Object>();
		Object transflow = jbj.opt("transflow");
		if(transflow!=null && !"".equals(transflow)){
			param.put("transflow", transflow.toString());
		}else{
			param.put("transdate", jbj.opt("transdate"));
			param.put("amount", jbj.opt("amount"));
			param.put("payno", jbj.opt("payno"));
			param.put("peyno", jbj.opt("peyno"));
		}
		int begin = jbj.optInt("begin");
		param.put("begin", begin);
		int limit = jbj.optInt("limit");
		param.put("limit", limit);
		
		FeedBack feedBack = new FeedBack();
		
		Integer count = tx401service.getgrTransactionsCount(param);
		log.info("[count="+count+"]");
		
		if(count == null || count == 0){
			feedBack.setSuc(false);
			feedBack.setMsg("没有查到记录");
			log.info("[没有查到记录]");
		}else{
			List<TxCaseReport_Transaction> trans = tx401service.listgrTransactions(param);
			
			ShowPage<TxCaseReport_Transaction> page = new ShowPage<TxCaseReport_Transaction>();
			page.setBeanList(trans);
			page.setBegin(begin);
			page.setLimit(limit);
			page.setCount(count);
			
			feedBack.setSuc(true);
			feedBack.setMsg("查询成功");
			feedBack.setData(page);
		}
		
		return feedBack;
	}
}
