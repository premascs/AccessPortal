package com.scs.accessportal.service;

import java.util.List;

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.AccessTypeModel;
import com.scs.accessportal.model.ApproverModel;

public interface ApproverService {
	public ApproverModel findUserByEmail(String email);
	public void saveUser(ApproverModel user);
	public void saveAccessType(AccessTypeModel aType);
	public void saveAccessModel(AccessModel aModel);
	public ApproverModel getApproverById(int id);
	public String getAccessTypeByApprover(int id);
	public List<AccessTypeModel> getAccessTypeByAppId(int id);
	public void deleteApproverById(int id);
	public List<ApproverModel> getApproverList();
	public void saveAdmin(ApproverModel user);
	public List<ApproverModel> getApproverListByEmail(String email);

}
