package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblClueRemark;

import java.util.List;

public interface TblClueRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblClueRemark record);

    int insertSelective(TblClueRemark record);

    List<TblClueRemark> selectClueRemarkByClueId(String id);

    int updateByPrimaryKeySelective(TblClueRemark record);

    int updateByPrimaryKey(TblClueRemark record);

    List<TblActivityRemark> selectActivityRemarkByClueId(String clueId);

	int deleteClueRemarkByClueId(String clueId);
}