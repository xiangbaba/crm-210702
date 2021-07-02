package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblClueRemark;
import com.bjpowernode.crm.workbench.mapper.TblClueRemarkMapper;
import com.bjpowernode.crm.workbench.service.TblClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-18 21:47
 */
@Service
public class TblClueRemarkServiceImpl implements TblClueRemarkService {
	@Autowired
	private TblClueRemarkMapper tblClueRemarkMapper;

	@Override
	public List<TblClueRemark> selectClueRemarkByClueId(String id) {
		return tblClueRemarkMapper.selectClueRemarkByClueId(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TblClueRemark tblClueRemark) {
		return tblClueRemarkMapper.updateByPrimaryKeySelective(tblClueRemark);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return tblClueRemarkMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TblClueRemark tblClueRemark) {
		return tblClueRemarkMapper.insert(tblClueRemark);
	}

	@Override
	public List<TblActivityRemark> selectActivityRemarkByClueId(String clueId) {
		return tblClueRemarkMapper.selectActivityRemarkByClueId(clueId);
	}
}
