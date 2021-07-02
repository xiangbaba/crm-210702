package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblTranHistory;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-21 21:32
 */
public interface TblTranHistoryService {

	List<TblTranHistory> selectByTranId(String tranId);
}
