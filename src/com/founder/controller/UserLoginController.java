package com.founder.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.founder.beans.Com;
import com.founder.beans.FeedBack;
import com.founder.beans.ShowPage;
import com.founder.beans.Teller;
import com.founder.service.MainService;
import com.founder.tools.DateUtil;
import com.founder.tools.MD5;
import com.founder.tools.SessionUtils;

@Controller
public class UserLoginController {
	public final Logger log = Logger.getLogger(this.getClass());

	@Resource(name="mainService")
	private MainService userService;
	
	/**
	 * 用户登陆处理
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public FeedBack userLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		log.info("[URL=/login]");
		request.setCharacterEncoding("UTF-8");
		
		FeedBack entity = new FeedBack();
		entity.setSuc(false);
		
		String userName = request.getParameter("userName");
		if(userName == null || "".equals(userName)){
			entity.setMsg("柜员号为空");
			log.info("[柜员号为空]");
			return entity;
		}
		int userid = Integer.parseInt(userName);
		
		String password = request.getParameter("password");
		log.info("params[userName:"+userid+"; password:"+password+"]");
		
		if (StringUtils.isBlank(password)) {
			entity.setMsg("密码为空");
			return entity;
		}
		
		if(password.length()<2){
			entity.setMsg("密码长度不符合要求！");
			return entity;
		}
		
		// 校验用户是否存在
		Teller teller = userService.getTeller(userid);
		if(teller == null){
			entity.setMsg("用户名不存在");
			log.error("[用户名不存在]");
			return entity;
		}

		//校验密码
		if(!teller.getPassword().equals(MD5.md5(password))){
			entity.setMsg("密码错误");
			log.error("[密码错误]");
			return entity;
		}
		
		//考虑已经登录用户，再次登陆的情况如何处理
		//将登陆用户放入Session
		SessionUtils.setUser(request, teller);

		//登录成功后的操作
		entity.setSuc(true);
		entity.setMsg("登录成功");
		log.info("[登陆成功]");
		
		// 登陆记录
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0010);
		para.put("feature", Com.F5555);
		para.put("userid", teller.getUserid());
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		userService.recordOpt(para);
		
//		try {//ajax不能在服务端重定向
//			request.getRequestDispatcher("").forward(request, response);
//			System.out.println(request.getContextPath());
//			response.sendRedirect("index");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return entity;
	}
	
	/**
	 * 获取菜单
	 */
	@ResponseBody
	@RequestMapping(value="/getmenu",method=RequestMethod.POST)
	public FeedBack mainPage(HttpServletRequest request){
		log.info("URL[/getmenu]");
		FeedBack entity = new FeedBack();
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		//获取Session中的user
		Teller user = (Teller) SessionUtils.getUser(request);
		map.put("user", user);
		
		if(user != null){
			Object menus =  userService.getOrderMenu(user.getUserid());
			map.put("menu", menus);
			//将菜单放入session
			SessionUtils.setAttr(request,SessionUtils.SESSION_MENUS,menus);
			
			entity.setData(map);
			entity.setMsg("获取菜单成功");
			entity.setSuc(true);
		} else {
			log.info("[用户未登录]");
			entity.setMsg("用户未登录");
			entity.setSuc(false);
		}
		
		return entity;
	}

	/**
	 * 退出登陆
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		log.info("URL[/logout]");
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
		//将登陆用户及菜单从Session移除
		SessionUtils.removeAttr(request, SessionUtils.SESSION_USER);
		SessionUtils.removeAttr(request,SessionUtils.SESSION_MENUS);
		
		// 登陆记录
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("type", Com.T0010);
		para.put("feature", Com.F5556);
		para.put("userid", teller.getUserid());
		para.put("optdate", DateUtil.getNowDate());
		para.put("opttime", DateUtil.getNowTime());
		userService.recordOpt(para);
		
		return "redirect:/tologin";
	}

	/**
	 * 查询日志
	 */
	@ResponseBody
	@RequestMapping(value="/getOptRecord",method=RequestMethod.POST)
	public FeedBack getOptRecord(@RequestBody String params,HttpServletRequest request){
		log.info("URL[/getOptRecord] 进入,params["+params+"]");
		FeedBack entity = new FeedBack();
		
		Teller teller = (Teller) SessionUtils.getUser(request);
		log.info("[Teller="+teller+"]");
		
		JSONObject jbj = JSONObject.fromObject(params);
		Integer begin = jbj.optInt("begin");
		Integer limit = jbj.optInt("limit");
		Integer bankid = jbj.optInt("bankid");
		String transDate = jbj.optString("transDate");
		
		Map<String,Object> param = new HashMap<String,Object>();
		// 如果是管理员，则根据条件查询
		if(teller.getRoleid()==1000){
			param.put("userid", bankid);
		}else{
			param.put("userid", teller.getUserid());
		}
		param.put("begin", begin);
		param.put("limit", limit);
		param.put("optdate", transDate);
		
		ShowPage<Map<String,Object>> page = new ShowPage<Map<String,Object>>();
		page.setBegin(begin);
		page.setLimit(limit);
		page.setCount(userService.getOptRecordCount(param));
		page.setBeanList(userService.getOptRecord(param));
		
		entity.setSuc(true);
		entity.setMsg("查询成功");
		entity.setData(page);

		return entity;
	}
	
	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequestMapping(value="/changePwd",method=RequestMethod.POST)
	public FeedBack changePwd(HttpServletRequest request,@RequestBody String params) {
		log.info("URL[/changePwd] 进入,params["+params+"]");
		FeedBack entity = new FeedBack();
		
		JSONObject jobj = JSONObject.fromObject(params);
		String password0 = jobj.optString("password0");
		String password1 = jobj.optString("password1");
		
		Teller user = (Teller) SessionUtils.getUser(request);
		if(user != null){
			if(!user.getPassword().equals(MD5.md5(password0))){
				entity.setSuc(false);
				entity.setMsg("原密码错误");
			} else {
				user.setPassword(MD5.md5(password1));
				userService.update(user);
				entity.setSuc(true);
				entity.setMsg("密码修改成功");
			}
			
			// 操作记录
			Map<String,Object> para = new HashMap<String, Object>();
			para.put("type", Com.T0010);
			para.put("feature", Com.F5557);
			para.put("userid", user.getUserid());
			para.put("optdate", DateUtil.getNowDate());
			para.put("opttime", DateUtil.getNowTime());
			userService.recordOpt(para);
		}else{
			entity.setSuc(false);
			entity.setMsg("登陆已超时");
		}
		
		return entity;
	}
}
