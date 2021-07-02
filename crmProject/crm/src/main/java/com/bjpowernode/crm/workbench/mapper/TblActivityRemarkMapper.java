package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TblActivityRemark;
import com.bjpowernode.crm.workbench.domain.TblActivityRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TblActivityRemarkMapper {
    long countByExample(TblActivityRemarkExample example);

    int deleteByExample(TblActivityRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblActivityRemark record);

    int insertSelective(TblActivityRemark record);

    List<TblActivityRemark> selectByExample(TblActivityRemarkExample example);

    TblActivityRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblActivityRemark record, @Param("example") TblActivityRemarkExample example);

    int updateByExample(@Param("record") TblActivityRemark record, @Param("example") TblActivityRemarkExample example);

    int updateByPrimaryKeySelective(TblActivityRemark record);

    int updateByPrimaryKey(TblActivityRemark record);

    List<TblActivityRemark> selectRemarkByActivityId(String activityId);

    int updateNoteContentByActivityId(@Param("activityId") String activityId ,
                                      @Param("noteContent")String noteContent);
}