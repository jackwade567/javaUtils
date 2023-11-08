package com.bcbsfl.ccn.cmparticipant.dto;

public class ManagerDto {

	private int id;
	private String managerFirstName;
	private String managerLastName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	@Override
	public String toString() {
		return "ManagerDto [id=" + id + ", managerFirstName=" + managerFirstName + ", managerLastName="
				+ managerLastName + "]";
	}

}
