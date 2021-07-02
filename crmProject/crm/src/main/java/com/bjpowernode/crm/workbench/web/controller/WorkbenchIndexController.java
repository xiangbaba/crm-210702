package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lzx
 * @create 2021/6/4 19:18
 */
@Controller
@RequestMapping("/workbench")
public class WorkbenchIndexController {
	/**
	 * @return 跳转到系统主界面
	 */
	@RequestMapping("/index")
	public String index(){
		return "workbench/index";
	}

	/**
	 * @return 跳转到工作台主界面
	 */
	@RequestMapping("/main/index.do")
	public String mainIndex(){
		return "workbench/main/index";
	}
}
