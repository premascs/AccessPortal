package com.scs.accessportal.dao;

import java.io.Serializable;
/*import java.io.Console;*/
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.scs.accessportal.model.AccessTypeModel;
@Repository
@Transactional
public class DataTypeDaoImpl implements DataTypeDao {
	 @Autowired
	 SessionFactory sessionFactory;
	 
	  /*private Session getSession() {
	    return sessionFactory.getCurrentSession();
	  }*/
	  
	 @Override
	 public List<AccessTypeModel> getList() {
	  Session session = sessionFactory.openSession();
	  System.out.println("Session established");
	  @SuppressWarnings("unchecked")
	  List<AccessTypeModel> accessTypeList = session.createQuery("from AccessTypeModel").list();
	  System.out.println(accessTypeList);
	  session.close();
	  return accessTypeList;
	 }
	 @Override
		public int save(AccessTypeModel type) {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(type);
			tx.commit();
			Serializable id = session.getIdentifier(type);
			session.close();
			return (Integer) id;
		}
	 @Override
		public String getAccessTypeByApprover(int id){
			Session session = sessionFactory.openSession();
			
			SQLQuery typeQuery = (SQLQuery) session
			.createSQLQuery(
					"select type from access_type t, approver a where t.approver_id = a.id and a.id= :id")
			.setParameter("id", id);		
		/*	session.createQuery("type from AccessTypeModel t, ApproverModel a where a.id= t.approverId and a.id= :id").setParameter("id", id).toString();*/
			@SuppressWarnings("unchecked")
			List<Object> type = typeQuery.list();
			String ty= type.get(0).toString();
			session.close();
			return ty;
		}

		@Override
		public void setAccessTypeByApprover(int id, String type){
			Session session = sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			SQLQuery typeQuery = (SQLQuery) session
			.createSQLQuery("UPDATE access_type SET `type` =:type where approver_id =:id").setParameter("id", id).setParameter("type", type); 
			typeQuery.executeUpdate();
			session.close();
			
		}
		
		@Override
		public void setApproverIdByAccessType(int id, String type){
			Session session = sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			SQLQuery typeQuery = (SQLQuery) session
			.createSQLQuery("UPDATE access_type SET `approver_id` =:id where type =:type").setParameter("id", id).setParameter("type", type); 
			typeQuery.executeUpdate();
			session.close();
			
		}
		@Override
		public List<AccessTypeModel> findRowByAccessType(String type) {
			Session session = sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<AccessTypeModel> accessList = session.createQuery(" from AccessTypeModel where type=:type").setParameter("type", type).list();
			session.close();
			return accessList;
		}
		
		@Override
		public void updateApproverIdToNull(int id) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.openSession();
			
			SQLQuery typeQuery = (SQLQuery) session
			.createSQLQuery("UPDATE access_type SET `approver_id`=null where id =:id").setParameter("id", id); 
			typeQuery.executeUpdate();
			session.close();
		}
		@Override
		public List<AccessTypeModel> getAccessTypeByAppId(int id) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<AccessTypeModel> accessList = session.createQuery(" from AccessTypeModel where approver_id =:id").setParameter("id", id).list();
			session.close();
			return accessList;
			
		/*	
			SQLQuery typeQuery = (SQLQuery) session
			.createSQLQuery(
					"select * from access_type where approver_id =:id")
			.setParameter("id", id);
			
			List<AccessTypeModel> type = typeQuery.list();
			System.out.println(typeQuery.toString());
			
			AccessTypeModel tModel= type.get(0);
			
			session.close();
			return tModel ;*/
		}
		
		
	
}
