package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblTranHistory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TblTranHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblTranHistory record);

    int insertSelective(TblTranHistory record);

    TblTranHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblTranHistory record);

    int updateByPrimaryKey(TblTranHistory record);

    List<TblTranHistory> selectByTranId(String tranId);
}