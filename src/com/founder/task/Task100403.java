package com.founder.task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.founder.beans.Com;
import com.founder.service.BaseService;
import com.founder.tools.DateUtil;

public class Task100403 {
	
	private static final Logger log = Logger.getLogger(Task100403.class);
	
	@Resource(name="tx100403")
	private BaseService tx100403;

	/**
	 * 取消上传可疑账户
	 */
	public void task0000() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，取消上传可疑账户]");
		tx100403.execute(Com.F0000);
	}
	
	/**
	 * 定时任务，上传频繁开户可疑账户信息
	 */
	public void task1001() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-频繁开户可疑数据]");
		tx100403.execute(Com.F1001);
	}
	
	/**
	 * 定时任务，上传累计开户可疑账户信息
	 */
	public void task1002() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-累计开户可疑数据]");
		tx100403.execute(Com.F1002);
	}
}
