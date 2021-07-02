package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblClue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblClueMapper {
    int deleteByPrimaryKey(String id);

    int insert(TblClue record);

    int insertSelective(TblClue record);

    TblClue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TblClue record);

    int updateByPrimaryKey(TblClue record);

    List<TblClue> selectByConditionAndLimit(Map<String ,Object> map);

    int selectByConditionCount(Map<String ,Object> map);

    TblClue selectClueForDetailByClueId(String id);

    int deleteRelationByActivityIdAndClueId(
            @Param("activityId") String activityId,
            @Param("clueId") String clueId);

	int deleteClueByIds(String[] id);

}