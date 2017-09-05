package com.scs.accessportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "user_role")
public class RoleModel {

		@Id
		@Column(name="user_id")
		private int id;
		
		@Column(name="role_id")
		private String role;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
}
