package com.bcbsfl.ccn.cmparticipant.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantDto {

	@NotBlank(message = "racf cannot be empty")
	@NotNull(message = "racf cannot be null")
	@JsonProperty("racf")
	private String racf;

	@JsonProperty("firstName")
	@NotBlank(message = "firstName cannot be empty")
	@NotNull(message = "firstName cannot be null")
	@NotEmpty(message = "firstName cannot be empty!")
	private String firstName;

	@NotBlank(message = "lastName cannot be empty")
	@NotNull(message = "lastName cannot be null")
	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("teamId")
	@Min(value = 1, message = "team Id should not be null")
	private int teamId;

	@Min(value = 1, message = "roleId should not be null")
	@JsonProperty("roleId")
	private int roleId;

	@Min(value = 1, message = "managerId should not be null")
	@JsonProperty("managerId")
	private int managerId;

//	@Size(min = 1, message = "At least one languagesId required")
	@JsonProperty("languagesId")
	private List<Integer> languagesId = new ArrayList<>();

	@NotNull(message = "lob cannot be null")
	@NotBlank(message = "lob cannot be empty")
	@JsonProperty("lob")
	private String lob;

	@NotBlank(message = "activeStatus cannot be empty")
	@NotNull(message = "activeStatus cannot be null")
	@JsonProperty("activeStatus")
	private String activeStatus;

	@JsonProperty("language")
	private String language;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public ParticipantDto(
			@NotBlank(message = "racf cannot be empty") @NotNull(message = "racf cannot be null") String racf,
			@NotBlank(message = "firstName cannot be empty") @NotNull(message = "firstName cannot be null") @NotEmpty(message = "firstName cannot be empty!") String firstName,
			@NotBlank(message = "lastName cannot be empty") @NotNull(message = "lastName cannot be null") String lastName,
			@Min(value = 1, message = "team Id should not be null") int teamId,
			@Min(value = 1, message = "roleId should not be null") int roleId,
			@Min(value = 1, message = "managerId should not be null") int managerId,
			@Size(min = 1, message = "At least one languagesId required") List<Integer> languagesId,
			@NotNull(message = "lob cannot be null") String lob,
			@NotBlank(message = "activeStatus cannot be empty") @NotNull(message = "activeStatus cannot be null") String activeStatus,
			String language) {
		super();
		this.racf = racf;
		this.firstName = firstName;
		this.lastName = lastName;
		this.teamId = teamId;
		this.roleId = roleId;
		this.managerId = managerId;
		this.languagesId = languagesId;
		this.lob = lob;
		this.activeStatus = activeStatus;
		this.language = language;
	}

	public ParticipantDto() {
		super();
	}

	@Override
	public String toString() {
		return "ParticipantDto [racf=" + racf + ", firstName=" + firstName + ", lastName=" + lastName + ", teamId="
				+ teamId + ", roleId=" + roleId + ", managerId=" + managerId + ", languagesId=" + languagesId + ", lob="
				+ lob + ", activeStatus=" + activeStatus + ", language=" + language + "]";
	}

}