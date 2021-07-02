package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblContactsRemark;

public interface TblContactsRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblContactsRemark record);

    int insertSelective(TblContactsRemark record);

    TblContactsRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblContactsRemark record);

    int updateByPrimaryKey(TblContactsRemark record);
}