package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.TblCustomer;
import com.bjpowernode.crm.workbench.domain.TblTran;
import com.bjpowernode.crm.workbench.domain.TblTranHistory;
import com.bjpowernode.crm.workbench.mapper.TblCustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TblTranHistoryMapper;
import com.bjpowernode.crm.workbench.mapper.TblTranMapper;
import com.bjpowernode.crm.workbench.service.TblTranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021-06-21 18:44
 */
@Service
public class TblTranServiceImpl implements TblTranService {
	@Autowired
	private TblTranMapper tblTranMapper;
	@Autowired
	private TblCustomerMapper tblCustomerMapper;
	@Autowired
	private TblTranHistoryMapper tblTranHistoryMapper;
	@Override
	public TblTran selectByIdForDetail(String id) {
		return tblTranMapper.selectByIdForDetail(id);
	}

	@Override
	public void saveCreateTran(Map<String, Object> map) {
		//根据客户id是否为空串来判断是否生成新的顾客
		User user = (User) map.get("user");
		TblTran tran = (TblTran) map.get("tran");
		TblCustomer customer = null;
		if (tran.getCustomerId()==null||"".equals(tran.getCustomerId().trim())){
			customer = new TblCustomer();
			customer.setId(UUIDUtils.getUUID());
			customer.setDescription(tran.getDescription());
			customer.setNextContactTime(tran.getNextContactTime());
			customer.setContactSummary(tran.getContactSummary());
			customer.setCreateTime(DateUtils.formatDateTime(new Date()));
			customer.setCreateBy(user.getId());
			customer.setName((String) map.get("customerName"));
			customer.setOwner(user.getId());
			customer.setEditBy(user.getId());
			customer.setEditTime(DateUtils.formatDateTime(new Date()));
			//将客户插入到数据库
			tblCustomerMapper.insert(customer);
			//把customer的id设置到tran对象中
			tran.setCustomerId(customer.getId());
			//保存交易记录

			tblTranMapper.insert(tran);
			//（7）如果创建了交易，则创建一条该交易下的交易历史
			TblTranHistory tranHistory = new TblTranHistory();
			tranHistory.setCreateBy(user.getId());
			tranHistory.setCreateTime(DateUtils.formatDateTime(new Date()));
			tranHistory.setExpectedDate(tran.getExpectedDate());
			tranHistory.setId(UUIDUtils.getUUID());
			tranHistory.setMoney(tran.getMoney());
			tranHistory.setStage(tran.getStage());
			tranHistory.setTranId(tran.getId());
			tblTranHistoryMapper.insert(tranHistory);
		}
	}

	@Override
	public Map<String, Object> selectTranByLimitAndCondition(Map<String, Object> map) {
		List<TblTran> tblTrans = tblTranMapper.selectTranByLimitAndCondition(map);
		int total = tblTranMapper.getTotalByCondition(map);
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("tblTrans",tblTrans);
		retMap.put("totalRows", total);
		return retMap;
	}

	@Override
	public List<FunnelVO> queryCountOfTranGroupByStage() {
		return tblTranMapper.selectCountOfChart();
	}


}
