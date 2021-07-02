package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.service.TblActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * @author lzx
 * @create 2021/6/10 18:26
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityRemarkController {
	@Autowired
	private TblActivityRemarkService tblActivityRemarkService;

	/**
	 * 保存被修改的备注
	 * @param id 需要被修改的备注的id
	 * @param noteContent 备注内容
	 * @param session 写入当前的修改者
	 * @return 返回结果级
	 */
	@RequestMapping("/saveEditActivityRemark")
	@ResponseBody
	public Object saveEditActivityRemark(String id,String noteContent,HttpSession session){
		//根据session中的Session对象获取当前的登录用户
		User user=(User)session.getAttribute(Contants.SESSION_USER);
		TblActivityRemark tblActivityRemark = new TblActivityRemark();
		//写入修改内容
		tblActivityRemark.setId(id);
		tblActivityRemark.setNoteContent(noteContent);
		tblActivityRemark.setEditTime(DateUtils.formatDateTime(new Date()));
		tblActivityRemark.setEditBy(user.getId());
		tblActivityRemark.setEditFlag("1");
		//调用service层方法
		int i = tblActivityRemarkService.updateByPrimaryKeySelective(tblActivityRemark);
		ReturnObject returnObject = new ReturnObject();
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("更新数据失败!");
		}
		return returnObject;
	}

	/**
	 * 保存备注
	 * @param activityId 备注表中市场活动外键
	 * @param noteContent 备注内容
	 * @param session 获取用户
	 * @return
	 */
	@RequestMapping("/saveCreateActivityRemark")
	@ResponseBody
	public Object saveCreateActivityRemark(String activityId, String noteContent, HttpSession session){
		//获取用户
		User user=(User)session.getAttribute(Contants.SESSION_USER);
		TblActivityRemark tblActivityRemark = new TblActivityRemark();
		//写入内容
		tblActivityRemark.setActivityId(activityId);
		tblActivityRemark.setId(UUIDUtils.getUUID());
		tblActivityRemark.setNoteContent(noteContent);
		tblActivityRemark.setCreateBy(user.getId());
		tblActivityRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
		tblActivityRemark.setEditBy(user.getId());
		tblActivityRemark.setEditTime(DateUtils.formatDateTime(new Date()));
		tblActivityRemark.setEditFlag("0");
		int insert = tblActivityRemarkService.insert(tblActivityRemark);
		//返回市场活动详情页时调用方法再刷新一次备注表
		List<TblActivityRemark> tblActivityRemarks = tblActivityRemarkService.selectRemarkByActivityId(activityId);
		ReturnObject returnObject = new ReturnObject();
		if (insert>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			returnObject.setRetData(tblActivityRemarks);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("保存数据失败!");
		}
		return returnObject;
	}

	/**
	 * @param id 删除指定id的备注
	 * @return 返回处理结果集
	 */
	@RequestMapping("/deleteActivityRemarkById")
	@ResponseBody
	public Object deleteActivityRemarkById(String id){
		int i = tblActivityRemarkService.deleteByPrimaryKey(id);
		ReturnObject returnObject = new ReturnObject();
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("删除数据失败!");
		}
		return returnObject;


	}

}
