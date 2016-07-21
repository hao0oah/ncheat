package com.founder.task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.founder.beans.Com;
import com.founder.service.BaseService;
import com.founder.tools.DateUtil;

public class Task100404 {
	
	private static final Logger log = Logger.getLogger(Task100404.class);
	
	@Resource(name="tx100404")
	private BaseService tx100404;

	/**
	 * 取消上传
	 */
	public void task0000() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，取消上传]");
		tx100404.execute(Com.F0000);
	}
	
	/**
	 * 定时任务，上传涉案账户信息
	 */
	public void task2001() throws Exception{
		log.info("[时间："+DateUtil.getNow()+"，开始上传-涉案账户数据]");
		tx100404.execute(Com.F2001);
	}

}
