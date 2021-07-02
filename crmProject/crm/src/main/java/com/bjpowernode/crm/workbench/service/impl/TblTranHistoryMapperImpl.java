package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TblTranHistory;
import com.bjpowernode.crm.workbench.mapper.TblTranHistoryMapper;
import com.bjpowernode.crm.workbench.service.TblTranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-21 21:32
 */
@Service
public class TblTranHistoryMapperImpl implements TblTranHistoryService {
	@Autowired
	private TblTranHistoryMapper tblTranHistoryMapper;

	@Override
	public List<TblTranHistory> selectByTranId(String tranId) {
		return tblTranHistoryMapper.selectByTranId(tranId);
	}
}
