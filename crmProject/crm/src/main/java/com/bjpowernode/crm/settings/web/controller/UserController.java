package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.provider.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author lzx
 * @create 2021/6/4 8:21
 */

@Controller
@RequestMapping("/settings/qx/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用于处理login的controller
	 *
	 * @param loginAct 登陆名
	 * @param loginPwd 登陆密码
	 * @param isRemPwd 是否勾选十天免登录按钮
	 * @param request  请求对象
	 * @param response 响应对象
	 * @return 返回处理结果对象
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response) {
//		将提交的密码转换成md5密文
//		生成一个map用来保存用户名和密码
		Map<String, Object> map = new HashMap<>(6);
		map.put("loginAct", loginAct);
		map.put("loginPwd", MD5Util.getMD5(loginPwd));
//		调用service层的方法返回一个user
		User user = userService.selectUserByUserNameAndPwd(map);
//		根据查询结果，生成响应信息
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
		if (user == null) {
			//账号或者密码错误,登录失败!
			returnObject.setMessage("账号或者密码错误,登录失败!");
		} else {
			//进一步判断账号是否过期,状态是否被锁定,ip是否被允许
			if (DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime()) > 0) {
				//当前时间大于账号有效期限,登录失败
				returnObject.setMessage("账号过期,登录失败");
			} else if (Contants.RETURN_OBJECT_CODE_FAIL.equals(user.getLockState())) {
				//账号状态被锁定,登陆失败
				returnObject.setMessage("账号状态被锁定,登陆失败");
			} else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
				//ip受限,登录失败;
				returnObject.setMessage("ip受限,登录失败");
			} else {
				//登录成功
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				request.getSession().setAttribute(Contants.SESSION_USER, user);
//				判断是否免登录
				Cookie cookie1 = new Cookie("loginAct", loginAct);
				Cookie cookie2 = new Cookie("loginPwd", loginPwd);
				if (Contants.ISREMPWD.equals(isRemPwd)) {
					cookie1.setMaxAge(10 * 24 * 60 * 60);
					cookie2.setMaxAge(10 * 24 * 60 * 60);
				} else {
					cookie1.setMaxAge(0);
					cookie2.setMaxAge(0);
				}
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
		}
		return returnObject;
	}

	/**
	 * 处理logout的controller
	 *
	 * @return 重定向至欢迎界面
	 */
	@RequestMapping("/logout")
	public String loginOut(HttpServletResponse response, HttpSession session) {
		Cookie cookie1 = new Cookie("loginAct", null);
		Cookie cookie2 = new Cookie("loginPwd", null);
		//设置新的cookie并设置保存时间为0以替代之前保存的cookie
		cookie1.setMaxAge(0);
		cookie2.setMaxAge(0);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
//		销毁Session资源
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * 接收index界面跳转的请求
	 * 在这个处理器中可以遍历访问用户的cookie实现十天免登录处理
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request) {
		//获取访问用户的cookies
		Cookie[] cookies = request.getCookies();
		String loginAct = null;
		String loginPwd = null;
		//如果cookies为null直接跳转登录界面
		if (cookies == null) {
			return "settings/qx/user/login";
		}
		//遍历cookie
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			//如果遍历出cookie的key和loginAct一致就存到变量中
			if ("loginAct".equals(name)) {
				loginAct = cookie.getValue();
				continue;
			}
			if ("loginPwd".equals(name)) {
				loginPwd = cookie.getValue();
			}
		}
		if (loginAct != null && loginPwd != null) {
			//封装参数
			Map<String, Object> map = new HashMap<>(6);
			map.put("loginAct", loginAct);
			map.put("loginPwd", MD5Util.getMD5(loginPwd));
			/*
			 * 这里的查询语句即使返回null在第72行会更新session,在拦截器中会进行下一步判断
			 * */
			User user = userService.selectUserByUserNameAndPwd(map);
			//注意拦截器中是判断在session中是否有sessionUser中存放user对象
			request.getSession().setAttribute("sessionUser", user);
			//成功跳转至workbench主界面
			return "redirect:/workbench/index.do";
		} else {
			//否则返回登录界面
			return "settings/qx/user/login";
		}
	}

	@RequestMapping("/updatePwd")
	@ResponseBody
	public Object updatePwd(String oldPwd,String newPwd,HttpSession session) {
		User user =(User) session.getAttribute(Contants.SESSION_USER);
		ReturnObject returnObject = new ReturnObject();
		if (!user.getLoginPwd().equals(MD5Util.getMD5(oldPwd))){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("原密码输入不正确");
		}else {
			User updateUser = new User();
			updateUser.setId(user.getId());
			updateUser.setLoginPwd(MD5Util.getMD5(newPwd));
			int i = userService.updateByPrimaryKeySelective(updateUser);
			if (i>0){
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("更新失败!");
			}
		}
		return returnObject;
	}
}
