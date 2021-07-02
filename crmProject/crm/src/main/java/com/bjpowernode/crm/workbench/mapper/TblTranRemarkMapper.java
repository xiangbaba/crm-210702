package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblTranRemark;

import java.util.List;

public interface TblTranRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblTranRemark record);

    int insertSelective(TblTranRemark record);

    TblTranRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblTranRemark record);

    int updateByPrimaryKey(TblTranRemark record);

    int insertTranRemarkByList(List<TblTranRemark> trList);
}