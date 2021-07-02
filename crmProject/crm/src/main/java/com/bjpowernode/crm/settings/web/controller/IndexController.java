package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lzx
 * @create 2021/6/3 21:43
 */
@Controller
public class IndexController {
	/**
	 * 欢迎页直接跳转的这个controller
	 * @return 不做任何处理返回到index界面
	 */
	@RequestMapping("/")
	public String index(){
		return "index";
	}
}
