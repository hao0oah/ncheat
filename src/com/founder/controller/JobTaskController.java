package com.founder.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.founder.beans.Com;
import com.founder.beans.FeedBack;
import com.founder.beans.ScheduleJob;
import com.founder.service.JobTaskService;
import com.founder.task.Task100403;
import com.founder.task.Task100405;
import com.founder.tools.SpringUtils;

@Controller
public class JobTaskController {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="jobTaskService")
	private JobTaskService taskService;
	
	@Resource
	private Task100403 task100403;
	
	@Resource
	private Task100405 task100405;

	@ResponseBody
	@RequestMapping(value="/tasklist",method=RequestMethod.POST)
	public FeedBack taskList() {
		log.info("[进入taskList]");
		FeedBack feedBack = new FeedBack();
		
		//获取所有定时任务
		List<ScheduleJob> tasklist = taskService.getAllTask();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tasklist", tasklist);
		map.put("debugType", Com.DEBUG);
		
		feedBack.setData(map);
		feedBack.setMsg("查询成功");
		feedBack.setSuc(true);
		
		return feedBack;
	}

	@ResponseBody
	@RequestMapping("taskadd")
	public FeedBack taskadd(HttpServletRequest request, ScheduleJob scheduleJob) {
		FeedBack retObj = new FeedBack();
		retObj.setSuc(false);
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
		}
		Object obj = null;
		try {
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
			} else {
				Class<?> clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {
			log.error("[实例化定时任务类错误："+e.getMessage()+"]");
		}
		if (obj == null) {
			retObj.setMsg("未找到目标类！");
			return retObj;
		} else {
			Class<? extends Object> clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName());
			} catch (Exception e) {
				log.error("[未找到目标方法错误："+e.getMessage()+"]");
			}
			if (method == null) {
				retObj.setMsg("未找到目标方法！");
				return retObj;
			}
		}
		try {
			taskService.addTask(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			retObj.setSuc(false);
			retObj.setMsg("保存失败，检查 name group 组合是否有重复！");
			return retObj;
		}

		retObj.setSuc(true);
		return retObj;
	}

	@ResponseBody
	@RequestMapping("changeJobStatus")
	public FeedBack changeJobStatus( Long jobId, String cmd) {
		FeedBack retObj = new FeedBack();
		retObj.setSuc(false);
		try {
			taskService.changeStatus(jobId, cmd);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			retObj.setMsg("任务状态改变失败！");
			return retObj;
		}
		retObj.setSuc(true);
		return retObj;
	}

	@ResponseBody
	@RequestMapping("updateCron")
	public FeedBack updateCron( Long jobId, String cron,String param) {
		FeedBack retObj = new FeedBack();
		retObj.setSuc(false);
		try {
			if(cron != null && !"".equals(cron)){
				CronScheduleBuilder.cronSchedule(cron);
			}
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
		}
		try {
			taskService.updateCron(jobId, cron, param);
		} catch (SchedulerException e) {
			retObj.setMsg("cron更新失败！");
			return retObj;
		}
		retObj.setSuc(true);
		return retObj;
	}
	
	@ResponseBody
	@RequestMapping("changeType")
	public FeedBack changeType( boolean type) {
		FeedBack retObj = new FeedBack();
		
		Com.DEBUG = type;
		if(type){
			retObj.setMsg("切换置手工模式");
			log.info("[切换致手工模式]");
		} else {
			retObj.setMsg("切换置自动模式");
			log.info("[切换致自动模式]");
		}
		retObj.setSuc(true);
		return retObj;
	}
	
	@ResponseBody
	@RequestMapping("runJust")
	public FeedBack runJust(String method,String param) throws Exception {
		log.info("[进入runJust,method:"+method+"]");
		FeedBack retObj = new FeedBack();
		
		boolean res = false;
		if("task1001".equals(method)){
			res = task100403.task1001(param);
		} else if("task1002".equals(method)){
			res = task100403.task1002(param);
		} else if("task3006".equals(method)){
			res = task100405.task3006(param);
		} else if("task3007".equals(method)){
			res = task100405.task3007(param);
		} else if("task3008".equals(method)){
			res = task100405.task3008(param);
		}
		
		retObj.setSuc(res);
		retObj.setMsg(res?"今日上报完成":"今日已经上报，不能重复操作");
		return retObj;
	}
	
}
