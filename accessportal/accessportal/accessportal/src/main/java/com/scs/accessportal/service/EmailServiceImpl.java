package com.scs.accessportal.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.scs.accessportal.model.AccessModel;
@Component
@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender javaMailSender;

		public AccessModel sendEmail(String to, AccessModel access) {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(message, true);
			helper.setSubject("Access Request");
			helper.setTo(to);
			helper.setText("Hi, "
					+ "<html><body><br>A request for access has been waiting for your approval."
					+ " <br><br><i>Please click on the button below to approve the request.</i>"
					+ access.getId()
					+ "<br><br><a href='http://www.scsanalytics.com'><button style='background-color:white;color: #008CBA;border:2px solid #008CBA;padding:3px' type='button'>"
					+"Approve Request</button></a><p>Thanks,<br>SCS Analytics<br></p></body></html>", true); 
		} catch (MessagingException e) {
			e.printStackTrace();
		} 
		javaMailSender.send(message);
		return access;
	}
		

}
