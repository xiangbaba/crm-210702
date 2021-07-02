package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblActivity;
import com.bjpowernode.crm.workbench.domain.TblActivityExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TblActivityMapper {
    long countByExample(TblActivityExample example);

    int deleteByExample(TblActivityExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblActivity record);

    int insertSelective(TblActivity record);

    List<TblActivity> selectByExample(TblActivityExample example);

    TblActivity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblActivity record, @Param("example") TblActivityExample example);

    int updateByExample(@Param("record") TblActivity record, @Param("example") TblActivityExample example);

    int updateByPrimaryKeySelective(TblActivity record);

    int updateByPrimaryKey(TblActivity record);

    List<TblActivity> selectActivityForPageByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);

    int deleteActivityByIds(String [] ids);

    TblActivity selectActivityByIdForParticulars(String id);

    List<TblActivity> selectAllActivityForUpload();

    List<TblActivity> selectByPrimaryKeys(String[] id);

	int insertActivities(List<TblActivity> activities);

    List<TblActivity> selectActivityForDetailByClueId(String id);

    List<TblActivity> selectUncorrelatedActivityByClueIdAndName(
            @Param("activityName")String activityName,
            @Param("clueId") String clueId);
}