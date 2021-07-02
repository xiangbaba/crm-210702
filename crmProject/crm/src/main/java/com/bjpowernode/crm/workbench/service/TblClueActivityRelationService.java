package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblClueActivityRelation;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-19 19:58
 */
public interface TblClueActivityRelationService {
	int insertByClueIdAndActivityId(List<TblClueActivityRelation> tblClueActivityRelations);
}
