package com.scs.accessportal.model;
/*import java.util.Set;*/

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/*import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;*/
import javax.persistence.Table;

@Entity
@Table(name="access_type") 

public class AccessTypeModel {
	 @Id
	 @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	 
	 @Column(name = "id")
	 private int id;
	 
	 @Column(name = "type")
	 private String type;
	 
	 @Column(name = "approver_id")
	 private Integer approverId;
	 
	/* private ApproverModel approver;
	 
	   @ManyToOne
	    @JoinColumn(name = "id")
	    public ApproverModel getApprover() {
	        return approver;
	    }*/
	
	 public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}
	
	
	public int getId() {
		//System.out.println(id);
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
