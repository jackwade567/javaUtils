package com.bcbsfl.ccn.realsogi.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {
	@Id
	@Column(name = "manager_id")
	private int managerId;

	@Column(name = "manager_status")
	private String managerStatus;

	@Column(name = "lob")
	private String lob;

	public enum lob {
		Medicare, Commercial
	}

	@Column(name = "manager_firstname")
	private String managerFirstname;

	@Column(name = "manager_lastname")
	private String managerLastname;

	public Manager(int managerId, String managerStatus, String lob, String managerFirstName, String managerLastName) {
		super();
		this.managerId = managerId;
		this.managerStatus = managerStatus;
		this.lob = lob;
		this.managerFirstname = managerFirstName;
		this.managerLastname = managerLastName;
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

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getManagerFirstname() {
		return managerFirstname;
	}

	public void setManagerFirstname(String managerFirstname) {
		this.managerFirstname = managerFirstname;
	}

	public String getManagerLastname() {
		return managerLastname;
	}

	public void setManagerLastname(String managerLastname) {
		this.managerLastname = managerLastname;
	}

	public Manager() {

	}

}
