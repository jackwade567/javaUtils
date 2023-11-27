package com.bcbsfl.ccn.realsogi.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "role_id")
	private int roleId;

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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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

	public Role(int roleId, String roleName, String roleStatus, String lob) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleStatus = roleStatus;
		this.lob = lob;
	}

	public Role() {

	}

}
