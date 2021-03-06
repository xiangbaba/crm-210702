package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.TblClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lzx
 * @create 2021-06-18 10:15
 */
@Service
public class TblClueServiceImpl implements TblClueService {
	@Autowired
	private TblClueMapper tblClueMapper;
	@Autowired
	private TblCustomerMapper tblCustomerMapper;
	@Autowired
	private TblContactsMapper tblContactsMapper;
	@Autowired
	private TblClueRemarkMapper tblClueRemarkMapper;
	@Autowired
	private TblCustomerRemarkMapper tblCustomerRemarkMapper;
	@Autowired
	private TblContactsRemarkMapper tblContactsRemarkMapper;
	@Autowired
	private TblClueActivityRelationMapper tblClueActivityRelationMapper;
	@Autowired
	private TblContactsActivityRelationMapper tblContactsActivityRelationMapper;
	@Autowired
	private TblTranMapper tblTranMapper;
	@Autowired
	private TblTranRemarkMapper tblTranRemarkMapper;
	@Autowired
	private TblTranHistoryMapper tblTranHistoryMapper;
	@Override
	public Map<String, Object> queryClueForPageByCondition(Map<String, Object> map) {
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("clueList",tblClueMapper.selectByConditionAndLimit(map));
		retMap.put("totalRows", tblClueMapper.selectByConditionCount(map));
		return retMap;
	}

	@Override
	public TblClue selectClueForDetailByClueId(String id) {
		return tblClueMapper.selectClueForDetailByClueId(id);
	}

	@Override
	public int insert(TblClue record) {
		return tblClueMapper.insert(record);
	}

	@Override
	public int deleteRelationByActivityIdAndClueId(String activityId, String clueId) {
		return tblClueMapper.deleteRelationByActivityIdAndClueId(activityId,clueId);
	}

	@Override
	public int updateByPrimaryKeySelective(TblClue record) {
		return tblClueMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteClueByIds(String[] id) {
		return tblClueMapper.deleteClueByIds(id);
	}
	@Override
	public void saveConvertClue(Map<String, Object> map) {
		//???1??????????????????id???????????????id??????????????????????????????????????????????????????????????????
		String clueId = (String) map.get("clueId");
		User  user = (User) map.get("user");
		TblClue clue = tblClueMapper.selectByPrimaryKey(clueId);
		//???2???????????????????????????????????????
		TblCustomer customer = new TblCustomer();
		customer.setId(UUIDUtils.getUUID());
		customer.setOwner(clue.getOwner());
		customer.setName(clue.getCompany());
		customer.setWebsite(clue.getWebsite());
		customer.setPhone(clue.getPhone());
		customer.setCreateBy(user.getId());
		customer.setCreateTime(DateUtils.formatDateTime(new Date()));
		customer.setContactSummary(clue.getContactSummary());
		customer.setNextContactTime(clue.getNextContactTime());
		customer.setDescription(clue.getDescription());
		customer.setAddress(clue.getAddress());
		tblCustomerMapper.insert(customer);
		//???3????????????????????????????????????????????????????????????
		TblContacts contacts = new TblContacts();
		contacts.setId(UUIDUtils.getUUID());
		contacts.setOwner(clue.getOwner());
		contacts.setSource(clue.getSource());
		contacts.setCustomerId(customer.getId());
		contacts.setFullName(clue.getFullName());
		contacts.setAppellation(clue.getAppellation());
		contacts.setEmail(clue.getEmail());
		contacts.setMphone(clue.getMphone());
		contacts.setJob(clue.getJob());
		contacts.setCreateBy(user.getId());
		contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
		contacts.setDescription(clue.getDescription());
		contacts.setContactSummary(clue.getContactSummary());
		contacts.setNextContactTime(clue.getNextContactTime());
		contacts.setAddress(clue.getAddress());
		tblContactsMapper.insert(contacts);
		//???4??? ??????????????????????????????????????????????????????
		List<TblClueRemark> tblClueRemarks = tblClueRemarkMapper.selectClueRemarkByClueId(clueId);
		TblCustomerRemark customerRemark = null;
		TblContactsRemark contactsRemark = null;
		for (TblClueRemark tblClueRemark : tblClueRemarks) {
			customerRemark = new TblCustomerRemark();
			contactsRemark = new TblContactsRemark();
			//????????????????????????
			customerRemark.setId(UUIDUtils.getUUID());
			customerRemark.setCreateBy(tblClueRemark.getCreateBy());
			customerRemark.setCreateTime(tblClueRemark.getCreateTime());
			customerRemark.setCustomerId(customer.getId());
			customerRemark.setNoteContent(tblClueRemark.getNoteContent());
			customerRemark.setEditBy(tblClueRemark.getEditBy());
			customerRemark.setEditFlag(tblClueRemark.getEditFlag());
			customerRemark.setEditTime(tblClueRemark.getEditTime());
			tblCustomerRemarkMapper.insert(customerRemark);
			//????????????????????????
			contactsRemark.setContactsId(contacts.getId());
			contactsRemark.setCreateBy(tblClueRemark.getCreateBy());
			contactsRemark.setCreateTime(tblClueRemark.getCreateTime());
			contactsRemark.setId(UUIDUtils.getUUID());
			contactsRemark.setNoteContent(tblClueRemark.getNoteContent());
			contactsRemark.setEditBy(tblClueRemark.getEditBy());
			contactsRemark.setEditFlag(tblClueRemark.getEditFlag());
			contactsRemark.setEditTime(tblClueRemark.getEditTime());
			tblContactsRemarkMapper.insert(contactsRemark);
		}
		//???5???????????????????????????????????????????????????????????????????????????????????????
		List<TblClueActivityRelation> clueArs = tblClueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
		List<TblContactsActivityRelation> contactsArs = new ArrayList<>();
		if (clueArs!=null&&clueArs.size()>0){
			TblContactsActivityRelation contactsAr = null;
			for (TblClueActivityRelation clueAr : clueArs) {
				contactsAr = new TblContactsActivityRelation();
				contactsAr.setId(UUIDUtils.getUUID());
				contactsAr.setActivityId(clueAr.getActivityId());
				contactsAr.setContactsId(contacts.getId());
				contactsArs.add(contactsAr);
			}
				tblContactsActivityRelationMapper.insertContactsActivityRelationByList(contactsArs);
		}
		//???6???????????????????????????????????????????????????
		if ("true".equals((String)map.get("isCreateTran"))){
			TblTran tran = new TblTran();
			tran.setId(UUIDUtils.getUUID());
			tran.setOwner(user.getId());
			tran.setMoney((String)map.get("amountOfMoney"));
			tran.setName((String)map.get("tradeName"));
			tran.setStage((String)map.get("stage"));
			tran.setExpectedDate((String)map.get("expectedClosingDate"));
			tran.setCustomerId(customer.getId());
			tran.setSource(clue.getSource());
			tran.setActivityId((String)map.get("activityId"));
			tran.setContactsId(contacts.getId());
			tran.setCreateBy(user.getId());
			tran.setCreateTime(DateUtils.formatDateTime(new Date()));
			tran.setDescription(clue.getDescription());
			tran.setContactSummary(clue.getContactSummary());
			tran.setNextContactTime(clue.getNextContactTime());
			tblTranMapper.insert(tran);
			//???????????????????????????????????????????????????????????????????????????????????????????????????
			//??????clueRemarkList?????????TranRemark??????????????????????????????????????????????????????????????????
			if(tblClueRemarks!=null&&tblClueRemarks.size()>0){
				TblTranRemark tr=null;
				List<TblTranRemark> trList=new ArrayList<>();
				for(TblClueRemark cr:tblClueRemarks){
					tr=new TblTranRemark();
					tr.setCreateBy(cr.getCreateBy());
					tr.setCreateTime(cr.getCreateTime());
					tr.setEditby(cr.getEditBy());
					tr.setEditFlag(cr.getEditFlag());
					tr.setEditTime(cr.getEditTime());
					tr.setId(UUIDUtils.getUUID());
					tr.setNoteContent(cr.getNoteContent());
					tr.setTranId(tran.getId());
					trList.add(tr);
				}
				tblTranRemarkMapper.insertTranRemarkByList(trList);
			}
			//???7?????????????????????????????????????????????????????????????????????
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

		//??????clueId?????????????????????????????????
		tblClueRemarkMapper.deleteClueRemarkByClueId(clueId);
		//??????clueId?????????????????????????????????????????????
		tblClueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
		//??????id???????????????
		tblClueMapper.deleteByPrimaryKey(clueId);
	}

}

