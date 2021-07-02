package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblClueRemark;
import com.bjpowernode.crm.workbench.service.TblClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author lzx
 * @create 2021-06-19 19:05
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueRemarkController {
	@Autowired
	private TblClueRemarkService tblClueRemarkService;
	/**
	 * 修改线索备注
	 */
	@RequestMapping("/saveEditClueRemark")
	@ResponseBody
	public Object saveEditClueRemark(HttpSession session, String id, String noteContent){
		//根据session中的Session对象获取当前的登录用户
		User user=(User)session.getAttribute(Contants.SESSION_USER);
		TblClueRemark tblClueRemark = new TblClueRemark();
		//写入修改内容
		tblClueRemark.setId(id);
		tblClueRemark.setNoteContent(noteContent);
		tblClueRemark.setEditTime(DateUtils.formatDateTime(new Date()));
		tblClueRemark.setEditBy(user.getId());
		tblClueRemark.setEditFlag("1");
		//调用service层方法
		int i = tblClueRemarkService.updateByPrimaryKeySelective(tblClueRemark);
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
	 * 删除线索备注
	 */
	@RequestMapping("/deleteClueRemarkById")
	@ResponseBody
	public Object deleteClueRemarkById(String id){
		ReturnObject returnObject = new ReturnObject();
		int i = tblClueRemarkService.deleteByPrimaryKey(id);
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("删除数据失败!");
		}
		return returnObject;
	}
	/**
	 * 保存线索备注
	 */
	@RequestMapping("/saveCreateClueRemark")
	@ResponseBody
	public Object saveCreateClueRemark(String noteContent,String clueId,HttpSession session) {
		//获取用户
		User user = (User) session.getAttribute(Contants.SESSION_USER);
		TblClueRemark tblClueRemark = new TblClueRemark();
		//写入内容
		tblClueRemark.setClueId(clueId);
		tblClueRemark.setId(UUIDUtils.getUUID());
		tblClueRemark.setNoteContent(noteContent);
		tblClueRemark.setCreateBy(user.getId());
		tblClueRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
		tblClueRemark.setEditBy(user.getId());
		tblClueRemark.setEditTime(DateUtils.formatDateTime(new Date()));
		tblClueRemark.setEditFlag("0");
		int insert = tblClueRemarkService.insert(tblClueRemark);
		//返回市场活动详情页时调用方法再刷新一次备注表
		List<TblActivityRemark> tblActivityRemarks = tblClueRemarkService.selectActivityRemarkByClueId(clueId);
		ReturnObject returnObject = new ReturnObject();
		if (insert > 0) {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			returnObject.setRetData(tblActivityRemarks);
		} else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("保存数据失败!");
		}
		return returnObject;
	}
}
