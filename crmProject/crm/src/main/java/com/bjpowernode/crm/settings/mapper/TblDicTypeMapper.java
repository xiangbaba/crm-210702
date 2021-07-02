package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.TblDicType;
import com.bjpowernode.crm.settings.domain.TblDicTypeExample;
;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblDicTypeMapper {
    long countByExample(TblDicTypeExample example);

    int deleteByExample(TblDicTypeExample example);

    int deleteByPrimaryKey(String code);

    int insert(TblDicType record);

    int insertSelective(TblDicType record);

    List<TblDicType> selectByExample(TblDicTypeExample example);

    TblDicType selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") TblDicType record, @Param("example") TblDicTypeExample example);

    int updateByExample(@Param("record") TblDicType record, @Param("example") TblDicTypeExample example);

    int updateByPrimaryKeySelective(TblDicType record);

    int updateByPrimaryKey(TblDicType recod);

    int deleteByCodeArrays(String [] code);
}