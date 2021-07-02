package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TblClueActivityRelation;
import com.bjpowernode.crm.workbench.mapper.TblClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.service.TblClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-19 19:58
 */
@Service
public class TblClueActivityRelationServiceImpl implements TblClueActivityRelationService {
	@Autowired
	private TblClueActivityRelationMapper tblClueActivityRelationMapper;
	@Override
	public int insertByClueIdAndActivityId(List<TblClueActivityRelation> tblClueActivityRelations) {
		return tblClueActivityRelationMapper.insertByClueIdAndActivityId(tblClueActivityRelations);
	}
}
