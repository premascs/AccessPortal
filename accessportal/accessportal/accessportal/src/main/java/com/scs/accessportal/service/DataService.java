package com.scs.accessportal.service;
  
import java.util.List;

/*import javax.persistence.Access;*/

import com.scs.accessportal.model.AccessModel;  
  
public interface DataService {  
public int insertRow(AccessModel access); 
/*
 public void deleteRow(AccessModel access);*/ 
 
 //public String deleteRow(String userId, String accessType);
 
public List<AccessModel> getList();  
 
public AccessModel getRowById(int id);  

public String getApproverEmail(int id);
/* 
public int updateRow(AccessModel access);  
 */

List<AccessModel> getListById(int appId);

public int save(AccessModel aModel);
public List<AccessModel> getAccessListById(int id);
/*public void setAccessTypeByApprover(int id, String type);*/
public void deleteReqById(int id);
  
}  