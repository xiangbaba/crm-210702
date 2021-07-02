package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.service.TbDicTypeService;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lzx
 * @create 2021/6/6 9:17
 */
@Controller
@RequestMapping("/settings/dictionary/value")
public class TbDicValueController {
	@Autowired
	private TblDicValueService tblDicValueService;
	@Autowired
	private TbDicTypeService tbDicTypeService;
	/**
	 * 字典主页面的字典值连接
	 * @return 跳转到字典值主界面
	 */
	@RequestMapping("/index")
	public String getDicValueListAndReturnIndex(HttpServletRequest request, @RequestParam(name="page",required = true,defaultValue = "1")int page, @RequestParam(name="size",required = true,defaultValue = "5")int size ){
		List<TblDicValue> dicValueList = tblDicValueService.selectByExample(page,size);
		PageInfo pageInfo =new PageInfo(dicValueList);
		//将PageInfo放到request作用域中
		request.setAttribute("pageInfo", pageInfo);
		return "settings/dictionary/value/index";
	}

	/**
	 * 跳转到新建页面
	 */
	@RequestMapping("/toSave")
	public String toSave(HttpServletRequest request){
		List<TblDicType> tblDicTypes = tbDicTypeService.selectByExample();
		request.setAttribute("dicTypeList", tblDicTypes);
		return "settings/dictionary/value/save";
	}

	/**
	 * 验证同一个字典类型编码下的字典值，是不能重复的
	 * @param value  字典值
	 * @param dicTypeCode 字典类型编码
	 * @return 返回结果集
	 */
	@RequestMapping("/checkValue")
	@ResponseBody
	public Object checkValue(String value,String dicTypeCode){
		ReturnObject returnObject = new ReturnObject();
		List<String> strings = tblDicValueService.selectValueByTypeCode(dicTypeCode);
		for (String string : strings) {
			if (value.equals(string)){
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("字典值重复");
				return returnObject;
			}
		}
		returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		return returnObject;
	}

	/**
	 * 添加字典值
	 * @param dicValue 字典值对象
	 * @return 返回结果集
	 */
	@RequestMapping("/saveCreateDicValue")
	@ResponseBody
	public Object saveCreateDicValue(TblDicValue dicValue){
		//设置uuid
		dicValue.setId(UUIDUtils.getUUID());
		//执行插入语句
		int insert = tblDicValueService.insert(dicValue);
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
	 * 删除选中的id
	 * @param id 选中的id
	 */
	@RequestMapping("/deleteDicValueByIds")
	@ResponseBody
	public Object deleteDicValueByIds(String [] id){
		int i = tblDicValueService.deleteDicValueByIds(id);
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
	 * 字典值主界面跳转至编辑界面
	 */
	@RequestMapping("/editDicValue")
	public String editDicValueIndex(String id,HttpServletRequest request){
		TblDicValue tblDicValue = tblDicValueService.selectByPrimaryKey(id);
		request.setAttribute("dicValue",tblDicValue);
		return "settings/dictionary/value/edit";
	}
	/**
	 * 更新字典值
	 */
	@RequestMapping("/saveEditDicValue")
	@ResponseBody
	public Object saveEditDicValue(TblDicValue dicValue){
		int i = tblDicValueService.updateByPrimaryKey(dicValue);
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

