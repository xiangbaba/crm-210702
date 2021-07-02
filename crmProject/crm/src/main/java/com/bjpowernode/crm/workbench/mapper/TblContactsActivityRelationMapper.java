package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblContactsActivityRelation;

import java.util.List;

public interface TblContactsActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblContactsActivityRelation record);

    int insertSelective(TblContactsActivityRelation record);

    TblContactsActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblContactsActivityRelation record);

    int updateByPrimaryKey(TblContactsActivityRelation record);

	int insertContactsActivityRelationByList(List<TblContactsActivityRelation> contactsArs);
}