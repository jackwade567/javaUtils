package com.bcbsfl.ccn.cmparticipant.dto;

import java.util.ArrayList;
import java.util.List;

import com.bcbsfl.ccn.cmparticipant.entites.Participant.lob_enum;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.status_enum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantDto {
	@JsonProperty("cm_participant_id")
	private Long cm_participant_id;

	@JsonProperty("racf")
	private String racf;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("teamId")
	private int teamId;

	@JsonProperty("roleId")
	private int roleId;

	@JsonProperty("managerId")
	private int managerId;

	@JsonProperty("languagesId")
	private List<Integer> languagesId = new ArrayList<>();

	@JsonProperty("lob")
	private lob_enum lob;

	@JsonProperty("activeStatus")
	private String activeStatus;

	public Long getCm_participant_id() {
		return cm_participant_id;
	}

	public void setCm_participant_id(Long cm_participant_id) {
		this.cm_participant_id = cm_participant_id;
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

	public List<Integer> getLanguagesId() {
		return languagesId;
	}

	public void setLanguagesId(List<Integer> languagesId) {
		this.languagesId = languagesId;
	}

	public lob_enum getLob() {
		return lob;
	}

	public void setLob(lob_enum lob) {
		this.lob = lob;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "ParticipantDto [cm_participant_id=" + cm_participant_id + ", racf=" + racf + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", teamId=" + teamId + ", roleId=" + roleId + ", managerId=" + managerId
				+ ", languagesId=" + languagesId + ", lob=" + lob + ", activeStatus=" + activeStatus + "]";
	}

}