package com.bcbsfl.ccn.cmparticipant.dto;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantDetailsDto {

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

	@JsonProperty("adRole")
	private String adRole;

	@JsonProperty("addedByRacf")
	private String addedByRacf;

	@JsonProperty("updatedByRacf")
	private String updatedByRacf;

	@JsonProperty("createdAt")
	private String createdAt;

	@JsonProperty("updatedAt")
	private String updatedAt;

	@JsonProperty("team")
	private String teamName;

	@JsonProperty("role")
	private String roleName;

	@JsonProperty("manager_first_name")
	private String managerFName;

	@JsonProperty("manager_last_name")
	private String managerLName;

	@JsonProperty("languages")
	private List<String> languages;



	public BigInteger getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(BigInteger cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
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

	public String getAdRole() {
		return adRole;
	}

	public void setAdRole(String adRole) {
		this.adRole = adRole;
	}

	public String getAddedByRacf() {
		return addedByRacf;
	}

	public void setAddedByRacf(String addedByRacf) {
		this.addedByRacf = addedByRacf;
	}

	public String getUpdatedByRacf() {
		return updatedByRacf;
	}

	public void setUpdatedByRacf(String updatedByRacf) {
		this.updatedByRacf = updatedByRacf;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
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

	public String getManagerFName() {
		return managerFName;
	}

	public void setManagerFName(String managerFName) {
		this.managerFName = managerFName;
	}

	public String getManagerLName() {
		return managerLName;
	}

	public void setManagerLName(String managerLName) {
		this.managerLName = managerLName;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	@Override
	public String toString() {
		return "ParticipantDetailsDto [cm_participant_id=" + cmParticipantId + ", racf=" + racf + ", lob=" + lob
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", adRole=" + adRole + ", addedByRacf="
				+ addedByRacf + ", updatedByRacf=" + updatedByRacf + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", team_name=" + teamName + ", role_name=" + roleName + ", manager_FName="
				+ managerFName + ", manager_LName=" + managerLName + ", languages=" + languages + "]";
	}

}
