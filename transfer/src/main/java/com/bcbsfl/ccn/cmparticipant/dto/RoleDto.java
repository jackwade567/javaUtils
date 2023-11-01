package com.bcbsfl.ccn.cmparticipant.dto;

public class RoleDto {
	private Long role_id;
	private String roleName;
	private String roleStatus;
	
	public enum roleStatus {
		Active,Inactive
	}
	private String lob;
	
	public enum lob {
		Medicare,Commercial
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
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
	
}
