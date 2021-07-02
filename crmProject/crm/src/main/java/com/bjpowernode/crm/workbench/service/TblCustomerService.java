package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TblCustomer;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-21 20:21
 */
public interface TblCustomerService {
	List<TblCustomer> selectCustomerByName(String name);
}
