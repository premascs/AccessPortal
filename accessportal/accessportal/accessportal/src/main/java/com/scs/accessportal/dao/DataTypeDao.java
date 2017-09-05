package com.scs.accessportal.dao;

import java.util.List;

import com.scs.accessportal.model.AccessTypeModel;

public interface DataTypeDao {

	
	 public List<AccessTypeModel> getList();
	 public int save(AccessTypeModel access);
	public String getAccessTypeByApprover(int id);
	public List<AccessTypeModel> getAccessTypeByAppId(int id);
	public void setAccessTypeByApprover(int id, String type);
	//AccessTypeModel updateAccessType(int id, int approverId, String atype);
	public void setApproverIdByAccessType(int id, String type);
	public void updateApproverIdToNull( int id);
	public List<AccessTypeModel> findRowByAccessType(String type);
}
