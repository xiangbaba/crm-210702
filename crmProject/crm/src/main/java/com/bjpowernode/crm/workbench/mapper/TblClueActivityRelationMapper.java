package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblClueActivityRelation;

import java.util.List;

public interface TblClueActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblClueActivityRelation record);

    int insertSelective(TblClueActivityRelation record);

    TblClueActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblClueActivityRelation record);

    int updateByPrimaryKey(TblClueActivityRelation record);

    int insertByClueIdAndActivityId(List<TblClueActivityRelation> tblClueActivityRelations);

	List<TblClueActivityRelation> selectClueActivityRelationByClueId(String clueId);

	int deleteClueActivityRelationByClueId(String clueId);
}