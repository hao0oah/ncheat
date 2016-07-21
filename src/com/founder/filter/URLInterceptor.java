package com.founder.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.founder.beans.Menu;
import com.founder.beans.Teller;
import com.founder.tools.SessionUtils;

/**
 * URL拦截器
 * 过滤用户访问的连接
 * 如果连接为非公开，并且当前用户没有访问该连接的权限，则转向权限错误页面
 * 如果连接为非公开，当前用户没有登陆，则转向登陆页面
 * @author hao
 */
public class URLInterceptor implements HandlerInterceptor {
	
	private static final Logger log = Logger.getLogger(URLInterceptor.class);
	
	/**
	 * 在业务处理器处理之前调用
	 * 如果返回false
	 *    则从当前的处理器往回执行afterCompletion(),再退出拦截连
	 * 如果返回true
	 *    执行下一个拦截器，直到所有拦截器你执行完毕
	 *    再执行业务处理器Controller
	 *    然后进入拦截器链
	 *    从最后一个拦截器往回执行所有的postHandle()
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		String uri = request.getRequestURI();
		String requestUrl = uri.replace(uri.substring(0, uri.indexOf(request.getContextPath())+request.getContextPath().length()), "");
		log.info("[requestURL="+requestUrl+"]");
		
		List<String> execUrl = new ArrayList<String>();
		//访问控制例外的连接
		execUrl.add("/");
		execUrl.add("/tologin");
		execUrl.add("/login");
		execUrl.add("/logout");
		execUrl.add("/trans01");
		execUrl.add("/trans02");
		
		// 如果不是例外的连接，进行权限判断
		if(!ifInExecRes(execUrl,requestUrl)){
			Teller teller = (Teller)SessionUtils.getUser(request);
			// TODO: 如果没有登陆，或者登陆超时，重定向到登陆界面
			if(teller == null){
				return false;
			}
			
			@SuppressWarnings("unchecked")
			List<Menu> menus = (List<Menu>) SessionUtils.getAttr(request,SessionUtils.SESSION_MENUS);
			// TODO: 如果没有菜单数据，重定向到获取菜单页面
			if(menus == null){
				return false;
			}
			
			// TODO: 如果没有权限，该如何处理？
			if(!ifInExecRes2(menus,requestUrl)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 判断请求的连接是否在访问控制连接的例外情况之内
	 * @param resUrls
	 * @param reqUrl
	 * @return
	 */
	private boolean ifInExecRes(List<String> resUrls,String reqUrl){
		boolean result = false;
		for(String url : resUrls){
			if(reqUrl.startsWith(url)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 用户是否有权限访问该地址
	 */
	private boolean ifInExecRes2(List<Menu> menus,String reqUrl){
		boolean result = false;
		for (Menu menu : menus) {
			if(reqUrl.contains(menu.getUrl())){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 在业务处理器处理完成之后执行
	 * 在生成视图操作之前执行
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView mav) throws Exception {
	}
	
	/**
	 * 在DispatcherServlet完全处理完请求后调用
	 *    如果发生异常，则会从当前的拦截器往回执行所有的afterCompletion	
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

}
