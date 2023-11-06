package com.bcbsfl.ccn.cmparticipant.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_participant_language")
public class EncounterParticipantLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_language_id", nullable = false, unique = true)
	private int encounterParticipantLanguageId;

	@Column(name = "encounter_participant_id")
	private long encounterParticipantId;

	@Column(name = "language_id")
	private int languageId;

	public enum activeStatus {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private activeStatus status = activeStatus.Active;

	@Column(name = "updated_by_cm_participant_id")
	private long cmParticipantId;

	@Column(name = "transaction_id", nullable = false, unique = true)
	private UUID uuid = UUID.randomUUID();

	@Column(name = "system_id")
	private String systemId;

	@Column(name = "created_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime dateTime;

	public activeStatus getStatus() {
		return status;
	}

	public void setStatus(activeStatus status) {
		this.status = status;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getEncounterParticipantLanguageId() {
		return encounterParticipantLanguageId;
	}

	public void setEncounterParticipantLanguageId(int encounterParticipantLanguageId) {
		this.encounterParticipantLanguageId = encounterParticipantLanguageId;
	}

	public long getEncounterParticipantId() {
		return encounterParticipantId;
	}

	public void setEncounterParticipantId(long encounterParticipantId) {
		this.encounterParticipantId = encounterParticipantId;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public long getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(long cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "EncounterParticipantLanguage [encounterParticipantLanguageId=" + encounterParticipantLanguageId
				+ ", encounterParticipantId=" + encounterParticipantId + ", languageId=" + languageId + ", status="
				+ status + ", cmParticipantId=" + cmParticipantId + ", uuid=" + uuid + ", systemId=" + systemId
				+ ", dateTime=" + dateTime + "]";
	}

}
