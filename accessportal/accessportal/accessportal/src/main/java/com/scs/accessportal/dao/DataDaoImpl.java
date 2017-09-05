
package com.scs.accessportal.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
/*import org.hibernate.Query;*/
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/*import org.springframework.web.bind.annotation.ModelAttribute;*/

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.AccessTypeModel;
import com.scs.accessportal.model.ApproverModel;
import com.scs.accessportal.model.RoleModel;
import com.scs.accessportal.model.UserRoleModel;

/*import com.scs.accessportal.model.AccessTypeModel;*/
@Repository
@Transactional
public class DataDaoImpl implements DataDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override

	public int insertRow(AccessModel access) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(access);
		tx.commit();
		Serializable id = session.getIdentifier(access);
		session.close();
		return (Integer) id;
	}

	/*
	 * @Override public void deleteRow(AccessModel access ) { Session session =
	 * sessionFactory.openSession(); Transaction tx =
	 * session.beginTransaction(); Query query = session.
	 * createQuery("delete from AccessModel where access_type = :accessType and user_id= :userId"
	 * ); query.setParameter("accessType",access.getAccessType());
	 * query.setParameter("userId",access.getUserId()); query.executeUpdate();
	 * session.delete(access); tx.commit(); session.close(); }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public String getApproverEmail(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		SQLQuery emailQuery = (SQLQuery) session
				.createSQLQuery(
						"select C.approver_email from access_request A inner join access_type B on A.access_type = B.type inner join approver C on B.approver_id = C.id where A.id = :id")
				.setParameter("id", id);
		List<String> email = (List<String>) emailQuery.list();
		tx.commit();
		session.close();
		return email.get(0);
	}

	@Override
	public List<AccessModel> getList() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<AccessModel> accessList = session.createQuery("from AccessModel").list();
		session.close();
		return accessList;
	}
	

	@Override
	public List<AccessModel> getAccessListById(int id) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<AccessModel> accessList = session.createQuery("from AccessModel where id= :id").setParameter("id", id).list();
		session.close();
		return accessList;
	}
	
	

	@Override
	public List<AccessModel> getListById(int appId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		SQLQuery accessByAppQuery = (SQLQuery) session
				.createSQLQuery(
						"select * from access_request where access_type=(select type from access_type where approver_id = :appId)")
				.setParameter("appId", appId);
		//accessByAppQuery.setResultTransformer(Transformers.aliasToBean(AccessModel.class));
		@SuppressWarnings("unchecked")
		//List<AccessModel> accessList  =  accessByAppQuery.list();
			List<Object[]> accessList  =  accessByAppQuery.list();
		List<AccessModel> accList = new ArrayList<>();
		for(Object[] a: accessList){
			AccessModel acc = new AccessModel();
			acc.setId((int) a[0]);
			acc.setAccessType((String) a[1]);
			acc.setEffEndDate((String) a[2]);
			acc.setEffStartDate((String) a[3]);
			acc.setOrgApprover((String) a[4]);
			acc.setTemporary((String) a[5]);
			acc.setUserId((String) a[6]);
			acc.setAccessReqAction((String) a[7]);
			acc.setComments((String) a[8]);
			acc.setStatus((String) a[9]);
			acc.setDatestamp((String) a[10]);
			acc.setUserEmail((String) a[11]);

		accList.add(acc);
		}
		tx.commit();
		session.close();
		return accList;
	}
	

	@Override
	public AccessModel getRowById(int id) {
		Session session = sessionFactory.openSession();
		AccessModel access = (AccessModel) session.load(AccessModel.class, id);
		session.close();
		return access;
	}
	
	@Override
	public ApproverModel getApproverById(int id) {
	/*	Session session = sessionFactory.openSession();
		ApproverModel approver = (ApproverModel) session.load(ApproverModel.class, id);
		session.close();*/
		 return sessionFactory.getCurrentSession().get(ApproverModel.class, id); 
	}
	
	 @Override
		public int save(AccessModel aModel) {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(aModel);
			tx.commit();
			Serializable id = session.getIdentifier(aModel);
			session.close();
			return (Integer) id;
		}
	 
	 
	 @Override
	 public List<ApproverModel> getApproverList() {
	  Session session = sessionFactory.openSession();
	  System.out.println("Session established");
	  @SuppressWarnings("unchecked")
	  List<ApproverModel> appList = session.createQuery("from ApproverModel where active= 2").list();
	  //System.out.println(appList);
	  session.close();
	  return appList;
	 }
	 @Override
	 public List<ApproverModel> getApproverListByEmail(String email) {
	  Session session = sessionFactory.openSession();
	  System.out.println("Session established");
	  @SuppressWarnings("unchecked")
	  List<ApproverModel> appList = session.createQuery("from ApproverModel where active= 2 and email =:email").setParameter("email", email).list();
	  //System.out.println(appList);
	  session.close();
	  return appList;
	 }
	/*
	 * @Override public int deleteRow(String id,String accessType ) { Session
	 * session = sessionFactory.openSession(); Transaction tx =
	 * session.beginTransaction(); AccessModel access = (AccessModel)
	 * session.load(AccessModel.class, id, accessType); //retrieve access id
	 * from the table matching other fields //set id as access.setId(id) //
	 * Query query = session.
	 * createQuery("delete from access_request where access_type = :accessType and user_id= :userId"
	 * ); //query.setParameter("accessType",access.getAccessType());
	 * //query.setParameter("userId",access.getUserId()); //int result =
	 * query.executeUpdate(); //session.delete(access); //tx.commit();
	 * Serializable ids = session.getIdentifier(access); session.close(); return
	 * (Integer) ids; }
	 */

	@Override
	public void deleteReqById(int id) {
		// TODO Auto-generated method stub
		 AccessModel access = (AccessModel) sessionFactory.getCurrentSession().load(
				 AccessModel.class, id);
	        if (null != access) {
	            this.sessionFactory.getCurrentSession().delete(access);
	        }
		
	}

	@Override
	public void deleteApproverById(int id) {
		// TODO Auto-generated method stub
		/*RoleModel role = (RoleModel) sessionFactory.getCurrentSession().load(
				RoleModel.class, id);
		 if (null != role) {
	            this.sessionFactory.getCurrentSession().delete(role);
	        }*/
		 Session session = sessionFactory.openSession();
			SQLQuery typeQuery = (SQLQuery) session
			.createSQLQuery("delete from user_role where user_id=:id").setParameter("id", id); 
			typeQuery.executeUpdate();
			session.close();
			
		 
		 
		 ApproverModel app = (ApproverModel) sessionFactory.getCurrentSession().load(
				 ApproverModel.class, id);
	        if (null != app) {
	            this.sessionFactory.getCurrentSession().delete(app);
	        }
	}

}