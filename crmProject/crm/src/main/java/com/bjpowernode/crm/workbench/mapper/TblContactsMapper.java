package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblContacts;

public interface TblContactsMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblContacts record);

    int insertSelective(TblContacts record);

    TblContacts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblContacts record);

    int updateByPrimaryKey(TblContacts record);
}