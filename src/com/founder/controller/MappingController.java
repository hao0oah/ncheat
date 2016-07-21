package com.founder.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.founder.beans.Teller;
import com.founder.tools.SessionUtils;

@Controller
public class MappingController {
	public final Logger log = Logger.getLogger(this.getClass());

	// 登陆界面
	@RequestMapping(value = {"/","/tologin"})
	public String tologin(HttpServletRequest request) {
		// TODO: （已经在过滤器中判断，是否可以省略）如果已经登陆，即session中有用户，则直接重定向到首页
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[tologin-Teller="+teller+"]");
		
		if(teller != null){
			return "redirect:index";
		}
		return "ncheat/login";
	}
	
	// 登陆首页
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		// TODO: （已经在过滤器中判断，是否可以省略）如果没有登陆，即session中没有有用户，则直接重定向到登陆页面
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[index-Teller="+teller+"]");
		
		if(teller == null){
			return "redirect:tologin";
		}
		return "ncheat/main";
	}
	
	// 菜单页面
	@RequestMapping(value = "navBar")
	public String navBar() {
		return "ncheat/navMenu";
	}
	
	// 修改密码页面
	@RequestMapping(value = "chgpwdpage")
	public String chgpwdpage() {
		return "ncheat/chgpwd";
	}
	
	// 查询日志页面（普通柜员）
	@RequestMapping(value = "lslog")
	public String lslog() {
		return "ncheat/lslog";
	}
	
	// 查询日志页面（管理员）
	@RequestMapping(value = "lslogr")
	public String lslogr() {
		return "ncheat/lslogr";
	}
	
	// 100401 - 案件举报
	@RequestMapping(value = "/tx100401")
	public String tx100401() {
		return "ncheat/tx100401";
	}
	
	// 100405 - 可疑事件上报
	@RequestMapping(value = "/tx100405")
	public String tx100405() {
		return "ncheat/tx100405";
	}
	
	// 100405 - 可疑事件筛选（普通柜员）
	@RequestMapping(value = "/ls100405")
	public String ls100405() {
		return "ncheat/ls100405";
	}
	
	// 100405 - 可疑事件筛选（管理员）
	@RequestMapping(value = "/ls100405r")
	public String ls100405r() {
		return "ncheat/ls100405r";
	}

	// taskmanage - 定时任务列表
	@RequestMapping(value = "/taskmanage")
	public String taskmanage() {
		return "ncheat/tasklist";
	}
	
	// trans01 - 导出账户所有交易
	@RequestMapping(value = "/trans01")
	public String trans01() {
		return "ncheat/trans01";
	}
	
	// trans02 - 导出账户所有余额
	@RequestMapping(value = "/trans02")
	public String trans02() {
		return "ncheat/trans02";
	}
	
}
