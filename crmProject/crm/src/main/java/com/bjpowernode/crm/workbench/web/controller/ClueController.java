package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblClue;
import com.bjpowernode.crm.workbench.domain.TblClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.TblClueRemark;
import com.bjpowernode.crm.workbench.service.TbActivityService;
import com.bjpowernode.crm.workbench.service.TblClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.TblClueRemarkService;
import com.bjpowernode.crm.workbench.service.TblClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author lzx
 * @create 2021-06-18 8:48
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
	@Autowired
	private TblClueRemarkService tblClueRemarkService;
	@Autowired
	private TblClueService tblClueService;
	@Autowired
	private TblDicValueService tblDicValueService;
	@Autowired
	private UserService userService;
	@Autowired
	private TbActivityService tbActivityService;
	@Autowired
	private TblClueActivityRelationService tblClueActivityRelationService;

	/**
	 * @return 跳转到线索主界面
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		//查询所有称呼
		List<TblDicValue> appellationList = tblDicValueService.selectDicValueByTypeCode("appellation");
		//查询所有的线索来源
		List<TblDicValue> sourceList = tblDicValueService.selectDicValueByTypeCode("source");
		//查询所有的线索状态
		List<TblDicValue> clueStateList = tblDicValueService.selectDicValueByTypeCode("clueState");
		//查询所有的用户
		List<User> users = userService.selectAllUsers();

		request.setAttribute("appellationList",appellationList);
		request.setAttribute("sourceList",sourceList);
		request.setAttribute("clueStateList",clueStateList);
		request.setAttribute("userList", users);
		return "workbench/clue/index";
	}

	/**
	 * 多条件分页查询
	 * @param pageNo 第几页
	 * @param pageSize 一页展示多少条数据
	 * @param fullName 线索名称
	 * @param owner 拥有者
	 * @param source 来源
	 * @param phone 座机
	 * @param mphone 手机
	 * @param state 线索状态
	 * @param company 公司
	 * @return 返回结果集
	 */
	@RequestMapping("/queryClueForPageByCondition")
	@ResponseBody
	public Object queryClueForPageByCondition(Integer pageNo, Integer pageSize, String fullName, String owner, String source, String phone, String mphone, String state, String company){
		Map<String,Object> map = new HashMap<>();
		map.put("pageNo",(pageNo-1)*pageSize);
		map.put("pageSize",pageSize);
		map.put("fullName",fullName);
		map.put("owner",owner);
		map.put("source",source);
		map.put("phone",phone);
		map.put("mphone",mphone);
		map.put("state",state);
		map.put("company",company);
		//调用service层参数,返回值是map,第一个键值对是查询出来的结果集,第二个键值对是
		//总条数
		return tblClueService.queryClueForPageByCondition(map);
	}

	/**
	 * 线索详情页
	 */
	@RequestMapping("/detailClue")
	public String detailClue(Model model,String id){
		//根据线索id查询对应的线索和线索备注
		TblClue clue = tblClueService.selectClueForDetailByClueId(id);
		List<TblClueRemark> tblClueRemark = tblClueRemarkService.selectClueRemarkByClueId(id);
		List<TblActivity> activityList= tbActivityService.selectActivityForDetailByClueId(id);
		model.addAttribute("clue", clue);
		model.addAttribute("remarkList", tblClueRemark);
		model.addAttribute("activityList", activityList);
		return "workbench/clue/detail";
	}
	/**
	 * 创建线索
	 */
	@RequestMapping("/saveCreateClue")
	@ResponseBody
	public Object saveCreateClue(HttpSession session,TblClue clue){
		ReturnObject returnObject = new ReturnObject();
		User user = (User) session.getAttribute(Contants.SESSION_USER);
		clue.setId(UUIDUtils.getUUID());
		clue.setCreateBy(user.getId());
		clue.setCreateTime(DateUtils.formatDateTime(new Date()));
		int insert = tblClueService.insert(clue);
		if (insert>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("创建失败");
		}
		return returnObject;
	}
	/**
	 * 解除市场活动关联
	 */
	@RequestMapping("/dissociatedActivity")
	@ResponseBody
	public Object dissociatedActivity(String activityId,String clueId){
		ReturnObject returnObject = new ReturnObject();
		int i = tblClueService.deleteRelationByActivityIdAndClueId(activityId,clueId);
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("解除失败");
		}
		return returnObject;
	}
	/**
	 * 显示所有未关联的市场活动
	 */
	@RequestMapping("/selectUncorrelatedActivityByClueIdAndName")
	@ResponseBody
	public Object selectUncorrelatedActivityByClueIdAndName(String clueId,String activityName){
		return tbActivityService.selectUncorrelatedActivityByClueIdAndName(activityName, clueId);
	}
	/**
	 * 将信息展示到修改页面
	 */
	@RequestMapping("/editClue")
	@ResponseBody
	public Object editClue(String id){
		return tblClueService.selectClueForDetailByClueId(id);
	}
	/**
	 * 更新线索
	 */
	@RequestMapping("/saveEditClue")
	@ResponseBody
	public Object saveEditClue(HttpSession session, TblClue clue){
		User user = (User) session.getAttribute(Contants.SESSION_USER);
		clue.setEditBy(user.getId());
		clue.setEditTime(DateUtils.formatDateTime(new Date()));
		int i = tblClueService.updateByPrimaryKeySelective(clue);
		ReturnObject returnObject = new ReturnObject();
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("解除失败");
		}
		return returnObject;
	}
	/**
	 * 根据id数组删除线索
	 */
	@RequestMapping("/deleteClueByIds")
	@ResponseBody
	public Object deleteClueByIds(String[] id){
		int i = tblClueService.deleteClueByIds(id);
		ReturnObject returnObject = new ReturnObject();
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("删除数据失败!");
		}
		return returnObject;
	}
	/**
	 * 关联市场活动
	 */
	@RequestMapping("/saveBundActivity")
	@ResponseBody
	public Object saveBundActivity(String[] activityId,String clueId){
		ReturnObject returnObject = new ReturnObject();
		//创建市场活动和线索关系表对象
		TblClueActivityRelation clueActivityRelation = null;
		//创建市场活动和线索关系列表
		List<TblClueActivityRelation> clueActivityRelations = new ArrayList<>();
		for (String ai : activityId) {
			clueActivityRelation = new TblClueActivityRelation();
			clueActivityRelation.setId(UUIDUtils.getUUID());
			clueActivityRelation.setClueId(clueId);
			clueActivityRelation.setActivityId(ai);
			clueActivityRelations.add(clueActivityRelation);
		}
		//调用service层中的方法批量插入市场活动和线索关系表对象
		int i = tblClueActivityRelationService.insertByClueIdAndActivityId(clueActivityRelations);
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			returnObject.setRetData(tbActivityService.selectByPrimaryKeys(activityId));
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("关联失败");
		}
		return returnObject;
	}

	/**
	 * 跳转到转换页面
	 * @param id 线索id
	 */
	@RequestMapping("/convertClue")
	public ModelAndView convertClue(String id){
		ModelAndView mv = new ModelAndView();
		//根据线索id查询对应线索
		TblClue clue = tblClueService.selectClueForDetailByClueId(id);
		//查询阶段
		List<TblDicValue> stage = tblDicValueService.selectDicValueByTypeCode("stage");
		mv.addObject("stageList", stage);
		mv.addObject("clue", clue);
		mv.setViewName("workbench/clue/convert");
		return mv;
	}
	/**
	 * 转换线索
	 */
	@RequestMapping("/saveConvertClue")
	@ResponseBody
	public Object saveConvertClue(HttpSession session, String clueId, String isCreateTran, String amountOfMoney, String tradeName, String expectedClosingDate, String stage, String activityId){
		ReturnObject returnObject = new ReturnObject();
		//创建map集合接受参数
		Map<String,Object> map = new HashMap<>();
		map.put("clueId",clueId);
		map.put("isCreateTran",isCreateTran);
		map.put("amountOfMoney",amountOfMoney);
		map.put("tradeName",tradeName);
		map.put("expectedClosingDate",expectedClosingDate);
		map.put("stage",stage);
		map.put("activityId",activityId);
		User user =(User) session.getAttribute(Contants.SESSION_USER);
		map.put("user", user);
		//调用service层方法
		try {
			tblClueService.saveConvertClue(map);
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		} catch (Exception e) {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("转换失败");
			e.printStackTrace();
		}
		return returnObject;
	}
}
