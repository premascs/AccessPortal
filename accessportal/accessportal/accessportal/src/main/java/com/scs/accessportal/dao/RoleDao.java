package com.scs.accessportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scs.accessportal.model.UserRoleModel;

@Repository("roleRepository")
public interface RoleDao extends JpaRepository<UserRoleModel, Integer>{
	UserRoleModel findByRole(String role);

}
