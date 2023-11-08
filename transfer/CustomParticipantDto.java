package com.bcbsfl.ccn.cmparticipant.dto;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomParticipantDto {

	@JsonProperty("cm_participant_id")
	private BigInteger cmParticipantId;

	@JsonProperty("racf")
	private String racf;

	@JsonProperty("lob")
	private String lob;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("team")
	private String teamName;

	@JsonProperty("role")
	private String roleName;

	@JsonProperty("managerFN")
	private String managerFirstName;

	@JsonProperty("managerLN")
	private String managerLastName;

	@JsonProperty("languages")
	private List<Lang> languages;

	public List<Lang> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Lang> languages) {
		this.languages = languages;
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

	public BigInteger getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(BigInteger cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		return "CustomParticipantDto [cm_participant_id=" + cmParticipantId + ", racf=" + racf + ", lob=" + lob
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", team_name=" + teamName + ", role_name="
				+ roleName + ", manager_FName=" + managerFirstName + ", manager_LName=" + managerLastName
				+ ", languages=" + languages + "]";
	}

}
