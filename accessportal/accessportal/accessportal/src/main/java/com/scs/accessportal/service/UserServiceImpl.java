package com.scs.accessportal.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.ApproverModel;
import com.scs.accessportal.dao.ResetDao;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private ResetDao userRepository;

	
	@Override
	public Optional<ApproverModel> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	
	@Override
	public Optional<ApproverModel> findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

	@Override
	public void save(ApproverModel user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}
	
	
}
