package com.bcbsfl.ccn.realsogi.entites;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_participant")
public class EncounterParticipant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_id")
	private long encounterParticipantId;

	@Column(name = "cipid", nullable = false, unique = true)
	private String cipId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	public enum Status {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status", nullable = false)
	private Status activeStatus = Status.Active;

	@Column(name = "encounter_participant_party_id")
	private Long encounterParticipantPartyId;

	@Column(name = "created_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createDateTime;

	@Column(name = "last_update_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime updateDateTime;

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

	public long getEncounterParticipantId() {
		return encounterParticipantId;
	}

	public void setEncounterParticipantId(long encounterParticipantId) {
		this.encounterParticipantId = encounterParticipantId;
	}

	public Long getEncounterParticipantPartyId() {
		return encounterParticipantPartyId;
	}

	public void setEncounterParticipantPartyId(Long encounterParticipantPartyId) {
		this.encounterParticipantPartyId = encounterParticipantPartyId;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Override
	public String toString() {
		return "EncounterParticipant [encounterParticipantId=" + encounterParticipantId + ", cipId=" + cipId
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", activeStatus=" + activeStatus
				+ ", encounterParticipantPartyId=" + encounterParticipantPartyId + ", createDateTime=" + createDateTime
				+ ", updateDateTime=" + updateDateTime + "]";
	}

}
