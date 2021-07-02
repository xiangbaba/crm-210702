package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;

/**
 * @author lzx
 * @create 2021/6/5 16:18
 */
@Controller
@RequestMapping("/settings")
public class SettingsIndexController {
	/**
	 * workbench界面系统设置连接
	 * @return 返回到设置主界面
	 */
	@RequestMapping("/index.do")
	public String index(){
		return "settings/index";
	}

	/**
	 * 设置主界面数据字典表连接
	 * @return 跳转到字典主界面
	 */
	@RequestMapping("/dictionary/index")
	public String dictionaryIndex(){
		return "settings/dictionary/index";
	}

}
