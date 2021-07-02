package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.TblDicValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblDicValueMapper {
    long countByExample(TblDicValueExample example);

    int deleteByExample(TblDicValueExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblDicValue record);

    int insertSelective(TblDicValue record);

    List<TblDicValue> selectByExample(TblDicValueExample example);

    TblDicValue selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblDicValue record, @Param("example") TblDicValueExample example);

    int updateByExample(@Param("record") TblDicValue record, @Param("example") TblDicValueExample example);

    int updateByPrimaryKeySelective(TblDicValue record);

    int updateByPrimaryKey(TblDicValue record);

    List<String> selectValueByTypeCode(String dicTypeCode);

    int deleteDicValueByIds(String [] ids);

    List<TblDicValue> selectDicValueByTypeCode(String dicTypeCode);
}