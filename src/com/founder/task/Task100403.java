package com.founder.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cfca.safeguard.api.bank.bean.tx.upstream.Tx100403;

import com.founder.beans.Com;
import com.founder.service.MainService;
import com.founder.service.Tx100403Service;
import com.founder.tools.DateUtil;

public class Task100403 {
	
	private static final Logger log = Logger.getLogger(Task100403.class);
	
	@Resource(name="tx100403")
	private Tx100403Service tx100403Service;
	
	@Resource(name="mainService")
	private MainService userService;

	/**
	 * 取消上传可疑账户
	 */
	public void task0000() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，取消上传可疑账户]");
		tx100403Service.execute(null);
	}
	
	/**
	 * 定时任务，上传频繁开户可疑账户信息
	 */
	public boolean task1001(String params) throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-频繁开户可疑数据]");
		int result = 0;
		long statTime = System.currentTimeMillis();
		
		JSONObject jbj = JSONObject.fromObject(params==null?"{}":params);
		
		Integer last = jbj.optInt("last");		//向前几天
		String start = jbj.optString("start");	//开始日期
		String end = jbj.optString("end");		//结束日期
		Integer count = jbj.optInt("count");	//大于几张卡
		Integer day = jbj.optInt("day");		//大于几天
		boolean first = jbj.optBoolean("first");//是否第一次运行
		Integer yest = jbj.optInt("yest");		//昨日？
		
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.DEBUG?Com.T0002:Com.T0000);
		para.put("feature", Com.F1001);
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		
		if(userService.isExist(para)){
			log.info("[今日已上报，不能重复操作]");
			return false;
		}
		
//		log.info("[params=start:"+start+";end:"+end+";count:"+count+";day:"+day+";last:"+last+"]");
		
		//数据库查询参数，查询5个工作日内开卡大于3张卡的所有客户
    	Map<String,Object> param = new HashMap<String,Object>();
    	if(last != null && last != 0){
    		param.put("last", last);
    	}
    	if(start != null && !"".equals(start.trim())){
    		param.put("start", start);
    	}
    	if(end != null && !"".equals(end.trim())){
    		param.put("end", end);
    	}
    	int mcount = (count==null||count==0)?3:count;
    	param.put("count", mcount);
    	
    	List<String> custs = tx100403Service.getF1001grCusts(param);
		
		if(custs != null && custs.size()>0){
			for (String cust : custs) {
				param.put("zjbh", cust);
				param.put("yest", (yest==null||yest==0)?1:yest);
				if(!first && !tx100403Service.getFgrIsCount(param)){
					continue;	//第一次上报不会判断当日是否开卡;以后需要判断符合条件后，当日是否开卡
		    	}
				
				List<String> days = tx100403Service.getF1001grDays(param);
				log.info("days="+Arrays.toString(days.toArray()));
				
				List<String> list = new ArrayList<String>();
				if(tx100403Service.isF1001Cust(days,list,(day==null||day==0)?5:day)){
					Tx100403 tx100403 = tx100403Service.getFgrCust(param);
			    	if(tx100403 != null){
			    		tx100403.setFeatureCode(Com.F1001);
			    		param.put("list", list);
						tx100403.setAccountsList(tx100403Service.getF1001grAcntList(param));
						tx100403Service.execute(tx100403);
						Thread.sleep(1);
						result++;
			    	}
				}
			}
			// para已经在最开始定义
			userService.recordOpt(para);
		}else{
			log.info("[没有需要上报的可疑账户]");
		}
		
		long endTime = System.currentTimeMillis();
		log.info("[用时："+(endTime-statTime)/1000+"秒，上传完成["+result+"]条频繁开户可疑数据]");
		
		return true;
	}

	
	/**
	 * 定时任务，上传累计开户可疑账户信息
	 */
	public boolean task1002(String params) throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-累计开户可疑数据]");
		int result = 0;
		long statTime = System.currentTimeMillis();
		
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.DEBUG?Com.T0002:Com.T0000);
		para.put("feature", Com.F1002);
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		
		if(userService.isExist(para)){
			log.info("[今日已上报，不能重复操作]");
			return false;
		}
		
		JSONObject jbj = JSONObject.fromObject(params==null?"{}":params);
		
		Integer last = jbj.optInt("last");		//向前几天
		String start = jbj.optString("start");	//开始日期
		String end = jbj.optString("end");		//结束日期
		Integer count = jbj.optInt("count");	//大于几张卡
		boolean first = jbj.optBoolean("first");//是否第一次运行
		Integer yest = jbj.optInt("yest");		//昨日？
		
		//数据库查询参数，查询所有卡大于10张卡的所有客户
    	Map<String,Object> param = new HashMap<String,Object>();
    	if(last != null && last != 0){
    		param.put("last", last);
    	}
    	if(start != null && !"".equals(start.trim())){
    		param.put("start", start);
    	}
    	if(end != null && !"".equals(end.trim())){
    		param.put("end", end);
    	}
    	
    	int mcount = (count==null||count==0)?10:count;
    	param.put("count", mcount);
    	
    	List<String> custs = null;
    	if(first){		//第一次全量上报；
    		custs = tx100403Service.getF1002grCusts(param);
    	} else {		//以后每次查询当日有开卡的客户，是否需要上报
    		param.put("yest", (yest==null||yest==0)?1:yest);
    		List<String> tmp = tx100403Service.getFgrCards(param);
    		if(tmp != null && tmp.size()>0){
    			custs = new ArrayList<String>();
    			for (String str : tmp) {
    				if(tx100403Service.getFgrCount(str) >= mcount){
    					custs.add(str);
    				}
    			}
    		}
    	}
    	
		if(custs != null && custs.size()>0){
			for (String cust : custs) {
				param.put("zjbh", cust);
				
				Tx100403 tx100403 = tx100403Service.getFgrCust(param);
				if(tx100403 != null){
					tx100403.setFeatureCode(Com.F1002);
					tx100403.setAccountsList(tx100403Service.getFgrAcntList(param));
					tx100403Service.execute(tx100403);
					Thread.sleep(1);
					result++;
		    	}
			}
			userService.recordOpt(para);
		}else{
			log.info("[没有需要上报的可疑账户]");
		}
		
		long endTime = System.currentTimeMillis();
		log.info("[用时间："+(endTime-statTime)/1000+"秒，上传完成["+result+"]条累计开户可疑数据]");
		
		return true;
	}
}
