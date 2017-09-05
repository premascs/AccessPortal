package com.scs.accessportal.service;

import java.io.Console;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.AccessTypeModel;
import com.scs.accessportal.model.ApproverModel;

@Service
public class MailClientService {
	@Autowired
    public MailClientService(JavaMailSender mailSender, MailContentBuilderService mailContentBuilder) {
    }
	@Autowired
	ApproverService appService;
	
    final String username = "scsanalyticsportal@gmail.com";
	final String password = "portal123";
	final String host = "smtp.gmail.com";
	
	private Properties getProperties() {
		Properties props = System.getProperties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", host);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.port", "465");
		props.put("mail.smtps.starttls.enable", true);
		props.put("mail.smtps.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		return props;
	}
	
	

	public void sendUserEmail(String to, String appEmail,int appId, AccessModel access) {
		Session session = Session.getInstance(getProperties(), null);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			 message.setSubject("Access Request");	
			 
			 
			 int id = access.getId();
			 String user=access.getUserId();
			 String status = access.getStatus();
			 String userEmail = access.getUserEmail();
			 String domain =access.getDomain();
			 String action = access.getAccessReqAction();
			 String temp = access.getTemporary();
			 String orgApp = access.getOrgApprover();
			 String eStart = access.getEffStartDate();
			 String eEnd = access.getEffEndDate();
			 ApproverModel app= new ApproverModel();
			 
			 List<ApproverModel> at=appService.getApproverListByEmail(appEmail);

			 int aid;
			 if (at.isEmpty()){
					aid=3;
					
				}else{
				aid=at.get(0).getId();
				}
			 
			 List<AccessTypeModel> atype=appService.getAccessTypeByAppId(aid);
			 
			 String type;
			 
			 if (atype.isEmpty()){
					type="No Approver";
					
				}else{
				type=atype.get(0).getType();
				}
			 System.out.println(action);
			 System.out.println(temp);
			 System.out.println(temp);
			 MimeBodyPart mbp = new MimeBodyPart();
			 if(type.equals("WO Approver") && action.equals("Change")  && temp.equals("Temporary")){
				 mbp.setText( "<html><head></head><body>"
	            		 +"<p style='font-size: 15px;font-weight:bold;'>Hello,</p>"
	            		 +"<p style='font-size: 15px;font-weight:bold;'>Thank you for submitting access request.</p>"
	            		 +"<p style='font-size: 15px;'>"
	            		 +"Your request has been submitted and waiting for the approval. </p>"
	            		 
	            		 +"<p style='font-size: 15px;'>Request Details</p><hr></hr>"
	            		 +"<table><tbody><tr>"
						 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
						 +"<td style='font-size: 14px;color:#428bca;'>"
						 +id
						 + "</td></tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Access Type</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +type
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Action</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +action
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>User ID</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +user
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>User Email</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +userEmail
						 + "</td>"
						  +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Domain</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +domain
						 + "</td>"
						  +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Original Approver</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +orgApp
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Change Type</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +"Temporary"
						 + "</td>"
						 +"</tr><tr>"
 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Effective Start Date</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +eStart
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Effective End Date</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +eEnd
						 + "</td>"
						 
						 +"</tr><tr></tr>	"	
						 +"</tbody></table>"
						 +"<p>   </p><hr></hr>"
						 
	            		 +"<p style='font-size: 15px;'>Please find the status of your access request below.</p><hr></hr>"
	            		 +"<table><tbody><tr>"
						 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
						 +"<td style='font-size: 14px;color:#428bca;'>"
						 +id
						 + "</td></tr><tr>"
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Status</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +status
						 + "</td>"
						 +"</tr><tr>"
						 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Approver</td>"
						 +"<td style='font-size: 14px;color:#428bca;'>"
						 + appEmail
						 + "</td>"
						 +"</tr><tr></tr>	"	
						 +"</tbody></table>"
						 +"<p>   </p><hr></hr>"
	            		+"<p>   </p><p>   </p>"
	            		+"<p style='font-size: 15px;font-weight:bold;'>"
	            		+"Thanks"
	            		+",</p>"
	            		+"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
	            		+"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
	            		+"</body></html>", "UTF-8","html");
			 }
			 
			 
			 else if(type.equals("WO Approver") && action.equals("Change") && (temp.equals("") || temp.isEmpty() || temp.equals("null"))){
				 mbp.setText( "<html><head></head><body>"
	            		 +"<p style='font-size: 15px;font-weight:bold;'>Hello,</p>"
	            		 +"<p style='font-size: 15px;font-weight:bold;'>Thank you for submitting access request.</p>"
	            		 +"<p style='font-size: 15px;'>"
	            		 +"Your request has been submitted and waiting for the approval. </p>"
	            		 
	            		 +"<p style='font-size: 15px;'>Request Details</p><hr></hr>"
	            		 +"<table><tbody><tr>"
						 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
						 +"<td style='font-size: 14px;color:#428bca;'>"
						 +id
						 + "</td></tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Access Type</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +type
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Action</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +action
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>User ID</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +user
						 + "</td>"
						 +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>User Email</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +userEmail
						 + "</td>"
						  +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Domain</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +domain
						 + "</td>"
						  +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Original Approver</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +orgApp
						 + "</td>"
						  +"</tr><tr>"
						 
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Change Type</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +"Permanent"
						 + "</td>"
					
						 +"</tr><tr></tr>	"	
						 +"</tbody></table>"
						 +"<p>   </p><hr></hr>"
						 
	            		 +"<p style='font-size: 15px;'>Please find the status of your access request below.</p><hr></hr>"
	            		 +"<table><tbody><tr>"
						 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
						 +"<td style='font-size: 14px;color:#428bca;'>"
						 +id
						 + "</td></tr><tr>"
						 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Status</td>"
						 +"<td style='font-size: 14px;color:#428bca;' >"
						 +status
						 + "</td>"
						 +"</tr><tr>"
						 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Approver</td>"
						 +"<td style='font-size: 14px;color:#428bca;'>"
						 + appEmail
						 + "</td>"
						 +"</tr><tr></tr>	"	
						 +"</tbody></table>"
						 +"<p>   </p><hr></hr>"
	            		+"<p>   </p><p>   </p>"
	            		+"<p style='font-size: 15px;font-weight:bold;'>"
	            		+"Thanks"
	            		+",</p>"
	            		+"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
	            		+"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
	            		+"</body></html>", "UTF-8","html");
			 }else 
			 
			{
			
             mbp.setText( "<html><head></head><body>"
            		 +"<p style='font-size: 15px;font-weight:bold;'>Hello,</p>"
            		 +"<p style='font-size: 15px;font-weight:bold;'>Thank you for submitting access request.</p>"
            		 +"<p style='font-size: 15px;'>"
            		 +"Your request has been submitted and waiting for the approval. </p>"
            		 
            		 +"<p style='font-size: 15px;'>Request Details</p><hr></hr>"
            		 +"<table><tbody><tr>"
					 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
					 +"<td style='font-size: 14px;color:#428bca;'>"
					 +id
					 + "</td></tr><tr>"
					 
					 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Access Type</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
					 +type
					 + "</td>"
					 +"</tr><tr>"
					 
					 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Action</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
					 +action
					 + "</td>"
					 +"</tr><tr>"
					 
					 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>User ID</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
					 +user
					 + "</td>"
					 +"</tr><tr>"
					 
					 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>User Email</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
					 +userEmail
					 + "</td>"
					  +"</tr><tr>"
					 
					 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Domain</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
					 +domain
					 + "</td>"
				
					 +"</tr><tr></tr>	"	
					 +"</tbody></table>"
					 +"<p>   </p><hr></hr>"
					 
            		 +"<p style='font-size: 15px;'>Please find the status of your access request below.</p><hr></hr>"
            		 +"<table><tbody><tr>"
					 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
					 +"<td style='font-size: 14px;color:#428bca;'>"
					 +id
					 + "</td></tr><tr>"
					 + "<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Status</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
					 +status
					 + "</td>"
					 +"</tr><tr>"
					 +"<td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Approver</td>"
					 +"<td style='font-size: 14px;color:#428bca;'>"
					 + appEmail
					 + "</td>"
					 +"</tr><tr></tr>	"	
					 +"</tbody></table>"
					 +"<p>   </p><hr></hr>"
            		+"<p>   </p><p>   </p>"
            		+"<p style='font-size: 15px;font-weight:bold;'>"
            		+"Thanks"
            		+",</p>"
            		+"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
            		+"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
            		+"</body></html>", "UTF-8","html");
			 }
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(mbp);
             message.setContent(multipart);
			Transport transport = (Transport) session.getTransport("smtps");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();		

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
    

   

    public void sendEmail(String to, AccessModel access) {
    	
Session session = Session.getInstance(getProperties(), null);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			 message.setSubject("Access Request");			
			 MimeBodyPart mbp = new MimeBodyPart();
             mbp.setText( "<html><head></head><body>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>"
    				 +"Hi,"
    				 +"</p><p style='font-size: 15px;font-weight:bold;'>"
    				 +"A request for access has been waiting for your approval."
    				 +"</p><p>"
    				 +"<i>"
    				 +"Please click on the link below to process the request."
    				 +"</i></p><a href = 'http://http://access.scsanalytics.com'><i>"
    				 + "Access Request</i></a>" 	
    				 +"<p>   </p><p>   </p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>"
    				 +"Thanks"
    				 +",</p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
    				 +"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
    				 +"</body></html>", "UTF-8","html"
            		 );
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(mbp);
             message.setContent(multipart);
			Transport transport = (Transport) session.getTransport("smtps");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();		

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
    
    
    
    public void sendAutoPassword( String to, String pwd, ApproverModel approver){
    	
Session session = Session.getInstance(getProperties(), null);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			 message.setSubject("Approver Request Granted");
			 String appEmail = approver.getEmail();
			 MimeBodyPart mbp = new MimeBodyPart();
             mbp.setText( "<html><head></head><body>"
            		 +"<p style='font-size: 15px;font-weight:bold;'>Hi,</p>"
					 +"<p style='font-size: 15px;font-weight:bold;'>Your request for approver is completed.</p>"
            		 +"<p style='font-size: 15px;'>You are now authorized to approve access requests</p>"
            		 +"<p style='font-size: 14px;font-weight:bold;'>User Name : <i>"
            		 + appEmail
            		 +"</i></p>"
            		 +"<p style='font-size: 14px;font-weight:bold;'>Password : <i>"
            		 +pwd
            		 + "</i></p>"
           
            		  +"<p><i>"
    				 +"Please click <a href = 'http://access.scsanalytics.com'>here</a> to login to your dashboard."
    				 +"</i></p>"
    				 
            		 +"<p>   </p><p>   </p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>"
    				 +"Thanks"
    				 +",</p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
    				 +"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
    				 +"</body></html>", "UTF-8","html"	
            		 );
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(mbp);
             message.setContent(multipart);
			Transport transport = (Transport) session.getTransport("smtps");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();		

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
   
    public void sendAdminPassword( String to, String pwd, ApproverModel approver){
    	
    	Session session = Session.getInstance(getProperties(), null);
    			
    			try {
    				
    				Message message = new MimeMessage(session);
    				message.setFrom(new InternetAddress(username));
    				message.setRecipients(Message.RecipientType.TO,
    						InternetAddress.parse(to));
    				 message.setSubject("Admin Request Granted");
    				 String appEmail = approver.getEmail();
    				 MimeBodyPart mbp = new MimeBodyPart();
    	             mbp.setText( "<html><head></head><body>"
    	            		 +"<p style='font-size: 15px;font-weight:bold;'>Hi,</p>"
    						 +"<p style='font-size: 15px;font-weight:bold;'>Your request for admin is completed.</p>"
    	            		 +"<p style='font-size: 15px;'>You are now authorized to add approvers</p>"
    	            		 +"<p style='font-size: 14px;font-weight:bold;'>User Name : <i>"
    	            		 + appEmail
    	            		 +"</i></p>"
    	            		 +"<p style='font-size: 14px;font-weight:bold;'>Password : <i>"
    	            		 +pwd
    	            		 + "</i></p>"
    	           
    	            		  +"<p><i>"
    	    				 +"Please click <a href = 'http://access.scsanalytics.com'>here</a> to login to your dashboard."
    	    				 +"</i></p>"
    	    				 
    	            		 +"<p>   </p><p>   </p>"
    	    				 +"<p style='font-size: 15px;font-weight:bold;'>"
    	    				 +"Thanks"
    	    				 +",</p>"
    	    				 +"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
    	    				 +"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
    	    				 +"</body></html>", "UTF-8","html"	
    	            		 );
    	             Multipart multipart = new MimeMultipart();
    	             multipart.addBodyPart(mbp);
    	             message.setContent(multipart);
    				Transport transport = (Transport) session.getTransport("smtps");
    				transport.connect(host, username, password);
    				transport.sendMessage(message, message.getAllRecipients());
    				transport.close();		

    			} catch (MessagingException e) {
    				throw new RuntimeException(e);
    			}
    	    }
    	    
    
    public void sendRequestStatusEmail(String to, AccessModel access) {
    	
Session session = Session.getInstance(getProperties(), null);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			 message.setSubject("Access Request Status");
			 int id = access.getId();
			 String status = access.getStatus();
			 MimeBodyPart mbp = new MimeBodyPart();
             mbp.setText( "<html><head></head><body>"
            		+" <p style='font-size: 15px;font-weight:bold;'>Hello,</p>"
            		 +"<p style='font-size: 15px;font-weight:bold;'>Thank you for submitting access request.</p>"
            		 +"<p style='font-size: 15px;'>"
            		 +"Your request have been processed. </p>"
            		 +"<p style='font-size: 15px;'>Please find the status of your access request below.</p><hr></hr>"
            		 +"<table>"
            		 +"<tbody><tr><td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Request ID</td>"
					 +"<td style='font-size: 14px;color:#428bca;' >"
            		 +id
            		+"</td>"
					+"</tr><tr><td style='font-size: 15px;font-weight:bold;padding-left:5px;padding-right:40px;'>Status</td>"
					+"<td style='font-size: 14px;color:#428bca;' >"
					+status
					+ "</td>"
					+"</tr><tr></tr></tbody></table>"
					+"<hr></hr>"	 
            		 +"<p>   </p><p>   </p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>"
    				 +"Thanks"
    				 +",</p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
    				 +"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
    				 +"</body></html>", "UTF-8","html"	
            		 );
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(mbp);
             message.setContent(multipart);
			Transport transport = (Transport) session.getTransport("smtps");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();		

		} catch (MessagingException e) {
			System.out.println(" ** ValidUnsent Addresses"+e);
	
			throw new RuntimeException(e);
			
		}
		

    }



	public void sendResetPassword(String uto, String token, String appUrl) {
		// TODO Auto-generated method stub
Session session = Session.getInstance(getProperties(), null);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(uto));
			 message.setSubject("Reset Password");
			 MimeBodyPart mbp = new MimeBodyPart();
             mbp.setText( "<html><head></head><body>"
            		+" <p style='font-size: 15px;font-weight:bold;'>Hi,</p>"
            		 +"<p style='font-size: 15px;font-weight:bold;'>To reset your password, click the link below:</p>"              		
            		  + appUrl + "/reset?token=" +token
            		 
            		 +"<p>   </p><p>   </p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>"
    				 +"Thanks"
    				 +",</p>"
    				 +"<p style='font-size: 15px;font-weight:bold;'>Unilever.</p><p>        </p>"
    				 +"<p><i>This is an automated email please do not reply to this message. This message is for the designated recipient only and may contain privileged, proprietary, or otherwise private information.If you have received it in error, please delete. Any other use of the email by you is prohibited.</i></p>"						
    				 +"</body></html>", "UTF-8","html"	
            		 );
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(mbp);
             message.setContent(multipart);
			Transport transport = (Transport) session.getTransport("smtps");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();		

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	} 
}
/*   
     public void sendEmail(String to, AccessModel access) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
                  
            messageHelper.setSubject("Access Request");
            String user = mailContentBuilder.build(access);
            messageHelper.setText(user, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
         
        }
    }
    
    public void sendUserEmail(String to, String appEmail, AccessModel access) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject("Access Request");
            String user = mailContentBuilder.userEmailBuild(appEmail,access);
            messageHelper.setText(user, true);
           // InternetAddress ia = new InternetAddress(to);
            //messageHelper.addRecipients(RecipientType.TO, );
        	
        };
        try {
            mailSender.send(messagePreparator);
            
            
        } catch (MailException e) {
         
        }
    }
    
     public void sendAutoPassword( String to, String password, ApproverModel approver){
    	
    	 MimeMessagePreparator messagePreparator = mimeMessage -> {
             MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
             messageHelper.setTo(to);
             messageHelper.setSubject("Auto Generated Password");
             String user = mailContentBuilder.AutoPassword(password, approver);
             messageHelper.setText(user, true);
         };
         try {
             mailSender.send(messagePreparator);
         } catch (MailException e) {
          
         }
    }
    
     
    public void sendRequestStatusEmail(String to, AccessModel access) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject("Access Request Status");
            String user = mailContentBuilder.requestStatusEmailBuild(access);
            messageHelper.setText(user, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
         
        }
    }
    
}
    */
  
   
