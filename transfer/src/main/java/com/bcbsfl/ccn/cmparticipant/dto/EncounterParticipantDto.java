package com.bcbsfl.ccn.cmparticipant.dto;

import java.util.ArrayList;
import java.util.List;

import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncounterParticipantDto {

	@JsonProperty("encounterParticipantId")
	private Long encounterParticipantId;

	@JsonProperty("cipId")
	private String cipId;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("activeStatus")
	private Status activeStatus;

	@JsonProperty("updateByPartcipantId")
	private long updateByPartcipantId;

	@JsonProperty("languagesId")
	private List<Integer> languagesId = new ArrayList<>();

	public String getCipId() {
		return cipId;
	}

	public void setCipId(String cipId) {
		this.cipId = cipId;
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

	public Status getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Status activeStatus) {
		this.activeStatus = activeStatus;
	}

	public List<Integer> getLanguagesId() {
		return languagesId;
	}

	public void setLanguagesId(List<Integer> languagesId) {
		this.languagesId = languagesId;
	}

	public Long getEncounterParticipantId() {
		return encounterParticipantId;
	}

	public void setEncounterParticipantId(Long encounterParticipantId) {
		this.encounterParticipantId = encounterParticipantId;
	}

	public long getUpdateByPartcipantId() {
		return updateByPartcipantId;
	}

	public void setUpdateByPartcipantId(long updateByPartcipantId) {
		this.updateByPartcipantId = updateByPartcipantId;
	}

	@Override
	public String toString() {
		return "EncounterParticipantDto [encounterParticipantId=" + encounterParticipantId + ", cipId=" + cipId
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", activeStatus=" + activeStatus
				+ ", updateByPartcipantId=" + updateByPartcipantId + ", languagesId=" + languagesId + "]";
	}

}
