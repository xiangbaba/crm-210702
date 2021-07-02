package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblCustomerRemark;

public interface TblCustomerRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblCustomerRemark record);

    int insertSelective(TblCustomerRemark record);

    TblCustomerRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblCustomerRemark record);

    int updateByPrimaryKey(TblCustomerRemark record);
}