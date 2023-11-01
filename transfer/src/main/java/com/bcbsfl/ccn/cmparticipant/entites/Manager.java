package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {
	@Id
	@Column(name = "manager_id")
	private int manager_id;

	@Column(name = "manager_status")
	private String managerStatus;

	@Column(name = "lob")
	private String lob;

	public enum lob {
		Medicare, Commercial
	}

	@Column(name = "manager_firstname")
	private String manager_firstname;

	@Column(name = "manager_lastname")
	private String manager_lastname;

	public Manager(int manager_id, String managerStatus, String lob, String manager_firstname,
			String manager_lastname) {
		super();
		this.manager_id = manager_id;
		this.managerStatus = managerStatus;
		this.lob = lob;
		this.manager_firstname = manager_firstname;
		this.manager_lastname = manager_lastname;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
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

	public String getManager_firstname() {
		return manager_firstname;
	}

	public void setManager_firstname(String manager_firstname) {
		this.manager_firstname = manager_firstname;
	}

	public String getManager_lastname() {
		return manager_lastname;
	}

	public void setManager_lastname(String manager_lastname) {
		this.manager_lastname = manager_lastname;
	}

	public Manager() {

	}

}
