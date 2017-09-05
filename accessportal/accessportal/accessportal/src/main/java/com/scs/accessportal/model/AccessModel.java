package com.scs.accessportal.model;



import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="access_request") 

public class AccessModel {

 @Id
 @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
 
 @Column(name = "id")
 private int id;
 
 @Column(name = "access_type")
 private String accessType;
 
 @Column(name = "user_id")
 private String userId;
 
 @Column(name = "user_email")
 private String userEmail;
 
 @Column(name = "status")
 private String status;
 
 @Column(name = "domain")
 private String domain;
 

public String getDomain() {
	return domain;
}
public void setDomain(String domain) {
	this.domain = domain;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getUserEmail() {
	return userEmail;
}
public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}
@Column(name = "original_approver")
 private String orgApprover;
 
 @Column(name = "change_type")
 private String temporary;
 
 @Column(name = "eff_start_date")
 private String effStartDate;
 
 @Column(name = "eff_end_date")
 private String effEndDate;

 @Column(name = "action")
 private String accessReqAction;
 
 @Column(name = "comments")
 private String comments;

 @Column(name = "datestamp")
 private String datestamp;
 
 
public String getDatestamp() {
	return datestamp;
}
public void setDatestamp(String datestamp) {
	//System.out.println("==================================="+datestamp);
	this.datestamp = datestamp;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getAccessReqAction() {
	return accessReqAction;
}
public void setAccessReqAction(String accessReqAction) {
	this.accessReqAction = accessReqAction;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getAccessType() {
	return accessType;
}
public void setAccessType(String accessType) {
	this.accessType = accessType;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getOrgApprover() {
	return orgApprover;
}
public void setOrgApprover(String orgApprover) {
	this.orgApprover = orgApprover;
}
public String getTemporary() {
	return temporary;
}
public void setTemporary(String temporary) {
	this.temporary = temporary;
}
public String getEffStartDate() {
	return effStartDate;
}
public void setEffStartDate(String effStartDate) {
	this.effStartDate = effStartDate;
}
public String getEffEndDate() {
	return effEndDate;
}
public void setEffEndDate(String effEndDate) {
	this.effEndDate = effEndDate;
}


}


