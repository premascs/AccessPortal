package com.scs.accessportal.service;
import java.util.List;

import com.scs.accessportal.model.AccessTypeModel; 
public interface AccessTypeService {
	public List<AccessTypeModel> getList(); 
	

	public void setApproverIdByAccessType(int id, String type);
	public void setAccessTypeByApprover(int id, String type);
	public List<AccessTypeModel> getAccessTypeByAppId(int approverId);
	public List<AccessTypeModel> findRowByAccessType(String type);
	public void updateApproverIdToNull( int id);
}
