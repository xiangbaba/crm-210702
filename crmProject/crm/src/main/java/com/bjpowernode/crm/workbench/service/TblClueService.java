package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblClue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021-06-18 10:15
 */
@Service
public interface TblClueService {

	Map<String, Object> queryClueForPageByCondition(Map<String, Object> map);

	TblClue selectClueForDetailByClueId(String id);

	int insert(TblClue record);

	int deleteRelationByActivityIdAndClueId(String activityId, String clueId);

	int updateByPrimaryKeySelective(TblClue record);

	int deleteClueByIds(String[] id);


	void saveConvertClue(Map<String, Object> map);
}
