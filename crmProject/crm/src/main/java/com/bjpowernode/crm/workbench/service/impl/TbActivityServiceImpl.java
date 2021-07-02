package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.domain.ov.PaginationVO;
import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.mapper.TblActivityMapper;
import com.bjpowernode.crm.workbench.service.TbActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021/6/7 21:36
 */
@Service
public class TbActivityServiceImpl implements TbActivityService {
	@Autowired
	private TblActivityMapper tblActivityMapper;

	@Override
	public PaginationVO queryActivityForPageByCondition(Map<String, Object> map) {
		//创建一个结果视图对象
		PaginationVO<TblActivity> paginationVO = new PaginationVO<>();
		//调用mapper层方法
		List<TblActivity> tblActivities = tblActivityMapper.selectActivityForPageByCondition(map);
		//查询总记录条数
		Integer total = tblActivityMapper.getTotalByCondition(map);
		//封装到结果对象
		paginationVO.setActivityList(tblActivities);
		paginationVO.setTotalRows(total);
		return paginationVO;
	}

	@Override
	public int insert(TblActivity record) {
		return tblActivityMapper.insert(record);
	}

	@Override
	public TblActivity selectByPrimaryKey(String id) {
		return tblActivityMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TblActivity record) {
		return tblActivityMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteActivityByIds(String[] ids) {
		return tblActivityMapper.deleteActivityByIds(ids);
	}

	@Override
	public TblActivity selectActivityByIdForParticulars(String id) {
		return tblActivityMapper.selectActivityByIdForParticulars(id);
	}

	@Override
	public List<TblActivity> selectAllActivityForUpload() {
		return tblActivityMapper.selectAllActivityForUpload();
	}

	@Override
	public List<TblActivity> selectByPrimaryKeys(String[] id) {
		return tblActivityMapper.selectByPrimaryKeys(id);
	}

	@Override
	public int saveCreateActivityByList(List<TblActivity> activities) {
		return tblActivityMapper.insertActivities(activities);
	}

	@Override
	public List<TblActivity> selectActivityForDetailByClueId(String id) {
		return tblActivityMapper.selectActivityForDetailByClueId(id);
	}

	@Override
	public List<TblActivity> selectUncorrelatedActivityByClueIdAndName(String clueId,String activityName ) {
		return tblActivityMapper.selectUncorrelatedActivityByClueIdAndName(clueId,activityName);
	}

}
