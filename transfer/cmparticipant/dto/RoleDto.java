package com.bcbsfl.ccn.cmparticipant.dto;

public class RoleDto {
	private Long roleId;
	private String roleName;
	private String roleStatus;
	
	public enum roleStatus {
		Active,Inactive
	}
	private String lob;
	
	public enum lob {
		Medicare,Commercial
	}



	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
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
	
}
