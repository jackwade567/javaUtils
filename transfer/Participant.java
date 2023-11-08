package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cm_participant")
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_participant_id", nullable = false, unique = true)
	private Long cmParticipantId;

	@Column(name = "racf", nullable = false, unique = true)
	private String racf;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "team_id")
	private int teamId;

	@Column(name = "role_id")
	private int roleId;

	@Column(name = "manager_id")
	private int managerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "lob")
	private lob_enum lob;

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private status_enum activeStatus;

	public enum status_enum {
		Active, Inactive

	}

	public enum lob_enum {
		Medicare, Commercial

	}

	public Long getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(Long cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
	}

	public String getRacf() {
		return racf;
	}

	public void setRacf(String racf) {
		this.racf = racf;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public lob_enum getLob() {
		return lob;
	}

	public void setLob(lob_enum lob) {
		this.lob = lob;
	}

	public status_enum getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(status_enum activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "Participant [cm_participant_id=" + cmParticipantId + ", racf=" + racf + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", team_id=" + teamId + ", role_id=" + roleId + ", manager_id=" + managerId
				+ ", lob=" + lob + ", activeStatus=" + activeStatus + "]";
	}

}
