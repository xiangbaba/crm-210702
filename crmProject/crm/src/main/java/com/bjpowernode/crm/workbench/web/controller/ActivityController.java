package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.domain.ov.PaginationVO;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.service.TbActivityService;
import com.bjpowernode.crm.workbench.service.TblActivityRemarkService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author lzx
 * @create 2021/6/7 21:04
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
	@Autowired
	private TbActivityService tbActivityService;
	@Autowired
	private UserService userService;
	@Autowired
	private TblActivityRemarkService tblActivityRemarkService;
	/**
	 * @return 跳转至市场活动页面
	 */
	@RequestMapping("/index.do")
	public String activityIndex(Model model) {
		List<User> users = userService.selectAllUsers();
		model.addAttribute("userList",users);
		return "workbench/activity/index";
	}

	/**
	 * 接收前段ajax分页查询请求
	 * @param pageNo 第几页
	 * @param pageSize 一页查多少条
	 * @param name 市场活动名称
	 * @param owner 活动拥有者
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 返回 paginationVO对象 交给前段页面处理
	 */
	@RequestMapping("/queryActivityForPageByCondition")
	@ResponseBody
	public PaginationVO queryActivityForPageByCondition(Integer pageNo, Integer pageSize, String name, String owner, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		//分页公式
		pageNo = (pageNo-1)*pageSize;
		//将接收到的参数放到map中
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("name",name);
		map.put("owner",owner);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		//调用service层方法
		return tbActivityService.queryActivityForPageByCondition(map);
	}

	/**
	 * @param tblActivity 插入数据库的记录
	 * @return 返回结果集
	 */
	@RequestMapping("/saveCreateActivity")
	@ResponseBody
	public Object saveCreateActivity(TblActivity tblActivity,HttpSession session){
		//设置id 创建者,修改者,创建时间
		User user = (User) session.getAttribute(Contants.SESSION_USER);
		tblActivity.setId(UUIDUtils.getUUID());
		tblActivity.setCreateBy(user.getId());
		tblActivity.setEditBy(user.getId());
		tblActivity.setEditTime(DateUtils.formatDateTime(new Date()));
		tblActivity.setCreateTime(DateUtils.formatDateTime(new Date()));
		ReturnObject returnObject = new ReturnObject();
		//条件判断返回结果集
		int insert = tbActivityService.insert(tblActivity);
		if (insert>0){
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		}else {
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("插入数据失败");
		}
		return returnObject;
	}

	/**
	 * 处理修改单击事件请求
	 * @return
	 */
	@RequestMapping("/editActivity")
	@ResponseBody
	public Object editActivity(String id){
		//将需要修改的记录返回给前段页面
		return tbActivityService.selectByPrimaryKey(id);
	}

	/**
	 * 更新一条记录
	 * @param record 需要被更新的数据
	 */
	@RequestMapping("/saveEditActivity")
	@ResponseBody
	public Object saveEditActivity(TblActivity record, HttpSession session){
		//获取登录的用户
		User user=(User)session.getAttribute(Contants.SESSION_USER);
		//更改信息
		record.setEditBy(user.getId());
		record.setEditTime(DateUtils.formatDateTime(new Date()));
		//执行service层的方法
		int i = tbActivityService.updateByPrimaryKeySelective(record);
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
	 * 删除所选记录
	 */
	@RequestMapping("/deleteActivityByIds")
	@ResponseBody
	public Object deleteActivityByIds(String [] id){
		int i = tbActivityService.deleteActivityByIds(id);
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
	 * 跳转到市场活动详情页
	 */
	@RequestMapping("/detailActivity")
	public ModelAndView detailActivity(String id){
		ModelAndView modelAndView = new ModelAndView();
		TblActivity tblActivity = tbActivityService.selectActivityByIdForParticulars(id);
		List<TblActivityRemark> activityRemarks = tblActivityRemarkService.selectRemarkByActivityId(id);
		modelAndView.addObject("activity", tblActivity);
		modelAndView.addObject("remarkList", activityRemarks);
		modelAndView.setViewName("workbench/activity/detail");
		return modelAndView;
	}
	/**
	 * 处理批量导出请求
	 */
	@RequestMapping("/exportAllActivity")
	public void exportAllActivity(HttpServletResponse response) throws IOException {
		//获取全部列表
		List<TblActivity> tblActivities = tbActivityService.selectAllActivityForUpload();
		download(response, tblActivities);
	}

	/**
	 * @param id 需要导出的数据的id
	 * @param response 相应对象
	 */
	@RequestMapping("/exportActivitySelective")
	public void exportActivitySelective(String[] id,HttpServletResponse response) throws IOException {
		List<TblActivity> tblActivities = tbActivityService.selectByPrimaryKeys(id);
		download(response,tblActivities);
	}

	/**
	 * 用来导出市场活动记录的控制器
	 * @param response 响应体
	 * @param tblActivities 需要被导出的数据
	 */
	private void download(HttpServletResponse response, List<TblActivity> tblActivities) throws IOException {
		//创建一个workbook对象,对应一个excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建一个sheet对象,对应一个页面
		HSSFSheet sheet = wb.createSheet("市场活动");
		//创建一个row对象,对应一行
		HSSFRow row = sheet.createRow(0);
		//创建一个单元格
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("id");
		cell = row.createCell(1);
		cell.setCellValue("name");
		cell = row.createCell(2);
		cell.setCellValue("owner");
		cell = row.createCell(3);
		cell.setCellValue("startDate");
		cell = row.createCell(4);
		cell.setCellValue("endDate");
		cell = row.createCell(5);
		cell.setCellValue("cost");
		cell = row.createCell(6);
		cell.setCellValue("description");
		//创建HSSFCellStyle对象，对应样式
		HSSFCellStyle style=wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		TblActivity tblActivity = null;
		for (int i = 0; i < tblActivities.size(); i++) {
			tblActivity= tblActivities.get(i);
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(tblActivity.getId());
			cell = row.createCell(1);
			cell.setCellValue(tblActivity.getName());
			cell = row.createCell(2);
			cell.setCellValue(tblActivity.getOwner());
			cell = row.createCell(3);
			cell.setCellValue(tblActivity.getStartDate());
			cell = row.createCell(4);
			cell.setCellValue(tblActivity.getEndDate());
			cell = row.createCell(5);
			cell.setCellValue(tblActivity.getCost());
			cell = row.createCell(6);
			cell.setCellValue(tblActivity.getDescription());
		}
		//返回响应信息
		//1.设置响应类型
		response.setContentType("application/octet-stream;charset=UTF-8");

		//不同的浏览器接收响应头采用的编码格式不一样：
		//IE采用 urlencoded
		////火狐采用 ISO8859-1
		String fileName= URLEncoder.encode("市场活动列表","UTF-8");

		//默认情况下，浏览器接收到响应信息之后，直接在显示窗口中打开；
		//可以设置响应头信息，使浏览器接收到响应信息之后，在下载窗口打开
		response.addHeader("Content-Disposition","attachment;filename="+fileName+".xls");

		//2.获取输出流
		OutputStream os2=response.getOutputStream();

		wb.write(os2);
		os2.flush();
		wb.close();
	}
	/**
	 * 上传Excel文件并解析出所有activity对象将其插入数据库
	 */
	@RequestMapping("/importActivity")
	@ResponseBody
	public Object importActivity(HttpSession session,MultipartFile activityFile){
		Map<String,Object> retMap =null;
		//创建一个输入流
		try {
			retMap = new HashMap<>();
			User user = (User) session.getAttribute(Contants.SESSION_USER);
			List<TblActivity> activities = new ArrayList<>();
			TblActivity activity = null;
			InputStream is = activityFile.getInputStream();
			HSSFWorkbook wb = new HSSFWorkbook(is);
			//获取列表第一页
			HSSFSheet sheetAt = wb.getSheetAt(0);
			HSSFRow row = null;
			HSSFCell cell = null;
			for (int i = 1; i <= sheetAt.getLastRowNum(); i++) {
				activity = new TblActivity();
				activity.setCreateBy(user.getId());
				activity.setOwner(user.getId());
				activity.setId(UUIDUtils.getUUID());
				activity.setCreateTime(DateUtils.formatDateTime(new Date()));
				row = sheetAt.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (j==0){
						activity.setName(getCellValue(cell));
					}else if (j==1){
						activity.setStartDate(getCellValue(cell));
					}else if (j==2){
						activity.setEndDate(getCellValue(cell));
					}else if (j==3){
						activity.setCost(getCellValue(cell));
					}else if (j==4){
						activity.setDescription(getCellValue(cell));
					}
				}
				activities.add(activity);
			}
			//调用service层方法，保存数据
			int ret=tbActivityService.saveCreateActivityByList(activities);

			retMap.put("code",Contants.RETURN_OBJECT_CODE_SUCCESS);
			retMap.put("count",ret);
		} catch (IOException e) {
			retMap.put("code",Contants.RETURN_OBJECT_CODE_FAIL);
			retMap.put("message","系统忙，请稍后重试...");
			e.printStackTrace();
		}
		return retMap;

	}
	public static String getCellValue(HSSFCell cell){
		String ret="";
		switch (cell.getCellType()){
			case HSSFCell.CELL_TYPE_STRING:
				ret=cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				ret=cell.getBooleanCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				ret=cell.getNumericCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				ret=cell.getCellFormula();
				break;
			default:
				ret="";
		}

		return ret;
	}


}
