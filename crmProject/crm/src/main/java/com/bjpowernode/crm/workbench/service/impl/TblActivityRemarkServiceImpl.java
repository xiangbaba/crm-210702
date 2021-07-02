package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblActivityRemarkExample;
import com.bjpowernode.crm.workbench.mapper.TblActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.TblActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzx
 * @create 2021/6/10 16:57
 */
@Service
public class TblActivityRemarkServiceImpl implements TblActivityRemarkService {
	@Autowired
	private TblActivityRemarkMapper tblActivityRemarkMapper;
	@Override
	public List<TblActivityRemark> selectRemarkByActivityId(String activityId) {
		return tblActivityRemarkMapper.selectRemarkByActivityId(activityId);
	}


	@Override
	public int insert(TblActivityRemark record) {
		return tblActivityRemarkMapper.insert(record);
	}

	@Override
	public List<TblActivityRemark> selectByExample() {
		return tblActivityRemarkMapper.selectByExample(new TblActivityRemarkExample());
	}

	@Override
	public int updateByPrimaryKeySelective(TblActivityRemark record) {
		return tblActivityRemarkMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return tblActivityRemarkMapper.deleteByPrimaryKey(id);
	}
}
