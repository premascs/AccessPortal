package com.scs.accessportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import com.scs.accessportal.AccessportalApplication;*/
import com.scs.accessportal.dao.DataDao;
import com.scs.accessportal.model.AccessModel;

@Service
public class DataServiceImpl implements DataService {
 
 @Autowired
 DataDao dataDao;

 @Override
 public int insertRow(AccessModel access) {
  return dataDao.insertRow(access);
 }
 /*
 @Override
 public void deleteRow(AccessModel access){
	 dataDao.deleteRow(access);
 }*/

 @Override
 public List<AccessModel> getList() {
  return dataDao.getList();
 }
 
 @Override
 public List<AccessModel> getListById(int appId){
 return dataDao.getListById(appId);
 }
 @Override
 public AccessModel getRowById(int id) {
  return dataDao.getRowById(id);
 }
 
 @Override
 public int save(AccessModel aModel){
	 return dataDao.save(aModel);
 }
 
 @Override
 public String getApproverEmail(int id) {
 	// TODO Auto-generated method stub
 	return dataDao.getApproverEmail(id);
 }
/*
 @Override
 public int updateRow(AccessModel employee) {
  return dataDao.updateRow(employee);
 }

 @Override
 public int deleteRow(int id) {
  return dataDao.deleteRow(id);
 }
 */

@Override
public List<AccessModel> getAccessListById(int id) {
	// TODO Auto-generated method stub
	return dataDao.getAccessListById(id);
}

@Override
public void deleteReqById(int id) {
	// TODO Auto-generated method stub
	 dataDao.deleteReqById(id);
}

/*@Override
public void setAccessTypeByApprover(int id, String type) {
	// TODO Auto-generated method stub
	dataDao.setAccessTypeByApprover(id, type);
}*/


}