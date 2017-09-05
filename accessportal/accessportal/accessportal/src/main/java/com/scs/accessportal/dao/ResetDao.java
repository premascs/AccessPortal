package com.scs.accessportal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.ApproverModel;

@Repository("resetRepository")
public interface ResetDao extends JpaRepository<ApproverModel, Long> {
	 
	 Optional<ApproverModel> findByEmail(String email);
	 Optional<ApproverModel> findByResetToken(String resetToken);
}