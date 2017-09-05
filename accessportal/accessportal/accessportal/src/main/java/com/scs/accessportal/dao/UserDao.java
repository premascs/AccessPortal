package com.scs.accessportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scs.accessportal.model.ApproverModel;


@Repository("userRepository")
public interface UserDao extends JpaRepository<ApproverModel, Long> {
	 ApproverModel findByEmail(String email);

}