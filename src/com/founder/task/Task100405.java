package com.founder.task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.founder.beans.Com;
import com.founder.service.BaseService;
import com.founder.tools.DateUtil;

public class Task100405 {
	
	private static final Logger log = Logger.getLogger(Task100405.class);
	
	@Resource(name="tx100405")
	private BaseService tx100405;

	/**
	 * 取消上传
	 */
	public void task0000() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，取消上传]");
		tx100405.execute(Com.F0000);
	}
	
	/**
	 * 定时任务，上传诈骗未遂[收款账户]信息
	 */
	public void task3001() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-诈骗未遂数据]");
		tx100405.execute(Com.F3001);
	}

}
