package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;

import java.util.List;

/**
 * @author lzx
 * @create 2021/6/10 16:57
 */
public interface TblActivityRemarkService {
	List<TblActivityRemark> selectRemarkByActivityId(String activityId);

	int insert(TblActivityRemark record);

	List<TblActivityRemark> selectByExample();

	int updateByPrimaryKeySelective(TblActivityRemark record);

	int deleteByPrimaryKey(String id);

}
