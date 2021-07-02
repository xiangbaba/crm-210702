package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.TblTran;
import com.bjpowernode.crm.workbench.domain.TblTranHistory;
import com.bjpowernode.crm.workbench.service.TblCustomerService;;
import com.bjpowernode.crm.workbench.service.TblTranHistoryService;
import com.bjpowernode.crm.workbench.service.TblTranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author lzx
 * @create 2021-06-21 18:35
 */
@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
	@Autowired
	private TblTranService tblTranService;
	@Autowired
	private UserService userService;
	@Autowired
	private TblDicValueService tblDicValueService;
	@Autowired
	private TblCustomerService tblCustomerService;
	@Autowired
	private TblTranHistoryService tblTranHistoryService;
	/**
	 * @return 跳转到交易页
	 */
	@RequestMapping("/index")
	public String index(HttpSession session){
		List<TblDicValue> sourceList = tblDicValueService.selectDicValueByTypeCode("source");
		List<TblDicValue> transactionTypeList = tblDicValueService.selectDicValueByTypeCode("transactionType");
		List<TblDicValue> stageList = tblDicValueService.selectDicValueByTypeCode("stage");
		session.setAttribute("transactionTypeList", transactionTypeList);
		session.setAttribute("sourceList", sourceList);
		session.setAttribute("stageList",stageList);
		return "workbench/transaction/index";
	}

	/**
	 *跳转到交易详情页
	 */
	@RequestMapping("/detailTran")
	public ModelAndView detailTranIndex(String id){
		List<TblTranHistory> tranHistoryList=tblTranHistoryService.selectByTranId(id);
		ModelAndView mv = new ModelAndView();
		//根据id查询tran
		TblTran tran = tblTranService.selectByIdForDetail(id);
		//根据tran对象的stage在possibility文件中获取可能性
		ResourceBundle rb = ResourceBundle.getBundle("possibility");
		String possibility = rb.getString(tran.getStage());
		tran.setPossibility(possibility);
		mv.addObject("tran", tran);
		mv.setViewName("workbench/transaction/detail");

		return mv;
	}

	/**
	 * 创建交易页面根据tran对象的stage在possibility文件中获取可能性
	 */
	@RequestMapping("/getPossibilityByStageValue")
	@ResponseBody
	public Object getPossibilityByStageValue(String stageValue){
		ResourceBundle rb = ResourceBundle.getBundle("possibility");
		return rb.getObject(stageValue);
	}

	/**
	 *创建线索页
	 */
	@RequestMapping("/createTran")
	public ModelAndView createTran(){
		ModelAndView mv = new ModelAndView();
		//获取页面所有者
		List<User> users = userService.selectAllUsers();
		//获取交易阶段
		mv.addObject("userList",users);
		mv.setViewName("workbench/transaction/save");
		return mv;
	}

	/**
	 * 自动补全,根据输入的内容进行模糊查询
	 */
	@RequestMapping("/queryCustomerByName")
	@ResponseBody
	public Object queryCustomerByName(String customerName){
		return tblCustomerService.selectCustomerByName(customerName);
	}

	/**
	 *创建线索保存
	 */
	@RequestMapping("/saveCreateTran")
	@ResponseBody
	public Object saveCreateTran(HttpSession session,TblTran tran,String customerName){
		ReturnObject returnObject = new ReturnObject();
		User user = (User) session.getAttribute(Contants.SESSION_USER);
		//封装参数
		tran.setId(UUIDUtils.getUUID());
		tran.setCreateBy(user.getId());
		tran.setCreateTime(DateUtils.formatDateTime(new Date()));
		Map<String,Object> map = new HashMap<>();
		map.put("user", user);
		map.put("tran", tran);
		map.put("customerName", customerName);
		try {
			tblTranService.saveCreateTran(map);
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("创建失败");
		}
		return returnObject;
	}
	/**
	 * 分页查询
	 */
	@RequestMapping("/queryTranForPageByCondition")
	@ResponseBody
	public Object queryTranForPageByCondition(Integer pageNo, Integer pageSize, String customerId, String stage, String owner, String contactsId, String type, String source){
		Map<String,Object> map = new HashMap<>();
		map.put("pageNo",(pageNo-1)*pageSize);
		map.put("pageSize",pageSize);
		map.put("customerId",customerId);
		map.put("stage",stage);
		map.put("owner",owner);
		map.put("contactsId",contactsId);
		map.put("type",type);
		map.put("source",source);
		return tblTranService.selectTranByLimitAndCondition(map);

	}

}
