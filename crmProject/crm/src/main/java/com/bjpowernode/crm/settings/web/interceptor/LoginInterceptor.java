package com.bjpowernode.crm.settings.web.interceptor;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lzx
 * @create 2021/6/4 21:31
 */

/**
 * 添加拦截器
 * 过滤未经登录访问其他页面的访问用户
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		HttpSession session = httpServletRequest.getSession(false);
		if (session == null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
			return false;
		}
		User user = (User) session.getAttribute(Contants.SESSION_USER);
		if (user == null) {
			//如果没登录过，跳转到登录页面
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
			return false;
		}
		//如果已经登录过，则放行
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
