package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.TblDicType;

import java.util.List;

/**
 * @author lzx
 * @create 2021/6/5 20:50
 */
public interface TbDicTypeService {
	/**
	 * 查询全部
	 */
	List<TblDicType> selectByExample();

	/**
	 * 增加一条记录
	 */
	int insert(TblDicType record);

	/**
	 * 按主键查找
	 * @param code dicType表的主键
	 */
	TblDicType selectByPrimaryKey(String code);

	/**
	 * 删除所选记录
	 */
	int deleteByCodeArrays(String [] code);

	/**
	 *
	 * @param recod
	 * @return
	 */
	int updateByPrimaryKey(TblDicType recod);
}
