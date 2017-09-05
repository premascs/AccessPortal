package com.scs.accessportal.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scs.accessportal.dao.DataDao;
import com.scs.accessportal.dao.DataTypeDao;
import com.scs.accessportal.dao.RoleDao;
import com.scs.accessportal.dao.UserDao;
import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.AccessTypeModel;
import com.scs.accessportal.model.ApproverModel;
import com.scs.accessportal.model.UserRoleModel;

@Service("approverService")
public class ApproverServiceImpl implements ApproverService{

	@Autowired
	private UserDao userDao;
	@Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
	MailClientService emailSender;
    @Autowired
	private DataDao dataDao;

	
	@Autowired
	private DataTypeDao typeDao;
    
	@Override
	public ApproverModel findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.findByEmail(email);
	}

	
	
	
	
	@Override
	public void saveUser(ApproverModel user) {
		// TODO Auto-generated method stub
		String temPassword =  RandomStringUtils.randomAlphanumeric(7).toUpperCase();
		String pswd= bCryptPasswordEncoder.encode(temPassword);
		String appEmail= user.getEmail();
		user.setPassword(pswd);
		user.setActive(2);
        UserRoleModel userRole = roleDao.findByRole("APPROVER");
        user.setRoles(new HashSet<UserRoleModel>(Arrays.asList(userRole)));
		userDao.save(user);
		emailSender.sendAutoPassword( appEmail, temPassword, user);
		
	}
	
	@Override
	public void saveAdmin(ApproverModel user) {
		// TODO Auto-generated method stub
		//String temPassword =  RandomStringUtils.randomAlphanumeric(7).toUpperCase();
		String temPassword ="ADMIN1";
		String pswd= bCryptPasswordEncoder.encode(temPassword);
		String appEmail= user.getEmail();
		user.setPassword(pswd);
		user.setActive(1);
        UserRoleModel userRole = roleDao.findByRole("ADMIN");
        user.setRoles(new HashSet<UserRoleModel>(Arrays.asList(userRole)));
		userDao.save(user);
		emailSender.sendAdminPassword( appEmail, temPassword, user);
		
	}
	
	
	
	
	@Override
	public void saveAccessType(AccessTypeModel aType) {
		typeDao.save(aType);
	}
	
	@Override
	public void saveAccessModel(AccessModel aModel) {	
		dataDao.save(aModel);

	}

	@Override
	public ApproverModel getApproverById(int id) {
		return dataDao.getApproverById(id);
	}
	
	@Override
	public void deleteApproverById(int id) {
		 dataDao.deleteApproverById(id);
	}

	@Override
	public String getAccessTypeByApprover(int id) {
		return typeDao.getAccessTypeByApprover(id);
	}





	@Override
	public List<AccessTypeModel> getAccessTypeByAppId(int id) {
		// TODO Auto-generated method stub
		return typeDao.getAccessTypeByAppId(id);
	}





	@Override
	public List<ApproverModel> getApproverList() {
		// TODO Auto-generated method stub
		return dataDao.getApproverList();
	}
	@Override
	public List<ApproverModel> getApproverListByEmail(String email) {
		// TODO Auto-generated method stub
		return dataDao.getApproverListByEmail(email);
	}

}
