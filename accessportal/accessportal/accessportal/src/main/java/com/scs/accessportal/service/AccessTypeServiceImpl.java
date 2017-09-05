package com.scs.accessportal.service;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scs.accessportal.dao.DataTypeDao;
import com.scs.accessportal.model.AccessTypeModel;
@Service
public class AccessTypeServiceImpl implements AccessTypeService {
	@Autowired
	 DataTypeDao dataTypeDao;
	@Override
	public List<AccessTypeModel> getList() {
		// TODO Auto-generated method stub
		 return dataTypeDao.getList();
	}
	
	@Override
	public void setAccessTypeByApprover(int id, String type) {
		// TODO Auto-generated method stub
		dataTypeDao.setAccessTypeByApprover(id, type);
	}
	@Override
	public List<AccessTypeModel> getAccessTypeByAppId(int approverId) {
		return dataTypeDao.getAccessTypeByAppId(approverId);
	}

	@Override
	public void setApproverIdByAccessType(int id, String type) {
		// TODO Auto-generated method stub
		dataTypeDao.setApproverIdByAccessType(id, type);
	}

	@Override
	public void updateApproverIdToNull( int id) {
		// TODO Auto-generated method stub
		dataTypeDao.updateApproverIdToNull( id);
	}

	@Override
	public List<AccessTypeModel> findRowByAccessType(String type) {
		// TODO Auto-generated method stub
		return dataTypeDao.findRowByAccessType(type);
	}
	
}
