package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.TblTran;

import java.util.List;
import java.util.Map;

public interface TblTranMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblTran record);

    int insertSelective(TblTran record);

    TblTran selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblTran record);

    int updateByPrimaryKey(TblTran record);

    TblTran selectByIdForDetail(String id);

    List<TblTran> selectTranByLimitAndCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    List<FunnelVO> selectCountOfChart();
}