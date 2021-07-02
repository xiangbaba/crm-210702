package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.service.TbDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lzx
 * @create 2021/6/5 20:58
 */
@Controller
@RequestMapping("/settings/dictionary/type")
public class TbDicTypeController {
	@Autowired
	private TbDicTypeService tbDicTypeService;

	/**
	 * 字典主页面的字典类型连接
	 * @return 跳转到字典类型主界面
	 */
	@RequestMapping("/index")
	public String getDicTypeListAndReturnIndex(HttpServletRequest request){
		//获取字典类型并放到request作用域中
		List<TblDicType> dicTypeList = tbDicTypeService.selectByExample();
		request.setAttribute("dicTypeList",dicTypeList);
		return "settings/dictionary/type/index";
	}

	/**
	 * 字典类型主界面的创建连接
	 * @return 转到save页面
	 */
	@RequestMapping("/toSave.do")
	public String toSave(){
		return "settings/dictionary/type/save";
	}

	/**
	 * 处理创建字典类型的请求
	 * @param dicType 参数是一个decType对象
	 * @return 返回returnObject对象
	 */
	@RequestMapping("/saveCreateDicType.do")
	@ResponseBody
	public Object createDicType(TblDicType dicType){
		int insert = tbDicTypeService.insert(dicType);
		ReturnObject returnObject = new ReturnObject();
		if (insert>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			returnObject.setMessage("插入数据成功!");
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("插入数据失败!");
		}
		return returnObject;
	}

	/**
	 * 在插入一条语句时先检查数中是否已存在相同主键的记录
	 */
	@RequestMapping("/checkCode.do")
	@ResponseBody
	public Object checkCode(TblDicType dicType){
		TblDicType tblDicType = tbDicTypeService.selectByPrimaryKey(dicType.getCode());
		ReturnObject returnObject = new ReturnObject();
		if (tblDicType!=null){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("编码已经存在");
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}
		return returnObject;
	}

	/**
	 *删除选中的记录
	 */
	@RequestMapping("/deleteDicTypeByCodes.do")
	@ResponseBody
	public Object deleteDicTypeByCodes(String [] code){
		int i = tbDicTypeService.deleteByCodeArrays(code);
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
	 *跳转到更新界面
	 */
	@RequestMapping("/editDicType.do")
	public String editDicType(String code ,HttpServletRequest request){
		TblDicType tblDicType = tbDicTypeService.selectByPrimaryKey(code);
		request.setAttribute("dicType", tblDicType);
		return "settings/dictionary/type/edit";
	}

	/**
	 *更新数据
	 */
	@RequestMapping("/saveEditDicType.do")
	@ResponseBody
	public Object saveEditDicType(TblDicType recod){
		int i = tbDicTypeService.updateByPrimaryKey(recod);
		ReturnObject returnObject = new ReturnObject();
		if (i>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("更新数据失败!");
		}
		return returnObject;
	}
}
