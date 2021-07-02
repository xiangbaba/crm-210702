package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.service.TblTranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Hashtable;
import java.util.List;


/**
 * @author lzx
 * @create 2021-06-25 16:45
 */
@Controller
@RequestMapping("/workbench/chart/transaction")
public class ChartController {
	@Autowired
	private TblTranService tblTranService;

	@RequestMapping("index")
	public String index(){
		return "workbench/chart/transaction/index";
	}
	@RequestMapping("/queryCountOfTranGroupByStage")
	@ResponseBody
	public Object queryCountOfTranGroupByStage(){
		return tblTranService.queryCountOfTranGroupByStage();
	}
}
