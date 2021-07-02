package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblClueRemark;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-18 21:47
 */
public interface TblClueRemarkService {
	List<TblClueRemark> selectClueRemarkByClueId(String id);

	int updateByPrimaryKeySelective(TblClueRemark tblClueRemark);

	int deleteByPrimaryKey(String id);

	int insert(TblClueRemark tblClueRemark);

	List<TblActivityRemark> selectActivityRemarkByClueId(String clueId);
}
