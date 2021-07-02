package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.TblDicValue;

import java.util.List;

/**
 * @author lzx
 * @create 2021/6/5 21:16
 */
public interface TblDicValueService {
	List<TblDicValue> selectByExample(int page,int size);

	List<String> selectValueByTypeCode(String dicTypeCode);

	int insert(TblDicValue record);

	int deleteDicValueByIds(String [] ids);

	TblDicValue selectByPrimaryKey(String id);

	int updateByPrimaryKey(TblDicValue record);

	List<TblDicValue> selectDicValueByTypeCode(String dicTypeCode);
}
