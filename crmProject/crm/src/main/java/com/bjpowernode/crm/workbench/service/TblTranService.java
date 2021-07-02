package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.TblTran;

import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021-06-21 18:44
 */
public interface TblTranService {

	TblTran selectByIdForDetail(String id);

	void saveCreateTran(Map<String, Object> map);

	Map<String, Object> selectTranByLimitAndCondition(Map<String, Object> map);

	List<FunnelVO> queryCountOfTranGroupByStage();

}
