package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.TblCustomer;
import com.bjpowernode.crm.workbench.mapper.TblCustomerMapper;
import com.bjpowernode.crm.workbench.service.TblCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzx
 * @create 2021-06-21 20:22
 */
@Service
public class TblCustomerServiceImpl implements TblCustomerService {
	@Autowired
	private TblCustomerMapper tblCustomerMapper;
	@Override
	public List<TblCustomer> selectCustomerByName(String name) {
		return tblCustomerMapper.selectCustomerByName(name);
	}
}
