package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.commons.domain.ov.PaginationVO;
import com.bjpowernode.crm.workbench.domain.TblActivity;

import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021/6/7 21:36
 */
public interface TbActivityService {
	/**
	 * 分页查询
	 * @return
	 */
	PaginationVO queryActivityForPageByCondition(Map<String,Object> map);

	/**
	 * @param record 插入一条市场活动记录
	 */
	int insert(TblActivity record);

	/**
	 * @param id 根据id查询一条信息
	 */
	TblActivity selectByPrimaryKey(String id);

	/**
	 * 根据id更新一条信息
	 * @param record 需要被更新的记录
	 */
	int updateByPrimaryKeySelective(TblActivity record);

	/**
	 * @param ids 需要被删除的记录的id
	 */
	int deleteActivityByIds(String [] ids);
	/**
	 * 根据id查询活动详情页
	 */
	TblActivity selectActivityByIdForParticulars(String id);

	List<TblActivity> selectAllActivityForUpload();

	List<TblActivity> selectByPrimaryKeys(String[] id);

	int saveCreateActivityByList(List<TblActivity> activities);

	List<TblActivity> selectActivityForDetailByClueId(String id);

	List<TblActivity> selectUncorrelatedActivityByClueIdAndName(String activityName,String clueId);
}
