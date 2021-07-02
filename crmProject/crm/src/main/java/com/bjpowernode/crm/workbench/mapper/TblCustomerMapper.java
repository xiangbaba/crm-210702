package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblCustomer;

import java.util.List;

public interface TblCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblCustomer record);

    int insertSelective(TblCustomer record);

    TblCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblCustomer record);

    int updateByPrimaryKey(TblCustomer record);

    List<TblCustomer> selectCustomerByName(String name);
}