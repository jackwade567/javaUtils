package com.bcbsfl.ccn.cmparticipant.dto;

public class ManagerDto {

	private String managerName;
	private String managerStatus;

	public enum managerStatus {
		Active, Inactive
	}

	private String lob;

	public enum lob {
		Medicare, Commercial
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(String managerStatus) {
		this.managerStatus = managerStatus;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

}
