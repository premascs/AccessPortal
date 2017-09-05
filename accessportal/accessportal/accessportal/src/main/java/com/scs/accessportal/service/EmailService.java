package com.scs.accessportal.service;

import com.scs.accessportal.model.AccessModel;

public interface EmailService {
	public AccessModel sendEmail(String to, AccessModel access);
}
