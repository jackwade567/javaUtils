package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "role_id")
	private int role_id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_status")
	private String roleStatus;

	public enum roleStatus {
		Active, Inactive
	}

	@Column(name = "lob")
	private String lob;

	public enum lob {
		Medicare, Commercial
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public Role(int role_id, String roleName, String roleStatus, String lob) {
		super();
		this.role_id = role_id;
		this.roleName = roleName;
		this.roleStatus = roleStatus;
		this.lob = lob;
	}

	public Role() {
		
	}
	

}
