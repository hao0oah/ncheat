package com.founder.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.founder.beans.Com;
import com.founder.service.Tx100404Service;
import com.founder.tools.DateUtil;

public class Task100404 {
	
	private static final Logger log = Logger.getLogger(Task100404.class);
	
	@Resource(name="tx100404")
	private Tx100404Service tx100404;

	/**
	 * 取消上传
	 */
	public void task0000() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，取消上传]");
		tx100404.execute(Com.F0000,null);
	}
	
	/**
	 * 定时任务，上传涉案账户信息
	 */
	public void task2001() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-涉案账户数据]");
		
		//数据库查询参数 : 获取所有涉案账户
    	Map<String,String> param = new HashMap<String,String>();
		List<String> custs = tx100404.getgrF2001AllCust(param);
		
		for (String cust : custs) {
			param.clear();
			param.put("khbh", cust);
			tx100404.execute(Com.F2001,param);
			
			Thread.sleep(1000);
		}
	}

}
