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
	private TeamDto team;

	@JsonProperty("role")
	private RoleDto role;

	@JsonProperty("manager")
	private ManagerDto manager;

	@JsonProperty("languages")
	private List<Lang> languages;

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

	public List<Lang> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Lang> languages) {
		this.languages = languages;
	}

	public TeamDto getTeam() {
		return team;
	}

	public void setTeam(TeamDto team) {
		this.team = team;
	}

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	public ManagerDto getManager() {
		return manager;
	}

	public void setManager(ManagerDto manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "ParticipantDetailsDto [cmParticipantId=" + cmParticipantId + ", racf=" + racf + ", lob=" + lob
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", adRole=" + adRole + ", addedByRacf="
				+ addedByRacf + ", updatedByRacf=" + updatedByRacf + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", team=" + team + ", role=" + role + ", manager=" + manager + ", languages=" + languages
				+ "]";
	}

}
