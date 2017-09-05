package com.scs.accessportal.dao;

import java.util.List;

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.AccessTypeModel;
/*import com.scs.accessportal.model.AccessTypeModel;*/
import com.scs.accessportal.model.ApproverModel;

public interface DataDao {
 public int insertRow(AccessModel access);
 
 
/* public void deleteRow(AccessModel access);*/
 public List<AccessModel> getList();
 public AccessModel getRowById(int id);
/*
 public List<AccessModel> getList();

 public AccessModel getRowById(int id);

 public int updateRow(AccessModel employee);

 public int deleteRow(String userId, String accessType);

 
*/


public String getApproverEmail(int id);


public List<AccessModel> getListById(int appId);


public int save(AccessModel aModel);


public List<AccessModel> getAccessListById(int id);


public void deleteReqById(int id);


public ApproverModel getApproverById(int id);


public void deleteApproverById(int id);


public List<ApproverModel> getApproverList();


List<ApproverModel> getApproverListByEmail(String email);








}