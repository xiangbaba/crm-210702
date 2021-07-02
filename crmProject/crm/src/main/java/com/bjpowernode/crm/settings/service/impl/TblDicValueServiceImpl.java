package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.TblDicValueExample;
import com.bjpowernode.crm.settings.domain.TblUserExample;
import com.bjpowernode.crm.settings.mapper.TblDicValueMapper;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzx
 * @create 2021/6/5 21:16
 */
@Service
public class TblDicValueServiceImpl implements TblDicValueService {
	@Autowired
	private TblDicValueMapper tblDicValueMapper;

	@Override
	public List<TblDicValue> selectByExample(int page,int size) {
		PageHelper.startPage(page, size);
		return tblDicValueMapper.selectByExample(new TblDicValueExample());
	}

	@Override
	public List<String> selectValueByTypeCode(String dicTypeCode) {
		return tblDicValueMapper.selectValueByTypeCode(dicTypeCode);
	}

	@Override
	public int insert(TblDicValue record) {
		return tblDicValueMapper.insert(record);
	}

	@Override
	public int deleteDicValueByIds(String[] ids) {
		return tblDicValueMapper.deleteDicValueByIds(ids);
	}

	@Override
	public TblDicValue selectByPrimaryKey(String id) {
		return tblDicValueMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(TblDicValue record) {
		return tblDicValueMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TblDicValue> selectDicValueByTypeCode(String dicTypeCode) {
		return tblDicValueMapper.selectDicValueByTypeCode(dicTypeCode);
	}

}
