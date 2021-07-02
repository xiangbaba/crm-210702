package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.domain.TblDicTypeExample;
import com.bjpowernode.crm.settings.mapper.TblDicTypeMapper;
import com.bjpowernode.crm.settings.service.TbDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzx
 * @create 2021/6/5 20:50
 */
@Service
public class TbDicTypeServiceImpl implements TbDicTypeService {
	@Autowired
	private TblDicTypeMapper tblDicTypeMapper;

	@Override
	public List<TblDicType> selectByExample() {
		return tblDicTypeMapper.selectByExample(new TblDicTypeExample());
	}

	@Override
	public int insert(TblDicType record) {
		return tblDicTypeMapper.insert(record);
	}

	@Override
	public TblDicType selectByPrimaryKey(String code) {
		return tblDicTypeMapper.selectByPrimaryKey(code);
	}

	@Override
	public int deleteByCodeArrays(String[] code) {
		return tblDicTypeMapper.deleteByCodeArrays(code);
	}

	@Override
	public int updateByPrimaryKey(TblDicType recod) {
		return tblDicTypeMapper.updateByPrimaryKey(recod);
	}

}
