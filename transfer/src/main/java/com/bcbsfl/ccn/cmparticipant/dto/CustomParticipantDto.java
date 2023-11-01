package com.bcbsfl.ccn.cmparticipant.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.bcbsfl.ccn.cmparticipant.entites.Participant.lob_enum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomParticipantDto {

	@JsonProperty("cm_participant_id")
	private BigInteger cm_participant_id;

	@JsonProperty("racf")
	private String racf;

	@JsonProperty("lob")
	private String lob;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("team")
	private String team_name;

	@JsonProperty("role")
	private String role_name;

	@JsonProperty("managerFN")
	private String manager_FName;

	@JsonProperty("managerLN")
	private String manager_LName;

	@JsonProperty("languages")
	private List<TwoFieldDto> languages;

	public List<TwoFieldDto> getLanguages() {
		return languages;
	}

	public void setLanguages(List<TwoFieldDto> languages) {
		this.languages = languages;
	}

	public BigInteger getCm_participant_id() {
		return cm_participant_id;
	}

	public void setCm_participant_id(BigInteger cm_participant_id) {
		this.cm_participant_id = cm_participant_id;
	}

	public String getRacf() {
		return racf;
	}

	public void setRacf(String racf) {
		this.racf = racf;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
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

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getManager_FName() {
		return manager_FName;
	}

	public void setManager_FName(String manager_FName) {
		this.manager_FName = manager_FName;
	}

	public String getManager_LName() {
		return manager_LName;
	}

	public void setManager_LName(String manager_LName) {
		this.manager_LName = manager_LName;
	}

	public CustomParticipantDto() {
		super();
	}

	public CustomParticipantDto(BigInteger cm_participant_id, String racf, String lob, String firstName, String lastName,
			String team_name, String role_name, String manager_FName, String manager_LName, List<TwoFieldDto> languages) {
		super();
		this.cm_participant_id = cm_participant_id;
		this.racf = racf;
		this.lob = lob;
		this.firstName = firstName;
		this.lastName = lastName;
		this.team_name = team_name;
		this.role_name = role_name;
		this.manager_FName = manager_FName;
		this.manager_LName = manager_LName;
		this.languages = languages;
	}

	@Override
	public String toString() {
		return "CustomParticipantDto [cm_participant_id=" + cm_participant_id + ", racf=" + racf + ", lob=" + lob
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", team_name=" + team_name + ", role_name="
				+ role_name + ", manager_FName=" + manager_FName + ", manager_LName=" + manager_LName + ", languages="
				+ languages + "]";
	}

}
